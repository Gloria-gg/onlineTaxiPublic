package com.mashibing.apiboss.remote;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: Gloria
 * @Description: 远程调用service driver user模块中的方法
 * @Date: Created in 1:54 PM 5/5/23
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

    /**
     * 添加司机信息
     *
     * @param driverUser
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

    /**
     * 修改司机信息
     *
     * @param driverUser
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);

    /**
     * 添加车辆信息
     *
     * @param car
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/car")
    public ResponseResult addCar(@RequestBody Car car);

    /**
     * 司机车辆关系绑定
     *
     * @param driverCarBindingRelationship
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/driver-car-binding-relationship/binding")
    public ResponseResult bindingDriverCar(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

    /**
     * 司机车辆关系绑定
     *
     * @param driverCarBindingRelationship
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/driver-car-binding-relationship/unbinding")
    public ResponseResult unbindingDriverCar(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship);

}
