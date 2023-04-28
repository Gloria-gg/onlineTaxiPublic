package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.remote.DicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:16 AM 4/28/23
 */
@Service
public class DicDistrictService {

    @Autowired
    private DicDistrictClient dicDistrictClient;

    public ResponseResult initDicDistrict(String keyWords) {
        dicDistrictClient.initDicDistrict(keyWords);


        return null;
    }
}
