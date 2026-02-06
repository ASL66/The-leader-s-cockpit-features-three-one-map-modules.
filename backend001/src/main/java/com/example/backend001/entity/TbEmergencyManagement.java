package com.example.backend001.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 应急管理实体类
 * 对应数据库表 tb_emergency_management，存储应急管理相关的信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 * @TableName(value = "tb_emergency_management") - 指定对应的数据库表名
 */
@Data
@TableName(value = "tb_emergency_management")
public class TbEmergencyManagement {
    
    /**
     * 主键ID
     * 自动递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 应急队伍数量
     * 对应数据库字段 emergency_team_count
     */
    @TableField(value = "emergency_team_count")
    private Integer emergencyTeamCount;

    /**
     * 应急人员数量
     * 对应数据库字段 emergency_personnel_count
     */
    @TableField(value = "emergency_personnel_count")
    private Integer emergencyPersonnelCount;

    /**
     * 应急车辆数量
     * 对应数据库字段 emergency_vehicle_count
     */
    @TableField(value = "emergency_vehicle_count")
    private Integer emergencyVehicleCount;

    /**
     * 应急车辆在线数量
     * 对应数据库字段 emergency_vehicle_online_count
     */
    @TableField(value = "emergency_vehicle_online_count")
    private Integer emergencyVehicleOnlineCount;

    /**
     * 应急物资数量
     * 对应数据库字段 emergency_material_count
     */
    @TableField(value = "emergency_material_count")
    private Integer emergencyMaterialCount;

    /**
     * 应急储备数量
     * 对应数据库字段 emergency_reserve_count
     */
    @TableField(value = "emergency_reserve_count")
    private Integer emergencyReserveCount;

    /**
     * 储备点经度
     * 对应数据库字段 reserve_longitude
     */
    @TableField(value = "reserve_longitude")
    private BigDecimal reserveLongitude;

    /**
     * 储备点纬度
     * 对应数据库字段 reserve_latitude
     */
    @TableField(value = "reserve_latitude")
    private BigDecimal reserveLatitude;

    /**
     * 重大危险源数量
     * 对应数据库字段 major_hazard_count
     */
    @TableField(value = "major_hazard_count")
    private Integer majorHazardCount;

    /**
     * 重大危险源监测数量
     * 对应数据库字段 major_hazard_monitor_count
     */
    @TableField(value = "major_hazard_monitor_count")
    private Integer majorHazardMonitorCount;

    /**
     * 应急事件总数
     * 对应数据库字段 emergency_event_total
     */
    @TableField(value = "emergency_event_total")
    private Integer emergencyEventTotal;

    /**
     * 燃气泄漏事件数量
     * 对应数据库字段 emergency_event_type_gas_leak
     */
    @TableField(value = "emergency_event_type_gas_leak")
    private Integer emergencyEventTypeGasLeak;

    /**
     * 爆炸事件数量
     * 对应数据库字段 emergency_event_type_explosion
     */
    @TableField(value = "emergency_event_type_explosion")
    private Integer emergencyEventTypeExplosion;

    /**
     * 其他应急事件数量
     * 对应数据库字段 emergency_event_type_other
     */
    @TableField(value = "emergency_event_type_other")
    private Integer emergencyEventTypeOther;

    /**
     * 应急演练次数
     * 对应数据库字段 emergency_drill_times
     */
    @TableField(value = "emergency_drill_times")
    private Integer emergencyDrillTimes;

    /**
     * 应急演练合格率
     * 对应数据库字段 emergency_drill_qualified_rate
     */
    @TableField(value = "emergency_drill_qualified_rate")
    private BigDecimal emergencyDrillQualifiedRate;

    /**
     * 已处理应急事件数量
     * 对应数据库字段 emergency_event_processed_count
     */
    @TableField(value = "emergency_event_processed_count")
    private Integer emergencyEventProcessedCount;

    /**
     * 应急事件处理率
     * 对应数据库字段 emergency_event_processing_rate
     */
    @TableField(value = "emergency_event_processing_rate")
    private BigDecimal emergencyEventProcessingRate;

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