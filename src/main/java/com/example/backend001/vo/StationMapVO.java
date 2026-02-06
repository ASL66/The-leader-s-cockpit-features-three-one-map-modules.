package com.example.backend001.vo;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 站点地图VO
 * 用于前端地图展示站点信息
 */
@Data
public class StationMapVO {
    
    /**
     * 站点ID
     */
    private String stationId;
    
    /**
     * 站点名称
     */
    private String stationName;
    
    /**
     * 站点类型
     */
    private String stationType;
    
    /**
     * 区域名称
     */
    private String regionName;
    
    /**
     * 企业名称
     */
    private String enterpriseName;
    
    /**
     * 供气能力
     */
    private BigDecimal gasSupplyCapacity;
    
    /**
     * 经度
     */
    private BigDecimal longitude;
    
    /**
     * 纬度
     */
    private BigDecimal latitude;
    
    /**
     * 隐患数量
     */
    private Integer hiddenDangerCount;
    
    /**
     * 状态
     */
    private Integer status;
    
    /**
     * IoT设备数量
     */
    private Integer iotDeviceCount;
    
    /**
     * 正常设备数量
     */
    private Integer normalDeviceCount;
}