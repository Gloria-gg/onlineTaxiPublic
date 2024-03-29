package com.mashibing.servicedriveruser.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrackResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Gloria
 * @Description: 远程调用service-map进行tid等参数获取
 * @Date: Created in 4:46 PM 5/15/23
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/terminal/add")
    public ResponseResult<TerminalResponse> getTid(@RequestParam String name,@RequestParam String desc);

    @RequestMapping(method = RequestMethod.POST, value = "/track/add")
    public ResponseResult<TrackResponse> getTrid(@RequestParam String tid);
}
