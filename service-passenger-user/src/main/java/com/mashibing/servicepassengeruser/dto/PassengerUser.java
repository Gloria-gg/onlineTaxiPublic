package com.mashibing.servicepassengeruser.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 2:38 PM 10/14/22
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
}
