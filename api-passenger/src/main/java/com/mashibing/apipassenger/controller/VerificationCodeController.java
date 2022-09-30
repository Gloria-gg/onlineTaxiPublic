package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.VerificationCodeDTO;
import com.mashibing.apipassenger.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 接收手机号，进行验证，返回验证码controller
 * @Date: Created in 9:21 AM 9/30/22
 */
@RestController
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 输入乘客手机号码，进行验证，
     * 然后调用产生验证码服务
     * 返回是否成功调用验证码生成服务的成功码
     *
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public String verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        System.out.println("接收到的passengerPhone是：" + passengerPhone);
        return verificationCodeService.generateCode(passengerPhone);
    }
}
