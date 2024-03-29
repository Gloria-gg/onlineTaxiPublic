package com.mashibing.internalcommon.constant;

/**
 * @Author: Gloria
 * @Description: 高德地图API需要的固定常量
 * @Date: Created in 2:39 PM 4/24/23
 */
public class MapConfigConstants {

    /**
     * 请求高德api接口的url，根据经纬度获取时长以及里程
     * https://restapi.amap.com/v3/direction/driving?origin=116.481028,39.989643&destination=116.465302,40.004717&extensions=base&output=json&key=c822c6e2c79ebd33327cf136934637e9
     */
    public static final String DIRECTION_URL = "https://restapi.amap.com/v3/direction/driving?origin=%s,%s&destination=%s,%s&extensions=%s&output=json&key=%s";

    /**
     * 请求高德api接口url，获取地点具体信息（城镇名称以及地市级等级等）
     * https://restapi.amap.com/v3/config/district?keywords=北京&subdistrict=2&key=<用户的key>
     */
    public static final String DIC_DISTRICT_URL = "https://restapi.amap.com/v3/config/district?keywords=%s&subdistrict=%s&key=%s";

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

    /**
     * 地图服务返回结果包含需要数据的第一层
     */
    public static final String DISTRICTS = "districts";

    /**
     * 地图服务中的adcode层
     */
    public static final String ADCODE = "adcode";

    /**
     * 地图服务中name（城市名称）层
     */
    public static final String CITY_NAME = "name";

    /**
     * 地图服务中level层
     */
    public static final String LEVEL = "level";

    /**
     * 地图服务中对应属性：street
     */
    public static final String STREET = "street";

    /**
     * 调用高德api，进行轨迹service创建
     */
    public static final String SERVICE_ADD_URL = "https://tsapi.amap.com/v1/track/service/add?key=%s&name=%s";

    /**
     * 调用高德api，进行轨迹service下的terminal创建
     */
    public static final String TERMINAL_ADD_URL = "https://tsapi.amap.com/v1/track/terminal/add?key=%s&sid=%s&name=%s&desc=%s";

    /**
     * 调用高德api，创建terminal下的车辆轨迹track
     */
    public static final String TRACK_ADD_URL = "https://tsapi.amap.com/v1/track/trace/add?key=%s&sid=%s&tid=%s";

    /**
     * 调用高德api，上传车辆轨迹下的轨迹点url
     */
    public static final String POINT_UPLOAD_URL = "https://tsapi.amap.com/v1/track/point/upload?key=%s&sid=%s&tid=%s&trid=%s&points=%s";

    /**
     * 上传轨迹点中需要将其中的points进行单独封装
     */
    public static final String POINTS_URL = "{\"location\":\"%s\",\"locatetime\":\"%s\"}";

    /**
     * 根据中心点和半径进行周围车辆搜索url
     */
    public static final String AROUND_SEARCH_URL = "https://tsapi.amap.com/v1/track/terminal/aroundsearch?key=%s&sid=%s&center=%s&radius=%s";

    /**
     * 根据轨迹点各项信息进行行程时长以及距离获取
     */
    public static final String TR_SEARCH_URL = "https://tsapi.amap.com/v1/track/terminal/trsearch?key=%s&sid=%s&tid=%s&starttime=%s&endtime=%s";
}
