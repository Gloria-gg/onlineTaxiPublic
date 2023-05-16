package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TrackResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Gloria
 * @Description: 远程调用高德api获得车辆轨迹track
 * @Date: Created in 10:12 AM 5/16/23
 */
@Service
public class TrackClient {
    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据车辆终端terminal的tid获取车辆trid轨迹点
     *
     * @param tid
     * @return
     */
    public ResponseResult<TrackResponse> addTrack(String tid) {
        String url = String.format(MapConfigConstants.TRACK_ADD_URL, amapKey, amapSid, tid);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, null, String.class);
        JSONObject result = JSONObject.fromObject(stringResponseEntity.getBody());
        String trid = result.getJSONObject("data").getString("trid");
        String trname = "";
        if (result.getJSONObject("data").has(trname)) {
            trname = result.getJSONObject("data").getString("trname");
        }
        TrackResponse trackResponse = new TrackResponse();
        trackResponse.setTrid(trid);
        trackResponse.setTrname(trname);

        return ResponseResult.success(trackResponse);
    }
}
