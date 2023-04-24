package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:50 AM 4/24/23
 */
@Service
@Slf4j
public class ForecastPriceService {

    public ResponseResult forecastPrice(String depLongitude, String depLatitude,
                                        String destLongitude, String destLatitude) {


        log.info("调用地图服务，查询距离和时长");



        log.info("读取计价规则");


        log.info("根据距离、时长以及计价规则，进行价格预估");



        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(34.54);

        return ResponseResult.success(forecastPriceResponse);
    }
}
