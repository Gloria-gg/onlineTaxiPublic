package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 2:14 PM 5/17/23
 */
@FeignClient("service-map")
public interface ServiceMapClient {

    /**
     * 远程调用service-map上传轨迹点points
     *
     * @param pointRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/point/upload")
    public ResponseResult uploadPoints(@RequestBody PointRequest pointRequest);
}
