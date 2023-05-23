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
            TerminalResponse terminalResponse = new TerminalResponse();
            terminalResponse.setTid(tid);
            terminalResponse.setCarId(carId);
            terminalResponseList.add(terminalResponse);
        }

        return ResponseResult.success(terminalResponseList);
    }
}
