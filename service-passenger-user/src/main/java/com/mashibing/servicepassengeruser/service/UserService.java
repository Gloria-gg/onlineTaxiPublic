package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:28 PM 10/13/22
 */
@Service
public class UserService {

    @Autowired
    private PassengerUserMapper passengerUserMapper;

    /**
     * 通过用户手机号码进行已经登陆或者注册判断
     *
     * @param passengerPhone
     * @return
     */
    public ResponseResult logOrRegister(String passengerPhone) {
        System.out.println("开始进行UserService服务调用！");

        //根据手机号判断用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
        System.out.println(passengerUsers.size() == 0 ? "无用户记录！" : passengerUsers.get(0).getPassengerPhone());

        //判断用户信息是否存在

        //如果不存在插入用户信息

        return ResponseResult.success();
    }
}
