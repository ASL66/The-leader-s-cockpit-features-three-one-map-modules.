<template>
  <!-- 运行监测调度中心页面 -->
  <!-- 功能说明：展示燃气系统运行状态的实时监测数据，包括报警、供气量、监测点位、管网压力等核心指标 -->
  <!-- 布局结构：顶部为页面标题和时间显示，主体为多个监测数据面板和图表 -->
  <div class="monitor-container dark-theme">
    <!-- 页面头部：包含标题和当前时间 -->
    <div class="header">
      <div class="title">
        <el-icon class="icon-pulse"><Monitor /></el-icon> 运行监测调度中心
      </div>
      <div class="header-right">
        <div class="time"><el-icon><Timer /></el-icon> {{ currentTime }}</div>
      </div>
    </div>
    
    <!-- 网格布局，包含多个监测板块 -->
    <div class="bento-grid">
      <!-- 1. 核心统计数据板块 -->
      <div class="grid-item core-stats clickable" @click="openDialog('MONITOR_DETAILS')">
        <div class="stat-mini-grid">
          <!-- 实时报警 -->
          <div class="stat-mini-card warning" @click.stop="openDialog('TOTAL_ALARM')">
            <div class="card-label">实时报警</div>
            <div class="card-value">{{ totalAlarmCount }}</div>
            <div class="spark-mini" ref="sparkAlarmRef"></div>
          </div>
          <!-- 今日供气量 -->
          <div class="stat-mini-card success" @click.stop="openDialog('TODAY_SUPPLY')">
            <div class="card-label">今日供气量</div>
            <div class="card-value">{{ todayTotalGas }}<span class="unit">m³</span></div>
            <div class="spark-mini" ref="sparkSupplyRef"></div>
          </div>
          <!-- 监测点位 -->
          <div class="stat-mini-card primary" @click.stop="openDialog('MONITOR_POINT')">
            <div class="card-label">监测点位</div>
            <div class="card-value">{{ monitorPointCount }}</div>
            <div class="spark-mini" ref="sparkPointRef"></div>
          </div>
          <!-- 管网压力 -->
          <div class="stat-mini-card info" @click.stop="openDialog('PIPE_PRESSURE')">
            <div class="card-label">管网压力</div>
            <div class="card-value">
              <span>{{ statInfo.avgPipePressure ?? '—' }}</span>
              <span v-if="statInfo.avgPipePressure !== null && statInfo.avgPipePressure !== undefined" class="unit">MPa</span>
            </div>
            <div class="spark-mini" ref="sparkPressureRef"></div>
          </div>
        </div>
      </div>

      <!-- 2. 报警分类分布 (中上) -->
      <div class="grid-item alarm-dist clickable" @click="openDialog('ALARM_ANALYSIS')">
        <div class="panel-header">
          <div class="header-left"><el-icon><PieChart /></el-icon> 报警分类分布 (饼图)</div>
        </div>
        <div ref="alarmChartRef" class="chart-container"></div>
      </div>

      <!-- 3. 系统健康度 (右上) -->
      <div class="grid-item health-gauge clickable" @click="openDialog('DEVICE_HEALTH')">
        <div class="panel-header">
          <div class="header-left"><el-icon><Compass /></el-icon> 系统健康评分 (仪表盘)</div>
        </div>
        <div ref="gaugeHealthRef" class="chart-container"></div>
      </div>

      <!-- 4. 供气态势对比 (中左) -->
      <div class="grid-item supply-trend clickable" @click="openDialog('SUPPLY_SITUATION')">
        <div class="panel-header">
          <div class="header-left"><el-icon><DataAnalysis /></el-icon> 区域供气对比 (柱状图)</div>
        </div>
        <div class="chart-container-wrapper">
          <div ref="supplyChartRef" class="chart-container"></div>
        </div>
      </div>

      <!-- 5. 年度报警热力 (正中) -->
      <div class="grid-item calendar-heatmap clickable" @click="openDialog('CALENDAR_HEATMAP')">
        <div class="panel-header">
          <div class="header-left"><el-icon><Calendar /></el-icon> 运行压力日历热力图 (热力图)</div>
        </div>
        <div ref="calendarChartRef" class="chart-container"></div>
      </div>

      <!-- 6. 气源流向分析 (中右) -->
      <div class="grid-item flow-analysis clickable" @click="openDialog('FLOW_ANALYSIS')">
        <div class="panel-header">
          <div class="header-left"><el-icon><Connection /></el-icon> 气源供应流向 (桑基图)</div>
        </div>
        <div ref="sankeyChartRef" class="chart-container"></div>
      </div>

      <!-- 7. 压力分布盒须 (下左) -->
      <div class="grid-item boxplot-dist clickable" @click="openDialog('BOXPLOT_DIST')">
        <div class="panel-header">
          <div class="header-left"><el-icon><Histogram /></el-icon> 区域压力分布区间 (盒须图)</div>
        </div>
        <div ref="boxplotChartRef" class="chart-container"></div>
      </div>

      <!-- 8. 多维综合雷达 (下中) -->
      <div class="grid-item radar-compare clickable" @click="openDialog('RADAR_COMPARE')">
        <div class="panel-header">
          <div class="header-left"><el-icon><TrendCharts /></el-icon> 区域综合特征雷达 (雷达图)</div>
        </div>
        <div ref="radarChartRef" class="chart-container"></div>
      </div>

      <!-- 9. 负载健康散点 (下右) -->
      <div class="grid-item scatter-depth clickable" @click="openDialog('SCATTER_DEPTH')">
        <div class="panel-header">
          <div class="header-left"><el-icon><Odometer /></el-icon> 负载/完好率聚类 (散点图)</div>
        </div>
        <div ref="scatterChartRef" class="chart-container"></div>
      </div>

      <!-- 10. 底部宽幅：平行坐标分析 (最底) -->
      <div class="grid-item parallel-full clickable" @click="openDialog('PARALLEL_ANALYSIS')">
        <div class="panel-header">
          <div class="header-left"><el-icon><Share /></el-icon> 运行多维平行指标分析 (平行坐标图)</div>
        </div>
        <div ref="parallelChartRef" class="chart-container"></div>
      </div>
    </div>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="70%" class="monitor-dialog" @opened="handleDialogOpened">
      <div v-if="dialogType === 'CONSUMPTION_TREND'" class="dialog-chart-container">
        <div ref="dialogChartRef" class="dialog-echarts"></div>
      </div>
      <el-table v-else-if="dialogType === 'PRESSURE_TREND'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="date" label="时间" />
        <el-table-column prop="value" label="用气量(m³)" />
      </el-table>
      
      <el-table v-if="dialogType === 'PRESSURE_TREND'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="date" label="时间" />
        <el-table-column prop="value" label="平均压力(MPa)" />
      </el-table>
      
      <el-table v-if="dialogType === 'ALARM_ANALYSIS' || dialogType === 'TOTAL_ALARM'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="name" label="区域名称" />
        <el-table-column prop="value" label="报警数量" />
      </el-table>

      <el-table v-if="dialogType === 'PROCESSING_PROGRESS' || dialogType === 'PROCESSING_RATE'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域名称" />
        <el-table-column prop="count" label="报警数量" />
        <el-table-column prop="rate" label="处置率(%)" />
      </el-table>

      <el-table v-if="dialogType === 'MONITOR_DETAILS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="regionName" label="区域名称" />
        <el-table-column prop="pressure" label="平均压力(MPa)" />
        <el-table-column prop="alarmCount" label="报警数量" />
        <el-table-column prop="processingRate" label="处置率(%)" />
        <el-table-column prop="status" label="状态">
          <template #default="scope">
            <el-tag :type="scope.row.alarmCount === 0 ? 'success' : 'danger'">{{ scope.row.alarmCount === 0 ? '正常' : '报警' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="time" label="统计时间" />
      </el-table>

      <!-- 监测点位详情 - 包含地图和表格 -->
      <div v-if="dialogType === 'MONITOR_POINT'" class="monitor-point-container">
        <!-- 高德地图容器 -->
        <div ref="mapContainerRef" class="map-container"></div>
        <!-- 监测点位表格 -->
        <el-table :data="dialogData" style="width: 100%" height="300" :stripe="false" class="mt-4">
          <el-table-column prop="name" label="场站名称" />
          <el-table-column prop="iotCount" label="设备总数" />
          <el-table-column prop="normalCount" label="正常设备数" />
          <el-table-column prop="rate" label="在线率" />
        </el-table>
      </div>
      
      <!-- 设备在线详情 -->
      <el-table v-if="dialogType === 'DEVICE_ONLINE'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="name" label="场站名称" />
        <el-table-column prop="iotCount" label="设备总数" />
        <el-table-column prop="normalCount" label="正常设备数" />
        <el-table-column prop="rate" label="在线率" />
      </el-table>
      
      <el-table v-if="dialogType === 'TODAY_SUPPLY'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="regionName" label="区域名称" />
        <el-table-column prop="value" label="今日供气量(m³)" />
      </el-table>
      
      <el-table v-if="dialogType === 'SUPPLY_SITUATION'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="regionName" label="区域名称" />
        <el-table-column prop="value" label="累计用气量(m³)" />
      </el-table>
      
      <el-table v-if="dialogType === 'FAULT_HISTORY'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="regionName" label="区域名称" />
        <el-table-column prop="alarmCount" label="报警数量" />
        <el-table-column prop="time" label="时间" />
      </el-table>

      <el-table v-if="dialogType === 'COMPRESSOR_LOAD'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域名称" />
        <el-table-column prop="value" label="负荷率(%)" />
        <el-table-column prop="time" label="时间" />
      </el-table>

      <el-table v-if="dialogType === 'DEVICE_HEALTH'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域名称" />
        <el-table-column prop="value" label="健康得分" />
        <el-table-column prop="status" label="状态" />
      </el-table>

      <el-table v-if="dialogType === 'DEVICE_INTACT'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域名称" />
        <el-table-column prop="value" label="完好率(%)" />
        <el-table-column prop="time" label="时间" />
      </el-table>

      <el-table v-if="dialogType === 'PIPE_PRESSURE'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域名称" />
        <el-table-column prop="value" label="压力(MPa)" />
        <el-table-column prop="time" label="时间" />
      </el-table>

      <el-table v-if="dialogType === 'CALENDAR_HEATMAP'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="date" label="日期" />
        <el-table-column prop="region" label="区域" />
        <el-table-column prop="value" label="平均压力(MPa)" />
      </el-table>

      <el-table v-if="dialogType === 'FLOW_ANALYSIS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域名称" />
        <el-table-column prop="consumption" label="累计消耗(m³)" />
        <el-table-column prop="predicted" label="预测需求(m³)" />
      </el-table>

      <el-table v-if="dialogType === 'BOXPLOT_DIST' || dialogType === 'RADAR_COMPARE' || dialogType === 'SCATTER_DEPTH' || dialogType === 'PARALLEL_ANALYSIS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="region" label="区域" width="120" />
        <el-table-column prop="pressure" label="压力(MPa)" align="center" />
        <el-table-column prop="alarm" label="报警" align="center" />
        <el-table-column prop="consumption" label="用量(m³)" align="center" />
        <el-table-column prop="health" label="健康度" align="center" />
        <el-table-column prop="load" label="负荷(%)" align="center" />
        <el-table-column prop="temp" label="温度(℃)" align="center" />
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
// 导入Vue核心API
import { ref, onMounted, computed, nextTick, onUnmounted, watch } from 'vue'
// 导入API接口
import { gasMapApi } from '@/api/mapApi'
// 导入Element Plus组件
import { ElMessage } from 'element-plus'
// 导入ECharts图表库
import * as echarts from 'echarts'
// 导入Element Plus图标
import { 
  DataAnalysis, // 数据分析图标
  TrendCharts, // 趋势图表图标
  PieChart, // 饼图图标
  Odometer, // 里程表图标
  List, // 列表图标
  Monitor, // 监控图标
  Timer, // 计时器图标
  BellFilled, // 铃声填充图标
  CircleCheckFilled, // 圆形检查填充图标
  DataLine, // 数据线图标
  LocationFilled, // 位置填充图标
  Cpu, // CPU图标
  FirstAidKit, // 急救箱图标
  Filter, // 过滤器图标
  Compass, // 指南针图标
  Calendar, // 日历图标
  Connection, // 连接图标
  Histogram, // 直方图图标
  Collection, // 集合图标
  Grid, // 网格图标
  Share // 分享图标
} from '@element-plus/icons-vue'

/**
 * 运行监测调度中心页面
 * 
 * 功能说明：
 * 1. 展示燃气系统运行状态的实时监测数据
 * 2. 通过多种图表展示不同维度的运行指标
 * 3. 提供数据详情查看功能
 * 4. 集成高德地图显示监测点位分布
 * 
 * 主要模块：
 * - 核心统计数据（实时报警、今日供气量、监测点位、管网压力）
 * - 报警分类分布（饼图）
 * - 系统健康度（仪表盘）
 * - 供气态势对比（柱状图）
 * - 年度报警热力（热力图）
 * - 气源流向分析（桑基图）
 * - 压力分布盒须（盒须图）
 * - 多维综合雷达（雷达图）
 * - 负载健康散点（散点图）
 * - 运行多维平行指标分析（平行坐标图）
 */

// 高德地图实例
let mapInstance = null

// 响应式数据定义
const monitorData = ref([]) // 监测数据列表
const statInfo = ref({}) // 统计信息数据
const stationList = ref([]) // 场站列表数据
const rawMonitorList = ref([]) // 原始监测数据列表
const enterpriseList = ref([]) // 企业列表数据

// 图表DOM引用
const pressureChartRef = ref(null) // 压力图表DOM引用
const alarmChartRef = ref(null) // 报警图表DOM引用
const funnelChartRef = ref(null) // 漏斗图表DOM引用
const supplyChartRef = ref(null) // 供气图表DOM引用
const radarChartRef = ref(null) // 雷达图表DOM引用
const treemapChartRef = ref(null) // 树图图表DOM引用
const heatmapChartRef = ref(null) // 热力图图表DOM引用
const sankeyChartRef = ref(null) // 桑基图图表DOM引用
const boxplotChartRef = ref(null) // 盒须图图表DOM引用
const scatterChartRef = ref(null) // 散点图图表DOM引用
const sunburstChartRef = ref(null) // 旭日图图表DOM引用
const calendarChartRef = ref(null) // 日历图表DOM引用
const parallelChartRef = ref(null) // 平行坐标图DOM引用
const graphChartRef = ref(null) // 关系图图表DOM引用
const dialogChartRef = ref(null) // 对话框图表DOM引用
const mapContainerRef = ref(null) // 地图容器DOM引用

// 迷你图表引用
const sparkAlarmRef = ref(null) // 报警迷你图表DOM引用
const sparkSupplyRef = ref(null) // 供气迷你图表DOM引用
const sparkPointRef = ref(null) // 点位迷你图表DOM引用
const sparkPressureRef = ref(null) // 压力迷你图表DOM引用

// 迷你仪表盘引用
const gaugeHealthRef = ref(null) // 健康度仪表盘DOM引用

// 图表实例
let pressureChart = null // 压力图表实例
let alarmChart = null // 报警图表实例
let funnelChart = null // 漏斗图表实例
let supplyChart = null // 供气图表实例
let radarChart = null // 雷达图表实例
let treemapChart = null // 树图图表实例
let heatmapChart = null // 热力图图表实例
let sankeyChart = null // 桑基图图表实例
let boxplotChart = null // 盒须图图表实例
let scatterChart = null // 散点图图表实例
let sunburstChart = null // 旭日图图表实例
let calendarChart = null // 日历图表实例
let parallelChart = null // 平行坐标图实例
let graphChart = null // 关系图图表实例
let dialogChart = null // 对话框图表实例
let sparkAlarm = null // 报警迷你图表实例
let sparkSupply = null // 供气迷你图表实例
let sparkPoint = null // 点位迷你图表实例
let sparkPressure = null // 压力迷你图表实例
let gaugeHealth = null // 健康度仪表盘实例

// 视图状态
const alarmViewType = ref('pie') // 报警视图类型
const activeVisualTab = ref('radar') // 活动的可视化标签

// 其他状态
const currentTime = ref('') // 当前时间
let timer = null // 定时器引用

// 对话框状态
const dialogVisible = ref(false) // 对话框显示状态
const dialogTitle = ref('') // 对话框标题
const dialogType = ref('') // 对话框类型
const dialogData = ref([]) // 对话框数据

// 对话框打开时处理函数
const handleDialogOpened = () => {
  if (dialogType.value === 'CONSUMPTION_TREND') {
    // 初始化消费趋势图表
    initDialogChart()
  } else if (dialogType.value === 'MONITOR_POINT') {
    // 初始化监测点位地图
    initMap()
  }
}

// 初始化高德地图
const initMap = () => {
  if (!mapContainerRef.value) return
  
  // 销毁现有地图实例
  if (mapInstance) {
    mapInstance.destroy()
    mapInstance = null
  }
  
  // 检查高德地图 API 是否加载
  if (!window.AMap) {
    // 动态加载高德地图 API
    const script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = 'https://webapi.amap.com/maps?v=2.0&key=4a30d91dd0859641f885be7158c674a8&plugin=AMap.MarkerClusterer'
    script.onload = () => {
      createMap()
    }
    script.onerror = () => {
      console.error('高德地图 API 加载失败')
      ElMessage.error('地图加载失败，请刷新页面重试')
    }
    document.head.appendChild(script)
  } else {
    createMap()
  }
}

// 创建地图实例
const createMap = () => {
  if (!window.AMap || !mapContainerRef.value) return
  
  // 创建地图实例
  mapInstance = new window.AMap.Map(mapContainerRef.value, {
    center: [117.4, 25.4], // 漳平市大致坐标
    zoom: 11,
    resizeEnable: true
  })
  
  // 添加地图控件
  mapInstance.addControl(new window.AMap.ToolBar())
  mapInstance.addControl(new window.AMap.Scale())
  mapInstance.addControl(new window.AMap.MapType())
  
  // 添加监测点位标记
  addMonitorMarkers()
}

// 添加监测点位标记
const addMonitorMarkers = () => {
  if (!mapInstance || !stationList.value.length) return
  
  // 模拟监测点位数据（实际项目中应从后端获取）
  const monitorPoints = stationList.value.map((station, index) => {
    // 生成随机但合理的坐标（基于漳平市中心）
    const centerLng = 117.4
    const centerLat = 25.4
    const offsetLng = (Math.random() - 0.5) * 0.2
    const offsetLat = (Math.random() - 0.5) * 0.2
    
    return {
      id: index,
      name: station.stationName,
      location: [centerLng + offsetLng, centerLat + offsetLat],
      deviceCount: station.iotDeviceCount || 0,
      normalCount: station.normalDeviceCount || 0,
      status: (station.normalDeviceCount || 0) / (station.iotDeviceCount || 1) > 0.9 ? 'normal' : 'warning'
    }
  })
  
  // 批量添加标记
  monitorPoints.forEach(point => {
    const marker = new window.AMap.Marker({
      position: point.location,
      title: point.name,
      icon: new window.AMap.Icon({
        size: new window.AMap.Size(32, 32),
        image: point.status === 'normal' 
          ? 'https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png' 
          : 'https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-red.png',
        imageSize: new window.AMap.Size(32, 32)
      })
    })
    
    // 添加信息窗口
    const infoWindow = new window.AMap.InfoWindow({
      content: `
        <div style="padding: 10px;">
          <h3 style="margin: 0 0 10px 0;">${point.name}</h3>
          <p>设备总数: ${point.deviceCount}</p>
          <p>正常设备: ${point.normalCount}</p>
          <p>状态: ${point.status === 'normal' ? '正常' : '需关注'}</p>
        </div>
      `,
      offset: new window.AMap.Pixel(0, -30)
    })
    
    // 点击标记显示信息窗口
    marker.on('click', () => {
      infoWindow.open(mapInstance, point.location)
    })
    
    marker.setMap(mapInstance)
  })
}

// 初始化对话框图表
const initDialogChart = () => {
  if (dialogChartRef.value) {
    if (dialogChart) dialogChart.dispose()
    dialogChart = echarts.init(dialogChartRef.value)
    
    const dates = dialogData.value.map(item => item.date).reverse()
    const values = dialogData.value.map(item => item.value).reverse()
    
    const option = {
      backgroundColor: 'transparent',
      tooltip: {
        trigger: 'axis',
        axisPointer: { type: 'cross', label: { backgroundColor: '#6a7985' } }
      },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: {
        type: 'category',
        boundaryGap: false,
        data: dates,
        axisLabel: { color: '#a0cfff' },
        axisLine: { lineStyle: { color: '#1f3a5e' } }
      },
      yAxis: {
        type: 'value',
        axisLabel: { color: '#a0cfff' },
        splitLine: { lineStyle: { color: 'rgba(64,158,255,0.1)' } }
      },
      series: [{
        name: '用气量(m³)',
        type: 'line',
        stack: 'Total',
        smooth: true,
        lineStyle: { width: 3, color: '#67C23A' },
        showSymbol: false,
        areaStyle: {
          opacity: 0.8,
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(103,194,58,0.5)' },
            { offset: 1, color: 'rgba(103,194,58,0.1)' }
          ])
        },
        emphasis: { focus: 'series' },
        data: values
      }]
    }
    dialogChart.setOption(option)
  }
}

