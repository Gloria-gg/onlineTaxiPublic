package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

        return ResponseResult.success("");
    }
}
