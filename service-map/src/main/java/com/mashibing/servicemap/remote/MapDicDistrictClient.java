package com.mashibing.servicemap.remote;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.MapConfigConstants;
import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
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
 * @Description: 远程调用高德api，获取地点具体位置以及对应信息
 * @Date: Created in 10:03 AM 4/28/23
 */
@Service
@Slf4j
public class MapDicDistrictClient {

    @Value("${amap.key}")
    private String amapKey;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DicDistrictMapper dicDistrictMapper;


    public String initDicDistrict(String keyWords) {
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
        parseDicDistrict(dicDistrictEntity.getBody());


        //插入数据库

        return dicDistrictEntity.getBody();

    }

    public ResponseResult parseDicDistrict(String dicDistrictString) {
        //解析地址信息
        try {

            JSONObject resultJSON = JSONObject.fromObject(dicDistrictString);
            //如果有status值并且为1
            if (resultJSON.has(MapConfigConstants.STATUS) && resultJSON.getInt(MapConfigConstants.STATUS) == 1) {
                //获取districts中的值
                if (resultJSON.has(MapConfigConstants.DISTRICTS)) {
                    JSONArray countryJsonArray = resultJSON.getJSONArray(MapConfigConstants.DISTRICTS);
                    //第一层country层数据，遍历array中数据进行数据库插入
                    for (int i = 0; i < countryJsonArray.size(); i++) {
                        JSONObject countryArraysJSONObject = countryJsonArray.getJSONObject(i);
                        String countryAddressCode = countryArraysJSONObject.getString(MapConfigConstants.ADCODE);
                        String countryAddressName = countryArraysJSONObject.getString(MapConfigConstants.CITY_NAME);
                        String countryParentAddressCode = "0";
                        String countryLevel = countryArraysJSONObject.getString(MapConfigConstants.LEVEL);

                        insertDicDistrict(countryAddressCode, countryAddressName,
                                countryParentAddressCode, countryLevel);

                        //在country层下面有province层数据，进行遍历获取
                        JSONArray provinceJsonArray = countryArraysJSONObject.getJSONArray(MapConfigConstants.DISTRICTS);
                        for (int j = 0; j < provinceJsonArray.size(); j++) {
                            JSONObject provinceArraysJSONObject = provinceJsonArray.getJSONObject(j);
                            String provinceAddressCode = provinceArraysJSONObject.getString(MapConfigConstants.ADCODE);
                            String provinceAddressName = provinceArraysJSONObject.getString(MapConfigConstants.CITY_NAME);
                            String provinceParentAddressCode = countryAddressCode;
                            String provinceLevel = provinceArraysJSONObject.getString(MapConfigConstants.LEVEL);

                            insertDicDistrict(provinceAddressCode, provinceAddressName,
                                    provinceParentAddressCode, provinceLevel);

                            //在province层下面有city层数据，进行遍历获取
                            JSONArray cityJsonArray = provinceArraysJSONObject.getJSONArray(MapConfigConstants.DISTRICTS);
                            for (int p = 0; p < cityJsonArray.size(); p++) {
                                JSONObject cityArraysJSONObject = cityJsonArray.getJSONObject(p);
                                String cityAddressCode = cityArraysJSONObject.getString(MapConfigConstants.ADCODE);
                                String cityAddressName = cityArraysJSONObject.getString(MapConfigConstants.CITY_NAME);
                                String cityParentAddressCode = provinceAddressCode;
                                String cityLevel = cityArraysJSONObject.getString(MapConfigConstants.LEVEL);

                                insertDicDistrict(cityAddressCode, cityAddressName,
                                        cityParentAddressCode, cityLevel);

                                //在city层下面是district层数据，进行遍历获取
                                JSONArray districtJsonArray = cityArraysJSONObject.getJSONArray(MapConfigConstants.DISTRICTS);
                                for (int q = 0; q < districtJsonArray.size(); q++) {
                                    JSONObject districtArraysJSONObject = districtJsonArray.getJSONObject(q);
                                    String districtAddressCode = districtArraysJSONObject.getString(MapConfigConstants.ADCODE);
                                    String districtAddressName = districtArraysJSONObject.getString(MapConfigConstants.CITY_NAME);
                                    String districtParentAddressCode = cityAddressCode;
                                    String districtLevel = districtArraysJSONObject.getString(MapConfigConstants.LEVEL);

                                    //city下面有可能是district，也有可能是street，我们直接取district数据即可，street数据直接跳过
                                    if (districtLevel.equals(MapConfigConstants.STREET)) {
                                        continue;
                                    }

                                    insertDicDistrict(districtAddressCode, districtAddressName,
                                            districtParentAddressCode, districtLevel);
                                }
                            }
                        }
                    }
                }
            } else {
                return ResponseResult.fail(CommonStatusEnum.MAP_DIC_DISTRICT_ERROR.getCode(),
                        CommonStatusEnum.MAP_DIC_DISTRICT_ERROR.getMessage());
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    /**
     * 插入dicDistrict对象进入数据库中
     *
     * @param addressCode
     * @param addressName
     * @param parentAddressCode
     * @param level
     */
    public void insertDicDistrict(String addressCode, String addressName,
                                  String parentAddressCode, String level) {
        DicDistrict dicDistrict = new DicDistrict();
        dicDistrict.setAddressCode(addressCode);
        dicDistrict.setAddressName(addressName);
        dicDistrict.setParentAddressCode(parentAddressCode);
        dicDistrict.setLevel(parseCityLevel(level));

        dicDistrictMapper.insert(dicDistrict);

    }

    /**
     * 把城市level的string换算成对应int类型值
     *
     * @param levelString
     * @return
     */
    public int parseCityLevel(String levelString) {
        switch (levelString) {
            case "country":
                return 0;
            case "province":
                return 1;
            case "city":
                return 2;
            case "district":
                return 3;
            default:
                return 999;
        }
    }
}
