package com.mashibing.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.COMM_FAILURE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:03 AM 5/5/23
 */
@Service
@Slf4j
public class DriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;

    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * 添加司机信息
     *
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);

        //初始化司机工作状态表
        DriverUserWorkStatus driverUserWorkStatus = new DriverUserWorkStatus();
        driverUserWorkStatus.setDriverId(driverUser.getId());
        driverUserWorkStatus.setWorkStatus(DriverCarConstants.DRIVER_WORK_STATUS_STOP);
        driverUserWorkStatus.setGmtCreate(now);
        driverUserWorkStatus.setGmtModified(now);
        driverUserWorkStatusMapper.insert(driverUserWorkStatus);

        return ResponseResult.success("");
    }

    /**
     * 修改司机信息
     *
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);

        return ResponseResult.success("");
    }

    /**
     * 通过司机手机号码获取司机信息
     *
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverUser> getDriverUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUserList = driverUserMapper.selectByMap(map);

        //司机数只能严格等于1，否则只能报错
        if (driverUserList.isEmpty() || driverUserList.size() != 1) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_INFO_ERROR.getCode(),
                    CommonStatusEnum.DRIVER_INFO_ERROR.getMessage());
        }

        DriverUser driverUser = driverUserList.get(0);
        return ResponseResult.success(driverUser);
    }

    /**
     * 通过车辆ID获取可以出车的司机信息
     *
     * @param carId
     * @return
     */
    public ResponseResult<OrderDriverResponse> getDriverByCarId(Long carId) {
        //根据车辆ID查询绑定的司机信息
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", carId);
        queryWrapper.eq("binding_state", DriverCarConstants.DRIVER_CAR_BIND);
        DriverCarBindingRelationship driverCarBindingRelationship = driverCarBindingRelationshipMapper.selectOne(queryWrapper);

        if (null == driverCarBindingRelationship) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),
                    CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
        }

        //根据查询出来的绑定信息中的司机信息进行司机信息状态查询
        QueryWrapper<DriverUserWorkStatus> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper1.eq("work_status", DriverCarConstants.DRIVER_WORK_STATUS_START);
        DriverUserWorkStatus driverUserWorkStatus = driverUserWorkStatusMapper.selectOne(queryWrapper1);

        if (null == driverUserWorkStatus) {
            return ResponseResult.fail(CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getCode(),
                    CommonStatusEnum.AVAILABLE_DRIVER_EMPTY.getMessage());
        }

        QueryWrapper<DriverUser> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("id", driverUserWorkStatus.getDriverId());
        DriverUser driverUser = driverUserMapper.selectOne(queryWrapper2);

        OrderDriverResponse orderDriverResponse = new OrderDriverResponse();
        orderDriverResponse.setCarId(carId);
        orderDriverResponse.setDriverId(driverUser.getId());
        orderDriverResponse.setDriverPhone(driverUser.getDriverPhone());

        return ResponseResult.success(orderDriverResponse);
    }
}
