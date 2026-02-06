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
 * 项目管理实体类
 * 对应数据库表 tb_project_management，存储项目管理相关的信息
 * 
 * 注解说明：
 * @Data - 自动生成 getter、setter、toString 等方法
 * @TableName(value = "tb_project_management") - 指定对应的数据库表名
 */
@Data
@TableName(value = "tb_project_management")
public class TbProjectManagement {
    
    /**
     * 主键ID
     * 自动递增
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 项目ID
     * 对应数据库字段 project_id
     */
    @TableField(value = "project_id")
    private String projectId;

    /**
     * 项目名称
     * 对应数据库字段 project_name
     */
    @TableField(value = "project_name")
    private String projectName;

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
     * 项目地址
     * 对应数据库字段 project_address
     */
    @TableField(value = "project_address")
    private String projectAddress;

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
     * 项目规模
     * 对应数据库字段 project_scale
     */
    @TableField(value = "project_scale")
    private String projectScale;

    /**
     * 项目类型
     * 对应数据库字段 project_type
     */
    @TableField(value = "project_type")
    private String projectType;

    /**
     * 申请时间
     * 对应数据库字段 apply_time
     */
    @TableField(value = "apply_time")
    private Date applyTime;

    /**
     * 审批状态
     * 对应数据库字段 approval_status
     */
    @TableField(value = "approval_status")
    private String approvalStatus;

    /**
     * 审批时间
     * 对应数据库字段 approval_time
     */
    @TableField(value = "approval_time")
    private Date approvalTime;

    /**
     * 施工开始时间
     * 对应数据库字段 construction_start_time
     */
    @TableField(value = "construction_start_time")
    private Date constructionStartTime;

    /**
     * 施工进度
     * 对应数据库字段 construction_progress
     */
    @TableField(value = "construction_progress")
    private String constructionProgress;

    /**
     * 施工人员数量
     * 对应数据库字段 construction_personnel_count
     */
    @TableField(value = "construction_personnel_count")
    private Integer constructionPersonnelCount;

    /**
     * 整改意见数量
     * 对应数据库字段 rectification_opinions_count
     */
    @TableField(value = "rectification_opinions_count")
    private Integer rectificationOpinionsCount;

    /**
     * 竣工申请时间
     * 对应数据库字段 completion_apply_time
     */
    @TableField(value = "completion_apply_time")
    private Date completionApplyTime;

    /**
     * 竣工状态
     * 对应数据库字段 completion_status
     */
    @TableField(value = "completion_status")
    private String completionStatus;

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