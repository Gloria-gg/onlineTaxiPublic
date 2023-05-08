package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Gloria
 * @Description: 司机与车辆关系表
 * @Date: Created in 2:49 PM 5/6/23
 */
@Data
public class DriverCarBindingRelationship {
    private Long id;

    private Long driverId;

    private Long carId;

    /**
     * 绑定状态
     */
    private Integer bindingState;

    /**
     * 绑定时间
     */
    private LocalDateTime bindingTime;

    /**
     * 解绑时间
     */
    private LocalDateTime unBindingTime;
}
