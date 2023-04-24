package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.servicemap.remote.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:12 AM 4/24/23
 */
@Service
public class DirectionService {

    @Autowired
    private MapDirectionClient mapDirectionClient;

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

        //调用第三方高德接口
        DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);

        return ResponseResult.success(direction);
    }
}
