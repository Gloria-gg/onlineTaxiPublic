package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:13 AM 5/6/23
 */
@Service
public class DriverUserService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult updateDriverUser(DriverUser driverUser) {
        return serviceDriverUserClient.updateDriverUser(driverUser);
    }
}
