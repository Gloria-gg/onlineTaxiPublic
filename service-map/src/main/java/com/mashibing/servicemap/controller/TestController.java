package com.mashibing.servicemap.controller;

import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:57 AM 4/24/23
 */
@RestController
public class TestController {

    @Autowired
    private DicDistrictMapper dicDistrictMapper;

    @GetMapping("/test")
    public String test() {
        return "test controller from service map module!";
    }

    @GetMapping("/testMap")
    public String testMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("address_code","110000");
        List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(map);
        return dicDistricts.get(0).getAddressName();
    }
}

