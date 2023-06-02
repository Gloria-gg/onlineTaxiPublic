package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.request.PushRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:21 PM 6/2/23
 */
@FeignClient("service-sse-push")
public interface ServiceSsePushClient {

    /**
     * 进行消息推送
     *
     * @param pushRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/push")
    public String push(@RequestBody PushRequest pushRequest);
}
