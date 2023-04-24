package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:12 AM 4/24/23
 */
@Service
public class DirectionService {

    /**
     * 通过输入的出发地和目的地经纬度，计算两地之间距离（米）和时长（分钟）
     *
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public ResponseResult driving(String depLongitude, String depLatitude,
                                  String destLongitude, String destLatitude) {


        DirectionResponse directionResponse = new DirectionResponse();
        directionResponse.setDistance(243);
        directionResponse.setDuration(21);


        return ResponseResult.success(directionResponse);
    }
}
