package com.mashibing.serviceprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:31 AM 4/24/23
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServicePriceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePriceApplication.class, args);
    }

}
