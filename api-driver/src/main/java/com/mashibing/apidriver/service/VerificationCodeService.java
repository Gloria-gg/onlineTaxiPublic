package com.mashibing.apidriver.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:25 PM 5/10/23
 */
@Service
public class VerificationCodeService {

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用service-driver-user，判断该手机号司机是否存在

        //获取验证码

        //调用第三方发送验证码

        //存入redis

        return ResponseResult.success("");
    }
}
