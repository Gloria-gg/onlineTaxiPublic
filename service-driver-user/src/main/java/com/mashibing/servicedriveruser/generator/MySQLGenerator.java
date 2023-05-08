package com.mashibing.servicedriveruser.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @Author: Gloria
 * @Description: 自动生成代码工具类mysql-plus-generator
 * @Date: Created in 11:26 AM 5/6/23
 */
public class MySQLGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/service_driver_user?characterEncoding=utf-8&serverTimezone=GMT%2B8";
        String username = "root";
        String password = "123456";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("Gloria")
                            .fileOverride()
                            .outputDir("/Users/ge/Documents/ideaProjects/onlineTaxiProject/onlineTaxiPublic/service-driver-user/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.mashibing.servicedriveruser")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    "/Users/ge/Documents/ideaProjects/onlineTaxiProject/onlineTaxiPublic/service-driver-user/src/main/java/com/mashibing/servicedriveruser/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude("driver_car_binding_relationship");
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();

    }
}
