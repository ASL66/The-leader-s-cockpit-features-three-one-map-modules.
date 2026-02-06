package com.example.backend001.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 场站信息实体类
 * 对应数据库表 tb_station_info，存储场站相关的信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 * @TableName(value = "tb_station_info") - 指定对应的数据库表名
 */
@Data
@TableName(value = "tb_station_info")
public class TbStationInfo {
    
    /**
     * 主键ID
     * 自动递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 站点ID
     * 对应数据库字段 station_id
     */
    @TableField(value = "station_id")
    private String stationId;

    /**
     * 站点名称
     * 对应数据库字段 station_name
     */
    @TableField(value = "station_name")
    private String stationName;

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
     * 站点类型
     * 对应数据库字段 station_type
     */
    @TableField(value = "station_type")
    private String stationType;

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
     * 地址
     * 对应数据库字段 address
     */
    @TableField(value = "address")
    private String address;

    /**
     * 经度
     * 对应数据库字段 longitude
     */
    @TableField(value = "longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     * 对应数据库字段 latitude
     */
    @TableField(value = "latitude")
    private BigDecimal latitude;

    /**
     * 供气能力
     * 对应数据库字段 gas_supply_capacity
     */
    @TableField(value = "gas_supply_capacity")
    private BigDecimal gasSupplyCapacity;

    /**
     * 年用气峰值
     * 对应数据库字段 annual_gas_peak
     */
    @TableField(value = "annual_gas_peak")
    private BigDecimal annualGasPeak;

    /**
     * 服务半径
     * 对应数据库字段 service_radius
     */
    @TableField(value = "service_radius")
    private BigDecimal serviceRadius;

    /**
     * 物联网设备数量
     * 对应数据库字段 iot_device_count
     */
    @TableField(value = "iot_device_count")
    private Integer iotDeviceCount;

    /**
     * 正常设备数量
     * 对应数据库字段 normal_device_count
     */
    @TableField(value = "normal_device_count")
    private Integer normalDeviceCount;

    /**
     * 告警设备数量
     * 对应数据库字段 warning_device_count
     */
    @TableField(value = "warning_device_count")
    private Integer warningDeviceCount;

    /**
     * 安全检查次数
     * 对应数据库字段 safety_inspection_times
     */
    @TableField(value = "safety_inspection_times")
    private Integer safetyInspectionTimes;

    /**
     * 隐患数量
     * 对应数据库字段 hidden_danger_count
     */
    @TableField(value = "hidden_danger_count")
    private Integer hiddenDangerCount;

    /**
     * 已整改隐患数量
     * 对应数据库字段 rectified_danger_count
     */
    @TableField(value = "rectified_danger_count")
    private Integer rectifiedDangerCount;

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