// 计算属性：总报警数
const totalAlarmCount = computed(() => {
  return rawMonitorList.value.reduce((sum, item) => sum + (item.alarmCount || 0), 0)
})

// 计算属性：平均处理率
const avgProcessingRate = computed(() => {
  if (!rawMonitorList.value.length) return 0
  const total = rawMonitorList.value.reduce((sum, item) => sum + (item.alarmProcessingRate || 0), 0)
  return (total / rawMonitorList.value.length).toFixed(1)
})

// 计算属性：设备在线率
const deviceOnlineRate = computed(() => {
  if (!stationList.value.length) return 0
  let total = 0
  let normal = 0
  stationList.value.forEach(item => {
    total += item.iotDeviceCount || 0
    normal += item.normalDeviceCount || 0
  })
  if (!total) return 0
  return ((normal / total) * 100).toFixed(1)
})

// 计算属性：监测点位总数
const monitorPointCount = computed(() => {
  if (!stationList.value.length) return 0
  return stationList.value.reduce((sum, item) => {
    return sum + (item.iotDeviceCount || 0)
  }, 0)
})

// 计算属性：最新日期字符串
const latestDateStr = computed(() => {
  if (!rawMonitorList.value.length) return ''
  const dates = rawMonitorList.value
    .map(item => {
      if (!item.statisticsDate) return ''
      return getDateKey(item.statisticsDate) || ''
    })
    .filter(d => d !== '')
  return dates.length ? dates.sort().reverse()[0] : ''
})

