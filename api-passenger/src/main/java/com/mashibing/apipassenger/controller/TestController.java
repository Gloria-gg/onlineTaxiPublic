package com.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
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
    public String test() {
        return "this is from test api-passenger controller!";
    }

    /**
     * 需要有token
     *
     * @return
     */
    @GetMapping("/authTest")
    public ResponseResult authTest() {
        return ResponseResult.success("auth test");
    }

    /**
     * 没有token也能正常返回
     *
     * @return
     */
    @GetMapping("/noauthTest")
    public ResponseResult noauthTest() {
        return ResponseResult.success("noauth test");
    }
}
