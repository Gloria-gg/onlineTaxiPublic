package com.mashibing.serviceverificationcode.controller;

import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 3:52 PM 9/30/22
 */
@RestController
public class NumberCodeController {

    @GetMapping("/numberCode/{size}")
    public String numberCode(@PathVariable("size") int size) {

        System.out.println("the size is : " + size);

        JSONObject result = new JSONObject();
        result.put("code", 1);
        result.put("message", "success");

        JSONObject data = new JSONObject();
        data.put("numberCode", 123456);

        result.put("data", data);

        return result.toString();

    }
}
