package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 司机信息是否存在响应
 * @Date: Created in 10:33 AM 5/11/23
 */
@Data
public class DriverUserExistsResponse {
    /**
     * 司机电话号码
     */
    private String driverPhone;

    /**
     * 司机信息是否存在
     */
    private Integer isExists;
}