// 计算属性：今日总供气量
const todayTotalGas = computed(() => {
  if (!rawMonitorList.value.length) return '0.00'
  const targetDate = latestDateStr.value
  if (!targetDate) return '0.00'
  
  const map = new Map()
  rawMonitorList.value.forEach(item => {
    let dateStr = ''
    if (item.statisticsDate) {
      dateStr = getDateKey(item.statisticsDate) || ''
    }
    if (dateStr === targetDate) {
      const current = map.get(item.regionName) || 0
      map.set(item.regionName, current + Number(item.totalGasConsumption || 0))
    }
  })
  const total = Array.from(map.values()).reduce((s, v) => s + v, 0)
  // If total is 0 but we have data, try to find any non-zero day as fallback
  if (total === 0 && rawMonitorList.value.length > 0) {
     const anyNonZero = rawMonitorList.value.find(item => Number(item.totalGasConsumption) > 0)
     if (anyNonZero) return Number(anyNonZero.totalGasConsumption).toFixed(2)
  }
  return total.toFixed(2)
})

// 计算属性：设备完好率
const deviceIntactRate = computed(() => {
  if (!stationList.value.length) return 0
  let total = 0
  let normal = 0
  stationList.value.forEach(item => {
    total += item.iotDeviceCount || 0
    normal += item.normalDeviceCount || 0
  })
  if (!total) return 0
  return ((normal / total) * 100).toFixed(1)
})

// 计算属性：故障历史
const faultHistory = computed(() => {
  return rawMonitorList.value
    .filter(item => (item.alarmCount || 0) > 0)
    .map(item => ({
      regionName: item.regionName,
      alarmCount: item.alarmCount,
      time: item.statisticsDate
    }))
})

