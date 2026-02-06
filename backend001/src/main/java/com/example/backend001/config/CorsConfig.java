package com.example.backend001.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * CORS 配置类
 * 负责配置跨域资源共享，允许前端应用访问后端 API
 * 
 * 注解说明：
 * @Configuration - 标记为 Spring 配置类
 */
@Configuration
public class CorsConfig {

    /**
     * 允许的跨域源，从配置文件读取
     */
    @Value("${spring.cors.allowed-origins}")
    private String allowedOrigins;

    /**
     * 创建并配置 CorsFilter Bean
     * 
     * @return 配置好的 CorsFilter 实例
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建 URL 基础的 CORS 配置源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 创建 CORS 配置
        CorsConfiguration config = new CorsConfiguration();
        
        // 添加允许的跨域源
        config.addAllowedOriginPattern(allowedOrigins);
        
        // 允许携带凭证
        config.setAllowCredentials(true);
        
        // 允许所有 HTTP 方法
        config.addAllowedMethod("*");
        
        // 允许所有请求头
        config.addAllowedHeader("*");
        
        // 暴露所有响应头
        config.addExposedHeader("*");
        
        // 注册 CORS 配置，对所有路径生效
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}