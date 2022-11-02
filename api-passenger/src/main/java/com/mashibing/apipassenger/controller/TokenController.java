package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TokenService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:38 AM 11/1/22
 */
@RestController
public class TokenController {

    @Autowired
    private TokenService tokenService;

    /**
     * 根据refreshToken来进行accessToken刷新
     *
     * @param tokenResponse
     * @return
     */
    @PostMapping("/token-refresh")
    public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {

        String refreshTokenSrc = tokenResponse.getRefreshToken();

        return tokenService.refreshToken(refreshTokenSrc);
    }
}
