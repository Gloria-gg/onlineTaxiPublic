package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:43 AM 9/30/22
 */
@Service
public class VerificationCodeService {

    /**
     * 存入redis的验证码的key的前缀
     */
    private String verificationCodePrefix = "passenger_verification_code_";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    /**
     * 该方法获取乘客手机号，生成一个六位验证码
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult generateCode(String passengerPhone) {
        //调用验证码服务，获取验证码
        ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationCodeClient.getNumberCode(6);
        int numberCode = numberCodeResponse.getData().getNumberCode();

        //存入redis
        //key：前缀+手机号   value：验证码   ttl：过期时间，在redis设置中统一设置
        String key = verificationCodePrefix + passengerPhone;
        //存入redis中
        stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

        //通过短信服务商，将对应验证码发送到手机上。阿里短信服务、腾讯短信通、华信、容联等。

        //返回成功或者失败码
        return ResponseResult.success();
    }

    /**
     * 接收乘客手机号以及输入的验证码
     * 对输入的验证码进行验证码校验以及是否超过验证时间校验
     * 返回结果，以及，若成功返回token
     *
     * @param passengerPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkVerificationCode(String passengerPhone, String verificationCode) {
        //根据手机号去redis中获取对应验证码
        System.out.println("根据手机号去redis中获取对应验证码");

        //redis验证码与输入验证码进行校验
        System.out.println("redis验证码与输入验证码进行校验");

        //若原来有用户，那么返回登录token；若没有用户，那么直接插入一条新数据
        System.out.println("若原来有用户，那么返回登录token；若没有用户，那么直接插入一条新数据");

        //颁发token令牌
        System.out.println("颁发token令牌");

        //设置返回值
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken("token value");

        return ResponseResult.success(tokenResponse);
    }
}
