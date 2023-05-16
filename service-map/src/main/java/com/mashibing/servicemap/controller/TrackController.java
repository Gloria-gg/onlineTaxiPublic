package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.servicemap.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: service下的terminal下的轨迹track
 * @Date: Created in 10:09 AM 5/16/23
 */
@RestController
@RequestMapping("/track")
public class TrackController {
    @Autowired
    private TrackService trackService;

    @PostMapping("/add")
    public ResponseResult<TrackResponse> addTrack(@RequestParam String tid) {
        return trackService.addTrack(tid);
    }
}
