package com.mashibing.apipassenger.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:25 AM 9/29/22
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){
        return "this is from test api-passenger controller!";
    }
}
