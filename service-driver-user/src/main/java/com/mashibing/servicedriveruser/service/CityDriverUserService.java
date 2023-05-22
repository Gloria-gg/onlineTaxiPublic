package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description: 和视图v_city_driver_user_work_status对应的service类
 * @Date: Created in 4:43 PM 5/22/23
 */
@Service
public class CityDriverUserService {
    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    /**
     * 根据城市码判断当前城市是否有司机
     *
     * @param cityCode
     * @return
     */
    public ResponseResult<Boolean> isAvailableDriver(String cityCode) {
        int count = driverUserWorkStatusMapper.selectDriverCountByCityCode(cityCode);
        if (count > 0) {
            return ResponseResult.success(true);
        }

        return ResponseResult.success(false);
    }
}
