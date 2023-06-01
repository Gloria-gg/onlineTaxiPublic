package com.mashibing.serviceorder.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.OrderConstants;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.internalcommon.request.PriceRuleIsNewRequest;
import com.mashibing.internalcommon.request.PushRequest;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import com.mashibing.serviceorder.config.RedisConfig;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.remote.ServiceDriverUserClient;
import com.mashibing.serviceorder.remote.ServiceMapClient;
import com.mashibing.serviceorder.remote.ServicePriceClient;
import com.mashibing.serviceorder.remote.ServiceSsePushClient;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gloria
 * @since 2023-05-18
 */
@Service
@Slf4j
public class OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private ServicePriceClient servicePriceClient;

    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 插入用户新增订单
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult addOrder(OrderRequest orderRequest) {
        //判断当前城市是否有司机提供
        ResponseResult<Boolean> driverAvailable = serviceDriverUserClient.isDriverAvailable(orderRequest.getAddress());
        if (!driverAvailable.getData()) {
            return ResponseResult.fail(CommonStatusEnum.CITY_DRIVER_EMPTY.getCode(),
                    CommonStatusEnum.CITY_DRIVER_EMPTY.getMessage());
        }

        //判断计价规则是否是当前最新计价规则
        PriceRuleIsNewRequest priceRuleIsNewRequest = new PriceRuleIsNewRequest();
        priceRuleIsNewRequest.setFareType(orderRequest.getFareType());
        priceRuleIsNewRequest.setFareVersion(orderRequest.getFareVersion());
        if (!(servicePriceClient.isLatestFareVersion(priceRuleIsNewRequest).getData())) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_CHANGED.getCode(),
                    CommonStatusEnum.PRICE_RULE_CHANGED.getMessage());
        }

        //判断当前城市是否有计价规则
        if (!ifPriceRuleExists(orderRequest)) {
            return ResponseResult.fail(CommonStatusEnum.CITY_NOT_PROVIDE_SERVICE.getCode(),
                    CommonStatusEnum.CITY_NOT_PROVIDE_SERVICE.getMessage());
        }

        //若系统中已经存在还未完成的订单，那么就不能进行新订单的生成
        if (isPassengerOrderGoingOn(orderRequest.getPassengerId()) > 0) {
            return ResponseResult.fail(CommonStatusEnum.ORDER_GOING_ON.getCode(),
                    CommonStatusEnum.ORDER_GOING_ON.getMessage());
        }
        //需要判断下单的用户是否是黑名单用户,是，则进行错误值返回
        if (isBlackDevice(orderRequest.getDeviceCode())) {
            return ResponseResult.fail(CommonStatusEnum.DEVICE_IS_BLACK.getCode(),
                    CommonStatusEnum.DEVICE_IS_BLACK.getMessage());
        }

        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderRequest, orderInfo);
        orderInfo.setOrderStatus(OrderConstants.ORDER_START);

        LocalDateTime now = LocalDateTime.now();
        orderInfo.setGmtCreate(now);
        orderInfo.setGmtModified(now);
        orderInfo.setOrderTime(LocalDateTime.now());
        orderInfo.setDepartTime(LocalDateTime.now());

        orderInfoMapper.insert(orderInfo);

        //进行实时订单派送,
        for (int i = 0; i < 2; i++) {
            int result = aroundSearchRealTime(orderInfo);

            if (result == 1) {
                break;
            }

            //若没有找到司机，那么停止20秒，进行下一轮搜索（测试，时间2毫秒）
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return ResponseResult.success("");
    }

    /**
     * 实时订单派送,2km,4km,6km这种范围进行车辆查询
     */
    public int aroundSearchRealTime(OrderInfo orderInfo) {
        log.info("调用2、4、6搜索周围车辆！");
        int result = 0;
        //2km
        String depLatitude = orderInfo.getDepLatitude();
        String depLongitude = orderInfo.getDepLongitude();
        String center = depLatitude + "," + depLongitude;


        List<Integer> radiusList = new ArrayList<>();
        radiusList.add(2000);
        radiusList.add(4000);
        radiusList.add(5000);

        List<TerminalResponse> listResponseResult = null;
        radius:
        for (Integer radius : radiusList) {
            listResponseResult = serviceMapClient.aroundSearch(center, radius).getData();
            //进行对象解析
            if (listResponseResult.size() > 0) {
                //对于list中每个对象进行解析
                for (TerminalResponse terminalResponse : listResponseResult) {
                    Long carId = terminalResponse.getCarId();
                    //远程调用service-driver-user查询否有司机可以派送订单
                    ResponseResult<OrderDriverResponse> driverByCarId = serviceDriverUserClient.getDriverByCarId(carId);
                    if (driverByCarId.getCode() == CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode()
                            || driverByCarId.getCode() == CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode()) {
                        continue radius;
                    }
                    log.info("找到了可以出车的司机ID：" + driverByCarId.getData().getDriverId());

                    OrderDriverResponse data = driverByCarId.getData();
                    Long driverId = data.getDriverId();

                    //进行分布式锁设置
                    String lockKey = (driverId + "").intern();
                    RLock lock = redissonClient.getLock(lockKey);
                    lock.lock();

                    //若司机存在正在进行的订单，那么就不能进行订单派送
                    if (isDriverOrderGoingOn(driverId) > 0) {
                        lock.unlock();
                        log.info("可以出车的司机：" + data.getDriverId() + " 正在接送乘客中，所以无法进行新订单的派送！");
                        continue radius;
                    }

                    //存在可以派单的司机，则系统直接进行派单，开始补齐orderInfo中缺失的部分
                    orderInfo.setCarId(carId);
                    orderInfo.setDriverId(driverId);
                    orderInfo.setDriverPhone(data.getDriverPhone());
                    orderInfo.setReceiveOrderCarLatitude(terminalResponse.getLatitude());
                    orderInfo.setReceiveOrderCarLongitude(terminalResponse.getLongitude());
                    orderInfo.setReceiveOrderTime(LocalDateTime.now());
                    orderInfo.setLicenseId(data.getLicenseId());
                    orderInfo.setVehicleNo(data.getVehicleNo());
                    orderInfo.setOrderStatus(OrderConstants.DRIVER_RECEIVE_ORDER);
                    orderInfo.setGmtModified(LocalDateTime.now());

                    orderInfoMapper.updateById(orderInfo);

                    //通知司机端
                    JSONObject driverContent = new JSONObject();
                    driverContent.put("passengerId", orderInfo.getPassengerId());
                    driverContent.put("passengerPhone", orderInfo.getPassengerPhone());
                    driverContent.put("departure", orderInfo.getDeparture());
                    driverContent.put("depLongitude", orderInfo.getDepLongitude());
                    driverContent.put("depLatitude", orderInfo.getDepLatitude());
                    driverContent.put("destination", orderInfo.getDestination());
                    driverContent.put("destLongitude", orderInfo.getDestLongitude());
                    driverContent.put("destLatitude", orderInfo.getDestLatitude());

                    PushRequest pushRequest = new PushRequest();
                    pushRequest.setUserId(driverId);
                    pushRequest.setIdentity(IdentityConstants.DRIVER_IDENTITY);
                    pushRequest.setContent(driverContent.toString());
                    serviceSsePushClient.push(pushRequest);


                    //通知乘客端
                    JSONObject passengerContent = new JSONObject();
                    passengerContent.put("driverId", driverId);
                    passengerContent.put("driverPhone", orderInfo.getDriverPhone());
                    passengerContent.put("vehicleNo", orderInfo.getVehicleNo());
                    passengerContent.put("receiveOrderCarLongitude", orderInfo.getReceiveOrderCarLongitude());
                    passengerContent.put("receiveOrderCarLatitude", orderInfo.getReceiveOrderCarLatitude());

                    Car car = serviceDriverUserClient.getCarById(orderInfo.getCarId()).getData();
                    passengerContent.put("brand", car.getBrand());
                    passengerContent.put("model", car.getModel());
                    passengerContent.put("vehicleColor", car.getVehicleColor());

                    PushRequest pushRequest2 = new PushRequest();
                    pushRequest2.setUserId(orderInfo.getPassengerId());
                    pushRequest2.setIdentity(IdentityConstants.PASSENGER_IDENTITY);
                    pushRequest2.setContent(passengerContent.toString());
                    serviceSsePushClient.push(pushRequest2);

                    result = 1;

                    //释放锁
                    lock.unlock();

                    break radius;
                }
            }
        }
        //若没有成功进行订单派送，那么之前创建的订单状态要设置为：9 （订单取消）
        if (listResponseResult.size() == 0
                || listResponseResult == null
                || orderInfo.getDriverId() == null) {

            orderInfo.setOrderStatus(OrderConstants.CANCEL_ORDER);
            orderInfoMapper.updateById(orderInfo);
            log.info("没有车辆可以派发！");
        }

        return result;
    }

    /**
     * 判断乘客是否有正在进行中的订单
     *
     * @param passengerId
     * @return
     */
    public int isPassengerOrderGoingOn(Long passengerId) {
        //乘客还有未完成的订单则不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("passenger_id", passengerId);
        queryWrapper.and(
                wrapper -> wrapper.eq("order_status", OrderConstants.ORDER_START)
                        .or().eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                        .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                        .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                        .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
                        .or().eq("order_status", OrderConstants.PASSENGER_GET_OFF)
                        .or().eq("order_status", OrderConstants.TO_START_PAY)
        );

        Integer validOrderNum = orderInfoMapper.selectCount(queryWrapper);

        return validOrderNum;
    }

    /**
     * 判断司机是否有正在进行的订单
     *
     * @param driverId
     * @return
     */
    public int isDriverOrderGoingOn(Long driverId) {
        //乘客还有未完成的订单则不允许下单
        QueryWrapper<OrderInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverId);
        queryWrapper.and(
                wrapper -> wrapper.eq("order_status", OrderConstants.DRIVER_RECEIVE_ORDER)
                        .or().eq("order_status", OrderConstants.DRIVER_TO_PICK_UP_PASSENGER)
                        .or().eq("order_status", OrderConstants.DRIVER_ARRIVED_DEPARTURE)
                        .or().eq("order_status", OrderConstants.PICK_UP_PASSENGER)
        );

        Integer validOrderNum = orderInfoMapper.selectCount(queryWrapper);

        return validOrderNum;
    }


    /**
     * 判断是否是黑名单用户，若不是，那么进行赋值，否则直接返回true
     * 每个用户使用的设备都有唯一设备标识码，若是使用次数超过2次，说明一台设备多次进行下单，列为黑名单
     *
     * @param deviceCode
     * @return
     */
    public Boolean isBlackDevice(String deviceCode) {
        String key = RedisPrefixUtils.blackDeviceCodePrefix + deviceCode;
        //存在key值，并且出现次数超过2，那么说明就是黑名单车辆
        if (stringRedisTemplate.hasKey(key)) {
            if (Integer.parseInt(stringRedisTemplate.opsForValue().get(key)) > 1) {
                return true;
            } else {
                stringRedisTemplate.opsForValue().increment(key);
            }
        } else {
            //不存在key值，那么进行key值初始化设置
            stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 1L, TimeUnit.HOURS);
        }
        return false;
    }

    /**
     * 判断计价规则是否存在
     *
     * @param orderRequest
     * @return
     */
    public Boolean ifPriceRuleExists(OrderRequest orderRequest) {
        String cityCode = orderRequest.getAddress();
        String vehicleType = orderRequest.getVehicleType();
        PriceRule priceRule = new PriceRule();
        priceRule.setCityCode(cityCode);
        priceRule.setVehicleType(vehicleType);

        return servicePriceClient.ifExists(priceRule).getData();
    }

    /**
     * 司机去接乘客，进行订单状态修改
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult toPickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();
        LocalDateTime toPickUpPassengerTime = orderRequest.getToPickUpPassengerTime();
        String toPickUpPassengerLongitude = orderRequest.getToPickUpPassengerLongitude();
        String toPickUpPassengerLatitude = orderRequest.getToPickUpPassengerLatitude();
        String toPickUpPassengerAddress = orderRequest.getToPickUpPassengerAddress();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setToPickUpPassengerTime(toPickUpPassengerTime);
        orderInfo.setToPickUpPassengerLongitude(toPickUpPassengerLongitude);
        orderInfo.setToPickUpPassengerLatitude(toPickUpPassengerLatitude);
        orderInfo.setToPickUpPassengerAddress(toPickUpPassengerAddress);
        orderInfo.setOrderStatus(OrderConstants.DRIVER_TO_PICK_UP_PASSENGER);
        orderInfo.setGmtModified(LocalDateTime.now());

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();
    }

    /**
     * 修改司机到达乘客出发点地状态及对应订单参数补齐
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult arrivedDeparture(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setOrderStatus(OrderConstants.DRIVER_ARRIVED_DEPARTURE);
        orderInfo.setDriverArrivedDepartureTime(LocalDateTime.now());
        orderInfo.setGmtModified(LocalDateTime.now());

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();
    }

    /**
     * 司机接到乘客
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult pickUpPassenger(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setOrderStatus(OrderConstants.PICK_UP_PASSENGER);
        orderInfo.setPickUpPassengerLatitude(orderRequest.getPickUpPassengerLatitude());
        orderInfo.setPickUpPassengerLongitude(orderRequest.getPickUpPassengerLongitude());
        orderInfo.setPickUpPassengerTime(LocalDateTime.now());
        orderInfo.setGmtModified(LocalDateTime.now());

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();
    }

    /**
     * 乘客到达目的地，行程结束
     *
     * @param orderRequest
     * @return
     */
    public ResponseResult passengerGetOff(OrderRequest orderRequest) {
        Long orderId = orderRequest.getOrderId();

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", orderId);
        OrderInfo orderInfo = orderInfoMapper.selectOne(queryWrapper);

        orderInfo.setOrderStatus(OrderConstants.PASSENGER_GET_OFF);
        orderInfo.setGmtModified(LocalDateTime.now());
        orderInfo.setPassengerGetoffTime(LocalDateTime.now());
        orderInfo.setPassengerGetoffLongitude(orderRequest.getPassengerGetoffLongitude());
        orderInfo.setPassengerGetoffLatitude(orderRequest.getPassengerGetoffLatitude());
        //更新行驶行程数以及行驶时间，远程调用service-map
        String tid = serviceDriverUserClient.getCarById(orderInfo.getCarId()).getData().getTid();
        TrSearchResponse trSearchResponse = serviceMapClient.trSearch(tid,
                orderInfo.getPickUpPassengerTime().toInstant(ZoneOffset.of("+8")).toEpochMilli(),
                LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli()).getData();

        orderInfo.setDriveTime(trSearchResponse.getDriveTime());
        orderInfo.setDriveMile(trSearchResponse.getDriveMile());

        orderInfoMapper.updateById(orderInfo);

        return ResponseResult.success();

    }

}
