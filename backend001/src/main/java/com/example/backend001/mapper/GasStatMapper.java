package com.example.backend001.mapper;

import com.example.backend001.vo.GasStatVO;
import org.apache.ibatis.annotations.Select;

/**
 * 燃气统计数据访问接口
 * 负责获取燃气系统的核心统计数据
 */
public interface GasStatMapper {
    /**
     * 查询燃气核心统计数据
     * 包含企业数量、站点数量、管网总长度、用户总数、隐患总数、已整改隐患数量、报警数量等信息
     * 通过多个子查询获取各表的统计数据
     * @return 燃气核心统计数据对象
     */
    @Select("SELECT " +
            "(SELECT COUNT(*) FROM tb_enterprise_info) as enterpriseCount, " +
            "(SELECT COUNT(*) FROM tb_station_info) as stationCount, " +
            "(SELECT SUM(pipe_network_length) FROM tb_basic_info) as totalPipeLength, " +
            "(SELECT SUM(user_total_count) FROM tb_basic_info) as totalUserCount, " +
            "(SELECT SUM(hidden_danger_total) FROM tb_safety_supervision) as totalHiddenDanger, " +
            "(SELECT SUM(hidden_danger_rectified) FROM tb_safety_supervision) as rectifiedDanger, " +
            "(SELECT COUNT(*) FROM tb_operation_monitor WHERE alarm_count>0) as alarmCount")
    GasStatVO selectGasCoreStat();
}