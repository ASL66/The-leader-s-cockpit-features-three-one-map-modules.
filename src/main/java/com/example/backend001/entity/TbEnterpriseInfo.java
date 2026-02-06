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
 * 企业信息实体类
 * 对应数据库表 tb_enterprise_info
 * 存储燃气企业的详细信息
 */
@Data
@TableName(value = "tb_enterprise_info")
public class TbEnterpriseInfo {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 企业ID
     */
    @TableField(value = "enterprise_id")
    private String enterpriseId;

    /**
     * 企业名称
     */
    @TableField(value = "enterprise_name")
    private String enterpriseName;

    /**
     * 法人
     */
    @TableField(value = "legal_person")
    private String legalPerson;

    /**
     * 经营范围
     */
    @TableField(value = "business_scope")
    private String businessScope;

    /**
     * 燃气经营许可证号
     */
    @TableField(value = "gas_license_no")
    private String gasLicenseNo;

    /**
     * 燃气经营许可证有效期
     */
    @TableField(value = "gas_license_validity")
    private Date gasLicenseValidity;

    /**
     * 区域编码
     */
    @TableField(value = "region_code")
    private String regionCode;

    /**
     * 区域名称
     */
    @TableField(value = "region_name")
    private String regionName;

    /**
     * 场站数量
     */
    @TableField(value = "station_count")
    private Integer stationCount;

    /**
     * 管网长度
     */
    @TableField(value = "pipe_network_length")
    private BigDecimal pipeNetworkLength;

    /**
     * 服务网点数量
     */
    @TableField(value = "service_outlet_count")
    private Integer serviceOutletCount;

    /**
     * 用户总数
     */
    @TableField(value = "user_count")
    private Integer userCount;

    /**
     * 居民用户数量
     */
    @TableField(value = "resident_user_count")
    private Integer residentUserCount;

    /**
     * 商业用户数量
     */
    @TableField(value = "commercial_user_count")
    private Integer commercialUserCount;

    /**
     * 企业用户数量
     */
    @TableField(value = "enterprise_user_count")
    private Integer enterpriseUserCount;

    /**
     * 安全管理人员数量
     */
    @TableField(value = "safety_manager_count")
    private Integer safetyManagerCount;

    /**
     * 安全管理人员姓名
     */
    @TableField(value = "safety_manager_names")
    private String safetyManagerNames;

    /**
     * 应急人员数量
     */
    @TableField(value = "emergency_personnel_count")
    private Integer emergencyPersonnelCount;

    /**
     * 应急人员姓名
     */
    @TableField(value = "emergency_personnel_names")
    private String emergencyPersonnelNames;

    /**
     * 应急车辆数量
     */
    @TableField(value = "emergency_vehicle_count")
    private Integer emergencyVehicleCount;

    /**
     * 应急物资数量
     */
    @TableField(value = "emergency_material_count")
    private Integer emergencyMaterialCount;

    /**
     * 总用气量
     */
    @TableField(value = "total_gas_sales")
    private BigDecimal totalGasSales;

    /**
     * 数据接入率
     */
    @TableField(value = "data_access_rate")
    private BigDecimal dataAccessRate;

    /**
     * 安全检查率
     */
    @TableField(value = "safety_inspection_rate")
    private BigDecimal safetyInspectionRate;

    /**
     * 隐患整改率
     */
    @TableField(value = "hidden_danger_rectification_rate")
    private BigDecimal hiddenDangerRectificationRate;

    /**
     * 违规次数
     */
    @TableField(value = "violation_count")
    private Integer violationCount;

    /**
     * 事故次数
     */
    @TableField(value = "accident_count")
    private Integer accidentCount;

    /**
     * 综合评分
     */
    @TableField(value = "comprehensive_score")
    private BigDecimal comprehensiveScore;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
