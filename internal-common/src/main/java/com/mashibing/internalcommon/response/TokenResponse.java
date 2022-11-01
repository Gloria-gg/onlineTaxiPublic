package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: token现在设置成双token，所以里面有两个参数
 * @Date: Created in 10:40 AM 10/13/22
 */
@Data
public class TokenResponse {

    private String accessToken;

    private String refreshToken;
}
