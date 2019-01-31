package com.mwb.shortlink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * -
 * Created by mengweibo on 2018/8/9.
 */

@Configuration
public class RedisConfig {
    @Value("${redis.address}")
    private String redisAddress;

    @Value("${redis.port}")
    private int redisPort;

    @Bean(name = "jedisPoolConfig")
    public JedisPoolConfig getJedisPoolConfig() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(300);
        config.setMaxIdle(10);
        config.setMinIdle(5);
        config.setMaxWaitMillis(6000);
        config.setTestOnBorrow(false);
        config.setTestOnReturn(true);
        return config;
    }

    @Bean(name = "jedisPool")
    public JedisPool getJedisPool() {
        return new JedisPool(getJedisPoolConfig(), redisAddress, redisPort);
    }

}
