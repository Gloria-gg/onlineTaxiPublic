package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gloria
 * @since 2023-05-06
 */
@RestController
@RequestMapping("/driver-car-binging-relationship")
public class DriverCarBindingRelationshipController {
    @Autowired
    private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

    /**
     * 司机与车辆关系绑定
     *
     * @param driverCarBindingRelationship
     * @return
     */
    @PostMapping("/binding")
    public ResponseResult bindingDriverCar(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {

        return driverCarBindingRelationshipService.bindingDriverCar(driverCarBindingRelationship);
    }

    /**
     * 司机与车辆关系解绑
     *
     * @param driverCarBindingRelationship
     * @return
     */
    @PostMapping("/unbinding")
    public ResponseResult unbindingDriverCar(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {

        return ResponseResult.success();
    }

}
