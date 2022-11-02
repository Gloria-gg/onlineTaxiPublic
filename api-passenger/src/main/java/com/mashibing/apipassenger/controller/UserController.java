package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Gloria
 * @Description: 获取用户信息
 * @Date: Created in 4:48 PM 11/1/22
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseResult getUser(HttpServletRequest request) {
        //从request中获取accessToken,使用service进行用户信息获取
        return userService.getUserByAccessToken(request.getHeader("Authorization"));
    }


}
