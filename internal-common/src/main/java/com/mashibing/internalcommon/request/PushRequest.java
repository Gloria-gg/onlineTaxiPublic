package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 5:12 PM 5/25/23
 */
@Data
public class PushRequest {

    private Long userId;
    private String identity;
    private String content;

}
