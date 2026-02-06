package com.example.backend001.service;

import com.example.backend001.entity.TbOperationMonitor;
import com.example.backend001.vo.GasMapTotalVO;

import java.util.List;

/**
 * 燃气地图服务接口
 * 负责处理燃气地图相关的数据获取和缓存操作
 */
public interface GasMapService {

    /**
     * 获取燃气地图总体数据
     * 包含企业、站点、管网等燃气系统的综合数据
     * @return 燃气地图总体数据对象
     */
    GasMapTotalVO getGasMapTotalData();

    /**
     * 刷新燃气地图总体数据缓存
     * 清除缓存，强制下次获取数据时重新从数据库查询
     */
    void refreshGasMapTotalData();

    /**
     * 获取运行监测列表数据
     * @return 运行监测数据列表
     */
    List<TbOperationMonitor> getOperationMonitorList();
}