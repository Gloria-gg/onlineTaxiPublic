package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicepassengeruser.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        //根据手机号判断用户信息
        Map<String, Object> map = new HashMap<>();
        map.put("passenger_phone", passengerPhone);
        List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);

        //判断用户信息是否存在
        if (passengerUsers.size() == 0) {
            //如果不存在插入用户信息
            PassengerUser passengerUser = new PassengerUser();
            passengerUser.setPassengerName("用户" + passengerPhone);
            passengerUser.setPassengerGender((byte) 0);
            passengerUser.setPassengerPhone(passengerPhone);
            passengerUser.setState((byte) 0);//0:有效  1:删除
            passengerUser.setGmtCreate(LocalDateTime.now());
            passengerUser.setGmtModified(LocalDateTime.now());

            passengerUserMapper.insert(passengerUser);
        }


        return ResponseResult.success();
    }
}
