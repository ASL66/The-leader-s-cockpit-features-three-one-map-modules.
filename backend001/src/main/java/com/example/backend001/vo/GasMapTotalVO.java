package com.example.backend001.vo;


import lombok.Data;

import java.util.List;

/**
 * 燃气地图总体数据VO
 * 用于传输燃气系统的总体数据，包含多个维度的数据列表和统计信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 */
@Data
public class GasMapTotalVO {
    /**
     * 区域燃气数据列表
     * 包含各区域的燃气相关数据
     */
    private List<RegionGasVO> regionList;
    
    /**
     * 站点地图数据列表
     * 包含各站点的地图相关数据
     */
    private List<StationMapVO> stationList;
    
    /**
     * 燃气核心统计信息
     * 包含燃气系统的核心统计指标
     */
    private GasStatVO statInfo;

    /**
     * 管网信息列表
     * 包含管网相关的详细数据
     */
    private List<com.example.backend001.entity.TbPipeNetworkInfo> pipeList;
    
    /**
     * 项目管理列表
     * 包含项目管理相关的详细数据
     */
    private List<com.example.backend001.entity.TbProjectManagement> projectList;
    
    /**
     * 应急管理列表
     * 包含应急管理相关的详细数据
     */
    private List<com.example.backend001.entity.TbEmergencyManagement> emergencyList;
    
    /**
     * 运行监测列表
     * 包含运行监测相关的详细数据
     */
    private List<com.example.backend001.entity.TbOperationMonitor> monitorList;
    
    /**
     * 企业信息列表
     * 包含企业相关的详细数据
     */
    private List<com.example.backend001.entity.TbEnterpriseInfo> enterpriseList;
    
    /**
     * 安全监管列表
     * 包含安全监管相关的详细数据
     */
    private List<com.example.backend001.entity.TbSafetySupervision> safetyList;
}
