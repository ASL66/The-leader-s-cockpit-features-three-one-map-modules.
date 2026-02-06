package com.example.backend001.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 运行监测实体类
 * 对应数据库表 tb_operation_monitor，存储运行监测相关的信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 * @TableName(value = "tb_operation_monitor") - 指定对应的数据库表名
 */
@Data
@TableName(value = "tb_operation_monitor")
public class TbOperationMonitor {
    
    /**
     * 主键ID
     * 自动递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 统计日期
     * 对应数据库字段 statistics_date
     */
    @TableField(value = "statistics_date")
    private Date statisticsDate;

    /**
     * 统计类型
     * 对应数据库字段 statistics_type
     */
    @TableField(value = "statistics_type")
    private String statisticsType;

    /**
     * 区域编码
     * 对应数据库字段 region_code
     */
    @TableField(value = "region_code")
    private String regionCode;

    /**
     * 区域名称
     * 对应数据库字段 region_name
     */
    @TableField(value = "region_name")
    private String regionName;

    /**
     * 企业ID
     * 对应数据库字段 enterprise_id
     */
    @TableField(value = "enterprise_id")
    private String enterpriseId;

    /**
     * 企业名称
     * 对应数据库字段 enterprise_name
     */
    @TableField(value = "enterprise_name")
    private String enterpriseName;

    /**
     * 用户增量
     * 对应数据库字段 user_increment
     */
    @TableField(value = "user_increment")
    private Integer userIncrement;

    /**
     * 居民用户增量
     * 对应数据库字段 resident_user_increment
     */
    @TableField(value = "resident_user_increment")
    private Integer residentUserIncrement;

    /**
     * 商业用户增量
     * 对应数据库字段 commercial_user_increment
     */
    @TableField(value = "commercial_user_increment")
    private Integer commercialUserIncrement;

    /**
     * 企业用户增量
     * 对应数据库字段 enterprise_user_increment
     */
    @TableField(value = "enterprise_user_increment")
    private Integer enterpriseUserIncrement;

    /**
     * 总用气量
     * 对应数据库字段 total_gas_consumption
     */
    @TableField(value = "total_gas_consumption")
    private BigDecimal totalGasConsumption;

    /**
     * 居民用气量
     * 对应数据库字段 resident_gas_consumption
     */
    @TableField(value = "resident_gas_consumption")
    private BigDecimal residentGasConsumption;

    /**
     * 商业用气量
     * 对应数据库字段 commercial_gas_consumption
     */
    @TableField(value = "commercial_gas_consumption")
    private BigDecimal commercialGasConsumption;

    /**
     * 企业用气量
     * 对应数据库字段 enterprise_gas_consumption
     */
    @TableField(value = "enterprise_gas_consumption")
    private BigDecimal enterpriseGasConsumption;

    /**
     * 用气排名
     * 对应数据库字段 gas_consumption_rank
     */
    @TableField(value = "gas_consumption_rank")
    private Integer gasConsumptionRank;

    /**
     * 预测总用气量
     * 对应数据库字段 predicted_total_consumption
     */
    @TableField(value = "predicted_total_consumption")
    private BigDecimal predictedTotalConsumption;

    /**
     * 预测商业用气量
     * 对应数据库字段 predicted_commercial_consumption
     */
    @TableField(value = "predicted_commercial_consumption")
    private BigDecimal predictedCommercialConsumption;

    /**
     * 预测企业用气量
     * 对应数据库字段 predicted_enterprise_consumption
     */
    @TableField(value = "predicted_enterprise_consumption")
    private BigDecimal predictedEnterpriseConsumption;

    /**
     * 平均管道压力
     * 对应数据库字段 pipe_pressure_avg
     */
    @TableField(value = "pipe_pressure_avg")
    private BigDecimal pipePressureAvg;

    /**
     * 日压力
     * 对应数据库字段 daily_pressure
     */
    @TableField(value = "daily_pressure")
    private BigDecimal dailyPressure;

    /**
     * 告警数量
     * 对应数据库字段 alarm_count
     */
    @TableField(value = "alarm_count")
    private Integer alarmCount;

    /**
     * 告警处理率
     * 对应数据库字段 alarm_processing_rate
     */
    @TableField(value = "alarm_processing_rate")
    private BigDecimal alarmProcessingRate;

    /**
     * 压缩机负载率
     * 对应数据库字段 compressor_load_rate
     */
    @TableField(value = "compressor_load_rate")
    private BigDecimal compressorLoadRate;

    /**
     * 设备健康分数
     * 对应数据库字段 device_health_score
     */
    @TableField(value = "device_health_score")
    private BigDecimal deviceHealthScore;

    /**
     * 设备完好率
     * 对应数据库字段 device_intact_rate
     */
    @TableField(value = "device_intact_rate")
    private BigDecimal deviceIntactRate;

    /**
     * 监控URL
     * 对应数据库字段 surveillance_url
     */
    @TableField(value = "surveillance_url")
    private String surveillanceUrl;

    /**
     * 设备振动值
     * 对应数据库字段 equipment_vibration
     */
    @TableField(value = "equipment_vibration")
    private BigDecimal equipmentVibration;

    /**
     * 环境温度
     * 对应数据库字段 environmental_temp
     */
    @TableField(value = "environmental_temp")
    private BigDecimal environmentalTemp;

    /**
     * 创建时间
     * 对应数据库字段 create_time
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     * 对应数据库字段 update_time
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}