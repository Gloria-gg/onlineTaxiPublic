package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Gloria
 * @Description: 用户类，各个包都需要这个类，所以放在公共包里
 * @Date: Created in 9:21 AM 11/2/22
 */
@Data
public class PassengerUser {
    private long id;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private String passengerPhone;

    private String passengerName;

    private byte passengerGender;

    private byte state;

    private String profilePhoto;
}
