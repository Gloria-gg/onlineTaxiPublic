package com.mashibing.servicepay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 10:38 AM 6/5/23
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ServicePayApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServicePayApplication.class, args);
    }
}
