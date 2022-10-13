package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:12 PM 10/13/22
 */
@Data
public class VerificationCodeDTO {
    private String passengerPhone;

    private String verificationCode;
}
