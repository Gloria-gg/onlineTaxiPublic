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
