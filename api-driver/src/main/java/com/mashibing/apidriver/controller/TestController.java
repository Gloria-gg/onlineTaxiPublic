package com.mashibing.apidriver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:44 AM 5/6/23
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "this is from api-driver test controller!";
    }
}
