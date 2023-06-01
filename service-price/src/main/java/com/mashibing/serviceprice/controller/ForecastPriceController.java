package com.mashibing.serviceprice.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:41 AM 4/24/23
 */
@RestController
public class ForecastPriceController {

    @Autowired
    private PriceService priceService;

    @PostMapping("/forecast-price")
    public ResponseResult<ForecastPriceResponse> forecastPrice(@RequestBody ForecastPriceDTO forecastPriceDTO) {
        String depLongitude = forecastPriceDTO.getDepLongitude();
        String depLatitude = forecastPriceDTO.getDepLatitude();
        String destLongitude = forecastPriceDTO.getDestLongitude();
        String destLatitude = forecastPriceDTO.getDestLatitude();
        String cityCode = forecastPriceDTO.getCityCode();
        String vehicleType = forecastPriceDTO.getVehicleType();

        ResponseResult responseResult = priceService.forecastPrice(depLongitude, depLatitude,
                destLongitude, destLatitude,
                cityCode, vehicleType);

        return responseResult;

    }

    @PostMapping("/calculate-price")
    public ResponseResult calculatePrice(@RequestParam Integer distance,
                                         @RequestParam Integer duration,
                                         @RequestParam String cityCode,
                                         @RequestParam String vehicleType) {
        return priceService.calculatePrice(distance, duration, cityCode, vehicleType);
    }
}
