package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:14 PM 11/1/22
 */
@Service
@Slf4j
public class UserService {

    /**
     * 根据传进来的accessToken获取用户信息并返回结果
     *
     * @param accessToken
     * @return
     */
    public ResponseResult getUserByAccessToken(String accessToken) {
        log.info("access token:" + accessToken);

        //解析accessToken，获取手机号
        TokenResult tokenResult = JwtUtils.checkToken(accessToken);
        String phone = tokenResult.getPhone();

        log.info("解析accessToken获取到的手机号是：" + phone);

        //根据手机号，获取用户信息


        PassengerUser passengerUser = new PassengerUser();
        passengerUser.setPassengerName("gg");
        passengerUser.setProfilePhoto("photo");

        return ResponseResult.success(passengerUser);
    }
}
