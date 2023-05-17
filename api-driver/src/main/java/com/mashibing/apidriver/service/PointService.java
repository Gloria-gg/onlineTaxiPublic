package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ApiDriverPointRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:28 AM 5/17/23
 */
@Service
@Slf4j
public class PointService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 通过传进来的车辆carId，查询数据库，获取tid以及trid，然后上传轨迹点
     *
     * @param apiDriverPointRequest
     * @return
     */
    public ResponseResult uploadPoints(ApiDriverPointRequest apiDriverPointRequest) {
        //通过carId获取tid、trid
        ResponseResult<Car> carResponseResult = serviceDriverUserClient.getCarById(apiDriverPointRequest.getCarId());
        Car car = carResponseResult.getData();
        String tid = car.getTid();
        String trid = car.getTrid();
        log.info("获取到的tid以及trid是：" + tid + "  /  " + trid);

        //调用service-driver-user进行轨迹点上传


        return ResponseResult.success("");
    }
}
