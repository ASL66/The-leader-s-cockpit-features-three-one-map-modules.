package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbSafetySupervision;
import com.example.backend001.service.TbSafetySupervisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 安全监管控制器
 * 负责处理安全监管相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/safety-supervision")
@RequiredArgsConstructor
public class TbSafetySupervisionController {

    private final TbSafetySupervisionService safetySupervisionService;

    /**
     * 获取所有安全监管信息列表
     * @return 包含所有安全监管信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbSafetySupervision>> list() {
        return Result.success(safetySupervisionService.list());
    }

    /**
     * 分页获取安全监管信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页安全监管信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbSafetySupervision>> page(@RequestParam(defaultValue = "1") Integer current,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(safetySupervisionService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取安全监管信息
     * @param id 安全监管信息ID
     * @return 包含指定ID安全监管信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbSafetySupervision> getById(@PathVariable Long id) {
        return Result.success(safetySupervisionService.getById(id));
    }

    /**
     * 保存安全监管信息
     * @param safetySupervision 安全监管信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbSafetySupervision safetySupervision) {
        return Result.success(safetySupervisionService.save(safetySupervision));
    }

    /**
     * 更新安全监管信息
     * @param safetySupervision 安全监管信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbSafetySupervision safetySupervision) {
        return Result.success(safetySupervisionService.updateById(safetySupervision));
    }

    /**
     * 删除安全监管信息
     * @param id 安全监管信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(safetySupervisionService.removeById(id));
    }
}
