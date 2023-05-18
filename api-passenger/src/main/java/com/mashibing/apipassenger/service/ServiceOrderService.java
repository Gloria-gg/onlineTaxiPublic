package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServiceOrderClient;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:36 PM 5/18/23
 */
@Service
public class ServiceOrderService {

    @Autowired
    private ServiceOrderClient serviceOrderClient;

    public ResponseResult addOrder(OrderRequest orderRequest) {
        return serviceOrderClient.addOrder(orderRequest);
    }
}