// 打开对话框函数
const openDialog = (type) => {
  dialogType.value = type
  dialogVisible.value = true
  if (type === 'CONSUMPTION_TREND') {
    dialogTitle.value = '实时用气趋势详情'
    const consumptionMap = new Map()
    rawMonitorList.value.forEach(item => {
        let dateStr = '-'
        if (item.statisticsDate) {
             dateStr = typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)
        }
        const current = consumptionMap.get(dateStr) || 0
        consumptionMap.set(dateStr, current + Number(item.totalGasConsumption || 0))
    })
    const sortedDates = Array.from(consumptionMap.keys()).sort().reverse()
    dialogData.value = sortedDates.map(date => ({
        date: date,
        value: consumptionMap.get(date).toFixed(2)
    }))
  } else if (type === 'PRESSURE_TREND') {
    dialogTitle.value = '管道压力监测趋势详情'
    const dateMap = new Map()
    rawMonitorList.value.forEach(item => {
        let dateStr = '-'
        if (item.statisticsDate) {
          dateStr = typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)
        }
        if (!dateMap.has(dateStr)) dateMap.set(dateStr, { sum: 0, count: 0 })
        const entry = dateMap.get(dateStr)
        entry.sum += Number(item.pipePressureAvg || 0)
        entry.count++
    })
    const sortedDates = Array.from(dateMap.keys()).sort().reverse()
    dialogData.value = sortedDates.map(date => {
        const entry = dateMap.get(date)
        return {
            date: date,
            value: (entry.sum / entry.count).toFixed(2)
        }
    })
  } else if (type === 'ALARM_ANALYSIS' || type === 'TOTAL_ALARM') {
    dialogTitle.value = type === 'TOTAL_ALARM' ? '实时报警总数详情' : '区域报警分析详情'
    const regionMap = new Map()
    rawMonitorList.value.forEach(item => {
        const current = regionMap.get(item.regionName) || 0
        regionMap.set(item.regionName, current + (item.alarmCount || 0))
    })
    dialogData.value = Array.from(regionMap.entries()).map(([name, value]) => ({ name, value }))
  } else if (type === 'PROCESSING_PROGRESS' || type === 'PROCESSING_RATE') {
    dialogTitle.value = '报警处置进度详情'
    dialogData.value = rawMonitorList.value.map(item => ({
        region: item.regionName,
        rate: item.alarmProcessingRate,
        count: item.alarmCount
    }))
  } else if (type === 'MONITOR_DETAILS') {
    dialogTitle.value = '区域监测详情'
    dialogData.value = rawMonitorList.value.map(item => ({
        regionName: item.regionName,
        pressure: item.pipePressureAvg,
        alarmCount: item.alarmCount,
        processingRate: item.alarmProcessingRate,
        time: item.statisticsDate
    }))
  } else if (type === 'DEVICE_ONLINE' || type === 'MONITOR_POINT') {
      dialogTitle.value = '设备在线与监测点位详情'
      dialogData.value = stationList.value.map(item => ({
          name: item.stationName,
          iotCount: item.iotDeviceCount,
          normalCount: item.normalDeviceCount,
          rate: item.iotDeviceCount ? ((item.normalDeviceCount / item.iotDeviceCount) * 100).toFixed(1) + '%' : '0%'
      }))
  } else if (type === 'TODAY_SUPPLY') {
      dialogTitle.value = '今日累计供气量详情'
      const regionMap = new Map()
      const targetDate = latestDateStr.value
      rawMonitorList.value.forEach(item => {
        let dateStr = ''
        if (item.statisticsDate) {
          dateStr = typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)
        }
        if (dateStr === targetDate) {
          const current = regionMap.get(item.regionName) || 0
          regionMap.set(item.regionName, current + Number(item.totalGasConsumption || 0))
        }
      })
      dialogData.value = Array.from(regionMap.entries()).map(([regionName, value]) => ({ regionName, value: Number(value).toFixed(2) }))
  } else if (type === 'PREDICTED_SUPPLY') {
      dialogTitle.value = '今日预测需气量详情'
      const regionMap = new Map()
      const targetDate = latestDateStr.value
      rawMonitorList.value.forEach(item => {
        let dateStr = ''
        if (item.statisticsDate) {
          dateStr = typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)
        }
        if (dateStr === targetDate) {
          const current = regionMap.get(item.regionName) || 0
          regionMap.set(item.regionName, current + Number(item.predictedTotalConsumption || 0))
        }
      })
      dialogData.value = Array.from(regionMap.entries()).map(([regionName, value]) => ({ regionName, value: Number(value).toFixed(2) }))
  } else if (type === 'USER_GROWTH') {
      dialogTitle.value = '今日新增用户详情'
      const regionMap = new Map()
      const targetDate = latestDateStr.value
      rawMonitorList.value.forEach(item => {
        let dateStr = ''
        if (item.statisticsDate) {
          dateStr = typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)
        }
        if (dateStr === targetDate) {
          if (!regionMap.has(item.regionName)) {
             regionMap.set(item.regionName, { total: 0, resident: 0, commercial: 0, enterprise: 0 })
          }
          const current = regionMap.get(item.regionName)
          current.total += Number(item.userIncrement || 0)
          current.resident += Number(item.residentUserIncrement || 0)
          current.commercial += Number(item.commercialUserIncrement || 0)
          current.enterprise += Number(item.enterpriseUserIncrement || 0)
        }
      })
      dialogData.value = Array.from(regionMap.entries()).map(([regionName, val]) => ({ 
          regionName, 
          total: val.total,
          resident: val.resident,
          commercial: val.commercial,
          enterprise: val.enterprise
      }))
  } else if (type === 'SUPPLY_SITUATION') {
      dialogTitle.value = '供气态势详情'
      const regionMap = new Map()
      rawMonitorList.value.forEach(item => {
        const current = regionMap.get(item.regionName) || 0
        regionMap.set(item.regionName, current + Number(item.totalGasConsumption || 0))
      })
      dialogData.value = Array.from(regionMap.entries()).map(([regionName, value]) => ({ regionName, value: Number(value).toFixed(2) }))
  } else if (type === 'FAULT_HISTORY') {
      dialogTitle.value = '历史故障记录详情'
      dialogData.value = faultHistory.value
  } else if (type === 'COMPRESSOR_LOAD') {
      dialogTitle.value = '压缩机负荷率详情'
      dialogData.value = rawMonitorList.value.map(item => ({
          region: item.regionName,
          value: item.compressorLoadRate,
          time: item.statisticsDate
      }))
  } else if (type === 'DEVICE_HEALTH') {
      dialogTitle.value = '设备健康度详情'
      dialogData.value = rawMonitorList.value.map(item => ({
          region: item.regionName,
          value: item.deviceHealthScore,
          status: Number(item.deviceHealthScore) >= 90 ? '良好' : (Number(item.deviceHealthScore) >= 75 ? '一般' : (item.deviceHealthScore !== null && item.deviceHealthScore !== undefined ? '较差' : '—'))
      }))
  } else if (type === 'DEVICE_INTACT') {
      dialogTitle.value = '设备完好率详情'
      dialogData.value = rawMonitorList.value.map(item => ({
          region: item.regionName,
          value: item.deviceIntactRate,
          time: item.statisticsDate
      }))
  } else if (type === 'PIPE_PRESSURE') {
      dialogTitle.value = '主管网压力详情'
      dialogData.value = rawMonitorList.value.map(item => ({
          region: item.regionName,
          value: item.pipePressureAvg,
          time: item.statisticsDate
      }))
  } else if (type === 'CALENDAR_HEATMAP') {
      dialogTitle.value = '运行压力历史记录'
      dialogData.value = rawMonitorList.value.map(item => ({
          date: item.statisticsDate ? (typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)) : '-',
          region: item.regionName,
          value: item.pipePressureAvg
      }))
  } else if (type === 'FLOW_ANALYSIS') {
      dialogTitle.value = '气源供应流向明细'
      dialogData.value = rawMonitorList.value.map(item => ({
          region: item.regionName,
          consumption: item.totalGasConsumption,
          predicted: item.predictedTotalConsumption
      }))
  } else if (type === 'BOXPLOT_DIST' || type === 'RADAR_COMPARE' || type === 'SCATTER_DEPTH' || type === 'PARALLEL_ANALYSIS') {
      dialogTitle.value = '区域运行综合指标明细'
      dialogData.value = rawMonitorList.value.map(item => ({
          region: item.regionName,
          pressure: item.pipePressureAvg,
          alarm: item.alarmCount,
          consumption: item.totalGasConsumption,
          health: item.deviceHealthScore,
          load: item.compressorLoadRate,
          temp: item.environmentalTemp
      }))
  }
}

