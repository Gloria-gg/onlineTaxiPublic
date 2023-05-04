package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 3:35 PM 5/4/23
 */
@Data
public class DriverUser {
    private long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private Byte passengerGender;

    private Byte state;

    private String profilePhoto;
}
