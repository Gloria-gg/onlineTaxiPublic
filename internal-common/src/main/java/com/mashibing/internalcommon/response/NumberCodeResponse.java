package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 这个表示验证码，不仅一个地方需要，其他地方也需要，所以定义在公共包里
 * @Date: Created in 5:31 PM 9/30/22
 */
@Data
public class NumberCodeResponse {

    private int numberCode;
}
