package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:21 PM 5/10/23
 */
@RestController
@Slf4j
public class VerificationCodeController {
    @Autowired
    private VerificationCodeService verificationCodeService;

    /**
     * 司机获取验证码
     *
     * @param verificationCodeDTO
     * @return
     */
    @GetMapping("/verification-code")
    public ResponseResult getVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        log.info("api-driver controller获取到的司机号码是：" + verificationCodeDTO.getDriverPhone());
        return verificationCodeService.checkAndSendVerificationCode(verificationCodeDTO.getDriverPhone());
    }
}
