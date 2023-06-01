package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:16 AM 5/15/23
 */
@Service
@Slf4j
public class TerminalClient {
    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据terminal名称创建service下的terminal
     *
     * @param name
     * @return
     */
    public ResponseResult<TerminalResponse> addTerminal(String name, String desc) {
        String url = String.format(MapConfigConstants.TERMINAL_ADD_URL, amapKey, amapSid, name, desc);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, null, String.class);
        JSONObject result = JSONObject.fromObject(stringResponseEntity.getBody());
        String tid = result.getJSONObject("data").getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return ResponseResult.success(terminalResponse);
    }

    /**
     * 根据中心点center进行半径为radius的范围搜索车辆
     *
     * @param center
     * @param radius
     * @return
     */
    public ResponseResult<List<TerminalResponse>> aroundSearch(String center, Integer radius) {
        String url = String.format(MapConfigConstants.AROUND_SEARCH_URL, amapKey, amapSid, center, radius);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, null, String.class);
        log.info("高德响应接口是：" + url);
        log.info("高德响应结果是：" + stringResponseEntity.getBody());

        //解析返回的结果
        JSONObject jsonObject = JSONObject.fromObject(stringResponseEntity.getBody());
        JSONArray results = jsonObject.getJSONObject("data").getJSONArray("results");
        List<TerminalResponse> terminalResponseList = new ArrayList<>();
        for (int i = 0; i < results.size(); i++) {
            JSONObject object = results.getJSONObject(i);
            long carId = Long.parseLong(object.getString("desc"));
            String tid = object.getString("tid");
            //再接着获取车辆的实时位置经纬度，后面订单生成需要
            JSONObject location = object.getJSONObject("location");
            String longitude = location.getDouble("longitude") + "";
            String latitude = location.getDouble("latitude") + "";

            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setTid(tid);
            terminalResponse.setCarId(carId);
            terminalResponse.setLongitude(longitude);
            terminalResponse.setLatitude(latitude);
            terminalResponseList.add(terminalResponse);
        }

        return ResponseResult.success(terminalResponseList);
    }


    /**
     * 远程调用高德api进行一系列轨迹点形成的时长以及距离获取
     *
     * @param tid
     * @param startTime
     * @param endTime
     * @return
     */
    public ResponseResult trSearch(String tid, Long startTime, Long endTime) {
        String url = String.format(MapConfigConstants.TR_SEARCH_URL, amapKey, amapSid, tid, startTime, endTime);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, null, String.class);
        log.info("高德响应接口是：" + url);
        log.info("高德响应结果是：" + stringResponseEntity.getBody());

        return null;

    }
}
