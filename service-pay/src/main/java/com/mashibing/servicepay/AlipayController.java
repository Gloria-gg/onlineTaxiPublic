package com.mashibing.servicepay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:10 AM 6/5/23
 */
@Controller
@RequestMapping("/alipay")
@ResponseBody
@Slf4j
public class AlipayController {

    /**
     * 进行支付,跳出支付页面进行付款
     *
     * @param subject     支付主题，比如：车费
     * @param outTradeNo  支付交易码，暂时随便
     * @param totalAmount 支付总金额
     * @return
     */
    @GetMapping("/pay")
    public String pay(String subject, String outTradeNo, String totalAmount) {
        AlipayTradePagePayResponse response;

        try {
            response = Factory.Payment.Page().pay(subject, outTradeNo, totalAmount, "");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


        return response.getBody();
    }


    /**
     * 付款给支付宝之后，支付宝要进行回调，进行回调系统操作
     *
     * @param request
     * @return
     */
    @PostMapping("/notify")
    public String notify(HttpServletRequest request) {
        log.info("支付宝开始进行回调！");

        String tradeStatus = request.getParameter("trade_status");

        //若是交易状态是成功，那么把传进去的参数进行获取
        if (tradeStatus.trim().equals("TRADE_SUCCESS")) {
            Map<String, String> param = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (String name : parameterMap.keySet()) {
                param.put(name, request.getParameter(name));
            }

            //防止黑客利用同样url进行参数获取，进行验证
            try {
                if (Factory.Payment.Common().verifyNotify(param)) {
                    log.info("支付宝验证通过！");
                    log.info("下面显示回调出来的参数：");
                    for (String name : param.keySet()) {
                        log.info(name + " : " + param.get(name));
                    }
                } else {
                    log.info("支付宝验证不通过！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "支付宝回调完成！";

    }
}
