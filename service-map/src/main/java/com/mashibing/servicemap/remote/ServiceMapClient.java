package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ServiceMapResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Gloria
 * @Description: 远程调用高德api进行service创建，然后进行车辆以及轨迹创建
 * @Date: Created in 5:09 PM 5/12/23
 */
@Service
@Slf4j
public class ServiceMapClient {
    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 添加轨迹service,返回service的sid
     *
     * @param serviceName
     * @return
     */
    public ResponseResult addService(String serviceName) {
        //得到url，进行高德api请求结果，解析结果，返回sid
        String url = String.format(MapConfigConstants.SERVICE_ADD_URL, amapKey, serviceName);
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url.toString(), null, String.class);
        JSONObject result = JSONObject.fromObject(stringResponseEntity.getBody());
        String sid = result.getJSONObject("data").getString("sid");
        ServiceMapResponse serviceMapResponse = new ServiceMapResponse();
        serviceMapResponse.setSid(sid);

        return ResponseResult.success(serviceMapResponse);
    }


}
