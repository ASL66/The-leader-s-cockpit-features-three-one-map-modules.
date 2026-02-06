package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbBasicInfo;
import com.example.backend001.service.TbBasicInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础信息控制器
 * 负责处理基础信息相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/basic-info")
@RequiredArgsConstructor
public class TbBasicInfoController {

    private final TbBasicInfoService basicInfoService;

    /**
     * 获取所有基础信息列表
     * @return 包含所有基础信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbBasicInfo>> list() {
        return Result.success(basicInfoService.list());
    }

    /**
     * 分页获取基础信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页基础信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbBasicInfo>> page(@RequestParam(defaultValue = "1") Integer current,
                                          @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(basicInfoService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取基础信息
     * @param id 基础信息ID
     * @return 包含指定ID基础信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbBasicInfo> getById(@PathVariable Long id) {
        return Result.success(basicInfoService.getById(id));
    }

    /**
     * 保存基础信息
     * @param basicInfo 基础信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbBasicInfo basicInfo) {
        return Result.success(basicInfoService.save(basicInfo));
    }

    /**
     * 更新基础信息
     * @param basicInfo 基础信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbBasicInfo basicInfo) {
        return Result.success(basicInfoService.updateById(basicInfo));
    }

    /**
     * 删除基础信息
     * @param id 基础信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(basicInfoService.removeById(id));
    }
}
