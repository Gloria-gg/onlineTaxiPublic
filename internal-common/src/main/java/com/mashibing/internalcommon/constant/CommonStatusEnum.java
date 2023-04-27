package com.mashibing.internalcommon.constant;

import lombok.Getter;

/**
 * @Author: Gloria
 * @Description: 所有子项目状态码都定义在这里
 * @Date: Created in 4:32 PM 9/30/22
 */
@Getter
public enum CommonStatusEnum {

    /**
     * 验证码错误提示：1000-1099
     * 比如：1001：验证码有效期过期等
     */
    VERIFICATION_CODE_ERROR(1099, "验证码不正确"),

    /**
     * token类错误提示：1100 - 1199
     */
    TOKEN_ERROR(1199, "token错误"),

    /**
     * 用户信息错误提示：1200-1299
     */
    USER_NOT_EXISTS(1299, "用户信息不存在"),

    /**
     * 计价规则不存在：1300-1399
     */
    PRICE_RULE_EMPTY(1399, "计价规则不存在"),

    /**
     * 成功
     */
    SUCCESS(1, "success"),


    /**
     * 失败
     */
    FAIL(0, "fail");

    private int code;
    private String message;

    CommonStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
