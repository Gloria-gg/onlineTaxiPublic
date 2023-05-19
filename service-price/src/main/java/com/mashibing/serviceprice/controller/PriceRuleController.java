package com.mashibing.serviceprice.controller;


import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.serviceprice.service.PriceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

}
