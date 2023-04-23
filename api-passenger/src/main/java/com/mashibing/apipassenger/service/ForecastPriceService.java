package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:48 AM 11/4/22
 */
@Service
public class ForecastPriceService {

    /**
     * 根据出发地和目的地经纬度预估价格
     *
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult forecastPrice(String depLongitude, String depLatitude,
                                        String destLongitude, String destLatitude) {

        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(34.54);

        return ResponseResult.success(forecastPriceResponse);

    }
}
