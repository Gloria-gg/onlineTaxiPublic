package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ResponseResult test() {
        DriverUser driverUser = driverUserMapper.selectById(1);
        log.info("从数据库中获取到的司机信息是：" + driverUser.getDriverName());

        return ResponseResult.success();
    }
}
