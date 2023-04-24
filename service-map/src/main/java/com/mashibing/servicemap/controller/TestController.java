package com.mashibing.servicemap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:57 AM 4/24/23
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test controller from service map module!";
    }
}
