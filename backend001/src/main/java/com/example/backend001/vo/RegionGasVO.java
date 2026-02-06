package com.example.backend001.vo;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 区域燃气信息VO
 * 用于前端展示各区域燃气相关统计数据
 */
@Data
public class RegionGasVO {
    
    /**
     * 区域代码
     */
    private String regionCode;
    
    /**
     * 区域名称
     */
    private String regionName;
    
    /**
     * 用户总数
     */
    private Integer userTotalCount;
    
    /**
     * 站点数量
     */
    private Integer stationCount;
    
    /**
     * 管网长度
     */
    private BigDecimal pipeNetworkLength;
    
    /**
     * 隐患数量
     */
    private Integer hiddenDangerCount;
    
    /**
     * 经度
     */
    private BigDecimal longitude;
    
    /**
     * 纬度
     */
    private BigDecimal latitude;
}
