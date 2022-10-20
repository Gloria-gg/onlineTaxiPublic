package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 对应token解析出来的结果对象
 * @Date: Created in 10:08 AM 10/20/22
 */
@Data
public class TokenResult {

    private String phone;

    private String identity;
}
