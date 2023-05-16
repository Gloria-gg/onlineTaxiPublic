package com.mashibing.servicemap.remote;

import cn.hutool.core.util.URLUtil;
import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PointDTO;
import com.mashibing.internalcommon.request.PointRequest;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:43 AM 5/16/23
 */
@Service
@Slf4j
public class PointClient {
    @Value("${amap.key}")
    private String amapKey;

    @Value("${amap.sid}")
    private String amapSid;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseResult uploadPoints(PointRequest pointRequest) {
        //将传进来的points进行封装，变成可以使用的url中的一部分
        StringBuilder pointsUrl = new StringBuilder();
        PointDTO[] points = pointRequest.getPoints();
        pointsUrl.append("[");
        for (PointDTO pointDTO : points) {
            String format = String.format(MapConfigConstants.POINTS_URL,
                    pointDTO.getLocation(),
                    pointDTO.getLocatetime());
            pointsUrl.append(format);
        }
        pointsUrl.append("]");

        String url = String.format(MapConfigConstants.POINT_UPLOAD_URL,
                amapKey,
                amapSid,
                pointRequest.getTid(),
                pointRequest.getTrid(),
                URLUtil.encode(pointsUrl.toString()));

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(URI.create(url.toString()), null, String.class);
        log.info("从接口处传来的返回值是：" + JSONObject.fromObject(stringResponseEntity.getBody()).toString());

        return ResponseResult.success("");
    }
}
