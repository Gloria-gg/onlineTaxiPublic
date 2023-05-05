package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:15 AM 5/5/23
 */
@RestController
public class DriverUserController {
    @Autowired
    private DriverUserService driverUserService;

    @PostMapping("/user")
    public ResponseResult insertUsers(@RequestBody DriverUser driverUser) {

        return driverUserService.addDriverUser(driverUser);
    }
}
