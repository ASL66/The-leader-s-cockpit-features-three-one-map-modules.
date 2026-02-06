package com.example.backend001.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend001.entity.TbBasicInfo;
import com.example.backend001.vo.RegionGasVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 基础信息数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供基础信息的CRUD操作
 */
public interface TbBasicInfoMapper extends BaseMapper<TbBasicInfo> {

    /**
     * 查询区域燃气地图数据
     * 包含区域编码、区域名称、用户总数、场站数量、管网长度、隐患数量、经度、纬度等信息
     * 经度和纬度使用随机值模拟
     * @return 区域燃气地图数据列表
     */
    @Select("SELECT " +
            "t.region_code, " +
            "t.region_name, " +
            "t.user_total_count, " +
            "t.station_count, " +
            "t.pipe_network_length, " +
            "t.old_pipe_network_length as hidden_danger_count, " +
            "ROUND(RAND()*0.05+117.2,6) as longitude, " +
            "ROUND(RAND()*0.08+25.0,6) as latitude " + 
            "FROM tb_basic_info t " +
            "ORDER BY t.region_code")
    List<RegionGasVO> selectRegionGasMapData();
}