package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbOperationMonitor;
import com.example.backend001.service.TbOperationMonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运行监测控制器
 * 负责处理运行监测相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/operation-monitor")
@RequiredArgsConstructor
public class TbOperationMonitorController {

    private final TbOperationMonitorService operationMonitorService;

    /**
     * 获取所有运行监测信息列表
     * @return 包含所有运行监测信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbOperationMonitor>> list() {
        return Result.success(operationMonitorService.list());
    }

    /**
     * 分页获取运行监测信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页运行监测信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbOperationMonitor>> page(@RequestParam(defaultValue = "1") Integer current,
                                                 @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(operationMonitorService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取运行监测信息
     * @param id 运行监测信息ID
     * @return 包含指定ID运行监测信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbOperationMonitor> getById(@PathVariable Long id) {
        return Result.success(operationMonitorService.getById(id));
    }

    /**
     * 保存运行监测信息
     * @param operationMonitor 运行监测信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbOperationMonitor operationMonitor) {
        return Result.success(operationMonitorService.save(operationMonitor));
    }

    /**
     * 更新运行监测信息
     * @param operationMonitor 运行监测信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbOperationMonitor operationMonitor) {
        return Result.success(operationMonitorService.updateById(operationMonitor));
    }

    /**
     * 删除运行监测信息
     * @param id 运行监测信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(operationMonitorService.removeById(id));
    }
}
