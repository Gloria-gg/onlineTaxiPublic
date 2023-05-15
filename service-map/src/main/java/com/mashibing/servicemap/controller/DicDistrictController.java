package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.service.DicDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Gloria
 * @Description: 获取地区字典
 * @Date: Created in 9:09 AM 4/28/23
 */
@RestController
public class DicDistrictController {
    @Autowired
    private DicDistrictService dicDistrictService;

    @GetMapping("/dic-district")
    public ResponseResult initDicDistrict(String keyWords) {

        ResponseResult responseResult = dicDistrictService.dicDistrict(keyWords);
        return responseResult;
    }
}
