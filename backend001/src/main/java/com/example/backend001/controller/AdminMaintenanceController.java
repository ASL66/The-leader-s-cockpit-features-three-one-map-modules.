package com.example.backend001.controller;

import com.example.backend001.common.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * 管理员维护控制器
 * 负责处理系统维护相关的API请求，如数据清理、缓存刷新等操作
 * 
 * 注解说明：
 * @RestController - 标记为REST风格控制器，返回JSON格式数据
 * @RequestMapping("/admin") - 定义基础请求路径
 * @RequiredArgsConstructor - 自动生成构造方法，用于依赖注入
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMaintenanceController {

    /** 数据源，用于执行SQL脚本 */
    private final DataSource dataSource;
    
    /** Redis模板，用于操作Redis缓存 */
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 清理漳平市名称数据
     * 执行清理SQL脚本，移除漳平市名称中的重复或无效数据
     * 并刷新Redis缓存，确保数据一致性
     * 
     * @return 操作结果，成功返回成功消息，失败返回错误信息
     */
    @GetMapping("/cleanup-zhangping")
    public Result<Void> cleanupZhangpingNames() {
        try {
            // 执行清理SQL脚本
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(false, false, "UTF-8", new ClassPathResource("sql/cleanup_zhangping_names.sql"));
            populator.execute(dataSource);
            
            // 清理Redis缓存，确保数据一致性
            redisTemplate.delete("gas:map:total_data");
            redisTemplate.delete("gas:map:total_data:v9");
            
            // 返回成功结果
            return Result.success();
        } catch (Exception e) {
            // 返回错误结果
            return Result.error("清理失败: " + e.getMessage());
        }
    }
}

