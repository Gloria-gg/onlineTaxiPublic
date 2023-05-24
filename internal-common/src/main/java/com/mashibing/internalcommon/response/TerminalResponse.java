package com.mashibing.internalcommon.response;

import com.sun.tools.javac.api.Formattable;
import lombok.Data;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:21 AM 5/15/23
 */
@Data
public class TerminalResponse {
    private String tid;

    private Long carId;

    private String longitude;

    private String latitude;
}
