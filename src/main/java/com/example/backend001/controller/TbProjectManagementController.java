package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbProjectManagement;
import com.example.backend001.service.TbProjectManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 项目管理控制器
 * 负责处理项目管理相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/project-management")
@RequiredArgsConstructor
public class TbProjectManagementController {

    private final TbProjectManagementService projectManagementService;

    /**
     * 获取所有项目管理信息列表
     * @return 包含所有项目管理信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbProjectManagement>> list() {
        return Result.success(projectManagementService.list());
    }

    /**
     * 分页获取项目管理信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页项目管理信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbProjectManagement>> page(@RequestParam(defaultValue = "1") Integer current,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(projectManagementService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取项目管理信息
     * @param id 项目管理信息ID
     * @return 包含指定ID项目管理信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbProjectManagement> getById(@PathVariable Long id) {
        return Result.success(projectManagementService.getById(id));
    }

    /**
     * 保存项目管理信息
     * @param projectManagement 项目管理信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbProjectManagement projectManagement) {
        return Result.success(projectManagementService.save(projectManagement));
    }

    /**
     * 更新项目管理信息
     * @param projectManagement 项目管理信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbProjectManagement projectManagement) {
        return Result.success(projectManagementService.updateById(projectManagement));
    }

    /**
     * 删除项目管理信息
     * @param id 项目管理信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(projectManagementService.removeById(id));
    }
}
