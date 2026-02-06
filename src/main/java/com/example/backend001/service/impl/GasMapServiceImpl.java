package com.example.backend001.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.backend001.entity.*;
import com.example.backend001.mapper.*;
import com.example.backend001.service.GasMapService;
import com.example.backend001.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 燃气地图服务实现类
 * 负责处理燃气地图相关的数据获取、缓存操作和业务逻辑处理
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GasMapServiceImpl implements GasMapService {

    private final TbBasicInfoMapper basicInfoMapper;
    private final TbStationInfoMapper stationInfoMapper;
    private final GasStatMapper gasStatMapper;
    
    private final TbPipeNetworkInfoMapper pipeNetworkInfoMapper;
    private final TbProjectManagementMapper projectManagementMapper;
    private final TbEmergencyManagementMapper emergencyManagementMapper;
    private final TbOperationMonitorMapper operationMonitorMapper;
    private final TbEnterpriseInfoMapper enterpriseInfoMapper;
    private final TbSafetySupervisionMapper safetySupervisionMapper;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 缓存键：燃气地图总体数据
     */
    private static final String CACHE_KEY_MAP = "gas:map:total_data:v9";
    
    /**
     * 缓存过期时间（从配置文件读取）
     */
    @Value("${gas.cache.expire-time}")
    private Long expireTime;

    /**
     * 获取燃气地图总体数据
     * 1. 首先尝试从Redis缓存获取数据
     * 2. 如果缓存不存在或获取失败，从数据库查询并计算
     * 3. 计算完成后将数据存入缓存
     * 4. 返回燃气地图总体数据
     * @return 燃气地图总体数据对象
     */
    @Override
    public GasMapTotalVO getGasMapTotalData() {
        // 尝试从Redis缓存获取数据
        try {
            Object cacheObj = redisTemplate.opsForValue().get(CACHE_KEY_MAP);
            if (cacheObj instanceof GasMapTotalVO) {
                return (GasMapTotalVO) cacheObj;
            }
        } catch (Exception e) {
            log.error("Redis get failed: {}", e.getMessage());
        }

        // 缓存不存在或获取失败，从数据库查询并计算
        GasMapTotalVO result = new GasMapTotalVO();
        
        // 获取区域燃气数据
        List<RegionGasVO> regionList = basicInfoMapper.selectRegionGasMapData();
        
        // 获取站点地图数据
        List<StationMapVO> stationList = stationInfoMapper.selectStationMapData();
        
        // 获取燃气核心统计数据
        GasStatVO statInfo = gasStatMapper.selectGasCoreStat();

        // 设置管网、项目、应急、企业、安全监管等数据列表
        result.setPipeList(pipeNetworkInfoMapper.selectList(null));
        result.setProjectList(projectManagementMapper.selectList(null));
        result.setEmergencyList(emergencyManagementMapper.selectList(null));
        result.setEnterpriseList(enterpriseInfoMapper.selectList(null));
        result.setSafetyList(safetySupervisionMapper.selectList(null));
        
        // 获取运行监测数据并按日期排序
        QueryWrapper<TbOperationMonitor> monitorQuery = new QueryWrapper<>();
        monitorQuery.orderByAsc("statistics_date");
        result.setMonitorList(operationMonitorMapper.selectList(monitorQuery));
        
        // 设置区域和站点数据
        result.setRegionList(regionList);
        result.setStationList(stationList);
        
        // 初始化统计信息对象
        if (statInfo == null) {
            statInfo = new GasStatVO();
        }

        // 计算运行监测相关统计数据
        List<TbOperationMonitor> monitors = result.getMonitorList();
        if (monitors != null && !monitors.isEmpty()) {
            // 获取最新日期的运行监测数据
            Date latestDate = monitors.get(monitors.size() - 1).getStatisticsDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String latestDateStr = sdf.format(latestDate);

            BigDecimal totalConsumption = BigDecimal.ZERO;
            BigDecimal totalPressure = BigDecimal.ZERO;
            int pressureCount = 0;

            // 计算当天的总用气量和平均压力
            for (TbOperationMonitor m : monitors) {
                if (m.getStatisticsDate() != null && sdf.format(m.getStatisticsDate()).equals(latestDateStr)) {
                    if (m.getTotalGasConsumption() != null) {
                        totalConsumption = totalConsumption.add(m.getTotalGasConsumption());
                    }
                    if (m.getPipePressureAvg() != null) {
                        totalPressure = totalPressure.add(m.getPipePressureAvg());
                        pressureCount++;
                    }
                }
            }

            // 设置当天供气总量
            statInfo.setTodayGasSupply(totalConsumption);
            
            // 计算实时用气量（估算值）
            statInfo.setRealTimeConsumption(totalConsumption.divide(new BigDecimal("24"), 2, java.math.RoundingMode.HALF_UP).multiply(new BigDecimal("1.5")));
            
            // 计算平均管道压力
            if (pressureCount > 0) {
                statInfo.setAvgPipePressure(totalPressure.divide(new BigDecimal(pressureCount), 2, java.math.RoundingMode.HALF_UP));
            }
        }

        // 计算设备完好率
        if (stationList != null && !stationList.isEmpty()) {
            int normalDev = 0;
            int totalDev = 0;
            for (StationMapVO s : stationList) {
                if (s.getNormalDeviceCount() != null) normalDev += s.getNormalDeviceCount();
                if (s.getIotDeviceCount() != null) totalDev += s.getIotDeviceCount();
            }
            
            if (totalDev > 0) {
                statInfo.setEquipmentIntegrityRate(new BigDecimal(normalDev).multiply(new BigDecimal("100")).divide(new BigDecimal(totalDev), 2, java.math.RoundingMode.HALF_UP));
            } else {
                statInfo.setEquipmentIntegrityRate(new BigDecimal("100"));
            }
        } else {
            statInfo.setEquipmentIntegrityRate(new BigDecimal("100"));
        }

        // 设置运行监测相关的附加统计数据
        statInfo.setCompressorComplianceRate(new BigDecimal("99.8"));
        statInfo.setSafeOperationDays(1258);
        statInfo.setDeviceHealthScore(new BigDecimal("95.5"));
        statInfo.setDeviceIntactRate(new BigDecimal("99.1"));
        statInfo.setCompressorLoadRate(new BigDecimal("88.2"));
        
        // 从最新的运行监测数据中更新设备健康分数、压缩机负载率和设备完好率
        if (monitors != null && !monitors.isEmpty()) {
            TbOperationMonitor latest = monitors.get(monitors.size() - 1);
            if (latest.getDeviceHealthScore() != null) statInfo.setDeviceHealthScore(latest.getDeviceHealthScore());
            if (latest.getCompressorLoadRate() != null) statInfo.setCompressorLoadRate(latest.getCompressorLoadRate());
            if (latest.getDeviceIntactRate() != null) statInfo.setDeviceIntactRate(latest.getDeviceIntactRate());
        }

        // 设置统计信息到结果对象
        result.setStatInfo(statInfo);
        
        // 将结果存入Redis缓存
        try {
            redisTemplate.opsForValue().set(CACHE_KEY_MAP, result, expireTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Redis set failed: {}", e.getMessage());
        }

        return result;
    }

    /**
     * 刷新燃气地图总体数据缓存
     * 1. 删除Redis缓存
     * 2. 重新获取数据并存入缓存
     */
    @Override
    public void refreshGasMapTotalData() {
        try {
            redisTemplate.delete(CACHE_KEY_MAP);
        } catch (Exception e) {
            log.error("Redis delete failed: {}", e.getMessage());
        }
        getGasMapTotalData();
    }

    /**
     * 获取运行监测列表数据
     * 按统计日期升序排序
     * @return 运行监测数据列表
     */
    @Override
    public List<TbOperationMonitor> getOperationMonitorList() {
        QueryWrapper<TbOperationMonitor> monitorQuery = new QueryWrapper<>();
        monitorQuery.orderByAsc("statistics_date");
        return operationMonitorMapper.selectList(monitorQuery);
    }
}
