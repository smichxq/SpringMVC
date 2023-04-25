package com.example.springmvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> getRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        //将Redis链接数据库的工厂类注入到RedisTemplate
        redisTemplate.setConnectionFactory(factory);

        //(默认)key序列化方式
        redisTemplate.setKeySerializer(RedisSerializer.string());

        //(默认)value序列化
        redisTemplate.setValueSerializer(RedisSerializer.json());

        //(hash)key序列化方式
        redisTemplate.setHashKeySerializer(RedisSerializer.string());

        //(hash)value序列化方式
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        //设置生效
        redisTemplate.afterPropertiesSet();

        return redisTemplate;

    }
}
