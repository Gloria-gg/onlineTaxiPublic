package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.ApiDriverOrderService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 3:07 PM 6/1/23
 */
@RestController
@RequestMapping("/order")
public class ApiDriverOrderController {

    @Autowired
    private ApiDriverOrderService apiDriverOrderService;

    /**
     * 司机接乘客进行订单状态修改
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return apiDriverOrderService.toPickUpPassenger(orderRequest);
    }

    /**
     * 司机接送乘客到达目的地订单状态修改
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest) {
        return apiDriverOrderService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return apiDriverOrderService.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地，行程结束
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/passenger-get-off")
    public ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest) {
        return apiDriverOrderService.passengerGetOff(orderRequest);
    }

    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId) {
        return apiDriverOrderService.cancel(orderId);
    }

}
