package com.example.backend001;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 后端应用主入口类
 * 
 * 功能说明：
 * 1. 启动 Spring Boot 应用
 * 2. 配置 MyBatis 扫描路径，自动扫描 mapper 包下的接口
 * 3. 打印启动成功日志
 * 
 * 注解说明：
 * @SpringBootApplication - 标记为 Spring Boot 应用主类
 * @MapperScan - 指定 MyBatis 映射接口的扫描路径
 */
@SpringBootApplication
@MapperScan("com.example.backend001.mapper")
public class Backend001Application {

    /**
     * 应用启动方法
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        SpringApplication.run(Backend001Application.class, args);
        // 打印启动成功日志
        System.out.println("===== 漳平市燃气监管后端服务启动成功 =====");
    }

}
