package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 司机客户端从app发送轨迹点实体对象
 * @Date: Created in 10:19 AM 5/17/23
 */
@Data
public class ApiDriverPointRequest {
    private Long carId;

    private PointDTO[] points;
}
