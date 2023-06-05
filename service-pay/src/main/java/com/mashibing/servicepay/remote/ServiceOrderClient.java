package com.mashibing.servicepay.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Gloria
 * @Description: 远程调用service-order模块
 * 支付宝在付款成功后进行回调，会回调service-pay服务进行回调；
 * service-pay就要远程调用service-order，告诉系统，需要修改订单状态为：成功付款
 * @Date: Created in 11:19 AM 6/5/23
 */
@FeignClient("service-order")
public interface ServiceOrderClient {

    @RequestMapping(method = RequestMethod.POST, value = "/order/pay")
    public ResponseResult pay(@RequestBody OrderRequest orderRequest);
}
