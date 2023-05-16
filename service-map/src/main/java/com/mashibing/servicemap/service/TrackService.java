package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TrackResponse;
import com.mashibing.servicemap.remote.TrackClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:19 AM 5/16/23
 */
@Service
public class TrackService {
    @Autowired
    private TrackClient trackClient;

    public ResponseResult<TrackResponse> addTrack(String tid) {
        return trackClient.addTrack(tid);
    }
}
