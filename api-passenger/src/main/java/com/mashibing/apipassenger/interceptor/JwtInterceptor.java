package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.constant.TokenConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: Gloria
 * @Description: jwt的拦截器，用于对token拦截，是否需要token还是不需要token就可以访问服务
 * @Date: Created in 2:22 PM 10/20/22
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean result = true;
        String resultString = "";

        String token = request.getHeader("Authorization");

        //使用之前自己写的工具进行token解析，在解析过程中出现异常进行捕获
        TokenResult tokenResult = null;
        try {
            tokenResult = JwtUtils.parseToken(token);
        } catch (SignatureVerificationException e) {
            resultString = "token signature error!";
            result = false;
        } catch (TokenExpiredException e) {
            resultString = "token time out!";
            result = false;
        } catch (AlgorithmMismatchException e) {
            resultString = "token algorithm error!";
            result = false;
        } catch (Exception e) {
            resultString = "token error!";
            result = false;
        }

        //从redis中获取token
        if (tokenResult == null) {
            resultString = "token error!";
            result = false;
        } else {
            String identity = tokenResult.getIdentity();
            String phone = tokenResult.getPhone();

            String tokenKey = RedisPrefixUtils.generateTokenKey(phone, identity, TokenConstants.ACCESS_TOKEN_TYPE);

            //从redis中根据key获取token
            String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);

            //和redis中token进行比较，看是否是正确的token
            if (StringUtils.isBlank(tokenRedis)) {
                resultString = "token error!";
                result = false;
            } else {
                //不是空，那么判断两个token是否一致
                if (!token.trim().equals(tokenRedis.trim())) {
                    resultString = "token error!";
                    result = false;
                }
            }
        }


        //如果有异常，那么通过response把异常信息转发到前端去
        if (!result) {
            PrintWriter writer = response.getWriter();
            writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
        }

        return result;
    }
}
