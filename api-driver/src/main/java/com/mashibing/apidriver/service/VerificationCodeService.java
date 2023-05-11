package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:25 PM 5/10/23
 */
@Service
@Slf4j
public class VerificationCodeService {
    @Autowired
    private ServiceDriverUserClient serviceDriverUserClient;

    public ResponseResult checkAndSendVerificationCode(String driverPhone) {
        //调用service-driver-user，判断该手机号司机是否存在
        ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.checkDriverUser(driverPhone);
        Integer isExists = driverUserExistsResponseResponseResult.getData().getIsExists();
        //司机不存在，那么返回错误信息
        if (isExists == DriverCarConstants.DRIVER_NOT_EXISTS) {
            log.info("司机不存在！");
            return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
                    CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
        }

        log.info("司机存在！");

        //获取验证码

        //调用第三方发送验证码

        //存入redis

        return ResponseResult.success("");
    }
}
