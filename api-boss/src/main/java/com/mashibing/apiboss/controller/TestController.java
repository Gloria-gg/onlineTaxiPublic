package com.mashibing.apiboss.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:41 AM 5/5/23
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "this is from api-boss test controller!";
    }
}
