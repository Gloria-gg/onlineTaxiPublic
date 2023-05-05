package com.mashibing.servicedriveruser.controller;

import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 3:58 PM 5/4/23
 */
@RestController
public class TestController {
    @Autowired
    private DriverUserService driverUserService;

    @GetMapping("/test")
    public String test() {

        driverUserService.test();
        return "this is from test service-driver-user controller!";
    }
}
