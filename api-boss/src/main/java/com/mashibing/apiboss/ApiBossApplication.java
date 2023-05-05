package com.mashibing.apiboss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 11:39 AM 5/5/23
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ApiBossApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiBossApplication.class);
    }
}
