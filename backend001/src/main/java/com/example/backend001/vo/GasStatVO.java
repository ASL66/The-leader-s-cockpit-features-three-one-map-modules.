package com.example.backend001.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 燃气核心统计数据VO
 * 用于传输燃气系统的核心统计指标数据
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 */
@Data
public class GasStatVO {
    /**
     * 企业数量
     */
    private Integer enterpriseCount;
    
    /**
     * 站点数量
     */
    private Integer stationCount;
    
    /**
     * 管网总长度
     */
    private BigDecimal totalPipeLength;
    
    /**
     * 用户总数
     */
    private Integer totalUserCount;
    
    /**
     * 隐患总数
     */
    private Integer totalHiddenDanger;
    
    /**
     * 已整改隐患数量
     */
    private Integer rectifiedDanger;
    
    /**
     * 告警数量
     */
    private Integer alarmCount;

    /**
     * 今日供气量
     */
    private BigDecimal todayGasSupply;

    /**
     * 压缩机合规率
     */
    private BigDecimal compressorComplianceRate;

    /**
     * 设备完好率
     */
    private BigDecimal equipmentIntegrityRate;

    /**
     * 安全运行天数
     */
    private Integer safeOperationDays;

    /**
     * 设备健康分数
     */
    private BigDecimal deviceHealthScore;

    /**
     * 设备完好率
     */
    private BigDecimal deviceIntactRate;

    /**
     * 压缩机负载率
     */
    private BigDecimal compressorLoadRate;

    /**
     * 平均管道压力
     */
    private BigDecimal avgPipePressure;

    /**
     * 实时用气量
     */
    private BigDecimal realTimeConsumption;
}