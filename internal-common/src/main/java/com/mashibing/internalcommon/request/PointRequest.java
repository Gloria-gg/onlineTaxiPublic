package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * @Author: Gloria
 * @Description: 调用高德api获取轨迹点请求对象
 * @Date: Created in 11:44 AM 5/16/23
 */
@Data
public class PointRequest {

    private String tid;

    private String trid;

    private PointDTO[] points;
}
