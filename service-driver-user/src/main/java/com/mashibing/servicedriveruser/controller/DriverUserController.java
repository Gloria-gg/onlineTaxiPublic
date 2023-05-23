package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.OrderDriverResponse;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:15 AM 5/5/23
 */
@RestController
public class DriverUserController {
    @Autowired
    private DriverUserService driverUserService;

    /**
     * 插入司机信息
     *
     * @param driverUser
     * @return
     */
    @PostMapping("/user")
    public ResponseResult insertUsers(@RequestBody DriverUser driverUser) {

        return driverUserService.addDriverUser(driverUser);
    }

    /**
     * 修改司机信息
     *
     * @param driverUser
     * @return
     */
    @PutMapping("/user")
    public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser) {
        return driverUserService.updateDriverUser(driverUser);
    }

    /**
     * 查询司机
     *
     * @param driverPhone
     * @return
     */
    @GetMapping("/check-driver/{driverPhone}")
    public ResponseResult<DriverUserExistsResponse> getDriver(@PathVariable("driverPhone") String driverPhone) {
        ResponseResult<DriverUser> driverUserByPhone = driverUserService.getDriverUserByPhone(driverPhone);
        DriverUser driverUserDB = driverUserByPhone.getData();
        int isExists = DriverCarConstants.DRIVER_EXISTS;
        DriverUserExistsResponse driverUserExistsResponse = new DriverUserExistsResponse();
        //若查询出来数据是空，那么进行错误值返回
        if (driverUserDB == null) {
            isExists = DriverCarConstants.DRIVER_NOT_EXISTS;
        }

        driverUserExistsResponse.setDriverPhone(driverPhone);
        driverUserExistsResponse.setIsExists(isExists);

        return ResponseResult.success(driverUserExistsResponse);
    }

    /**
     * 根据车辆ID获取可以出车的司机信息
     *
     * @param carId
     * @return
     */
    @GetMapping("/get-available-driver/{carId}")
    public ResponseResult<OrderDriverResponse> getDriverByCarId(@PathVariable("carId") Long carId) {
        return driverUserService.getDriverByCarId(carId);
    }
}
