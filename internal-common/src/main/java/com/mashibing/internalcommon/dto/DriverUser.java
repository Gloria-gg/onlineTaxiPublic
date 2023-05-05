package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 3:35 PM 5/4/23
 */
@Data
public class DriverUser {
    private Long id;

    /**
     * 地区code，对应表格dic_district
     */
    private String address;

    private String driverName;

    private String driverPhone;

    /**
     * 性别，0：未知 1：男 2：女 9：未说明的性别
     */
    private Integer driverGender;

    private LocalDate driverBirthday;

    /**
     * 国籍，对应表dic_nation
     */
    private String driverNation;

    private String driverContactAddress;

    /**
     * 驾照号码
     */
    private String licenseId;

    /**
     * 获得驾照日期
     */
    private LocalDate getDriverLicenseDate;

    /**
     * 驾照有效期起始
     */
    private LocalDate driverLicenseOn;

    /**
     * 驾照有效期截止
     */
    private LocalDate driverLicenseOff;

    /**
     * 是否巡游出租汽车：1：是；0：否
     */
    private Integer taxiDriver;

    /**
     * 网络预约出租汽车驾驶员资格证号
     */
    private String certificateNo;

    /**
     * 网络预约出租汽车驾驶员发证机构
     */
    private String networkCarIssueOrganization;

    /**
     * 资格证发证日期
     */
    private LocalDate networkCarIssueDate;

    /**
     * 初次领取资格证日期
     */
    private LocalDate getNetworkCarProofDate;

    /**
     * 资格证有效期起始日期
     */
    private LocalDate networkCarProofOn;

    /**
     * 资格证有效期截止日期
     */
    private LocalDate networkCarProofOff;

    /**
     * 报备日期
     */
    private LocalDate registerDate;

    /**
     * 服务类型：1：网络预约出租汽车；2：巡游出租汽车；3：私人小客车合乘
     */
    private Integer commercialType;

    /**
     * 驾驶员合同（协议）签署公司
     */
    private String contractCompany;

    /**
     * 合同（协议）有效期起
     */
    private LocalDate contractOn;

    /**
     * 合同（协议）有效期止
     */
    private LocalDate contractOff;

    /**
     * 司机状态：0：有效；1：失效
     */
    private Integer state;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

}
