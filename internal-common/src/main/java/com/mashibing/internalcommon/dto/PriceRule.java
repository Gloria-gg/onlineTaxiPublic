package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:09 AM 4/27/23
 */
@Data
public class PriceRule {

    /**
     * 城市代码
     */
    private String cityCode;

    /**
     * 车辆类型
     */
    private String vehicleType;

    /**
     * 起步价
     */
    private BigDecimal startFare;

    /**
     * 起步里程
     */
    private Integer startMile;

    /**
     * 后续每公里价格
     */
    private BigDecimal unitPricePerMile;

    /**
     * 后续每分钟价格
     */
    private BigDecimal unitPricePerMinute;
}
