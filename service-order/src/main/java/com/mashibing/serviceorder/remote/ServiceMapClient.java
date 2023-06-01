package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.internalcommon.response.TrSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 8:52 AM 5/23/23
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    @RequestMapping(method = RequestMethod.POST, value = "/terminal/aroundsearch")
    public ResponseResult<List<TerminalResponse>> aroundSearch(@RequestParam String center, @RequestParam Integer radius);

    @RequestMapping(method = RequestMethod.POST, value = "/terminal/trsearch")
    public ResponseResult<TrSearchResponse> trSearch(@RequestParam String tid, @RequestParam Long starttime, @RequestParam Long endtime);
}
