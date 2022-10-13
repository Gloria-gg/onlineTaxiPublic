package com.mashibing.apipassenger.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: Gloria
 * @Description: 输入手机号传输对象
 * @Date: Created in 9:23 AM 9/30/22
 */
@Getter
@Setter
@ToString
public class VerificationCodeDTO {
    private String passengerPhone;

    private String verificationCode;
}
