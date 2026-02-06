package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbEnterpriseInfo;
import com.example.backend001.service.TbEnterpriseInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 企业信息控制器
 * 负责处理企业信息相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/enterprise-info")
@RequiredArgsConstructor
public class TbEnterpriseInfoController {

    private final TbEnterpriseInfoService enterpriseInfoService;

    /**
     * 获取所有企业信息列表
     * @return 包含所有企业信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbEnterpriseInfo>> list() {
        return Result.success(enterpriseInfoService.list());
    }

    /**
     * 分页获取企业信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页企业信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbEnterpriseInfo>> page(@RequestParam(defaultValue = "1") Integer current,
                                               @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(enterpriseInfoService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取企业信息
     * @param id 企业信息ID
     * @return 包含指定ID企业信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbEnterpriseInfo> getById(@PathVariable Long id) {
        return Result.success(enterpriseInfoService.getById(id));
    }

    /**
     * 保存企业信息
     * @param enterpriseInfo 企业信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbEnterpriseInfo enterpriseInfo) {
        return Result.success(enterpriseInfoService.save(enterpriseInfo));
    }

    /**
     * 更新企业信息
     * @param enterpriseInfo 企业信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbEnterpriseInfo enterpriseInfo) {
        return Result.success(enterpriseInfoService.updateById(enterpriseInfo));
    }

    /**
     * 删除企业信息
     * @param id 企业信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(enterpriseInfoService.removeById(id));
    }
}
