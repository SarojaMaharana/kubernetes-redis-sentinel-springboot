package com.redis.bulk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableAutoConfiguration
public class RedisSentinel {

    @Value("${spring.redis.sentinel.master}")
    private String master;

    @Value("${spring.redis.sentinel.nodes}")
    private String sentinelHost;

    @Value("${spring.redis.sentinel.port}")
    private Integer sentinelPort;
    
    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisPoolConfig getRedisConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        return config;
    }

    @Bean
    @ConfigurationProperties(prefix="spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisPoolConfig config = getRedisConfig();
        JedisConnectionFactory factory = new JedisConnectionFactory(getRedisSentinelConfig(), config);
        factory.setPoolConfig(config);
        return factory;
    }

    @Bean
    public RedisSentinelConfiguration getRedisSentinelConfig(){
        RedisSentinelConfiguration sentinelConfiguration = new RedisSentinelConfiguration();
        sentinelConfiguration.setMaster(master);
        System.out.print("the master is: " + master);
        sentinelConfiguration.sentinel(sentinelHost,sentinelPort);
        System.out.print("the sentinelHost is: " + sentinelHost);
        System.out.print("the sentinelPort is: " + sentinelPort);
        return sentinelConfiguration;
    }


    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(){
        RedisTemplate<?,?> redisTemplate = new StringRedisTemplate(getConnectionFactory());
        return redisTemplate;
    }
}

