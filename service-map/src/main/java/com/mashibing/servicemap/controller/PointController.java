package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.servicemap.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 调用高德api获取轨迹点point
 * @Date: Created in 11:41 AM 5/16/23
 */
@RestController
@RequestMapping("/point")
public class PointController {
    @Autowired
    private PointService pointService;

    /**
     * 上传轨迹点points
     *
     * @param pointRequest
     * @return
     */
    @PostMapping("/upload")
    public ResponseResult uploadPoints(@RequestBody PointRequest pointRequest) {
        return pointService.uploadPoints(pointRequest);
    }
}
