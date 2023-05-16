package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointRequest;
import com.mashibing.servicemap.remote.PointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:49 AM 5/16/23
 */
@Service
public class PointService {
    @Autowired
    private PointClient pointClient;

    public ResponseResult uploadPoints(PointRequest pointRequest) {
        return pointClient.uploadPoints(pointRequest);
    }
}
