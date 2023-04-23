package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.ForecastPriceService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 预估价格controller
 * @Date: Created in 4:38 PM 11/3/22
 */
@RestController
@Slf4j
public class ForecastPriceController {

    @Autowired
    private ForecastPriceService forecastPriceService;

    @PostMapping("/forecast-price")
    public ResponseResult forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {

        log.info("出发地经纬度：" + forecastPriceDTO.getDepLongitude() + " ; " + forecastPriceDTO.getDepLatitude());
        log.info("目的地经纬度：" + forecastPriceDTO.getDestLongitude() + " ; " + forecastPriceDTO.getDestLatitude());

        return forecastPriceService.forecastPrice(forecastPriceDTO.getDepLongitude(), forecastPriceDTO.getDepLatitude(),
                forecastPriceDTO.getDestLongitude(), forecastPriceDTO.getDestLatitude());
    }

}
