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
 * 管网信息实体类
 * 对应数据库表 tb_pipe_network_info，存储管网相关的信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 * @TableName(value = "tb_pipe_network_info") - 指定对应的数据库表名
 */
@Data
@TableName(value = "tb_pipe_network_info")
public class TbPipeNetworkInfo {
    
    /**
     * 主键ID
     * 自动递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 管道ID
     * 对应数据库字段 pipe_id
     */
    @TableField(value = "pipe_id")
    private String pipeId;

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
     * 管段编码
     * 对应数据库字段 pipe_segment_code
     */
    @TableField(value = "pipe_segment_code")
    private String pipeSegmentCode;

    /**
     * 起点经度
     * 对应数据库字段 start_longitude
     */
    @TableField(value = "start_longitude")
    private BigDecimal startLongitude;

    /**
     * 起点纬度
     * 对应数据库字段 start_latitude
     */
    @TableField(value = "start_latitude")
    private BigDecimal startLatitude;

    /**
     * 终点经度
     * 对应数据库字段 end_longitude
     */
    @TableField(value = "end_longitude")
    private BigDecimal endLongitude;

    /**
     * 终点纬度
     * 对应数据库字段 end_latitude
     */
    @TableField(value = "end_latitude")
    private BigDecimal endLatitude;

    /**
     * 管道长度
     * 对应数据库字段 pipe_length
     */
    @TableField(value = "pipe_length")
    private BigDecimal pipeLength;

    /**
     * 压力等级
     * 对应数据库字段 pressure_level
     */
    @TableField(value = "pressure_level")
    private String pressureLevel;

    /**
     * 管道直径
     * 对应数据库字段 pipe_diameter
     */
    @TableField(value = "pipe_diameter")
    private BigDecimal pipeDiameter;

    /**
     * 管道材质
     * 对应数据库字段 pipe_material
     */
    @TableField(value = "pipe_material")
    private String pipeMaterial;

    /**
     * 敷设日期
     * 对应数据库字段 laying_date
     */
    @TableField(value = "laying_date")
    private Date layingDate;

    /**
     * 敷设方式
     * 对应数据库字段 laying_method
     */
    @TableField(value = "laying_method")
    private String layingMethod;

    /**
     * 设备状态
     * 对应数据库字段 device_status
     */
    @TableField(value = "device_status")
    private String deviceStatus;

    /**
     * 每日巡检长度
     * 对应数据库字段 daily_inspection_length
     */
    @TableField(value = "daily_inspection_length")
    private BigDecimal dailyInspectionLength;

    /**
     * 非每日巡检长度
     * 对应数据库字段 non_daily_inspection_length
     */
    @TableField(value = "non_daily_inspection_length")
    private BigDecimal nonDailyInspectionLength;

    /**
     * 巡检趋势
     * 对应数据库字段 inspection_trend
     */
    @TableField(value = "inspection_trend")
    private String inspectionTrend;

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