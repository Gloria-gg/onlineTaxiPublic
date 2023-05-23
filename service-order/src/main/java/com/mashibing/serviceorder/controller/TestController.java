package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.OrderInfo;
import com.mashibing.serviceorder.mapper.OrderInfoMapper;
import com.mashibing.serviceorder.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:23 PM 5/18/23
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String test() {
        return "test controller from service order!";
    }

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    OrderInfoMapper orderInfoMapper;


    @GetMapping("/test-real-time/{orderId}")
    public String testAroundSearch(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfoService.aroundSearchRealTime(orderInfo);

        return "real time around search success!";

    }
}
