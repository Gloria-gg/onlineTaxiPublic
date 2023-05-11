package com.mashibing.apidriver.remote;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author: Gloria
 * @Description: 远程调用service-verificationcode模块进行验证码生成服务
 * @Date: Created in 5:26 PM 5/10/23
 */@FeignClient("service-verificationcode")
public interface ServiceVerificationCodeClient {
}
