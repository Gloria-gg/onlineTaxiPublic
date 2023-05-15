package com.mashibing.servicedriveruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: Gloria
 * @Description: 司机端进行数据操作
 * @Date: Created in 3:34 PM 5/4/23
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mashibing.servicedriveruser.mapper")
@EnableFeignClients
public class ServiceDriverUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceDriverUserApplication.class, args);
    }
}
