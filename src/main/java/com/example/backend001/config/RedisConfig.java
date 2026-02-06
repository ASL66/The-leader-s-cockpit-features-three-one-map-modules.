package com.example.backend001.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


/**
 * Redis 配置类
 * 负责配置 RedisTemplate，用于 Redis 缓存操作
 * 
 * 注解说明：
 * @Configuration - 标记为 Spring 配置类
 */
@Configuration
public class RedisConfig {

    /**
     * 创建并配置 RedisTemplate Bean
     * 
     * @param factory Redis 连接工厂
     * @return 配置好的 RedisTemplate 实例
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        // 创建 RedisTemplate 实例
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        
        // 设置连接工厂
        redisTemplate.setConnectionFactory(factory);

        // 配置键序列化器为 StringRedisSerializer
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 配置值序列化器为 GenericJackson2JsonRedisSerializer
        ObjectMapper mapper = new ObjectMapper();
        // 设置所有属性可见
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 激活默认类型，支持多态
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        // 注册 Java 时间模块，支持 LocalDateTime 等时间类型
        mapper.registerModule(new JavaTimeModule());
        // 禁用将日期序列化为时间戳
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer(mapper);
        
        // 设置值序列化器
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashValueSerializer(jsonSerializer);

        // 初始化 RedisTemplate
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
