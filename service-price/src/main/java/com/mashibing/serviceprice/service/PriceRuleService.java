package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
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

    /**
     * 添加计价规则
     *
     * @param priceRule
     * @return
     */
    public ResponseResult addPriceRule(PriceRule priceRule) {

        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fare_type = cityCode + "$" + vehicleType;
        priceRule.setFareType(fare_type);

        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        Integer fareVersion = 1;
        if (priceRules.size() > 0) {
            //数据库中已存在计价规则，那么返回错误码
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EXISTS.getCode(),
                    CommonStatusEnum.PRICE_RULE_EXISTS.getMessage());
        }

        priceRule.setFareVersion(fareVersion);
        priceRuleMapper.insert(priceRule);

        return ResponseResult.success("");
    }

    /**
     * 1.若有修改内容，那么更新version值，然后整体重新插入
     * 2.若无修改内容，那么返回错误代码：无修改，无法编辑
     *
     * @param priceRule
     * @return
     */
    public ResponseResult editPriceRule(PriceRule priceRule) {
        String cityCode = priceRule.getCityCode();
        String vehicleType = priceRule.getVehicleType();
        String fare_type = cityCode + "$" + vehicleType;
        priceRule.setFareType(fare_type);

        QueryWrapper<PriceRule> wrapper = new QueryWrapper<>();
        wrapper.eq("city_code", cityCode);
        wrapper.eq("vehicle_type", vehicleType);
        wrapper.orderByDesc("fare_version");

        List<PriceRule> priceRules = priceRuleMapper.selectList(wrapper);
        PriceRule lastRule = priceRules.get(0);

        //只要有一个变量变化，那么久默认有变化，直接插入新值
        if (lastRule.getStartFare().doubleValue() != priceRule.getStartFare().doubleValue()
                || lastRule.getStartMile().intValue() != priceRule.getStartMile().intValue()
                || lastRule.getUnitPricePerMinute().doubleValue() != priceRule.getUnitPricePerMinute().doubleValue()
                || lastRule.getUnitPricePerMile().doubleValue() != priceRule.getUnitPricePerMile().doubleValue()) {
            priceRule.setFareVersion(lastRule.getFareVersion() + 1);

            priceRuleMapper.insert(priceRule);

            return ResponseResult.success("");

        }

        return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_CHANGE.getCode(),
                CommonStatusEnum.PRICE_RULE_NOT_CHANGE.getMessage());

    }

}
