package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbPipeNetworkInfo;
import com.example.backend001.service.TbPipeNetworkInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管网信息控制器
 * 负责处理管网信息相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/pipe-network-info")
@RequiredArgsConstructor
public class TbPipeNetworkInfoController {

    private final TbPipeNetworkInfoService pipeNetworkInfoService;

    /**
     * 获取所有管网信息列表
     * @return 包含所有管网信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbPipeNetworkInfo>> list() {
        return Result.success(pipeNetworkInfoService.list());
    }

    /**
     * 分页获取管网信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页管网信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbPipeNetworkInfo>> page(@RequestParam(defaultValue = "1") Integer current,
                                                @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(pipeNetworkInfoService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取管网信息
     * @param id 管网信息ID
     * @return 包含指定ID管网信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbPipeNetworkInfo> getById(@PathVariable Long id) {
        return Result.success(pipeNetworkInfoService.getById(id));
    }

    /**
     * 保存管网信息
     * @param pipeNetworkInfo 管网信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbPipeNetworkInfo pipeNetworkInfo) {
        return Result.success(pipeNetworkInfoService.save(pipeNetworkInfo));
    }

    /**
     * 更新管网信息
     * @param pipeNetworkInfo 管网信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbPipeNetworkInfo pipeNetworkInfo) {
        return Result.success(pipeNetworkInfoService.updateById(pipeNetworkInfo));
    }

    /**
     * 删除管网信息
     * @param id 管网信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(pipeNetworkInfoService.removeById(id));
    }
}
