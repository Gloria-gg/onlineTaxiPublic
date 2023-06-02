package com.mashibing.serviceorder.remote;

import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.PriceRuleIsNewRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:03 AM 5/22/23
 */
@FeignClient("service-price")
public interface ServicePriceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/price-rule/is-latest-version")
    public ResponseResult<Boolean> isLatestFareVersion(@RequestBody PriceRuleIsNewRequest priceRuleIsNewRequest);


    @RequestMapping(method = RequestMethod.GET, value = "/price-rule/if-exists")
    public ResponseResult<Boolean> ifExists(@RequestBody PriceRule priceRule);

    @RequestMapping(method = RequestMethod.POST, value = "calculate-price")
    public ResponseResult<Double> calculatePrice(@RequestParam Integer distance,
                                         @RequestParam Integer duration,
                                         @RequestParam String cityCode,
                                         @RequestParam String vehicleType);
}
