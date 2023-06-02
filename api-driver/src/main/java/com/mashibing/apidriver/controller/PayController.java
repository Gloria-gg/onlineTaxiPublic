package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PayService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:14 PM 6/2/23
 */
@RestController
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private PayService payService;

    /**
     * 司机端把价格信息推送给乘客
     *
     * @param orderId
     * @param price
     * @return
     */
    @PostMapping("/push-pay-info")
    public ResponseResult pushPayInfo(@RequestParam Long orderId, @RequestParam Double price, @RequestParam Long passengerId) {
        return payService.pushPayInfo(orderId, price, passengerId);
    }
}
