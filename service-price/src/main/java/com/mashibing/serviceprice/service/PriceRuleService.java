package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Gloria
 * @since 2023-05-19
 */
@Service
public class PriceRuleService {
    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult addPriceRule(PriceRule priceRule) {
        //拼接fareType
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fare_type = cityCode + "$" + vehicleType;

        priceRule.setFareType(fare_type);

        //获取fareVersion，取最大version值，然后+1
        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 1;
        if (priceRules.size() > 0) {
            //说明数据库中存在version值，那么取得最大值+1
            fareVersion = priceRules.get(0).getFareVersion() + 1;
        }
        priceRule.setFareVersion(fareVersion);

        priceRuleMapper.insert(priceRule);

        return ResponseResult.success("");
    }

}
