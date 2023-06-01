package com.mashibing.serviceprice.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.mashibing.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:50 AM 4/24/23
 */
@Service
@Slf4j
public class PriceService {

    @Autowired
    private ServiceMapClient serviceMapClient;

    @Autowired
    private PriceRuleMapper priceRuleMapper;

    public ResponseResult<ForecastPriceResponse> forecastPrice(String depLongitude, String depLatitude,
                                                               String destLongitude, String destLatitude,
                                                               String cityCode, String vehicleType) {

        ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
        forecastPriceDTO.setDepLongitude(depLongitude);
        forecastPriceDTO.setDepLatitude(depLatitude);
        forecastPriceDTO.setDestLongitude(destLongitude);
        forecastPriceDTO.setDestLatitude(destLatitude);

        ResponseResult<DirectionResponse> driving = serviceMapClient.direction(forecastPriceDTO);
        Integer distance = driving.getData().getDistance();
        Integer duration = driving.getData().getDuration();
        log.info("service-price中读取到的距离和时长分别是：" + distance + "   " + duration);


        //获取计价规则，获取要是最新的
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);


        //不能没有计价规则
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),
                    CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }

        PriceRule priceRule = priceRules.get(0);
        ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
        forecastPriceResponse.setPrice(getPrice(distance, duration, priceRule));
        forecastPriceResponse.setCityCode(cityCode);
        forecastPriceResponse.setVehicleType(vehicleType);
        forecastPriceResponse.setFareType(priceRule.getFareType());
        forecastPriceResponse.setFareVersion(priceRule.getFareVersion());

        return ResponseResult.success(forecastPriceResponse);
    }

    /**
     * 根据城市计价规则，以及距离和时间，计算最终价格
     *
     * @param distance
     * @param duration
     * @param cityCode
     * @param vehicleType
     * @return
     */
    public ResponseResult calculatePrice(Integer distance, Integer duration, String cityCode, String vehicleType) {
        //获取计价规则，获取要是最新的
        QueryWrapper<PriceRule> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_code", cityCode);
        queryWrapper.eq("vehicle_type", vehicleType);
        queryWrapper.orderByDesc("fare_version");
        List<PriceRule> priceRules = priceRuleMapper.selectList(queryWrapper);


        //不能没有计价规则
        if (priceRules.size() == 0) {
            return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_EMPTY.getCode(),
                    CommonStatusEnum.PRICE_RULE_EMPTY.getMessage());
        }

        PriceRule priceRule = priceRules.get(0);
        Double finalPrice = getPrice(distance, duration * 60, priceRule);
        log.info("获取的最新的计价规则是：" + priceRule.toString());

        return ResponseResult.success(finalPrice);
    }

    /**
     * 根据距离、时长、计价规则，进行价格计算
     *
     * @param distance
     * @param duration
     * @param priceRule
     * @return
     */
    private Double getPrice(Integer distance, Integer duration, PriceRule priceRule) {
        BigDecimal finalPrice = new BigDecimal(0);

        //起步价
        finalPrice = finalPrice.add(BigDecimal.valueOf(priceRule.getStartFare()));
//        log.info("第一步：起步价的价格是：" + priceRule.getStartFare());
//        log.info("第一步：加了起步价的价格是：" + finalPrice);

        //里程,出去起步价之后的里程，若是负数，那么自动赋值为0
        //得到km数
        BigDecimal kmDistance = new BigDecimal(distance).divide(new BigDecimal(1000), 2, BigDecimal.ROUND_HALF_UP);
//        log.info("第二步：一共路程数：" + distance + " 米");
//        log.info("第二步：换算成公里：" + kmDistance + " 千米");
        //总路程-起步路程
        BigDecimal subtract = kmDistance.subtract(new BigDecimal(priceRule.getStartMile()));
        if (subtract.compareTo(new BigDecimal(0)) > 0) {
            //只有超过起步里程，才能对多余的里程进行计价
            finalPrice = finalPrice.add(subtract.multiply(BigDecimal.valueOf(priceRule.getUnitPricePerMile())).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
//        log.info("第三步：起步里程是：" + priceRule.getStartMile() + " 千米");
//        log.info("第三步：加上多余的路程后总共价格：" + finalPrice + " 元");


        //分钟,每分钟都要进行计价
        //总时间换算成分钟
        BigDecimal minTime = new BigDecimal(duration).divide(new BigDecimal(60), 2, BigDecimal.ROUND_HALF_UP);
        //根据每分钟价格进行时间价格计算
        finalPrice = finalPrice.add(minTime.multiply(BigDecimal.valueOf(priceRule.getUnitPricePerMinute())).setScale(2, BigDecimal.ROUND_HALF_UP));

//        log.info("第四步：总共行驶分钟数：" + duration + " 秒");
//        log.info("第四步：换算成分钟为：" + minTime + " 分钟");
//        log.info("第四步：每分钟需要钱数：" + priceRule.getUnitPricePerMinute() + " 分钟/元");
//        log.info("第四步：最后总价格是：" + finalPrice + " 元");

        return finalPrice.doubleValue();
    }
}
