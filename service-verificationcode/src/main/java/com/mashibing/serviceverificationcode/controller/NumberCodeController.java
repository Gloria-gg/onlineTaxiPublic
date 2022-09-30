package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
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
    public ResponseResult numberCode(@PathVariable("size") int size) {

        System.out.println("the size is : " + size);

        //生成size位验证码
        int numberCode = (int) ((Math.random() * 9 + 1) * Math.pow(10, size - 1));

        NumberCodeResponse numberCodeResponse = new NumberCodeResponse();
        numberCodeResponse.setNumberCode(numberCode);


        return ResponseResult.success(numberCodeResponse);

    }

}
