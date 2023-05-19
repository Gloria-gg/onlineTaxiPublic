package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 返回计价结果对象
 * @Date: Created in 11:56 AM 11/4/22
 */
@Data
public class ForecastPriceResponse {

    private Double price;

    private String vehicleType;

    private String cityCode;
}
