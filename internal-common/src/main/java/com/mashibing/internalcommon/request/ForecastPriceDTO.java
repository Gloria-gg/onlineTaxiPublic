package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:06 AM 11/4/22
 */
@Data
public class ForecastPriceDTO {

    /**
     * 出发经度
     */
    private String depLongitude;

    /**
     * 出发纬度
     */
    private String depLatitude;

    /**
     * 目的地经度
     */
    private String destLongitude;

    /**
     * 目的地纬度
     */
    private String destLatitude;

    private String vehicleType;

    private String cityCode;
}
