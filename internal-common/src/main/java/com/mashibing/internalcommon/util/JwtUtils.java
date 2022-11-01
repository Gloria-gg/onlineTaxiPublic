package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.response.TokenResponse;

import javax.print.DocFlavor;
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

    //进行生成以及解析token时用到的key
    private static final String JWT_KEY_PHONE = "phone";

    private static final String JWT_KEY_IDENTITY = "identity";

    //token 类型
    private static final String JWT_TOKEN_TYPE = "tokenType";


    /**
     * 生成token
     *
     * @param phone 使用map方便
     * @return
     */
    public static String generateToken(String phone, String identity, String tokenType) {
        //根据实际情况进行map设置，除了设置手机号还要一个身份标示：是乘客还是司机
        Map<String, String> map = new HashMap<>();
        map.put(JWT_KEY_PHONE, phone);
        map.put(JWT_KEY_IDENTITY, identity);
        map.put(JWT_TOKEN_TYPE, tokenType);

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

        //整合过期时间,这里暂时先不设置有效期，因为在redis中设置了有效期是60天
        //builder.withExpiresAt(time);

        //builder生成token
        String token = builder.sign(Algorithm.HMAC256(SIGN));

        return token;
    }


    /**
     * 解析传进来的token
     * 对于本项目来说，只有一种返回结果，就是passengerPhone
     * 所以返回值直接就是string类型
     *
     * @param token
     * @return
     */
    public static TokenResult parseToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);

        TokenResult tokenResult = new TokenResult();
        tokenResult.setPhone(verify.getClaim(JWT_KEY_PHONE).asString());
        tokenResult.setIdentity(verify.getClaim(JWT_KEY_IDENTITY).asString());

        return tokenResult;
    }

    /**
     * 校验token，主要是判断token是否异常
     *
     * @param token
     * @return
     */
    public static TokenResult checkToken(String token) {
        TokenResult tokenResult = null;

        try {
            tokenResult = parseToken(token);

        } catch (Exception e) {

        }

        return null;
    }
}
