package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.PointService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 司机从app端输入轨迹点
 * @Date: Created in 10:20 AM 5/17/23
 */
@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;

    @PostMapping("/upload")
    public ResponseResult uploadPoints(@RequestBody ApiDriverPointRequest apiDriverPointRequest) {

        return pointService.uploadPoints(apiDriverPointRequest);
    }
}
