package com.mashibing.serviceprice.controller;


import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.service.PriceRuleService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Gloria
 * @since 2023-05-19
 */
@RestController
@RequestMapping("/price-rule")
public class PriceRuleController {
    @Autowired
    private PriceRuleService priceRuleService;

    @PostMapping("/add")
    public ResponseResult addPriceRule(@RequestBody PriceRule priceRule) {
        return priceRuleService.addPriceRule(priceRule);
    }

    @PostMapping("/edit")
    public ResponseResult editPriceRule(@RequestBody PriceRule priceRule) {
        return priceRuleService.editPriceRule(priceRule);
    }

    @GetMapping("/get-latest-version")
    public ResponseResult<PriceRule> getLatestFareVersion(@RequestParam String fareType) {
        return priceRuleService.getLatestFareVersion(fareType);
    }

    /**
     * 判断计价规则是否是最新计价规则
     *
     * @param fareType
     * @return
     */
    @GetMapping("/is-latest-version")
    public ResponseResult<Boolean> isLatestFareVersion(@RequestParam String fareType, @RequestParam Integer fareVersion) {
        return priceRuleService.isLatestFareVersion(fareType, fareVersion);
    }


    /**
     * 根据城市编码和车型判断计价规则是否存在
     *
     * @param priceRule
     * @return
     */
    @GetMapping("/if-exists")
    public ResponseResult<Boolean> ifExists(@RequestBody PriceRule priceRule) {
        return priceRuleService.ifExists(priceRule);
    }

}
