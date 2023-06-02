package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceSsePushClient;
import com.mashibing.internalcommon.constant.IdentityConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PushRequest;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:19 PM 6/2/23
 */
@Service
public class PayService {
    @Autowired
    private ServiceSsePushClient serviceSsePushClient;

    /**
     * 司机端推送价格消息给客户端
     *
     * @param orderId
     * @param price
     * @param passengerId
     * @return
     */
    public ResponseResult pushPayInfo(Long orderId, Double price, Long passengerId) {
        //封装消息，远程调用serviceSsePushClient进行消息推送
        JSONObject content = new JSONObject();
        content.put("price", price);
        content.put("orderId", orderId);

        PushRequest pushRequest = new PushRequest();
        pushRequest.setUserId(passengerId);
        pushRequest.setIdentity(IdentityConstants.PASSENGER_IDENTITY);
        pushRequest.setContent(content.toString());


        //推送消息
        serviceSsePushClient.push(pushRequest);


        return ResponseResult.success();
    }
}
