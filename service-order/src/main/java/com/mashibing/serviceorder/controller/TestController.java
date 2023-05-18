package com.mashibing.serviceorder.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:23 PM 5/18/23
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "test controller from service order!";
    }
}
