package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:06 PM 5/22/23
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    @RequestMapping(method = RequestMethod.GET, value = "/city-driver/is-available-driver")
    public ResponseResult<Boolean> isDriverAvailable(@RequestParam String  cityCode);

    @RequestMapping(method = RequestMethod.GET, value = "/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getDriverByCarId(@RequestParam Long carId);
}
