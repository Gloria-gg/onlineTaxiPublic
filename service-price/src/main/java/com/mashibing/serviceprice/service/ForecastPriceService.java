package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:50 AM 4/24/23
 */
@Service
@Slf4j
public class ForecastPriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    public ResponseResult forecastPrice(String depLongitude, String depLatitude,
                                        String destLongitude, String destLatitude) {

        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);

        log.info("调用地图服务，查询距离和时长");
        ResponseResult<DirectionResponse> driving = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = driving.getData().getDistance();
        Integer duration = driving.getData().getDuration();
        log.info("service-price中读取到的距离和时长分别是：" + distance + "   " + duration);


        log.info("读取计价规则");


        log.info("根据距离、时长以及计价规则，进行价格预估");


        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(34.54);

        return ResponseResult.success(forecastPriceResponse);
    }
}
