package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Gloria
 * @Description: 远程调用service-sse-push进行信息推送
 * @Date: Created in 8:48 AM 5/26/23
 */
@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    /**
     * 进行
     *
     * @param pushRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    public String push(@RequestBody PushRequest pushRequest);
}
