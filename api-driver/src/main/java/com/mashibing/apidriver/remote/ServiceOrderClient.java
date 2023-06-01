package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 2:59 PM 6/1/23
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST, value = "/order/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest);

    @RequestMapping(method = RequestMethod.POST, value = "/order/passenger-get-off")
    public ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest);
}