package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author: Gloria
 * @Description: 定义一个统一返回对象
 * @Date: Created in 4:37 PM 9/30/22
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class ResponseResult<T> {

    /**
     * 状态码1：成功；0：失败
     */
    private int code;
    /**
     * success；fail
     */
    private String message;
    /**
     * 对应返回数据
     */
    private T data;


    /**
     * 默认返回成功信息
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success() {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode()).setMessage(CommonStatusEnum.SUCCESS.getMessage());
    }


    /**
     * 成功响应的方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult success(T data) {
        return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
                .setMessage(CommonStatusEnum.SUCCESS.getMessage())
                .setData(data);
    }

    /**
     * 统一的失败方法
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult fail(T data) {
        return new ResponseResult().setData(data);
    }

    /**
     * 自定义失败响应的方法
     * 比如：1001：验证码生成失败  1002：验证码对比失败 等等
     *
     * @param code
     * @param message
     * @return
     */
    public static ResponseResult fail(int code, String message) {
        return new ResponseResult().setCode(code).setMessage(message);
    }

    /**
     * 带有具体数据的自定义失败响应方法
     *
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static ResponseResult fail(int code, String message, String data) {
        return new ResponseResult().setCode(code).setMessage(message).setData(data);
    }
}
