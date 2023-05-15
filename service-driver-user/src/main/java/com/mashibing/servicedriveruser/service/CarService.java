package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import com.mashibing.servicedriveruser.client.ServiceMapClient;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:39 AM 5/6/23
 */
@Service
public class CarService {
    @Autowired
    private CarMapper carMapper;

    @Autowired
    private ServiceMapClient serviceMapClient;

    /**
     * 添加car信息进入数据库
     *
     * @param car
     * @return
     */
    public ResponseResult addCar(Car car) {
        LocalDateTime now = LocalDateTime.now();
        car.setGmtCreate(now);
        car.setGmtModified(now);

        //获取车辆tid
        ResponseResult<TerminalResponse> responseResponseResult = serviceMapClient.getPid(car.getVehicleNo());
        String tid = responseResponseResult.getData().getTid();
        car.setTid(tid);

        carMapper.insert(car);

        return ResponseResult.success("");
    }
}
