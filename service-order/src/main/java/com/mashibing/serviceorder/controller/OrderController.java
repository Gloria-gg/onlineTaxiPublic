package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.constant.HeaderParamConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import com.mashibing.serviceorder.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest) {
        return orderInfoService.addOrder(orderRequest);
    }
}