// 初始化图表函数
const initCharts = () => {
  if (pressureChartRef.value) pressureChart = echarts.init(pressureChartRef.value)
  if (alarmChartRef.value) alarmChart = echarts.init(alarmChartRef.value)
  if (funnelChartRef.value) funnelChart = echarts.init(funnelChartRef.value)
  if (supplyChartRef.value) supplyChart = echarts.init(supplyChartRef.value)
  if (radarChartRef.value) radarChart = echarts.init(radarChartRef.value)
  if (treemapChartRef.value) treemapChart = echarts.init(treemapChartRef.value)
  if (heatmapChartRef.value) heatmapChart = echarts.init(heatmapChartRef.value)
  if (sankeyChartRef.value) sankeyChart = echarts.init(sankeyChartRef.value)
  if (boxplotChartRef.value) boxplotChart = echarts.init(boxplotChartRef.value)
  if (scatterChartRef.value) scatterChart = echarts.init(scatterChartRef.value)
  if (sunburstChartRef.value) sunburstChart = echarts.init(sunburstChartRef.value)
  if (calendarChartRef.value) calendarChart = echarts.init(calendarChartRef.value)
  if (parallelChartRef.value) parallelChart = echarts.init(parallelChartRef.value)
  if (graphChartRef.value) graphChart = echarts.init(graphChartRef.value)
  
  if (sparkAlarmRef.value) sparkAlarm = echarts.init(sparkAlarmRef.value)
  if (sparkSupplyRef.value) sparkSupply = echarts.init(sparkSupplyRef.value)
  if (sparkPointRef.value) sparkPoint = echarts.init(sparkPointRef.value)
  if (sparkPressureRef.value) sparkPressure = echarts.init(sparkPressureRef.value)
  
  if (gaugeHealthRef.value) gaugeHealth = echarts.init(gaugeHealthRef.value)
}

// 安全设置图表选项函数
const safeSetOption = (chart, option, emptyText = '暂无数据') => {
  if (!chart) return
  if (!option || (typeof option === 'object' && !Array.isArray(option) && Object.keys(option).length === 0)) {
    chart.clear()
    chart.setOption({
      graphic: {
        type: 'text',
        left: 'center',
        top: 'middle',
        style: {
          text: emptyText,
          fill: '#a0cfff',
          fontSize: 14
        }
      }
    })
    return
  }
  try {
    chart.clear()
    chart.setOption(option, true)
  } catch (e) {
    chart.clear()
    chart.setOption({
      graphic: {
        type: 'text',
        left: 'center',
        top: 'middle',
        style: {
          text: emptyText,
          fill: '#a0cfff',
          fontSize: 14
        }
      }
    })
  }
}

// 获取日期键函数
function getDateKey(d) {
  if (!d) return null
  if (typeof d === 'string') return d.substring(0, 10)
  const date = new Date(d)
  if (!Number.isFinite(date.getTime())) return null
  return date.toISOString().substring(0, 10)
}

// 计算分位数函数
const quantile = (sorted, p) => {
  if (!sorted.length) return 0
  const idx = (sorted.length - 1) * p
  const lo = Math.floor(idx)
  const hi = Math.ceil(idx)
  if (lo === hi) return sorted[lo]
  return sorted[lo] + (sorted[hi] - sorted[lo]) * (idx - lo)
}

