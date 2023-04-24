package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:46 AM 4/24/23
 */
@Data
public class DirectionResponse {

    /**
     * 距离：米
     */
    private Integer distance;

    /**
     * 时长：分钟
     */
    private Integer duration;
}
