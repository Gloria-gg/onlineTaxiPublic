package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.ServiceMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 通过调用高德api进行service创建等操作，从而进行车辆以及轨迹创建，最后进行周边车辆搜索
 * @Date: Created in 5:01 PM 5/12/23
 */
@RestController
@RequestMapping("/service")
public class ServiceController {
    @Autowired
    private ServiceMapService serviceMapService;

    @PostMapping("/add")
    public ResponseResult addService(String name) {
        return serviceMapService.addService(name);
    }
}