// 更新图表函数
const updateCharts = () => {
  const source = rawMonitorList.value || []

  // 数据分层过滤
  let regionData = source.filter(item => item.statisticsType === 'REGION_DETAIL')
  let calendarSource = source.filter(item => item.statisticsType === 'DAILY_TOTAL')
  if (!regionData.length && source.length) regionData = source
  if (!calendarSource.length && source.length) calendarSource = source

  const latestDate = latestDateStr.value || ''
  const sameDay = (item) => {
    if (!latestDate) return true
    const d = getDateKey(item.statisticsDate)
    if (!d) return true
    return d === latestDate
  }
  const regionLatest = regionData.filter(sameDay)
  const dailyMap = new Map()
  ;(calendarSource.length ? calendarSource : source)
    .forEach(i => {
      const d = getDateKey(i.statisticsDate)
      if (!d) return
      if (!dailyMap.has(d)) dailyMap.set(d, { alarm: 0, gas: 0, pressureSum: 0, pressureCnt: 0 })
      const v = dailyMap.get(d)
      v.alarm += Number(i.alarmCount || 0)
      v.gas += Number(i.totalGasConsumption || 0)
      const p = Number(i.dailyPressure ?? i.pipePressureAvg)
      if (Number.isFinite(p) && p > 0) {
        v.pressureSum += p
        v.pressureCnt += 1
      }
    })
  const lastDates = Array.from(dailyMap.keys()).sort().slice(-7)
  const sparkAlarmSeries = lastDates.map(d => Number(dailyMap.get(d)?.alarm || 0))
  const sparkGasSeries = lastDates.map(d => Number(dailyMap.get(d)?.gas || 0))
  const sparkPressureSeries = lastDates.map(d => {
    const v = dailyMap.get(d)
    if (!v || !v.pressureCnt) return 0
    return Number((v.pressureSum / v.pressureCnt).toFixed(3))
  })
  const sparkOnlineSeries = lastDates.length ? lastDates.map(() => Number(deviceOnlineRate.value || 0)) : []

  const sparkCommon = (data, color) => ({
    grid: { left: 0, right: 0, top: 0, bottom: 0 },
    xAxis: { type: 'category', show: false, data: data.map((_, i) => String(i)) },
    yAxis: { type: 'value', show: false },
    series: [{
      data,
      type: 'line',
      smooth: true,
      symbol: 'none',
      lineStyle: { width: 2, color },
      areaStyle: { color, opacity: 0.12 }
    }]
  })
  if (sparkAlarm) safeSetOption(sparkAlarm, sparkAlarmSeries.length ? sparkCommon(sparkAlarmSeries, '#F56C6C') : {}, '暂无数据')
  if (sparkSupply) safeSetOption(sparkSupply, sparkGasSeries.length ? sparkCommon(sparkGasSeries, '#67C23A') : {}, '暂无数据')
  if (sparkPoint) safeSetOption(sparkPoint, sparkOnlineSeries.length ? sparkCommon(sparkOnlineSeries, '#409EFF') : {}, '暂无数据')
  if (sparkPressure) safeSetOption(sparkPressure, sparkPressureSeries.length ? sparkCommon(sparkPressureSeries, '#E6A23C') : {}, '暂无数据')

  const regionAlarmMap = new Map()
  regionLatest.forEach(i => {
    const r = i.regionName || '—'
    regionAlarmMap.set(r, (regionAlarmMap.get(r) || 0) + Number(i.alarmCount || 0))
  })
  const alarmData = Array.from(regionAlarmMap.entries())
    .map(([name, value]) => ({ name, value }))
    .sort((a, b) => b.value - a.value)
  safeSetOption(alarmChart, alarmData.length ? {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'item' },
    series: [{
      type: 'pie',
      radius: ['35%', '65%'],
      roseType: 'area',
      data: alarmData,
      label: { color: '#a0cfff', fontSize: 11 }
    }]
  } : {}, '暂无数据')

  const totalAlarm = Number(totalAlarmCount.value || 0)
  const rate = Number(avgProcessingRate.value || 0)
  const processed = Math.round(totalAlarm * (rate / 100))
  const pending = Math.max(0, totalAlarm - processed)
  safeSetOption(funnelChart, totalAlarm ? {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'item' },
    series: [{
      type: 'funnel',
      left: '12%',
      top: 30,
      bottom: 10,
      width: '76%',
      sort: 'descending',
      gap: 2,
      label: { color: '#fff', fontSize: 12, formatter: '{b}\n{c}' },
      itemStyle: { borderColor: '#020f26', borderWidth: 1 },
      data: [
        { value: totalAlarm, name: '报警总数' },
        { value: processed, name: '已处置' },
        { value: pending, name: '待处置' }
      ]
    }]
  } : {}, '暂无数据')

  const healthVals = regionLatest
    .map(i => Number(i.deviceHealthScore))
    .filter(v => Number.isFinite(v) && v >= 0)
  const avgHealth = healthVals.length ? Number((healthVals.reduce((a, b) => a + b, 0) / healthVals.length).toFixed(1)) : 0
  safeSetOption(gaugeHealth, healthVals.length ? {
    backgroundColor: 'transparent',
    series: [{
      type: 'gauge',
      progress: { show: true, width: 12, roundCap: true },
      axisLine: { roundCap: true, lineStyle: { width: 12, color: [[1, 'rgba(64,158,255,0.16)']] } },
      axisTick: { show: false },
      splitLine: { show: false },
      axisLabel: { show: false },
      pointer: { show: false },
      title: { show: false },
      detail: { valueAnimation: true, formatter: '{value} 分', color: '#fff', fontSize: 24, offsetCenter: [0, '0%'] },
      data: [{ value: avgHealth }]
    }]
  } : {}, '暂无数据')

  const supplyRegionMap = new Map()
  regionLatest.forEach(i => {
    const r = i.regionName || '—'
    supplyRegionMap.set(r, (supplyRegionMap.get(r) || 0) + Number(i.totalGasConsumption || 0))
  })
  const supplyRegions = Array.from(supplyRegionMap.keys())
  const supplyValues = supplyRegions.map(r => Number(supplyRegionMap.get(r) || 0))
  safeSetOption(supplyChart, supplyRegions.length ? {
    backgroundColor: 'transparent',
    grid: { top: 40, right: 15, bottom: 50, left: 15, containLabel: true },
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    xAxis: {
      type: 'category',
      data: supplyRegions,
      axisLabel: { color: '#a0cfff', rotate: 25, fontSize: 10 },
      axisLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.3)' } }
    },
    yAxis: {
      type: 'value',
      name: 'm³',
      axisLabel: { color: '#a0cfff', fontSize: 10 },
      splitLine: { lineStyle: { color: 'rgba(64, 158, 255, 0.1)' } }
    },
    series: [{
      name: '供气量',
      type: 'bar',
      data: supplyValues,
      itemStyle: {
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          { offset: 0, color: '#409EFF' },
          { offset: 1, color: 'rgba(64, 158, 255, 0.12)' }
        ]),
        borderRadius: [4, 4, 0, 0]
      },
      barWidth: '45%'
    }]
  } : {}, '暂无数据')

  const calendarRows = calendarSource
    .map(i => {
      const d = getDateKey(i.statisticsDate)
      if (!d) return null
      const v = Number(i.dailyPressure ?? i.pipePressureAvg)
      if (!Number.isFinite(v)) return null
      return [d, Number(v.toFixed(3))]
    })
    .filter(Boolean)
  const calRange = (() => {
    const ts = calendarRows.map(i => new Date(i[0]).getTime()).filter(t => Number.isFinite(t))
    const base = ts.length ? new Date(Math.max(...ts)) : new Date()
    const y = base.getFullYear()
    const m = String(base.getMonth() + 1).padStart(2, '0')
    return `${y}-${m}`
  })()
  const calMax = Math.max(0.0001, ...calendarRows.map(i => i[1]))
  const calMin = Math.min(...calendarRows.map(i => i[1]), calMax)
  safeSetOption(calendarChart, calendarRows.length ? {
    backgroundColor: 'transparent',
    tooltip: { formatter: (p) => `${p.value[0]}<br/>压力: ${p.value[1]} MPa` },
    visualMap: {
      show: true,
      min: calMin,
      max: calMax,
      orient: 'horizontal',
      left: 'center',
      bottom: 0,
      textStyle: { color: '#a0cfff', fontSize: 10 },
      inRange: { color: ['#0d2546', '#1f3a5e', '#409EFF', '#67C23A', '#E6A23C', '#F56C6C'] }
    },
    calendar: {
      left: 'center',
      top: 30,
      right: 10,
      range: calRange,
      cellSize: ['auto', 35],
      itemStyle: { color: '#020f26', borderWidth: 1, borderColor: '#1f3a5e' },
      dayLabel: { color: '#a0cfff', fontSize: 10, firstDay: 1 },
      monthLabel: { show: true, color: '#409EFF', fontSize: 12 },
      yearLabel: { show: false }
    },
    series: [{ type: 'heatmap', coordinateSystem: 'calendar', data: calendarRows }]
  } : {}, '暂无数据')

  const sankeyNodes = new Map()
  const addNode = (name) => {
    if (!sankeyNodes.has(name)) sankeyNodes.set(name, { name })
  }
  addNode('实际供气')
  addNode('预测需求')
  supplyRegions.forEach(addNode)
  const links = []
  regionLatest.forEach(i => {
    const r = i.regionName || '—'
    addNode(r)
    const actual = Number(i.totalGasConsumption || 0)
    const pred = Number(i.predictedTotalConsumption || 0)
    if (actual > 0) links.push({ source: '实际供气', target: r, value: actual })
    if (pred > 0) links.push({ source: '预测需求', target: r, value: pred })
  })
  safeSetOption(sankeyChart, links.length ? {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'item', triggerOn: 'mousemove' },
    series: [{
      type: 'sankey',
      nodeGap: 10,
      nodeWidth: 12,
      emphasis: { focus: 'adjacency' },
      label: { color: '#a0cfff', fontSize: 10 },
      itemStyle: { borderWidth: 1, borderColor: 'rgba(64,158,255,0.2)' },
      lineStyle: { color: 'source', curveness: 0.5, opacity: 0.6 },
      data: Array.from(sankeyNodes.values()),
      links
    }]
  } : {}, '暂无数据')

  const regionPressureMap = new Map()
  regionLatest.forEach(i => {
    const r = i.regionName || '—'
    const p = Number(i.pipePressureAvg)
    if (!Number.isFinite(p) || p <= 0) return
    if (!regionPressureMap.has(r)) regionPressureMap.set(r, [])
    regionPressureMap.get(r).push(p)
  })
  const boxRegions = Array.from(regionPressureMap.keys())
  const boxplotData = boxRegions.map(r => {
    const values = (regionPressureMap.get(r) || []).slice().sort((a, b) => a - b)
    if (!values.length) return [0, 0, 0, 0, 0]
    if (values.length === 1) return [values[0], values[0], values[0], values[0], values[0]]
    return [values[0], quantile(values, 0.25), quantile(values, 0.5), quantile(values, 0.75), values[values.length - 1]]
  })
  safeSetOption(boxplotChart, boxRegions.length ? {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'item', axisPointer: { type: 'shadow' } },
    grid: { top: 40, right: 15, bottom: 40, left: 15, containLabel: true },
    xAxis: { type: 'category', data: boxRegions, axisLabel: { color: '#a0cfff', fontSize: 10, rotate: 20 } },
    yAxis: { type: 'value', name: 'MPa', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.1)' } } },
    series: [{
      name: '压力分布',
      type: 'boxplot',
      data: boxplotData,
      itemStyle: { color: '#409EFF', borderColor: '#a0cfff', opacity: 0.7 }
    }]
  } : {}, '暂无数据')

  const scatterData = regionLatest
    .map(i => [
      Number(i.compressorLoadRate ?? 0),
      Number(i.deviceIntactRate ?? 0),
      Number(i.alarmCount ?? 0)
    ])
    .filter(v => v.every(n => Number.isFinite(n)))
  safeSetOption(scatterChart, scatterData.length ? {
    backgroundColor: 'transparent',
    tooltip: { formatter: (p) => `负载: ${p.value[0]}%<br/>完好率: ${p.value[1]}%<br/>报警: ${p.value[2]}` },
    xAxis: { name: '负载率', axisLabel: { color: '#a0cfff' }, splitLine: { show: false } },
    yAxis: { name: '完好率', axisLabel: { color: '#a0cfff' }, splitLine: { show: false } },
    series: [{
      type: 'scatter',
      symbolSize: (d) => Math.max(10, Number(d[2] || 0) * 4 + 10),
      data: scatterData,
      itemStyle: {
        color: new echarts.graphic.RadialGradient(0.4, 0.3, 1, [
          { offset: 0, color: '#f35352' },
          { offset: 1, color: '#fa1a1a' }
        ])
      }
    }]
  } : {}, '暂无数据')

  const radarAgg = supplyRegions
    .map(r => ({ r, gas: Number(supplyRegionMap.get(r) || 0) }))
    .sort((a, b) => b.gas - a.gas)
    .slice(0, 6)
    .map(i => i.r)
    .map(r => {
      const items = regionLatest.filter(m => (m.regionName || '—') === r)
      const len = items.length || 1
      const avgPress = items.reduce((a, b) => a + Number(b.pipePressureAvg || 0), 0) / len
      const totalAlarm = items.reduce((a, b) => a + Number(b.alarmCount || 0), 0)
      const totalGas = items.reduce((a, b) => a + Number(b.totalGasConsumption || 0), 0)
      const avgH = items.reduce((a, b) => a + Number(b.deviceHealthScore || 0), 0) / len
      const avgLoad = items.reduce((a, b) => a + Number(b.compressorLoadRate || 0), 0) / len
      return { r, avgPress, totalAlarm, totalGas, avgH, avgLoad }
    })
  safeSetOption(radarChart, radarAgg.length ? {
    backgroundColor: 'transparent',
    legend: { show: true, bottom: 0, textStyle: { color: '#a0cfff', fontSize: 10 } },
    radar: {
      indicator: [
        { name: '平均压力', max: Math.max(1, ...radarAgg.map(i => i.avgPress), 0.6) },
        { name: '累计报警', max: Math.max(1, ...radarAgg.map(i => i.totalAlarm), 10) },
        { name: '用气规模', max: Math.max(1, ...radarAgg.map(i => i.totalGas), 10) },
        { name: '设备健康', max: 100 },
        { name: '负荷率', max: 100 }
      ],
      center: ['50%', '45%'],
      radius: '65%',
      axisName: { color: '#a0cfff', fontSize: 10 },
      splitArea: { areaStyle: { color: ['rgba(64, 158, 255, 0.05)', 'rgba(64, 158, 255, 0.1)'] } }
    },
    series: [{
      type: 'radar',
      areaStyle: { opacity: 0.2 },
      data: radarAgg.map(i => ({
        name: i.r,
        value: [
          Number(i.avgPress.toFixed(3)),
          i.totalAlarm,
          Number(i.totalGas.toFixed(0)),
          Math.max(0, Math.min(100, Number(i.avgH.toFixed(0)))),
          Math.max(0, Math.min(100, Number(i.avgLoad.toFixed(0))))
        ]
      }))
    }]
  } : {}, '暂无数据')

  const parallelRows = regionLatest
    .map(i => ({
      pressure: Number(i.pipePressureAvg || 0),
      alarm: Number(i.alarmCount || 0),
      gas: Number(i.totalGasConsumption || 0),
      health: Number(i.deviceHealthScore || 0),
      temp: Number(i.environmentalTemp || 0)
    }))
    .filter(i => Object.values(i).every(v => Number.isFinite(v)))
    .map(i => [i.pressure, i.alarm, i.gas, i.health, i.temp])
  const pMax = {
    pressure: Math.max(1, ...parallelRows.map(r => r[0])),
    alarm: Math.max(1, ...parallelRows.map(r => r[1])),
    gas: Math.max(1, ...parallelRows.map(r => r[2])),
    health: 100,
    temp: Math.max(1, ...parallelRows.map(r => r[4]))
  }
  safeSetOption(parallelChart, parallelRows.length ? {
    backgroundColor: 'transparent',
    parallelAxis: [
      { dim: 0, name: '压力', min: 0, max: pMax.pressure },
      { dim: 1, name: '报警', min: 0, max: pMax.alarm },
      { dim: 2, name: '用量', min: 0, max: pMax.gas },
      { dim: 3, name: '健康', min: 0, max: pMax.health },
      { dim: 4, name: '温度', min: 0, max: pMax.temp }
    ],
    parallel: { left: 30, right: 30, top: 30, bottom: 30 },
    series: [{
      type: 'parallel',
      lineStyle: { width: 2, color: '#409EFF', opacity: 0.5 },
      data: parallelRows
    }]
  } : {}, '暂无数据')
}

