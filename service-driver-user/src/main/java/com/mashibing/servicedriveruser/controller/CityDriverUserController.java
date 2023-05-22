package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CityDriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:49 PM 5/22/23
 */
@RestController
@RequestMapping("/city-driver")
public class CityDriverUserController {
    @Autowired
    private CityDriverUserService cityDriverUserService;

    @GetMapping("/is-available-driver")
    public ResponseResult<Boolean> isDriverAvailable(String cityCode) {
        return cityDriverUserService.isAvailableDriver(cityCode);
    }
}
