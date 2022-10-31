package com.mashibing.internalcommon.util;

/**
 * @Author: Gloria
 * @Description: redis生成key的工具类
 * @Date: Created in 4:13 PM 10/31/22
 */
public class RedisPrefixUtils {

    /**
     * 存入redis的验证码的key的前缀
     */
    public static String verificationCodePrefix = "passenger_verification_code_";

    /**
     * 存入redis的token的前缀
     */
    public static String tokenKeyPrefix = "token_";


    /**
     * 根据乘客电话号码进行redis的key生成
     * 因为有拷贝行为，所以对有拷贝行为都生成一个方法
     * 万一之后改变规则，也好进行统一更改
     * （算是一个开发中的小技巧）
     *
     * @param passengerPhone
     * @return
     */
    public static String generateRedisKeyByPassengerPhone(String passengerPhone) {
        return verificationCodePrefix + passengerPhone;
    }

    /**
     * 生成token存储到redis中的key
     *
     * @param phone
     * @param identity
     * @return
     */
    public static String generateTokenKey(String phone, String identity) {
        return tokenKeyPrefix + phone + "_" + identity;
    }

}