// 获取数据函数
const fetchData = async () => {
  try {
    // 调用API获取总数据
    const data = await gasMapApi.getTotalData()
    // 更新原始监测数据列表
    rawMonitorList.value = data.monitorList || []
    // 更新场站列表
    stationList.value = data.stationList || []
    // 更新企业列表
    enterpriseList.value = data.enterpriseList || []
    
    // 按区域聚合用户数
    const regionUserMap = new Map()
    enterpriseList.value.forEach(ent => {
      const r = ent.regionName || '—'
      regionUserMap.set(r, (regionUserMap.get(r) || 0) + (ent.userCount || 0))
    })

    // 更新监测数据列表
    monitorData.value = rawMonitorList.value.map(item => ({
      regionName: item.regionName || '—',
      pressure: item.pipePressureAvg,
      alarmCount: item.alarmCount,
      processingRate: item.alarmProcessingRate,
      status: item.alarmCount === 0 ? 'normal' : 'alarm',
      time: item.statisticsDate ? (typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)) : '—',
      userCount: regionUserMap.get(item.regionName || '—') || 0
    }))
    
    nextTick(() => {
      updateCharts()
    })
  } catch (error) {
    console.error(error)
    ElMessage.error('获取运行监测数据失败')
  }
}

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString()
}

// 调整图表大小函数
const resizeCharts = () => {
  pressureChart && pressureChart.resize()
  alarmChart && alarmChart.resize()
  funnelChart && funnelChart.resize()
  supplyChart && supplyChart.resize()
  radarChart && radarChart.resize()
  treemapChart && treemapChart.resize()
  heatmapChart && heatmapChart.resize()
  sankeyChart && sankeyChart.resize()
  boxplotChart && boxplotChart.resize()
  scatterChart && scatterChart.resize()
  sunburstChart && sunburstChart.resize()
  calendarChart && calendarChart.resize()
  parallelChart && parallelChart.resize()
  graphChart && graphChart.resize()
  dialogChart && dialogChart.resize()
  sparkAlarm && sparkAlarm.resize()
  sparkSupply && sparkSupply.resize()
  sparkPoint && sparkPoint.resize()
  sparkPressure && sparkPressure.resize()
  gaugeHealth && gaugeHealth.resize()
}

// 组件挂载时执行
onMounted(() => {
  // 更新当前时间
  updateTime()
  // 设置定时器，每秒更新时间
  timer = setInterval(updateTime, 1000)
  // 初始化图表
  initCharts()
  // 获取数据
  fetchData()
  // 添加窗口大小改变事件监听
  window.addEventListener('resize', resizeCharts)
})

// 组件卸载时执行
onUnmounted(() => {
  // 清除定时器
  if (timer) clearInterval(timer)
  // 移除窗口大小改变事件监听
  window.removeEventListener('resize', resizeCharts)
  // 销毁图表实例
  pressureChart?.dispose()
  alarmChart?.dispose()
  funnelChart?.dispose()
  supplyChart?.dispose()
  radarChart?.dispose()
  treemapChart?.dispose()
  heatmapChart?.dispose()
  sankeyChart?.dispose()
  boxplotChart?.dispose()
  scatterChart?.dispose()
  sunburstChart?.dispose()
  calendarChart?.dispose()
  parallelChart?.dispose()
  graphChart?.dispose()
  dialogChart?.dispose()
  sparkAlarm?.dispose()
  sparkSupply?.dispose()
  sparkPoint?.dispose()
  sparkPressure?.dispose()
  gaugeHealth?.dispose()
  
  // 销毁地图实例
  if (mapInstance) {
    mapInstance.destroy()
    mapInstance = null
  }
})
</script>

<style scoped>
.monitor-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
  background: #020f26;
  color: #fff;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  overflow: hidden;
}

.header {
  height: 60px;
  background: linear-gradient(90deg, #020f26 0%, #0d2546 50%, #020f26 100%);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
  border-bottom: 1px solid #1f3a5e;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5);
}

.title {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  display: flex;
  align-items: center;
  gap: 10px;
  text-shadow: 0 0 10px rgba(64, 158, 255, 0.5);
}

.icon-pulse {
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0% { transform: scale(1); opacity: 1; }
  50% { transform: scale(1.2); opacity: 0.8; }
  100% { transform: scale(1); opacity: 1; }
}

.time {
  font-size: 16px;
  color: #a0cfff;
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(255, 255, 255, 0.05);
  padding: 5px 15px;
  border-radius: 20px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.bento-grid {
  flex: 1;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(4, 320px);
  gap: 15px;
  padding: 20px;
  background: #020f26;
  overflow-y: auto;
  overflow-x: hidden;
  height: calc(100vh - 60px);
}

.grid-item {
  background: rgba(13, 37, 70, 0.4);
  backdrop-filter: blur(8px);
  border: 1px solid rgba(64, 158, 255, 0.1);
  border-radius: 12px;
  padding: 15px;
  display: flex;
  flex-direction: column;
  min-height: 0;
  transition: transform 0.3s, box-shadow 0.3s;
}

.grid-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 5px 15px rgba(64, 158, 255, 0.15);
  border-color: rgba(64, 158, 255, 0.3);
}

/* Specific Bento Areas */
.core-stats { grid-column: 1; grid-row: 1; }
.alarm-dist { grid-column: 2; grid-row: 1; }
.health-gauge { grid-column: 3; grid-row: 1; }

.supply-trend { grid-column: 1; grid-row: 2; }
.calendar-heatmap { grid-column: 2; grid-row: 2; }
.flow-analysis { grid-column: 3; grid-row: 2; }

.boxplot-dist { grid-column: 1; grid-row: 3; }
.radar-compare { grid-column: 2; grid-row: 3; }
.scatter-depth { grid-column: 3; grid-row: 3; }

.parallel-full { grid-column: 1 / span 3; grid-row: 4; }

.stat-mini-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-template-rows: repeat(2, 1fr);
  gap: 12px;
  height: 100%;
}

