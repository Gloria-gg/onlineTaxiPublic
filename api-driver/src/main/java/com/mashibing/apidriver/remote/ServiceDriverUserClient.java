package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:14 AM 5/6/23
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    /**
     * 更新司机信息
     *
     * @param driverUser
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

    /**
     * 根据电话号码查询司机信息是否存在
     *
     * @param driverPhone
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> checkDriverUser(@PathVariable("driverPhone") String driverPhone);
}
