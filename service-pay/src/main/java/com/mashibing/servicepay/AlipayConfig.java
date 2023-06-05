package com.mashibing.servicepay;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:56 AM 6/3/23
 */
@Component
@ConfigurationProperties(prefix = "alipay")
@Data
@Slf4j
public class AlipayConfig {

    private String appId;


    private String appPrivateKey;


    private String publicKey;


    private String notifyUrl;


    @PostConstruct
    public void init() {
        Config config = new Config();

        config.protocol = "https";
        config.gatewayHost = "openapi.alipaydev.com";
        config.signType = "RSA2";

        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.publicKey;
        config.notifyUrl = this.notifyUrl;

        Factory.setOptions(config);
        log.info("支付宝初始化配置完成！");


    }
}
