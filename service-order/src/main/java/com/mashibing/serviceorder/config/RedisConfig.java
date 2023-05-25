package com.mashibing.serviceorder.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author: Gloria
 * @Description:
 * @Date: Created in 9:57 AM 5/25/23
 */
@Component
public class RedisConfig {

    private String protocol = "redis://";

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;


    /**
     * 单机版创建redisso对象进行分布式锁创建
     *
     * @return
     */
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer().setAddress(protocol + redisHost + ":" + redisPort).setDatabase(0);

        return Redisson.create(config);
    }
}
