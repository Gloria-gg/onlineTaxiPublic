package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TerminalResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:16 AM 5/15/23
 */
@Service
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
    public ResponseResult addTerminal(String name) {
        String url = String.format(MapConfigConstants.TERMINAL_ADD_URL, amapKey, amapSid, name);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url, null, String.class);
        JSONObject result = JSONObject.fromObject(stringResponseEntity.getBody());
        String tid = result.getJSONObject("data").getString("tid");
        TerminalResponse terminalResponse = new TerminalResponse();
        terminalResponse.setTid(tid);

        return ResponseResult.success(terminalResponse);
    }
}
