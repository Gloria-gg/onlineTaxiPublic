package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.request.VerificationCodeDTO;
import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 接收手机号，进行验证，返回验证码controller
 * 该controller对应的是api-passenger的所有接口，
 * 相当于之前swagger里面所需要的rest接口
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
    public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        return verificationCodeService.generateCode(passengerPhone);
    }

    /**
     * 用户输入手机号以及接收到的验证码，
     * 输入进行验证，是否是之前发送的验证码
     * 以及是否在规定的验证时间之内
     *
     * @param verificationCodeDTO
     * @return
     */
    @PostMapping("/verification-code-check")
    public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        String passengerPhone = verificationCodeDTO.getPassengerPhone();
        String verificationCode = verificationCodeDTO.getVerificationCode();

        System.out.println("手机号：" + passengerPhone + ", 验证码：" + verificationCode);
        return verificationCodeService.checkVerificationCode(passengerPhone,verificationCode);
    }
}
