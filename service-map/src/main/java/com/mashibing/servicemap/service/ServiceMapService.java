package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.ServiceMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description: 调用高德api进行轨迹service获取等操作
 * @Date: Created in 5:22 PM 5/12/23
 */
@Service
public class ServiceMapService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 创建一个新的轨迹service，并返回sid
     *
     * @param name
     * @return
     */
    public ResponseResult addService(String name) {
        return serviceMapClient.addService(name);
    }
}
