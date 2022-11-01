package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Gloria
 * @Description: 双token需要的业务服务
 * @Date: Created in 10:42 AM 11/1/22
 */
@Service
public class TokenService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 根据源refreshToken来进行新的双token生成
     *
     * @param refreshTokenSrc
     * @return
     */
    public ResponseResult refreshToken(String refreshTokenSrc) {
        //解析refreshTokenSrc
        TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
        //若解析出来的token是null，那么直接返回结果fail提示
        if (tokenResult == null) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),
                    CommonStatusEnum.TOKEN_ERROR.getMessage());
        }

        //去redis中获取refreshToken，比较两者
        String identity = tokenResult.getIdentity();
        String phone = tokenResult.getPhone();

        String generateRefreshTokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);
        String redisRefreshToken = stringRedisTemplate.opsForValue().get(generateRefreshTokenKey);

        //校验refreshToken
        if ((StringUtils.isBlank(redisRefreshToken)) || (!refreshTokenSrc.trim().equals(redisRefreshToken.trim()))) {
            return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(),
                    CommonStatusEnum.TOKEN_ERROR.getMessage());
        }

        //生成双token
        String accessToken = JwtUtils.generateToken(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(phone, identity, TokenConstants.REFRESH_TOKEN_TYPE);

        String accessTokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

        //存入redis中
        stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        stringRedisTemplate.opsForValue().set(generateRefreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);


        return ResponseResult.success(tokenResponse);
    }
}
