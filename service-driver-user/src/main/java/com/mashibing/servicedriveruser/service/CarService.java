package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.servicedriveruser.client.ServiceMapClient;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:39 AM 5/6/23
 */
@Service
public class CarService {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 添加car信息进入数据库
     *
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);
        carMapper.insert(car);

        //获取车辆tid
        ResponseResult<TerminalResponse> responseResponseResult = serviceMapClient.getTid(car.getVehicleNo(), car.getId() + "");
        String tid = responseResponseResult.getData().getTid();
        car.setTid(tid);

        //获取车辆trid
        ResponseResult<TrackResponse> responseResult = serviceMapClient.getTrid(tid);
        String trid = responseResult.getData().getTrid();
        String trname = responseResult.getData().getTrname();
        car.setTrid(trid);
        car.setTrname(trname);

        //更新车辆
        carMapper.updateById(car);


        return ResponseResult.success("");
    }

    /**
     * 通过车辆carId获取车辆信息
     *
     * @param carId
     * @return
     */
    public ResponseResult<Car> getCarById(Long carId) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", carId);
        List<Car> cars = carMapper.selectByMap(map);
        if (cars.size() != 1) {
            return ResponseResult.fail(CommonStatusEnum.CAR_INFO_ERROR.getCode(),
                    CommonStatusEnum.CAR_INFO_ERROR.getMessage());
        }
        return ResponseResult.success(cars.get(0));
    }
}
