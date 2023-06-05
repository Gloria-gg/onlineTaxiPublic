package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.constant.HeaderParamConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RRateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.memory.HeapBlock;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:27 PM 5/18/23
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 生成订单
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.addOrder(orderRequest);
    }

    /**
     * 司机接乘客进行订单状态修改
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/to-pick-up-passenger")
    public ResponseResult toPickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.toPickUpPassenger(orderRequest);
    }

    /**
     * 司机接送乘客到达目的地订单状态修改
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/arrived-departure")
    public ResponseResult arrivedDeparture(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.arrivedDeparture(orderRequest);
    }

    /**
     * 司机接到乘客
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/pick-up-passenger")
    public ResponseResult pickUpPassenger(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pickUpPassenger(orderRequest);
    }

    /**
     * 乘客到达目的地，行程结束
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/passenger-get-off")
    public ResponseResult passengerGetOff(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.passengerGetOff(orderRequest);
    }

    /**
     * 付款成功，修改订单状态为成功付款
     *
     * @param orderRequest
     * @return
     */
    @PostMapping("/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.pay(orderRequest);
    }


    /**
     * 进行订单取消操作
     *
     * @param orderId
     * @param identity
     * @return
     */
    @PostMapping("/cancel")
    public ResponseResult cancel(@RequestParam Long orderId, @RequestParam String identity) {
        return orderInfoService.cancel(orderId, identity);
    }
}
