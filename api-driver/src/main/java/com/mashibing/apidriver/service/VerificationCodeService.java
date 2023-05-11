package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:25 PM 5/10/23
 */
@Service
@Slf4j
public class VerificationCodeService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ServiceVerificationCodeClient serviceVerificationCodeClient;

    /**
     * 判断司机信息是否存在，并且进行验证码传入redis操作
     *
     * @param driverPhone
     * @return
     */
    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用service-driver-user，判断该手机号司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriverUser(driverPhone);
        Integer isExists = driverUserExistsResponseResponseResult.getData().getIsExists();
        //司机不存在，那么返回错误信息
        if (isExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
                    CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
        }

        //获取验证码
        ResponseResult<NumberCodeResponse> numberCode = serviceVerificationCodeClient.getNumberCode(6);
        NumberCodeResponse numberCodeResponse = numberCode.getData();

        //调用第三方发送验证码

        //存入redis,1.key 2.存入redis
        String redisKey = RedisPrefixUtils.generateRedisKeyByPassengerPhone(driverPhone, IdentityConstants.DRIVER_IDENTITY);
        redisTemplate.opsForValue().set(redisKey, numberCodeResponse.getNumberCode().toString(), 2, TimeUnit.MINUTES);


        return ResponseResult.success("");
    }

    /**
     * 通过司机手机号以及传入的验证码进行redis中验证码对比
     *
     * @param driverPhone
     * @param verificationCode
     * @return
     */
    public ResponseResult checkVerificationCode(String driverPhone, String verificationCode) {
        //根据手机号去redis中获取对应验证码。第一步：生成key
        String key = RedisPrefixUtils.generateRedisKeyByPassengerPhone(driverPhone, IdentityConstants.DRIVER_IDENTITY);
        //第二步：根据key获取value
        String redisCode = redisTemplate.opsForValue().get(key);

        //redis验证码与输入验证码进行校验
        if (StringUtils.isBlank(redisCode)) {
            //验证码为空，提示验证码不正确
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(),
                    CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());
        }

        if (!verificationCode.trim().equals(redisCode.trim())) {
            //若用户输入验证码和redis中验证码不一致，也进行报错提示
            return ResponseResult.fail(CommonStatusEnum.VERIFICATION_CODE_ERROR.getCode(), CommonStatusEnum.VERIFICATION_CODE_ERROR.getMessage());
        }

        //颁发token令牌,使用JwtUtils进行令牌颁发,暂定"1"是乘客，后面需要一个枚举类进行重新定义
        String accessToken = JwtUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshToken = JwtUtils.generateToken(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);

        //把token存到redis中
        String accessTokenKey = RedisPrefixUtils.generateTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.ACCESS_TOKEN_TYPE);
        String refreshTokenKey = RedisPrefixUtils.generateTokenKey(driverPhone, IdentityConstants.DRIVER_IDENTITY, TokenConstants.REFRESH_TOKEN_TYPE);
        //这里设置refreshToken要比accessToken多一些时间，这样可以利用refreshToken来设置新的双token，用户体验好
        redisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
        redisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

        //设置返回值
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(accessToken);
        tokenResponse.setRefreshToken(refreshToken);

        return ResponseResult.success(tokenResponse);
    }
}
