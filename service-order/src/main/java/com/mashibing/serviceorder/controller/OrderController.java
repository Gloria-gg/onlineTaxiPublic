package com.mashibing.serviceorder.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:27 PM 5/18/23
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @PostMapping("/add")
    public ResponseResult addOrder(@RequestBody OrderRequest orderRequest) {
        log.info("从service-order获取到的数据是：" + orderRequest.toString());
        return ResponseResult.success("");
    }
}
