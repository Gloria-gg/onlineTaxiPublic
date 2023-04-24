package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Gloria
 * @Description: 远程调用高德API，进行出发地和目的地之间距离和时间计算
 * @Date: Created in 2:28 PM 4/24/23
 */
@Service
@Slf4j
public class MapDirectionClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 调用远程高德API接口，获取两地之间的距离和时长
     *
     * @param depLongitude
     * @param depLatitude
     * @param destLongitude
     * @param destLatitude
     * @return
     */
    public DirectionResponse direction(String depLongitude, String depLatitude,
                                       String destLongitude, String destLatitude) {

        //组装请求调用url
        //下面是一个测试通过的请求调用url，需要出发地和目的地经纬度、extensions（base或者all）、key（已申请，是固定的）
        String extensions = "base";
        String url = String.format(MapConfigConstants.DIRECTION_URL, depLongitude, depLatitude, destLongitude, destLatitude, extensions, amapKey);

        //调用高德API接口
        ResponseEntity<String> directionEntity = restTemplate.getForEntity(url, String.class);

        //解析返回值
        return parseDirectionString(directionEntity.getBody());
    }

    /**
     * 把string类型的距离时长信息解析成需要的对象信息
     *
     * @param directionString
     * @return
     */
    private DirectionResponse parseDirectionString(String directionString) {
        DirectionResponse directionResponse = null;
        //当输入参数不正确时，返回的结果值是错误的，这时候解析会失败，所以要进行异常处理
        try {

            JSONObject resultJSON = JSONObject.fromObject(directionString);
            //如果有status值并且为1
            if (resultJSON.has(MapConfigConstants.STATUS) && resultJSON.getInt(MapConfigConstants.STATUS) == 1) {
                //获取route值
                if (resultJSON.has(MapConfigConstants.ROUTE)) {
                    JSONObject routeJSON = resultJSON.getJSONObject(MapConfigConstants.ROUTE);
                    if (routeJSON.has(MapConfigConstants.PATHS)) {
                        JSONArray pathsJSONArray = routeJSON.getJSONArray(MapConfigConstants.PATHS);
                        JSONObject pathObject = pathsJSONArray.getJSONObject(0);
                        if (pathObject.has(MapConfigConstants.DISTANCE)
                                && pathObject.has(MapConfigConstants.DURATION)) {
                            int duration = pathObject.getInt(MapConfigConstants.DURATION);
                            int distance = pathObject.getInt(MapConfigConstants.DISTANCE);
                            directionResponse = new DirectionResponse();
                            directionResponse.setDuration(duration);
                            directionResponse.setDistance(distance);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return directionResponse;
    }
}
