package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 接收用户电话号码，进行用户插入操作controller
 * @Date: Created in 5:08 PM 10/13/22
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过用户手机号码进行查询是否是已经注册用户，若不是，那么进行用户插入
     *
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping(value = "/user")
    public ResponseResult logOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        return userService.logOrRegister(verificationCodeDTO.getPassengerPhone());
    }


    /**
     * 通过手机号查询用户信息并返回
     *
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/user")
    public ResponseResult getUserByPhone(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        return userService.getUserByPhone(verificationCodeDTO.getPassengerPhone());
    }
}