.stat-mini-card {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.05);
  border-radius: 8px;
  padding: 12px;
  display: flex;
  flex-direction: column;
  position: relative;
  transition: background 0.3s;
}

.stat-mini-card:hover {
  background: rgba(255, 255, 255, 0.08);
}

.card-label { font-size: 13px; color: #a0cfff; }
.card-value { font-size: 20px; font-weight: bold; margin: 6px 0; color: #fff; }
.spark-mini { flex: 1; min-height: 30px; }

.panel-header {
  height: 28px;
  margin-bottom: 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-left {
  font-size: 15px;
  font-weight: 600;
  color: #fff;
  display: flex;
  align-items: center;
  gap: 8px;
}

.chart-container-wrapper {
  flex: 1;
  width: 100%;
  min-height: 0;
  display: flex;
  flex-direction: column;
}

.chart-container {
  flex: 1;
  width: 100%;
  min-height: 150px;
}

.unit { font-size: 12px; margin-left: 4px; color: #a0cfff; }

/* Scrollbar Style */
.bento-grid::-webkit-scrollbar {
  width: 6px;
}

.bento-grid::-webkit-scrollbar-thumb {
  background: rgba(64, 158, 255, 0.2);
  border-radius: 3px;
}

.bento-grid::-webkit-scrollbar-track {
  background: transparent;
}


:deep(.el-table) {
  background-color: transparent !important;
  color: #fff;
  --el-table-border-color: rgba(64, 158, 255, 0.1);
  --el-table-header-bg-color: rgba(64, 158, 255, 0.1);
  --el-table-row-hover-bg-color: rgba(64, 158, 255, 0.15);
  --el-table-tr-bg-color: transparent;
}

:deep(.el-table th.el-table__cell) {
  background-color: rgba(64, 158, 255, 0.1) !important;
  color: #409EFF;
  border-bottom: 1px solid rgba(64, 158, 255, 0.2) !important;
}

:deep(.el-table td.el-table__cell) {
  border-bottom: 1px solid rgba(64, 158, 255, 0.1);
}

:deep(.el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell) {
  background-color: rgba(64, 158, 255, 0.15) !important;
}

:deep(.el-table__inner-wrapper::before) {
  display: none;
}

.status-badge {
  padding: 2px 8px;
  border-radius: 10px;
  font-size: 12px;
  font-weight: bold;
}

.status-badge.danger {
  background: rgba(245, 108, 108, 0.2);
  color: #F56C6C;
  border: 1px solid rgba(245, 108, 108, 0.3);
  animation: blink 2s infinite;
}

.status-badge.normal {
  background: rgba(103, 194, 58, 0.2);
  color: #67C23A;
}

@keyframes blink {
  0% { opacity: 1; }
  50% { opacity: 0.6; }
  100% { opacity: 1; }
}


::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
::-webkit-scrollbar-thumb {
  background: rgba(64, 158, 255, 0.3);
  border-radius: 3px;
}
::-webkit-scrollbar-track {
  background: transparent;
}

.clickable {
  cursor: pointer;
  transition: all 0.3s;
}

.clickable:hover {
  text-shadow: 0 0 8px rgba(64, 158, 255, 0.8);
  color: #fff;
}

/* Dark theme dialog styles */
:deep(.monitor-dialog) {
  --el-dialog-bg-color: #0a1a33;
  --el-text-color-primary: #a0cfff;
  background: #0a1a33 !important;
  border: 1px solid #1f3a5e !important;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.8) !important;
}

:deep(.monitor-dialog .el-dialog) {
  background: #0a1a33 !important;
}

:deep(.monitor-dialog .el-dialog__title) {
  color: #409EFF !important;
  font-weight: bold;
}

:deep(.monitor-dialog .el-dialog__header) {
  background: #0d2546 !important;
  border-bottom: 1px solid #1f3a5e !important;
  margin-right: 0 !important;
  padding: 15px 20px !important;
}

:deep(.monitor-dialog .el-dialog__headerbtn .el-dialog__close) {
  color: #409EFF !important;
}

:deep(.monitor-dialog .el-dialog__headerbtn:hover .el-dialog__close) {
  color: #66b1ff !important;
}

:deep(.monitor-dialog .el-dialog__body) {
  background: #0a1a33 !important;
  padding: 20px !important;
  color: #a0cfff !important;
}

:deep(.monitor-dialog .el-table) {
  background-color: transparent !important;
  color: #a0cfff !important;
  --el-table-border-color: #1f3a5e;
  --el-table-header-bg-color: #0d2546;
  --el-table-row-hover-bg-color: #1f3a5e;
  --el-table-tr-bg-color: transparent;
}

:deep(.monitor-dialog .el-table th.el-table__cell) {
  background-color: #0d2546 !important;
  color: #409EFF !important;
  border-bottom: 1px solid #1f3a5e !important;
}

:deep(.monitor-dialog .el-table td.el-table__cell) {
  border-bottom: 1px solid #1f3a5e !important;
  color: #a0cfff !important;
}

:deep(.monitor-dialog .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell) {
  background-color: #1f3a5e !important;
}

:deep(.monitor-dialog .el-icon) {
  color: #409EFF !important;
}
</style>

<style>
/* Global styles for dialog to handle teleported elements */
.monitor-dialog {
  --el-dialog-bg-color: #0a1a33 !important;
  --el-text-color-primary: #a0cfff !important;
  background: #0a1a33 !important;
  border: 1px solid #1f3a5e !important;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.8) !important;
}

.monitor-dialog .el-dialog {
  background: #0a1a33 !important;
}

.monitor-dialog .el-dialog__title {
  color: #409EFF !important;
  font-weight: bold;
}

.monitor-dialog .el-dialog__header {
  background: #0d2546 !important;
  border-bottom: 1px solid #1f3a5e !important;
  margin-right: 0 !important;
  padding: 15px 20px !important;
}

.monitor-dialog .el-dialog__headerbtn .el-dialog__close {
  color: #409EFF !important;
}

.monitor-dialog .el-dialog__headerbtn:hover .el-dialog__close {
  color: #66b1ff !important;
}

.monitor-dialog .el-dialog__body {
  background: #0a1a33 !important;
  padding: 20px !important;
  color: #a0cfff !important;
}

.monitor-dialog .el-table {
  background-color: transparent !important;
  color: #a0cfff !important;
  --el-table-border-color: #1f3a5e;
  --el-table-header-bg-color: #0d2546;
  --el-table-row-hover-bg-color: #1f3a5e;
  --el-table-tr-bg-color: transparent;
}

.monitor-dialog .el-table th.el-table__cell {
  background-color: #0d2546 !important;
  color: #409EFF !important;
  border-bottom: 1px solid #1f3a5e !important;
}

.monitor-dialog .el-table td.el-table__cell {
  border-bottom: 1px solid #1f3a5e !important;
  color: #a0cfff !important;
}

.monitor-dialog .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell {
  background-color: #1f3a5e !important;
}

.monitor-dialog .el-icon {
  color: #409EFF !important;
}

/* 监测点位容器样式 */
.monitor-point-container {
  width: 100%;
}

/* 地图容器样式 */
.map-container {
  width: 100%;
  height: 400px;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid rgba(64, 158, 255, 0.2);
}

/* 表格间距 */
.mt-4 {
  margin-top: 16px;
}
</style>
