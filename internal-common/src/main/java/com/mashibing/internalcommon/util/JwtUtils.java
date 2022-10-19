package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description: JWT工具类，生成和解析token功能
 * @Date: Created in 4:23 PM 10/19/22
 */
public class JwtUtils {

    //盐，进行加密，自己随便写的一个字符串
    private static final String SIGN = "CHUJSIFYFBMDJ@HJDH*&7yr87";


    /**
     * 生成token
     *
     * @param map 使用map方便
     * @return
     */
    public static String generatorToken(Map<String, String> map) {
        //定义token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        Date time = calendar.getTime();

        //定义builder来进行token创建
        JWTCreator.Builder builder = JWT.create();

        //对于map中的每一个值进行迭代，每个值都放到builder里面
        map.forEach(
                (k, v) -> {
                    builder.withClaim(k, v);
                }
        );

        //整合过期时间
        builder.withExpiresAt(time);

        //builder生成token
        String token = builder.sign(Algorithm.HMAC256(SIGN));

        return token;
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("name","gg");
        map.put("age","18");
        String s = generatorToken(map);
        System.out.println(s);
    }


}
