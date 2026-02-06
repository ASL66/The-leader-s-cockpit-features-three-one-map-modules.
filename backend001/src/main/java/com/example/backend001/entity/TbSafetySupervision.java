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
 * 安全监管实体类
 * 对应数据库表 tb_safety_supervision，存储安全监管相关的信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 * @TableName(value = "tb_safety_supervision") - 指定对应的数据库表名
 */
@Data
@TableName(value = "tb_safety_supervision")
public class TbSafetySupervision {
    
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
     * 入户检查次数
     * 对应数据库字段 home_inspection_times
     */
    @TableField(value = "home_inspection_times")
    private Integer homeInspectionTimes;

    /**
     * 入户检查率
     * 对应数据库字段 home_inspection_rate
     */
    @TableField(value = "home_inspection_rate")
    private BigDecimal homeInspectionRate;

    /**
     * 场站检查次数
     * 对应数据库字段 station_inspection_times
     */
    @TableField(value = "station_inspection_times")
    private Integer stationInspectionTimes;

    /**
     * 管网检查次数
     * 对应数据库字段 pipe_inspection_times
     */
    @TableField(value = "pipe_inspection_times")
    private Integer pipeInspectionTimes;

    /**
     * 隐患总数
     * 对应数据库字段 hidden_danger_total
     */
    @TableField(value = "hidden_danger_total")
    private Integer hiddenDangerTotal;

    /**
     * 一级隐患数量
     * 对应数据库字段 hidden_danger_level_1
     */
    @TableField(value = "hidden_danger_level_1")
    private Integer hiddenDangerLevel1;

    /**
     * 二级隐患数量
     * 对应数据库字段 hidden_danger_level_2
     */
    @TableField(value = "hidden_danger_level_2")
    private Integer hiddenDangerLevel2;

    /**
     * 三级隐患数量
     * 对应数据库字段 hidden_danger_level_3
     */
    @TableField(value = "hidden_danger_level_3")
    private Integer hiddenDangerLevel3;

    /**
     * 管网隐患数量
     * 对应数据库字段 hidden_danger_type_pipe
     */
    @TableField(value = "hidden_danger_type_pipe")
    private Integer hiddenDangerTypePipe;

    /**
     * 场站隐患数量
     * 对应数据库字段 hidden_danger_type_station
     */
    @TableField(value = "hidden_danger_type_station")
    private Integer hiddenDangerTypeStation;

    /**
     * 用户端隐患数量
     * 对应数据库字段 hidden_danger_type_user
     */
    @TableField(value = "hidden_danger_type_user")
    private Integer hiddenDangerTypeUser;

    /**
     * 已整改隐患数量
     * 对应数据库字段 hidden_danger_rectified
     */
    @TableField(value = "hidden_danger_rectified")
    private Integer hiddenDangerRectified;

    /**
     * 隐患整改率
     * 对应数据库字段 hidden_danger_rectification_rate
     */
    @TableField(value = "hidden_danger_rectification_rate")
    private BigDecimal hiddenDangerRectificationRate;

    /**
     * 监管检查次数
     * 对应数据库字段 supervision_check_times
     */
    @TableField(value = "supervision_check_times")
    private Integer supervisionCheckTimes;

    /**
     * 监管违规数量
     * 对应数据库字段 supervision_violation_count
     */
    @TableField(value = "supervision_violation_count")
    private Integer supervisionViolationCount;

    /**
     * 安全事故数量
     * 对应数据库字段 safety_accident_count
     */
    @TableField(value = "safety_accident_count")
    private Integer safetyAccidentCount;

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