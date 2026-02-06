package com.example.backend001.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.backend001.common.Result;
import com.example.backend001.entity.TbStationInfo;
import com.example.backend001.service.TbStationInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 站点信息控制器
 * 负责处理站点信息相关的CRUD操作API请求
 */
@RestController
@RequestMapping("/station-info")
@RequiredArgsConstructor
public class TbStationInfoController {

    private final TbStationInfoService stationInfoService;

    /**
     * 获取所有站点信息列表
     * @return 包含所有站点信息的响应对象
     */
    @GetMapping("/list")
    public Result<List<TbStationInfo>> list() {
        return Result.success(stationInfoService.list());
    }

    /**
     * 分页获取站点信息
     * @param current 当前页码，默认为1
     * @param size 每页大小，默认为10
     * @return 包含分页站点信息的响应对象
     */
    @GetMapping("/page")
    public Result<Page<TbStationInfo>> page(@RequestParam(defaultValue = "1") Integer current,
                                            @RequestParam(defaultValue = "10") Integer size) {
        return Result.success(stationInfoService.page(new Page<>(current, size)));
    }

    /**
     * 根据ID获取站点信息
     * @param id 站点信息ID
     * @return 包含指定ID站点信息的响应对象
     */
    @GetMapping("/{id}")
    public Result<TbStationInfo> getById(@PathVariable Long id) {
        return Result.success(stationInfoService.getById(id));
    }

    /**
     * 保存站点信息
     * @param stationInfo 站点信息对象
     * @return 操作结果响应对象
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody TbStationInfo stationInfo) {
        return Result.success(stationInfoService.save(stationInfo));
    }

    /**
     * 更新站点信息
     * @param stationInfo 站点信息对象
     * @return 操作结果响应对象
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody TbStationInfo stationInfo) {
        return Result.success(stationInfoService.updateById(stationInfo));
    }

    /**
     * 删除站点信息
     * @param id 站点信息ID
     * @return 操作结果响应对象
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable Long id) {
        return Result.success(stationInfoService.removeById(id));
    }
}
