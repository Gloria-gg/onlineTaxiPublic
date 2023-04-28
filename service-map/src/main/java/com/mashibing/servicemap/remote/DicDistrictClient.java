package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.MapConfigConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Author: Gloria
 * @Description: 远程调用高德api，获取地点具体位置以及对应信息
 * @Date: Created in 10:03 AM 4/28/23
 */
@Service
@Slf4j
public class DicDistrictClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;


    public void initDicDistrict(String keyWords) {
        //根据高德api给出的url进行解析，得到对应地址
        //https://restapi.amap.com/v3/config/district?keywords=北京&subdistrict=2&key=<用户的key>
        //拼接请求url
        String subDistrict = "3";
        String url = String.format(MapConfigConstants.DIC_DISTRICT_URL, keyWords, subDistrict, amapKey);

        log.info("拼接出来的url是：" + url);

        //调用高德API接口
        ResponseEntity<String> dicDistrictEntity = restTemplate.getForEntity(url, String.class);

        //解析返回值


        //解析结果


        //插入数据库

    }
}
