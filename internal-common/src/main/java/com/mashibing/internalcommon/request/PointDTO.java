package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 在pointRequest对象中的一个参数需要数组
 * @Date: Created in 1:55 PM 5/16/23
 */
@Data
public class PointDTO {
    private String location;

    private String locatetime;
}
