package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbEmergencyManagement;
import com.example.backend001.service.TbEmergencyManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 应急管理控制器
 * 负责处理应急管理相关的API请求，包括应急资源的增删改查操作
 * 
 * 注解说明：
 * @RestController - 标记为REST风格控制器，返回JSON格式数据
 * @RequestMapping("/emergency-management") - 定义基础请求路径
 * @RequiredArgsConstructor - 自动生成构造方法，用于依赖注入
 */
@RestController
@RequestMapping("/emergency-management")
@RequiredArgsConstructor
public class TbEmergencyManagementController {

    /** 应急管理服务，用于处理应急管理相关的业务逻辑 */
    private final TbEmergencyManagementService emergencyManagementService;

    /**
     * 获取应急管理列表
     * 查询所有应急管理数据
     * 
     * @return 应急管理列表数据
     */
    @GetMapping("/list")
    public Result<List<TbEmergencyManagement>> list() {
        return Result.success(emergencyManagementService.list());
    }

    /**
     * 分页获取应急管理数据
     * 
     * @param current 当前页码，默认值为1
     * @param size 每页大小，默认值为10
     * @return 分页后的应急管理数据
     */
    @GetMapping("/page")
    public Result<Page<TbEmergencyManagement>> page(@RequestParam(defaultValue = "1") Integer current,
                                                    @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(emergencyManagementService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取应急管理数据
     * 
     * @param id 应急管理ID
     * @return 应急管理详情数据
     */
    @GetMapping("/{id}")
    public Result<TbEmergencyManagement> getById(@PathVariable Long id) {
        return Result.success(emergencyManagementService.getById(id));
    }

    /**
     * 新增应急管理数据
     * 
     * @param emergencyManagement 应急管理数据对象
     * @return 操作结果，成功返回true，失败返回false
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbEmergencyManagement emergencyManagement) {
        return Result.success(emergencyManagementService.save(emergencyManagement));
    }

    /**
     * 更新应急管理数据
     * 
     * @param emergencyManagement 应急管理数据对象
     * @return 操作结果，成功返回true，失败返回false
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbEmergencyManagement emergencyManagement) {
        return Result.success(emergencyManagementService.updateById(emergencyManagement));
    }

    /**
     * 删除应急管理数据
     * 
     * @param id 应急管理ID
     * @return 操作结果，成功返回true，失败返回false
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(emergencyManagementService.removeById(id));
    }
}
