package com.mashibing.internalcommon.constant;

/**
 * @Author: Gloria
 * @Description: 高德地图API需要的固定常量
 * @Date: Created in 2:39 PM 4/24/23
 */
public class MapConfigConstants {

    /**
     * 请求高德api接口的url
     * https://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=base&output=json&key=c822c6e2c79ebd33327cf136934637e9
     *
     */
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving?origin=%s,%s&destination=%s,%s&extensions=%s&output=json&key=%s";

    /**
     * 路径规划返回结果json中表示是否正常的"status"值，0表示异常，1表示正常
     */
    public static final String STATUS = "status";

    /**
     * 路径规划返回结果json中包含需要数据的route字段
     */
    public static final String ROUTE = "route";

    /**
     * 路径规划返回结果json中的route字段下的paths数组
     */
    public static final String PATHS = "paths";

    /**
     * path数组中第一个元素中distance参数
     */
    public static final String DISTANCE = "distance";

    /**
     * path数组中第一个元素中duration参数
     */
    public static final String DURATION = "duration";
}
