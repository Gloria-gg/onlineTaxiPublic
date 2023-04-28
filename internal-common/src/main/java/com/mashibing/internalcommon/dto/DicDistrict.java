package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 和数据库表数据一一对应
 * @Date: Created in 4:19 PM 4/27/23
 */
@Data
public class DicDistrict {
    /**
     * 地区编码
     */
    private String addressCode;

    /**
     * 地区名称
     */
    private String addressName;

    /**
     * 父级地区编码
     */
    private String parentAddressCode;

    /**
     * 等级（地市级、区县级等）
     */
    private Integer level;
}
