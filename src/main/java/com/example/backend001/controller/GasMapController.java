package com.example.backend001.controller;

import com.example.backend001.common.Result;
import com.example.backend001.service.GasMapService;
import com.example.backend001.vo.GasMapTotalVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 燃气地图控制器
 * 负责处理与燃气地图相关的API请求
 */
@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class GasMapController {

    private final GasMapService gasMapService;

    /**
     * 获取燃气地图总体数据
     * @return 包含燃气系统总体数据的响应对象
     */
    @GetMapping("/total-data")
    public Result<GasMapTotalVO> getGasMapTotalData() {
        GasMapTotalVO data = gasMapService.getGasMapTotalData();
        return Result.success(data);
    }

    /**
     * 刷新地图缓存
     * 清除缓存数据，强制重新从数据库获取最新数据
     * @return 操作结果响应对象
     */
    @GetMapping("/refresh-cache")
    public Result<Void> refreshMapCache() {
        gasMapService.refreshGasMapTotalData();
        return Result.success();
    }
}