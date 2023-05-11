package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:03 AM 5/5/23
 */
@Service
@Slf4j
public class DriverUserService {
    @Autowired
    private DriverUserMapper driverUserMapper;

    /**
     * 添加司机信息
     *
     * @param driverUser
     * @return
     */
    public ResponseResult addDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtCreate(now);
        driverUser.setGmtModified(now);

        driverUserMapper.insert(driverUser);

        return ResponseResult.success("");
    }

    /**
     * 修改司机信息
     *
     * @param driverUser
     * @return
     */
    public ResponseResult updateDriverUser(DriverUser driverUser) {
        LocalDateTime now = LocalDateTime.now();
        driverUser.setGmtModified(now);
        driverUserMapper.updateById(driverUser);

        return ResponseResult.success("");
    }

    /**
     * 通过司机手机号码获取司机信息
     *
     * @param driverPhone
     * @return
     */
    public ResponseResult<DriverUser> getDriverUserByPhone(String driverPhone) {
        Map<String, Object> map = new HashMap<>();
        map.put("driver_phone", driverPhone);
        map.put("state", DriverCarConstants.DRIVER_STATE_VALID);
        List<DriverUser> driverUserList = driverUserMapper.selectByMap(map);

        //司机数只能严格等于1，否则只能报错
        if (driverUserList.isEmpty() || driverUserList.size() != 1) {
            return ResponseResult.fail(CommonStatusEnum.DRIVER_INFO_ERROR.getCode(),
                    CommonStatusEnum.DRIVER_INFO_ERROR.getMessage());
        }

        DriverUser driverUser = driverUserList.get(0);
        return ResponseResult.success(driverUser);
    }
}
