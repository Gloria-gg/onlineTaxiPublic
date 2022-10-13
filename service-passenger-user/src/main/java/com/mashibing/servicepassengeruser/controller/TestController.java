package com.mashibing.servicepassengeruser.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 2:29 PM 10/13/22
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "this is from test service-passenger-user controller!";
    }
}
