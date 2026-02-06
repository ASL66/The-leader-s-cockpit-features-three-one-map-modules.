package com.example.backend001.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 基础信息实体类
 * 对应数据库表 tb_basic_info
 * 存储燃气系统的基础统计信息，按区域划分
 */
@Data
@TableName(value = "tb_basic_info")
public class TbBasicInfo {
    
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

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
     * 企业数量
     */
    @TableField(value = "enterprise_count")
    private Integer enterpriseCount;

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
     * 建设项目数量
     */
    @TableField(value = "construction_project_count")
    private Integer constructionProjectCount;

    /**
     * 用户总数
     */
    @TableField(value = "user_total_count")
    private Integer userTotalCount;

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
     * 老旧管网长度
     */
    @TableField(value = "old_pipe_network_length")
    private BigDecimal oldPipeNetworkLength;

    /**
     * 老旧管网区域
     */
    @TableField(value = "old_pipe_network_region")
    private String oldPipeNetworkRegion;

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