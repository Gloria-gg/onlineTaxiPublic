package com.mashibing.apidriver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:40 AM 5/6/23
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiDriverApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiDriverApplication.class);
    }
}
