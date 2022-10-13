package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:28 PM 10/13/22
 */
@Service
public class UserService {

    /**
     * 通过用户手机号码进行已经登陆或者注册判断
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult logOrRegister(String passengerPhone) {
        System.out.println("开始进行UserService服务调用！");
        //根据手机号判断用户信息


        //判断用户信息是否存在

        //如果不存在插入用户信息

        return ResponseResult.success();
    }
}
