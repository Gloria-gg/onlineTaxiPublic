package com.mashibing.apiboss.service;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 4:34 PM 5/10/23
 */
@Service
public class DriverCarBindingRelationshipService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    /**
     * 司机车辆关系绑定
     *
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult bindingDriverCar(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.bindingDriverCar(driverCarBindingRelationship);
    }

    /**
     * 司机车辆关系解绑
     *
     * @param driverCarBindingRelationship
     * @return
     */
    public ResponseResult unbindingDriverCar(DriverCarBindingRelationship driverCarBindingRelationship) {
        return serviceDriverUserClient.unbindingDriverCar(driverCarBindingRelationship);
    }
}
