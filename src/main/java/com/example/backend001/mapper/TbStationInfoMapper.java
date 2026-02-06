package com.example.backend001.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.backend001.entity.TbStationInfo;
import com.example.backend001.vo.StationMapVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 站点信息数据访问接口
 * 继承自MyBatis-Plus的BaseMapper，提供站点信息的CRUD操作
 */
public interface TbStationInfoMapper extends BaseMapper<TbStationInfo> {

    /**
     * 查询站点地图数据
     * 包含站点ID、站点名称、站点类型、企业名称、供气能力、经度、纬度、隐患数量、物联网设备数量、正常设备数量、状态等信息
     * 状态字段根据预警设备数量判断，大于0为1（有预警），否则为0（正常）
     * 只查询有经纬度的站点
     * @return 站点地图数据列表
     */
    @Select("SELECT " +
            "t.station_id, " +
            "t.station_name, " +
            "t.station_type, " +
            "t.enterprise_name, " +
            "t.gas_supply_capacity, " +
            "t.longitude, " +
            "t.latitude, " +
            "t.hidden_danger_count, " +
            "t.iot_device_count, " +
            "t.normal_device_count, " +
            "IF(t.warning_device_count>0,1,0) as status " +
            "FROM tb_station_info t " +
            "WHERE t.longitude IS NOT NULL AND t.latitude IS NOT NULL")
    List<StationMapVO> selectStationMapData();
}