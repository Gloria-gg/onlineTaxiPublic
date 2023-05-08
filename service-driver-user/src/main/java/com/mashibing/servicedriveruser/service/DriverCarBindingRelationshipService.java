package com.mashibing.servicedriveruser.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 2:51 PM 5/6/23
 */
@Service
@Slf4j
public class DriverCarBindingRelationshipService {
    @Autowired
    private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

    /**
     * 对输入的司机和车辆进行绑定
     *
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult bindingDriverCar(DriverCarBindingRelationship driverCarBindingRelationship) {
        //判断数据库中是否存在绑定关系，若存在则进行报错，不存在才能进行插入
        QueryWrapper<DriverCarBindingRelationship> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("binding_state", DriverCarConstants.DRIVER_CAR_BIND);
        Integer count = driverCarBindingRelationshipMapper.selectCount(queryWrapper);

        //车辆或者司机，任何一方被绑定了，都不可以再进行绑定
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("driver_id", driverCarBindingRelationship.getDriverId());
        queryWrapper.eq("binding_state", DriverCarConstants.DRIVER_CAR_BIND);
        Integer count2 = driverCarBindingRelationshipMapper.selectCount(queryWrapper);

        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("car_id", driverCarBindingRelationship.getCarId());
        queryWrapper.eq("binding_state", DriverCarConstants.DRIVER_CAR_BIND);
        Integer count3 = driverCarBindingRelationshipMapper.selectCount(queryWrapper);

        if (count2 > 0 || count3 > 0 || count > 0) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getCode(),
                    CommonStatusEnum.DRIVER_CAR_BIND_EXISTS.getMessage());
        }

        //设置对象参数
        LocalDateTime now = LocalDateTime.now();
        driverCarBindingRelationship.setBindingTime(now);
        driverCarBindingRelationship.setBindingState(DriverCarConstants.DRIVER_CAR_BIND);

        driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);

        return ResponseResult.success("");
    }

    /**
     * 对司机和车辆进行解绑
     *
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult unbindingDriverCar(DriverCarBindingRelationship driverCarBindingRelationship) {
        //判断输入的司机以及车辆是否在数据库中存在绑定关系
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverCarBindingRelationship.getDriverId());
        map.put("car_id", driverCarBindingRelationship.getCarId());
        map.put("binding_state", DriverCarConstants.DRIVER_CAR_BIND);

        List<DriverCarBindingRelationship> relationshipList = driverCarBindingRelationshipMapper.selectByMap(map);
        //若查出来不存在绑定关系，则报错
        if (relationshipList.isEmpty()) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),
                    CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
        }

        //存在绑定关系，那么进行解绑
        if (relationshipList.size() == 1) {
            DriverCarBindingRelationship driverCarBindingRelationship1 = relationshipList.get(0);
            driverCarBindingRelationship1.setBindingState(DriverCarConstants.DRIVER_CAR_UNBIND);
            driverCarBindingRelationship1.setUnBindingTime(LocalDateTime.now());
            driverCarBindingRelationshipMapper.updateById(driverCarBindingRelationship1);

            return ResponseResult.success("");
        }

        return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_ERROR.getCode(),
                CommonStatusEnum.DRIVER_CAR_BIND_ERROR.getMessage());
    }
}
