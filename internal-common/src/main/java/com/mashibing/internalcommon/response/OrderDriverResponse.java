package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:19 AM 5/23/23
 */
@Data
public class OrderDriverResponse {
    private Long driverId;

    private Long carId;

    private String driverPhone;
}
