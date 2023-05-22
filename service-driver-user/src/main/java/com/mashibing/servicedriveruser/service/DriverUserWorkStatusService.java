package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.DriverUserWorkStatus;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import com.mashibing.servicedriveruser.mapper.DriverUserWorkStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gloria
 * @since 2023-05-11
 */
@Service
public class DriverUserWorkStatusService {
    @Autowired
    private DriverUserWorkStatusMapper driverUserWorkStatusMapper;

    /**
     * 修改司机工作状态
     *
     * @param driverId
     * @param workStatus
     * @return
     */
    public ResponseResult changeWorkStatus(Long driverId, Integer workStatus) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_id", driverId);
        List<DriverUserWorkStatus> workStatusList = driverUserWorkStatusMapper.selectByMap(map);

        DriverUserWorkStatus userWorkStatus = workStatusList.get(0);
        userWorkStatus.setWorkStatus(workStatus);
        userWorkStatus.setGmtModified(LocalDateTime.now());
        driverUserWorkStatusMapper.updateById(userWorkStatus);

        return ResponseResult.success("");
    }



}
