package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 2:17 PM 5/23/23
 */
@Data
public class PriceRuleIsNewRequest {
    private String fareType;

    private Integer fareVersion;
}
