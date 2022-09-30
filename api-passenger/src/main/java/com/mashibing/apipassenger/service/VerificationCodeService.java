package com.mashibing.apipassenger.service;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:43 AM 9/30/22
 */
@Service
public class VerificationCodeService {

    public String generateCode(String passengerPhone) {
        //调用验证码服务，获取验证码
        System.out.println("调用验证码服务，获取验证码");
        String code = "111111";

        //存入redis
        System.out.println("存入验证码到redis中");


        //返回成功或者失败码
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 1);
        jsonObject.put("message", "success");
        return jsonObject.toString();
    }
}
