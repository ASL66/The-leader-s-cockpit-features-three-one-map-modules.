<template>
  <!-- 基础信息数据中心页面 -->
  <!-- 功能说明：展示燃气系统基础信息数据，包括核心指标、场站构成、用户规模等多个维度的可视化图表 -->
  <!-- 布局结构：采用网格布局，顶部为页面标题和时间显示，主体为多个图表面板 -->
  <div class="basic-container dark-theme">
    <!-- 页面头部 -->
    <div class="header">
      <div class="title">
        <el-icon class="icon-pulse"><DataAnalysis /></el-icon> 基础信息数据中心
      </div>
      <div class="header-right">
        <div class="time"><el-icon><Timer /></el-icon> {{ currentTime }}</div>
      </div>
    </div>
    <!-- 页面内容滚动区域 -->
    <div class="content-scroll">
      <!-- 图表网格布局 -->
      <div class="charts-grid">
        <!-- 核心指标画像面板 -->
        <div class="grid-item span-4 panel-sm">
          <div class="panel-header clickable" @click="openDialog('CORE_STATS')">
            <el-icon><Odometer /></el-icon> 核心指标画像
          </div>
          <div class="core-kpi-wrap">
            <div class="core-kpi-grid">
              <!-- 企业数量指标卡片 -->
              <div class="core-kpi-card kpi-primary clickable" @click.stop="openDialog('CORE_STATS')">
                <div class="kpi-header-row">
                  <div class="kpi-label">企业数量</div>
                  <div class="kpi-tag">企</div>
                </div>
                <div class="kpi-value">
                  <span>{{ coreKpi.enterpriseCount }}</span>
                  <span v-if="coreKpi.enterpriseCount !== '—'" class="kpi-unit">家</span>
                </div>
                <div class="kpi-footer-row">
                  <div class="kpi-foot-label">均用户</div>
                  <div class="kpi-foot-value">{{ coreKpi.avgUserPerEnt }}</div>
                </div>
              </div>
              <!-- 场站总数指标卡片 -->
              <div class="core-kpi-card kpi-success clickable" @click.stop="openDialog('CORE_STATS')">
                <div class="kpi-header-row">
                  <div class="kpi-label">场站总数</div>
                  <div class="kpi-tag">站</div>
                </div>
                <div class="kpi-value">
                  <span>{{ coreKpi.stationCount }}</span>
                  <span v-if="coreKpi.stationCount !== '—'" class="kpi-unit">座</span>
                </div>
                <div class="kpi-footer-row">
                  <div class="kpi-foot-label">均场站</div>
                  <div class="kpi-foot-value">{{ coreKpi.avgStationPerEnt }}</div>
                </div>
              </div>
              <!-- 管网长度指标卡片 -->
              <div class="core-kpi-card kpi-warn clickable" @click.stop="openDialog('CORE_STATS')">
                <div class="kpi-header-row">
                  <div class="kpi-label">管网长度</div>
                  <div class="kpi-tag">网</div>
                </div>
                <div class="kpi-value">
                  <span>{{ coreKpi.totalPipeLength }}</span>
                  <span v-if="coreKpi.totalPipeLength !== '—'" class="kpi-unit">km</span>
                </div>
                <div class="kpi-footer-row">
                  <div class="kpi-foot-label">均管网</div>
                  <div class="kpi-foot-value">{{ coreKpi.avgPipePerEnt }}</div>
                </div>
              </div>
              <!-- 用户总数指标卡片 -->
              <div class="core-kpi-card kpi-danger clickable" @click.stop="openDialog('CORE_STATS')">
                <div class="kpi-header-row">
                  <div class="kpi-label">用户总数</div>
                  <div class="kpi-tag">户</div>
                </div>
                <div class="kpi-value">
                  <span>{{ coreKpi.totalUserCount }}</span>
                  <span v-if="coreKpi.totalUserCount !== '—'" class="kpi-unit">户</span>
                </div>
                <div class="kpi-footer-row">
                  <div class="kpi-foot-label">企业数</div>
                  <div class="kpi-foot-value">{{ coreKpi.enterpriseCount }}</div>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- 场站构成（瀑布图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('STATION_DIST')">
            <el-icon><PieChart /></el-icon> 场站构成 (瀑布图)
          </div>
          <div ref="stationWaterfallChartRef" class="chart-container"></div>
        </div>

        <!-- 企业用户规模 TOP10（棒棒糖图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('TOP_USERS')">
            <el-icon><TrendCharts /></el-icon> 企业用户规模 TOP10 (棒棒糖图)
          </div>
          <div ref="userLollipopChartRef" class="chart-container"></div>
        </div>

        <!-- 用气热力（日历热力图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('GAS_TREND')">
            <el-icon><TrendCharts /></el-icon> 用气热力 (日历热力图)
          </div>
          <div ref="gasCalendarChartRef" class="chart-container"></div>
        </div>

        <!-- 关键场站设备画像（气泡图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('GATE_STATIONS')">
            <el-icon><Location /></el-icon> 关键场站设备画像 (气泡图)
          </div>
          <div ref="stationBubbleChartRef" class="chart-container"></div>
        </div>

        <!-- 管网长度分布（直方图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('PIPE_INFO')">
            <el-icon><Connection /></el-icon> 管网长度分布 (直方图)
          </div>
          <div ref="pipeHistogramChartRef" class="chart-container"></div>
        </div>

        <!-- 区域设备在线率排行（象形图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('ONLINE_RATE_RANK')">
            <el-icon><Connection /></el-icon> 区域设备在线率排行 (象形图)
          </div>
          <div ref="stationOnlinePictorialChartRef" class="chart-container"></div>
        </div>

        <!-- 企业用气与用户规模（散点图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('ENT_GAS_USER_RANK')">
            <el-icon><UserFilled /></el-icon> 企业用气与用户规模 (散点图)
          </div>
          <div ref="enterpriseEffectScatterChartRef" class="chart-container"></div>
        </div>

        <!-- 应急能力概览（子弹图）面板 -->
        <div class="grid-item span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('EMERGENCY_OVERVIEW')">
            <el-icon><FirstAidKit /></el-icon> 应急能力概览 (子弹图)
          </div>
          <div ref="emergencyBulletChartRef" class="chart-container"></div>
        </div>

        <!-- 企业结构（树图）面板 -->
        <div class="grid-item span-12 panel-lg">
          <div class="panel-header clickable" @click="openDialog('ENT_LIST')">
            <el-icon><OfficeBuilding /></el-icon> 企业结构 (树图)
          </div>
          <div ref="enterpriseTreeChartRef" class="chart-container"></div>
        </div>
      </div>
    </div>
  </div>
  
  <!-- 详情对话框 -->
  <el-dialog v-model="dialogVisible" :title="dialogTitle" width="70%" class="monitor-dialog">
    <!-- 用气量 TOP10 详情表格 -->
    <el-table v-if="dialogType === 'TOP_USERS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column type="index" label="排名" width="60" align="center" />
      <el-table-column prop="name" label="企业名称" show-overflow-tooltip />
      <el-table-column prop="value" label="用气量 (m³)" width="180" sortable />
    </el-table>
    
    <!-- 管网信息详情表格 -->
    <el-table v-if="dialogType === 'PIPE_INFO' || dialogType === 'PIPE_STATS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="name" label="管段编号" show-overflow-tooltip />
      <el-table-column prop="length" label="长度(km)" sortable />
      <el-table-column prop="pressure" label="压力级制" sortable />
      <el-table-column prop="material" label="材质" sortable />
      <el-table-column prop="status" label="状态" sortable />
    </el-table>

    <!-- 用气总态势详情表格 -->
    <el-table v-if="dialogType === 'GAS_TREND'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="date" label="日期" />
      <el-table-column prop="value" label="用气总量(m³)" />
    </el-table>
    
    <!-- 场站信息详情表格 -->
    <el-table v-if="dialogType === 'GATE_STATIONS' || dialogType === 'CHARGING_STATIONS' || dialogType === 'STATION_STATS' || dialogType === 'STATION_DIST'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="stationName" label="场站名称" show-overflow-tooltip />
      <el-table-column prop="regionName" label="所属区域" width="120" />
      <el-table-column prop="stationType" label="类型" width="100" />
      <el-table-column prop="iotDeviceCount" label="设备数" width="100" />
    </el-table>

    <!-- 企业信息详情表格 -->
    <el-table v-if="dialogType === 'ENT_STATS' || dialogType === 'ENT_LIST'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
      <el-table-column prop="regionName" label="区域" width="100" />
      <el-table-column prop="legalPerson" label="法人" width="100" />
      <el-table-column prop="stationCount" label="场站数" width="80" />
      <el-table-column prop="userCount" label="用户数" width="100" />
    </el-table>

    <!-- 管网统计详情表格 -->
    <el-table v-if="dialogType === 'PIPE_STATS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="name" label="管段编号" />
      <el-table-column prop="length" label="长度(km)" />
      <el-table-column prop="material" label="材质" />
      <el-table-column prop="pressure" label="压力级制" />
    </el-table>

    <!-- 用户统计详情表格 -->
    <el-table v-if="dialogType === 'USER_STATS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="enterpriseName" label="所属企业" show-overflow-tooltip />
      <el-table-column prop="regionName" label="所属区域" width="120" />
      <el-table-column prop="userCount" label="用户总数" width="120" sortable />
    </el-table>

    <!-- 核心指标详情表格 -->
    <el-table v-if="dialogType === 'CORE_STATS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="label" label="指标项" />
      <el-table-column prop="value" label="数值" />
    </el-table>

    <!-- 应急能力概览详情表格 -->
    <el-table v-if="dialogType === 'EMERGENCY_OVERVIEW'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="regionName" label="所属区域" width="140" />
      <el-table-column prop="emergencyTeamCount" label="队伍" sortable />
      <el-table-column prop="emergencyPersonnelCount" label="人员" sortable />
      <el-table-column prop="emergencyVehicleCount" label="车辆" sortable />
      <el-table-column prop="emergencyMaterialCount" label="物资" sortable />
      <el-table-column prop="emergencyEventProcessingRate" label="处置率(%)" sortable />
    </el-table>

    <!-- 区域设备在线率排行详情表格 -->
    <el-table v-if="dialogType === 'ONLINE_RATE_RANK'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="regionName" label="所属区域" width="160" show-overflow-tooltip />
      <el-table-column prop="rate" label="在线率(%)" width="120" sortable />
      <el-table-column prop="normal" label="在线设备" width="120" sortable />
      <el-table-column prop="total" label="设备总数" width="120" sortable />
      <el-table-column prop="stationCount" label="场站数" width="120" sortable />
    </el-table>

    <!-- 区域设备在线详情表格 -->
    <el-table v-if="dialogType === 'ONLINE_RATE_DETAIL'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="stationName" label="场站名称" show-overflow-tooltip />
      <el-table-column prop="stationType" label="类型" width="110" />
      <el-table-column prop="regionName" label="所属区域" width="120" />
      <el-table-column prop="iotDeviceCount" label="设备数" width="100" sortable />
      <el-table-column prop="normalDeviceCount" label="在线数" width="100" sortable />
      <el-table-column prop="rate" label="在线率(%)" width="110" sortable />
    </el-table>

    <!-- 企业用气与用户规模详情表格 -->
    <el-table v-if="dialogType === 'ENT_GAS_USER_RANK'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column type="index" label="排名" width="60" align="center" />
      <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
      <el-table-column prop="regionName" label="区域" width="120" />
      <el-table-column prop="userCount" label="用户数" width="120" sortable />
      <el-table-column prop="totalGasSales" label="用气量(m³)" width="140" sortable />
    </el-table>

    <!-- 企业详情表格 -->
    <el-table v-if="dialogType === 'ENT_GAS_USER_DETAIL'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
      <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
      <el-table-column prop="regionName" label="区域" width="120" />
      <el-table-column prop="userCount" label="用户数" width="120" sortable />
      <el-table-column prop="totalGasSales" label="用气量(m³)" width="160" sortable />
      <el-table-column prop="stationCount" label="场站数" width="100" sortable />
    </el-table>
  </el-dialog>
</template>

<script setup>
// 导入Vue核心API
import { ref, onMounted, nextTick, onUnmounted, computed } from 'vue'
// 导入API接口
import { gasMapApi } from '@/api/mapApi'
// 导入Element Plus组件
import { ElMessage } from 'element-plus'
// 导入ECharts图表库
import * as echarts from 'echarts'
// 导入Element Plus图标
import { 
  DataAnalysis, // 数据分析图标
  Timer, // 计时器图标
  Odometer, // 里程表图标
  OfficeBuilding, // 办公楼图标
  Location, // 位置图标
  Connection, // 连接图标
  UserFilled, // 用户填充图标
  PieChart, // 饼图图标
  TrendCharts, // 趋势图表图标
  List, // 列表图标
  FirstAidKit // 急救箱图标
} from '@element-plus/icons-vue'

// 响应式数据定义
const enterpriseList = ref([]) // 企业列表数据
const statInfo = ref({}) // 统计信息数据
const stationList = ref([]) // 场站列表数据
const monitorList = ref([]) // 监测数据列表
const pipeList = ref([]) // 管网列表数据
const emergencyList = ref([]) // 应急资源列表数据
const topGasUsers = ref([]) // 用气量TOP10数据
const currentTime = ref('') // 当前时间
let timer = null // 定时器引用

// 图表DOM引用
const enterpriseTreeChartRef = ref(null) // 企业树图DOM引用
const stationWaterfallChartRef = ref(null) // 场站瀑布图DOM引用
const userLollipopChartRef = ref(null) // 用户棒棒糖图DOM引用
const gasCalendarChartRef = ref(null) // 用气日历热力图DOM引用
const stationBubbleChartRef = ref(null) // 场站气泡图DOM引用
const pipeHistogramChartRef = ref(null) // 管网直方图DOM引用
const stationOnlinePictorialChartRef = ref(null) // 场站在线率象形图DOM引用
const enterpriseEffectScatterChartRef = ref(null) // 企业效果散点图DOM引用
const emergencyBulletChartRef = ref(null) // 应急能力子弹图DOM引用

// 图表实例
let enterpriseTreeChart = null // 企业树图实例
let stationWaterfallChart = null // 场站瀑布图实例
let userLollipopChart = null // 用户棒棒糖图实例
let gasCalendarChart = null // 用气日历热力图实例
let stationBubbleChart = null // 场站气泡图实例
let pipeHistogramChart = null // 管网直方图实例
let stationOnlinePictorialChart = null // 场站在线率象形图实例
let enterpriseEffectScatterChart = null // 企业效果散点图实例
let emergencyBulletChart = null // 应急能力子弹图实例

let chartEventsBound = false // 图表事件绑定标记
const dialogVisible = ref(false) // 对话框显示状态
const dialogTitle = ref('') // 对话框标题
const dialogType = ref('') // 对话框类型
const dialogData = ref([]) // 对话框数据

// 计算属性：门站列表
const gateStationList = computed(() => (stationList.value || []).filter(s => (s.stationType || '').includes('门站')))
// 计算属性：充装站列表
const chargingStationList = computed(() => (stationList.value || []).filter(s => (s.stationType || '').includes('充装')))

// 计算属性：关键场站列表
const keyStationList = computed(() => (stationList.value || []).filter(s => (s.stationType || '').includes('门站') || (s.stationType || '').includes('充装')))

// 计算属性：应急资源统计
const emergencyStat = computed(() => {
  if (!emergencyList.value.length) return { teams: 0, personnel: 0, vehicles: 0, materials: 0 }
  return emergencyList.value.reduce((acc, curr) => {
    acc.teams += (curr.emergencyTeamCount || 0)
    acc.personnel += (curr.emergencyPersonnelCount || 0)
    acc.vehicles += (curr.emergencyVehicleCount || 0)
    acc.materials += (curr.emergencyMaterialCount || 0)
    return acc
  }, { teams: 0, personnel: 0, vehicles: 0, materials: 0 })
})

// 更新当前时间函数
const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString()
}

// 计算属性：平均管网长度 per 企业
const avgPipePerEnt = computed(() => {
  const count = statInfo.value.enterpriseCount || 1
  const total = statInfo.value.totalPipeLength || 0
  return (total / count).toFixed(1)
})

// 计算属性：平均用户数 per 企业
const avgUserPerEnt = computed(() => {
  const count = statInfo.value.enterpriseCount || 1
  const total = statInfo.value.totalUserCount || 0
  return Math.round(total / count).toLocaleString()
})

// 计算属性：平均场站数 per 企业
const avgStationPerEnt = computed(() => {
  const count = statInfo.value.enterpriseCount || 1
  const total = statInfo.value.stationCount || 0
  return (total / count).toFixed(1)
})

// 计算属性：核心指标数据
const coreKpi = computed(() => {
  const s = statInfo.value || {}
  // 判断值是否为nil
  const isNil = (v) => v === null || v === undefined
  // 格式化整数
  const fmtInt = (v) => {
    if (isNil(v)) return '—'
    const n = Number(v)
    if (!Number.isFinite(n)) return '—'
    return Math.round(n).toLocaleString()
  }
  // 格式化保留一位小数的浮点数
  const fmtFloat1 = (v) => {
    if (isNil(v)) return '—'
    const n = Number(v)
    if (!Number.isFinite(n)) return '—'
    return n.toFixed(1)
  }

  const enterpriseCount = fmtInt(s.enterpriseCount)
  const stationCount = fmtInt(s.stationCount)
  const totalPipeLength = fmtFloat1(s.totalPipeLength)
  const totalUserCount = fmtInt(s.totalUserCount)

  return {
    enterpriseCount,
    stationCount,
    totalPipeLength,
    totalUserCount,
    avgPipePerEnt: enterpriseCount === '—' ? '—' : `${avgPipePerEnt.value} km/企`,
    avgStationPerEnt: enterpriseCount === '—' ? '—' : `${avgStationPerEnt.value} 座/企`,
    avgUserPerEnt: enterpriseCount === '—' ? '—' : `${avgUserPerEnt.value} 户/企`
  }
})

// 初始化图表函数
const initCharts = () => {
  if (enterpriseTreeChartRef.value) enterpriseTreeChart = echarts.init(enterpriseTreeChartRef.value)
  if (stationWaterfallChartRef.value) stationWaterfallChart = echarts.init(stationWaterfallChartRef.value)
  if (userLollipopChartRef.value) userLollipopChart = echarts.init(userLollipopChartRef.value)
  if (gasCalendarChartRef.value) gasCalendarChart = echarts.init(gasCalendarChartRef.value)
  if (stationBubbleChartRef.value) stationBubbleChart = echarts.init(stationBubbleChartRef.value)
  if (pipeHistogramChartRef.value) pipeHistogramChart = echarts.init(pipeHistogramChartRef.value)
  if (stationOnlinePictorialChartRef.value) stationOnlinePictorialChart = echarts.init(stationOnlinePictorialChartRef.value)
  if (enterpriseEffectScatterChartRef.value) enterpriseEffectScatterChart = echarts.init(enterpriseEffectScatterChartRef.value)
  if (emergencyBulletChartRef.value) emergencyBulletChart = echarts.init(emergencyBulletChartRef.value)
  // 绑定图表事件
  bindChartEvents()
}

// 安全设置图表选项函数
const safeSetOption = (chart, option) => {
  if (!chart) return
  chart.clear()
  chart.setOption(option, true)
}

// 格式化日期函数
const formatDate = (d) => {
  const date = new Date(d)
  if (!Number.isFinite(date.getTime())) return null
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${y}-${m}-${day}`
}

// 获取日期键函数
function getDateKey(d) {
  if (!d) return null
  if (typeof d === 'string') return d.substring(0, 10)
  const date = new Date(d)
  if (!Number.isFinite(date.getTime())) return null
  return date.toISOString().substring(0, 10)
}

// 猜测场站所属区域函数
const guessStationRegion = (station, regions) => {
  const direct = station?.regionName
  if (direct) return direct
  const stationName = station?.stationName || ''
  const text = `${stationName} ${station?.enterpriseName || ''}`
  for (const r of regions) {
    if (r && text.includes(r)) return r
  }
  const m = stationName.match(/^(.{2,8}?(?:街道|镇|乡|区))/)
  if (m?.[1]) return m[1]
  return '—'
}

// 计算区域在线率排名函数
const computeOnlineRank = (stations, regions) => {
  const regionOnline = new Map()
  stations.forEach(s => {
    const r = guessStationRegion(s, regions)
    const total = Number(s.iotDeviceCount || 0)
    const normal = Number(s.normalDeviceCount || 0)
    if (!regionOnline.has(r)) regionOnline.set(r, { total: 0, normal: 0, stationCount: 0 })
    const v = regionOnline.get(r)
    v.total += total
    v.normal += normal
    v.stationCount += 1
  })
  return Array.from(regionOnline.entries())
    .map(([regionName, v]) => ({
      regionName,
      total: v.total,
      normal: v.normal,
      stationCount: v.stationCount,
      rate: v.total ? Number(((v.normal / v.total) * 100).toFixed(1)) : 0
    }))
    .sort((a, b) => b.rate - a.rate)
}

// 绑定图表事件函数
const bindChartEvents = () => {
  if (chartEventsBound) return
  if (stationOnlinePictorialChart) {
    // 绑定场站在线率图表点击事件
    stationOnlinePictorialChart.on('click', (params) => {
      const regionName = params?.name
      if (regionName) openDialog('ONLINE_RATE_DETAIL', { regionName })
    })
  }
  if (enterpriseEffectScatterChart) {
    // 绑定企业效果散点图点击事件
    enterpriseEffectScatterChart.on('click', (params) => {
      const v = params?.value
      const enterpriseId = v?.[4]
      const enterpriseName = v?.[2]
      openDialog('ENT_GAS_USER_DETAIL', { enterpriseId, enterpriseName })
    })
  }
  chartEventsBound = true
}

// 更新图表函数
const updateCharts = () => {
  // 获取数据
  const sInfo = statInfo.value || {} // 统计信息
  const enterprises = enterpriseList.value || [] // 企业列表
  const stations = stationList.value || [] // 场站列表
  const pipes = pipeList.value || [] // 管网列表
  const monitors = monitorList.value || [] // 监测数据列表
  const emergencies = emergencyList.value || [] // 应急资源列表

  const regionMap = new Map()
  enterprises.forEach(e => {
    const r = e.regionName || '—'
    if (!regionMap.has(r)) regionMap.set(r, [])
    regionMap.get(r).push(e)
  })
  const regionNodes = Array.from(regionMap.entries())
    .map(([r, list]) => ({
      name: r,
      value: list.reduce((s, i) => s + Number(i.userCount || 0), 0),
      children: list
        .sort((a, b) => Number(b.userCount || 0) - Number(a.userCount || 0))
        .slice(0, 15)
        .map(i => ({
          name: i.enterpriseName || '—',
          value: Number(i.userCount || 0)
        }))
    }))
    .sort((a, b) => b.value - a.value)
    .slice(0, 10)
  safeSetOption(enterpriseTreeChart, {
    tooltip: { formatter: (p) => `${p.name}<br/>用户: ${Number(p.value || 0).toLocaleString()}` },
    series: [{
      type: 'tree',
      data: [{ name: '漳平市', children: regionNodes }],
      top: 10,
      left: 10,
      bottom: 10,
      right: 40,
      symbol: 'circle',
      symbolSize: 8,
      orient: 'LR',
      expandAndCollapse: true,
      initialTreeDepth: 2,
      label: {
        position: 'left',
        verticalAlign: 'middle',
        align: 'right',
        color: '#a0cfff',
        fontSize: 10,
        width: 120,
        overflow: 'truncate'
      },
      leaves: {
        label: {
          position: 'right',
          align: 'left',
          color: '#fff',
          width: 160,
          overflow: 'truncate'
        }
      },
      lineStyle: { color: 'rgba(64,158,255,0.35)' }
    }]
  })

  const typeCount = new Map()
  stations.forEach(s => {
    const t = s.stationType || '其他'
    typeCount.set(t, (typeCount.get(t) || 0) + 1)
  })
  const topTypes = Array.from(typeCount.entries())
    .sort((a, b) => b[1] - a[1])
    .slice(0, 6)
  const wfCategories = [...topTypes.map(i => i[0]), '总计']
  const wfVals = [...topTypes.map(i => i[1])]
  const wfTotal = wfVals.reduce((a, b) => a + b, 0)
  const assist = []
  let sum = 0
  wfVals.forEach(v => {
    assist.push(sum)
    sum += v
  })
  assist.push(0)
  const wfData = [...wfVals, wfTotal]
  safeSetOption(stationWaterfallChart, {
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: 40, right: 20, top: 30, bottom: 40, containLabel: true },
    xAxis: { type: 'category', data: wfCategories, axisLabel: { color: '#a0cfff', rotate: 20 } },
    yAxis: { type: 'value', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.15)' } } },
    series: [
      { type: 'bar', stack: 'wf', itemStyle: { color: 'transparent' }, data: assist, silent: true },
      {
        name: '数量',
        type: 'bar',
        stack: 'wf',
        data: wfData,
        itemStyle: {
          color: (p) => (p.dataIndex === wfData.length - 1 ? '#67C23A' : '#E6A23C')
        },
        label: { show: true, position: 'top', color: '#fff' }
      }
    ]
  })

  const topUsers = (topGasUsers.value && topGasUsers.value.length)
    ? topGasUsers.value
    : enterprises
      .map(e => ({
        name: e.enterpriseName,
        value: Number(e.totalGasSales || 0) > 0 ? Number(e.totalGasSales) : Number(e.userCount || 0)
      }))
      .filter(i => i.name && Number.isFinite(i.value))
      .sort((a, b) => b.value - a.value)
      .slice(0, 10)
  const lpNames = topUsers.map(i => i.name)
  const lpVals = topUsers.map(i => Number(i.value || 0))
  const lpMax = Math.max(1, ...lpVals)
  safeSetOption(userLollipopChart, {
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: 60, right: 30, top: 20, bottom: 30, containLabel: true },
    xAxis: { type: 'value', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.12)' } } },
    yAxis: {
      type: 'category',
      data: lpNames.slice().reverse(),
      axisLabel: { color: '#a0cfff', width: 180, overflow: 'truncate' }
    },
    series: [
      {
        name: '对比线',
        type: 'bar',
        data: lpVals.slice().reverse(),
        barWidth: 2,
        itemStyle: { color: 'rgba(64,158,255,0.45)' },
        emphasis: { disabled: true },
        z: 1
      },
      {
        name: '节点',
        type: 'scatter',
        data: lpVals.slice().reverse().map((v, idx) => [v, idx]),
        symbol: 'circle',
        symbolSize: (val) => {
          const v = Number(val[0] || 0)
          const s = 8 + (v / lpMax) * 16
          return Math.max(8, Math.min(24, s))
        },
        itemStyle: { color: '#409EFF', borderColor: '#fff', borderWidth: 1 },
        z: 2
      }
    ]
  })

  const dailyMap = new Map()
  monitors.forEach(m => {
    const d = formatDate(m.statisticsDate)
    if (!d) return
    dailyMap.set(d, (dailyMap.get(d) || 0) + Number(m.totalGasConsumption || 0))
  })
  const days = Array.from(dailyMap.keys()).sort()
  const calRange = (() => {
    const base = days.length ? new Date(days[days.length - 1]) : new Date()
    const y = base.getFullYear()
    const m = String(base.getMonth() + 1).padStart(2, '0')
    return `${y}-${m}`
  })()
  const calData = days.map(d => [d, Number(dailyMap.get(d)).toFixed(2)])
  const calMax = Math.max(1, ...calData.map(i => Number(i[1])))
  safeSetOption(gasCalendarChart, {
    tooltip: { formatter: (p) => `${p.value[0]}<br/>用气: ${p.value[1]}` },
    visualMap: {
      min: 0,
      max: calMax,
      orient: 'horizontal',
      left: 'center',
      bottom: 0,
      textStyle: { color: '#a0cfff', fontSize: 10 },
      inRange: { color: ['#0d2546', '#1f3a5e', '#409EFF', '#67C23A', '#E6A23C'] }
    },
    calendar: {
      left: 'center',
      top: 40,
      range: calRange,
      cellSize: ['auto', 18],
      itemStyle: { color: '#020f26', borderWidth: 1, borderColor: '#1f3a5e' },
      dayLabel: { color: '#a0cfff', firstDay: 1, fontSize: 10 },
      monthLabel: { color: '#409EFF', fontSize: 12 },
      yearLabel: { show: false }
    },
    series: [{ type: 'heatmap', coordinateSystem: 'calendar', data: calData }]
  })

  const keyStations = (keyStationList.value && keyStationList.value.length) ? keyStationList.value : stations
  const bubble = keyStations.slice(0, 40).map(s => [
    Number(s.iotDeviceCount || 0),
    Number(s.normalDeviceCount || 0),
    Number(s.gasSupplyCapacity || 0),
    s.stationName || '—',
    s.stationType || '—',
    s.regionName || '—'
  ])
  const xMax = Math.max(1, ...bubble.map(i => i[0]))
  const yMax = Math.max(1, ...bubble.map(i => i[1]))
  const zMax = Math.max(1, ...bubble.map(i => i[2]))
  safeSetOption(stationBubbleChart, {
    tooltip: {
      formatter: (p) => `${p.value[3]}<br/>类型: ${p.value[4]}<br/>区域: ${p.value[5]}<br/>设备: ${p.value[0]}<br/>正常: ${p.value[1]}<br/>供气: ${p.value[2]}`
    },
    grid: { left: 50, right: 20, top: 20, bottom: 40, containLabel: true },
    xAxis: { type: 'value', max: xMax, name: '设备数', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.10)' } } },
    yAxis: { type: 'value', max: yMax, name: '正常设备', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.10)' } } },
    series: [{
      type: 'scatter',
      data: bubble,
      symbolSize: (val) => Math.max(8, (Number(val[2]) / zMax) * 32),
      itemStyle: { color: 'rgba(230,162,60,0.85)' }
    }]
  })

  const lengths = pipes.map(p => Number(p.length || 0)).filter(v => Number.isFinite(v) && v >= 0)
  const maxLen = Math.max(1, ...lengths)
  const bins = 8
  const step = maxLen / bins
  const hist = Array.from({ length: bins }, () => 0)
  lengths.forEach(v => {
    const idx = Math.min(bins - 1, Math.floor(v / step))
    hist[idx] += 1
  })
  const histLabels = hist.map((_, i) => `${(i * step).toFixed(0)}-${((i + 1) * step).toFixed(0)}`)
  safeSetOption(pipeHistogramChart, {
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: 50, right: 20, top: 20, bottom: 45, containLabel: true },
    xAxis: { type: 'category', data: histLabels, axisLabel: { color: '#a0cfff', rotate: 20 } },
    yAxis: { type: 'value', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.12)' } } },
    series: [{
      type: 'bar',
      data: hist,
      barWidth: '55%',
      itemStyle: { color: 'rgba(103,194,58,0.75)' }
    }]
  })

  const knownRegions = Array.from(new Set((enterprises || []).map(e => e.regionName).filter(Boolean)))
  const onlineRows = computeOnlineRank(stations, knownRegions).slice(0, 12)
  const onlineNames = onlineRows.map(i => i.regionName).reverse()
  const onlineRates = onlineRows.map(i => Number(i.rate.toFixed(1))).reverse()
  if (stationOnlinePictorialChart) {
    if (!onlineNames.length) {
      stationOnlinePictorialChart.clear()
      stationOnlinePictorialChart.setOption({
        graphic: { type: 'text', left: 'center', top: 'middle', style: { text: '暂无数据', fill: '#a0cfff', fontSize: 14 } }
      })
    } else {
      safeSetOption(stationOnlinePictorialChart, {
        tooltip: {
          trigger: 'axis',
          axisPointer: { type: 'shadow' },
          formatter: (params) => {
            const idx = params?.[0]?.dataIndex ?? 0
            const item = onlineRows.slice().reverse()[idx]
            return `${item.regionName}<br/>在线率: ${item.rate.toFixed(1)}%<br/>在线设备: ${item.normal}<br/>设备总数: ${item.total}`
          }
        },
        grid: { left: 80, right: 30, top: 20, bottom: 30, containLabel: true },
        xAxis: {
          type: 'value',
          min: 0,
          max: 100,
          axisLabel: { color: '#a0cfff', formatter: '{value}%' },
          splitLine: { lineStyle: { color: 'rgba(64,158,255,0.12)' } }
        },
        yAxis: { type: 'category', data: onlineNames, axisLabel: { color: '#a0cfff', width: 160, overflow: 'truncate' } },
        series: [
          {
            type: 'bar',
            data: onlineNames.map(() => 100),
            barWidth: 16,
            itemStyle: { color: 'rgba(64,158,255,0.10)' },
            silent: true,
            z: 1
          },
          {
            type: 'pictorialBar',
            data: onlineRates,
            symbol: 'roundRect',
            symbolRepeat: 'fixed',
            symbolMargin: 2,
            symbolSize: [8, 14],
            symbolBoundingData: 100,
            symbolClip: true,
            itemStyle: { color: '#67C23A' },
            z: 2,
            label: { show: true, position: 'right', color: '#fff', formatter: '{c}%' }
          }
        ]
      })
    }
  }

  const latestDate = (() => {
    const daily = monitors.filter(m => m.statisticsType === 'DAILY_TOTAL')
    const keys = (daily.length ? daily : monitors).map(m => getDateKey(m.statisticsDate)).filter(Boolean)
    return keys.length ? keys.sort().reverse()[0] : ''
  })()

  const points = enterprises
    .map((e, idx) => {
      const x = Math.max(0, Number(e.userCount || 0))
      const y = Math.max(0, Number(e.totalGasSales || 0))
      return { name: e.enterpriseName || '—', region: e.regionName || '—', id: e.enterpriseId || idx, x, y }
    })
    .filter(p => Number.isFinite(p.x) && Number.isFinite(p.y) && (p.x > 0 || p.y > 0))
  const yMax2 = Math.max(1, ...points.map(p => p.y))
  const xMax2 = Math.max(1, ...points.map(p => p.x))
  if (enterpriseEffectScatterChart) {
    if (!points.length) {
      enterpriseEffectScatterChart.clear()
      enterpriseEffectScatterChart.setOption({
        graphic: { type: 'text', left: 'center', top: 'middle', style: { text: '暂无数据', fill: '#a0cfff', fontSize: 14 } }
      })
    } else {
      safeSetOption(enterpriseEffectScatterChart, {
        tooltip: {
          formatter: (p) => `${p.value[2]}<br/>区域: ${p.value[3]}<br/>用户数: ${Number(p.value[0]).toLocaleString()}<br/>用气量: ${Number(p.value[1]).toFixed(2)} m³<br/>统计日期: ${latestDate || '—'}`
        },
        grid: { left: 55, right: 20, top: 25, bottom: 45, containLabel: true },
        xAxis: {
          type: 'value',
          min: 0,
          max: xMax2,
          name: '用户数',
          nameTextStyle: { color: '#a0cfff' },
          axisLabel: { color: '#a0cfff' },
          splitLine: { lineStyle: { color: 'rgba(64,158,255,0.10)' } }
        },
        yAxis: {
          type: 'value',
          min: 0,
          max: yMax2,
          name: '用气量',
          nameTextStyle: { color: '#a0cfff' },
          axisLabel: { color: '#a0cfff' },
          splitLine: { lineStyle: { color: 'rgba(64,158,255,0.10)' } }
        },
        series: [{
          type: 'effectScatter',
          data: points.map(i => [i.x, i.y, i.name, i.region, i.id]),
          symbolSize: (val) => {
            const y = Number(val[1] || 0)
            const s = 10 + (y / yMax2) * 22
            return Math.max(10, Math.min(32, s))
          },
          rippleEffect: { scale: 2.2, brushType: 'stroke' },
          itemStyle: { color: '#409EFF' }
        }]
      })
    }
  }

  const regionEmergency = new Map()
  emergencies.forEach(e => {
    const r = e.regionName || '—'
    if (!regionEmergency.has(r)) regionEmergency.set(r, { personnel: 0, vehicles: 0, materials: 0, rate: 0, rateCount: 0 })
    const v = regionEmergency.get(r)
    v.personnel += Number(e.emergencyPersonnelCount || 0)
    v.vehicles += Number(e.emergencyVehicleCount || 0)
    v.materials += Number(e.emergencyMaterialCount || 0)
    if (Number.isFinite(Number(e.emergencyEventProcessingRate))) {
      v.rate += Number(e.emergencyEventProcessingRate)
      v.rateCount += 1
    }
  })
  const regionEmergencyArr = Array.from(regionEmergency.entries())
    .map(([r, v]) => ({
      r,
      personnel: v.personnel,
      vehicles: v.vehicles,
      materials: v.materials,
      rate: v.rateCount ? v.rate / v.rateCount : 0
    }))
    .sort((a, b) => b.rate - a.rate)
    .slice(0, 12)

  const cat = regionEmergencyArr.map(i => i.r)
  const rateSeries = regionEmergencyArr.map(i => Number(i.rate.toFixed(1)))
  const personnelSeries = regionEmergencyArr.map(i => i.personnel)
  const vehicleSeries = regionEmergencyArr.map(i => i.vehicles)
  const materialSeries = regionEmergencyArr.map(i => i.materials)
  const maxPersonnel = Math.max(1, ...personnelSeries)
  const maxVehicles = Math.max(1, ...vehicleSeries)
  const maxMaterials = Math.max(1, ...materialSeries)
  safeSetOption(emergencyBulletChart, {
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: 80, right: 30, top: 20, bottom: 30, containLabel: true },
    xAxis: {
      type: 'value',
      max: 100,
      axisLabel: { color: '#a0cfff' },
      splitLine: { lineStyle: { color: 'rgba(64,158,255,0.12)' } }
    },
    yAxis: {
      type: 'category',
      data: cat,
      axisLabel: { color: '#a0cfff' }
    },
    series: [
      {
        name: '处置率(%)',
        type: 'bar',
        data: rateSeries,
        barWidth: 12,
        itemStyle: { color: 'rgba(64,158,255,0.35)' },
        label: { show: true, position: 'right', color: '#fff', formatter: '{c}%' }
      },
      {
        name: '人员/车/物资(归一化)',
        type: 'scatter',
        data: regionEmergencyArr.map((i, idx) => [
          Math.min(100, (i.personnel / maxPersonnel) * 100),
          idx,
          i.personnel,
          i.vehicles,
          i.materials
        ]),
        symbolSize: 10,
        itemStyle: { color: '#E6A23C' },
        tooltip: {
          formatter: (p) => {
            const item = regionEmergencyArr[p.value[1]]
            return `${item.r}<br/>处置率: ${rateSeries[p.value[1]]}%<br/>人员: ${item.personnel}<br/>车辆: ${item.vehicles}<br/>物资: ${item.materials}`
          }
        }
      }
    ]
  })
}

const resizeCharts = () => {
  enterpriseTreeChart?.resize()
  stationWaterfallChart?.resize()
  userLollipopChart?.resize()
  gasCalendarChart?.resize()
  stationBubbleChart?.resize()
  pipeHistogramChart?.resize()
  stationOnlinePictorialChart?.resize()
  enterpriseEffectScatterChart?.resize()
  emergencyBulletChart?.resize()
}

// 组件挂载时执行
onMounted(async () => {
  // 更新当前时间
  updateTime()
  // 设置定时器，每秒更新时间
  timer = setInterval(updateTime, 1000)
  // 初始化图表
  initCharts()
  // 添加窗口大小改变事件监听
  window.addEventListener('resize', resizeCharts)
  try {
    // 调用API获取总数据
    const res = await gasMapApi.getTotalData()
    // 更新统计信息
    statInfo.value = res.statInfo || {}
    
    // 处理项目列表，聚合施工人员
    const projectMap = new Map()
    if (res.projectList) {
      res.projectList.forEach(p => {
         const name = p.enterpriseName
         if (name) {
           projectMap.set(name, (projectMap.get(name) || 0) + (p.constructionPersonnelCount || 0))
         }
      })
    }

    // 更新企业列表，添加施工人员数量
    enterpriseList.value = (res.enterpriseList || []).map(e => ({
      ...e,
      constructionPersonnelCount: projectMap.get(e.enterpriseName) || 0
    }))

    // 更新其他数据列表
    stationList.value = res.stationList || []
    monitorList.value = res.monitorList || []
    emergencyList.value = res.emergencyList || []

    // 处理管网数据
    if (res.pipeList) {
      pipeList.value = res.pipeList.map(item => ({
        name: item.pipeSegmentCode || '—',
        length: item.pipeLength ?? 0,
        material: item.pipeMaterial ?? '—',
        pressure: item.pressureLevel ?? '—',
        status: item.deviceStatus ?? '—'
      }))
    }

    // 处理用气量Top10 (基于最新日期的监控数据)
    if (res.monitorList && res.monitorList.length > 0) {
      // 获取最新日期
      const dates = res.monitorList.map(m => {
        const d = m.statisticsDate;
        return typeof d === 'string' ? d.substring(0, 10) : new Date(d).toISOString().substring(0, 10);
      });
      const latestDate = [...new Set(dates)].sort().reverse()[0];
      
      const enterpriseGas = {}
      res.monitorList.forEach(m => {
        const d = m.statisticsDate;
        const dateStr = typeof d === 'string' ? d.substring(0, 10) : new Date(d).toISOString().substring(0, 10);
        
        if (dateStr === latestDate && m.enterpriseName && m.totalGasConsumption) {
          if (!enterpriseGas[m.enterpriseName]) {
            enterpriseGas[m.enterpriseName] = 0
          }
          enterpriseGas[m.enterpriseName] += Number(m.totalGasConsumption)
        }
      })
      topGasUsers.value = Object.entries(enterpriseGas)
        .map(([name, value]) => ({ name, value }))
        .sort((a, b) => b.value - a.value)
        .slice(0, 10)
      if (!topGasUsers.value.length && res.enterpriseList) {
        topGasUsers.value = res.enterpriseList
          .map(e => ({
            name: e.enterpriseName,
            value: Number(e.totalGasSales || 0) > 0 ? Number(e.totalGasSales) : Number(e.userCount || 0)
          }))
          .filter(i => i.name && Number.isFinite(i.value))
          .sort((a, b) => b.value - a.value)
          .slice(0, 10)
      }
    } else if (res.enterpriseList) {
        topGasUsers.value = res.enterpriseList
          .map(e => ({
            name: e.enterpriseName,
            value: Number(e.totalGasSales || 0) > 0 ? Number(e.totalGasSales) : Number(e.userCount || 0)
          }))
          .filter(i => i.name && Number.isFinite(i.value))
          .sort((a, b) => b.value - a.value)
          .slice(0, 10)
    }
    
    // 下一帧更新图表
    nextTick(() => {
      updateCharts()
      setTimeout(() => resizeCharts(), 0)
    })
  } catch (e) {
    console.error(e)
    ElMessage.error('获取基础信息失败')
  }
})

// 组件卸载时执行
onUnmounted(() => {
  // 清除定时器
  if (timer) clearInterval(timer)
  // 移除窗口大小改变事件监听
  window.removeEventListener('resize', resizeCharts)
  // 销毁图表实例
  enterpriseTreeChart?.dispose()
  stationWaterfallChart?.dispose()
  userLollipopChart?.dispose()
  gasCalendarChart?.dispose()
  stationBubbleChart?.dispose()
  pipeHistogramChart?.dispose()
  stationOnlinePictorialChart?.dispose()
  enterpriseEffectScatterChart?.dispose()
  emergencyBulletChart?.dispose()
})

// 打开对话框函数
const openDialog = (type, payload = {}) => {
  dialogType.value = type
  dialogVisible.value = true
  if (type === 'TOP_USERS') {
    dialogTitle.value = '用气量 TOP10 详情'
    dialogData.value = [...topGasUsers.value]
  } else if (type === 'PIPE_INFO' || type === 'PIPE_STATS') {
    dialogTitle.value = '管网信息详情'
    dialogData.value = pipeList.value
  } else if (type === 'GAS_TREND') {
    dialogTitle.value = '用气总态势详情'
    const dateMap = new Map()
    monitorList.value.forEach(item => {
      let dateStr = '—'
      if (item.statisticsDate) {
        dateStr = typeof item.statisticsDate === 'string' ? item.statisticsDate.substring(0, 10) : new Date(item.statisticsDate).toISOString().substring(0, 10)
      }
      const current = dateMap.get(dateStr) || 0
      dateMap.set(dateStr, current + Number(item.totalGasConsumption || 0))
    })
    const dates = Array.from(dateMap.keys()).sort().reverse()
    dialogData.value = dates.map(d => ({ date: d, value: Number(dateMap.get(d)).toFixed(2) }))
  } else if (type === 'GATE_STATIONS') {
    dialogTitle.value = '燃气门站监管详情'
    dialogData.value = gateStationList.value
  } else if (type === 'CHARGING_STATIONS') {
    dialogTitle.value = '充装站信息详情'
    dialogData.value = chargingStationList.value
  } else if (type === 'ENT_STATS' || type === 'ENT_LIST') {
    dialogTitle.value = '燃气企业名录详情'
    dialogData.value = enterpriseList.value
  } else if (type === 'STATION_STATS' || type === 'STATION_DIST') {
    dialogTitle.value = '场站分布详情'
    dialogData.value = stationList.value
  } else if (type === 'USER_STATS') {
    dialogTitle.value = '用户分布详情'
    dialogData.value = enterpriseList.value
  } else if (type === 'CORE_STATS') {
    dialogTitle.value = '核心指标详情'
    dialogData.value = [
      { label: '企业数量', value: (statInfo.value.enterpriseCount || 0) + ' 家' },
      { label: '场站总数', value: (statInfo.value.stationCount || 0) + ' 座' },
      { label: '管网长度', value: (statInfo.value.totalPipeLength ? Number(statInfo.value.totalPipeLength).toFixed(1) : 0) + ' km' },
      { label: '用户总数', value: (statInfo.value.totalUserCount || 0) + ' 户' }
    ]
  } else if (type === 'EMERGENCY_OVERVIEW') {
    dialogTitle.value = '应急能力概览详情'
    dialogData.value = (emergencyList.value || []).map(i => ({
      regionName: i.regionName || '—',
      emergencyTeamCount: Number(i.emergencyTeamCount ?? 0),
      emergencyPersonnelCount: Number(i.emergencyPersonnelCount || 0),
      emergencyVehicleCount: Number(i.emergencyVehicleCount || 0),
      emergencyMaterialCount: Number(i.emergencyMaterialCount || 0),
      emergencyEventProcessingRate: Number.isFinite(Number(i.emergencyEventProcessingRate)) ? Number(i.emergencyEventProcessingRate).toFixed(1) : '—'
    }))
  } else if (type === 'ONLINE_RATE_RANK') {
    dialogTitle.value = '区域设备在线率排行详情'
    const regions = Array.from(new Set((enterpriseList.value || []).map(e => e.regionName).filter(Boolean)))
    dialogData.value = computeOnlineRank(stationList.value || [], regions).slice(0, 50)
  } else if (type === 'ONLINE_RATE_DETAIL') {
    const regionName = payload?.regionName || '—'
    dialogTitle.value = `${regionName} 设备在线详情`
    const regions = Array.from(new Set((enterpriseList.value || []).map(e => e.regionName).filter(Boolean)))
    dialogData.value = (stationList.value || [])
      .filter(s => guessStationRegion(s, regions) === regionName)
      .map(s => {
        const total = Number(s.iotDeviceCount || 0)
        const normal = Number(s.normalDeviceCount || 0)
        return {
          ...s,
          regionName,
          rate: total ? Number(((normal / total) * 100).toFixed(1)) : 0
        }
      })
      .sort((a, b) => Number(b.rate || 0) - Number(a.rate || 0))
  } else if (type === 'ENT_GAS_USER_RANK') {
    dialogTitle.value = '企业用气与用户规模详情'
    dialogData.value = (enterpriseList.value || [])
      .map(e => ({
        ...e,
        totalGasSales: Number(e.totalGasSales || 0)
      }))
      .sort((a, b) => Number(b.totalGasSales || 0) - Number(a.totalGasSales || 0))
  } else if (type === 'ENT_GAS_USER_DETAIL') {
    const id = payload?.enterpriseId
    const name = payload?.enterpriseName
    const list = enterpriseList.value || []
    const found = list.find(e => (e.enterpriseId ?? '') === String(id ?? '')) || list.find(e => (e.enterpriseName ?? '') === String(name ?? ''))
    dialogTitle.value = found?.enterpriseName ? `${found.enterpriseName} 详情` : '企业详情'
    dialogData.value = found ? [{
      ...found,
      totalGasSales: Number(found.totalGasSales || 0)
    }] : []
  }
}
</script>

<style scoped>
.basic-container {
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


.content-scroll {
  flex: 1;
  padding: 15px;
  overflow-y: auto;
}

.charts-grid {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: 15px;
}

.span-12 { grid-column: span 12; }
.span-6 { grid-column: span 6; }
.span-5 { grid-column: span 5; }
.span-4 { grid-column: span 4; }

.panel-md { height: 280px; }
.panel-sm { height: 320px; }
.panel-lg { height: 420px; }

.grid-item {
  background: rgba(13, 37, 70, 0.6);
  border: 1px solid rgba(64, 158, 255, 0.2);
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: transform 0.3s;
}

.grid-item:hover {
  box-shadow: 0 0 15px rgba(64, 158, 255, 0.2);
  border-color: rgba(64, 158, 255, 0.5);
}

.span-2 {
  grid-column: span 2;
}

.panel-header {
  height: 40px;
  padding: 0 15px;
  display: flex;
  align-items: center;
  gap: 8px;
  background: rgba(64, 158, 255, 0.1);
  border-bottom: 1px solid rgba(64, 158, 255, 0.1);
  color: #409EFF;
  font-weight: bold;
  font-size: 15px;
}
.panel-header.clickable { cursor: pointer; }
.panel-header.clickable:hover { color: #fff; background: rgba(64, 158, 255, 0.25); }

.chart-container {
  flex: 1;
  width: 100%;
  min-height: 0;
}

.core-kpi-wrap {
  flex: 1;
  min-height: 0;
  width: 100%;
}

.core-kpi-grid {
  flex: 1;
  min-height: 0;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  grid-template-rows: repeat(2, minmax(0, 1fr));
  gap: 14px;
  padding: 14px;
}

.core-kpi-card {
  background: linear-gradient(180deg, rgba(13, 37, 70, 0.55) 0%, rgba(6, 22, 48, 0.45) 100%);
  border: 1px solid rgba(64, 158, 255, 0.14);
  border-radius: 12px;
  padding: 14px 14px 12px;
  display: flex;
  flex-direction: column;
  min-height: 112px;
}

.kpi-header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  min-height: 20px;
}

.kpi-label {
  color: rgba(160, 207, 255, 0.92);
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.2px;
}

.kpi-tag {
  height: 20px;
  padding: 0 8px;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 700;
  color: #fff;
  background: rgba(64, 158, 255, 0.22);
  border: 1px solid rgba(64, 158, 255, 0.26);
}

.kpi-value {
  margin-top: 10px;
  font-size: 32px;
  line-height: 1.05;
  font-weight: 800;
  color: #fff;
  flex: 1;
  display: flex;
  align-items: flex-end;
  gap: 6px;
}

.kpi-unit {
  font-size: 12px;
  color: rgba(160, 207, 255, 0.9);
  font-weight: 600;
  margin-bottom: 2px;
}

.kpi-footer-row {
  margin-top: 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  color: rgba(160, 207, 255, 0.9);
  font-size: 12px;
}

@media (max-width: 1200px) {
  .span-5 { grid-column: span 12; }
}

.kpi-foot-label {
  color: rgba(160, 207, 255, 0.85);
}

.kpi-foot-value {
  color: #d7ebff;
  font-weight: 700;
}

.kpi-primary { border-color: rgba(64, 158, 255, 0.22); }
.kpi-primary .kpi-tag { background: rgba(64, 158, 255, 0.22); border-color: rgba(64, 158, 255, 0.26); }
.kpi-success { border-color: rgba(103, 194, 58, 0.22); }
.kpi-success .kpi-tag { background: rgba(103, 194, 58, 0.22); border-color: rgba(103, 194, 58, 0.26); }
.kpi-warn { border-color: rgba(230, 162, 60, 0.22); }
.kpi-warn .kpi-tag { background: rgba(230, 162, 60, 0.22); border-color: rgba(230, 162, 60, 0.26); }
.kpi-danger { border-color: rgba(245, 108, 108, 0.22); }
.kpi-danger .kpi-tag { background: rgba(245, 108, 108, 0.22); border-color: rgba(245, 108, 108, 0.26); }


.stats-box {
  flex: 1;
  display: grid;
  grid-template-columns: 1fr 1fr;
  grid-template-rows: 1fr 1fr;
  gap: 10px;
  padding: 10px;
}

.stat-block {
  background: rgba(255, 255, 255, 0.03);
  border-radius: 6px;
  display: flex;
  align-items: center;
  padding: 10px;
  gap: 10px;
}

.icon-wrapper {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
}

.stat-block.primary .icon-wrapper { color: #409EFF; background: rgba(64, 158, 255, 0.15); }
.stat-block.success .icon-wrapper { color: #67C23A; background: rgba(103, 194, 58, 0.15); }
.stat-block.warning .icon-wrapper { color: #E6A23C; background: rgba(230, 162, 60, 0.15); }
.stat-block.info .icon-wrapper { color: #909399; background: rgba(144, 147, 153, 0.15); }

.text-wrapper {
  display: flex;
  flex-direction: column;
}

.val {
  font-size: 20px;
  font-weight: bold;
  color: #fff;
}

.label {
  font-size: 12px;
  color: #a0cfff;
}


.table-wrapper {
  flex: 1;
  padding: 10px;
  overflow: hidden;
}

.pipe-val {
  color: #E6A23C;
  font-family: monospace;
}


.insight-content {
  flex: 1;
  padding: 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.insight-row {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.insight-row .label {
  color: #a0cfff;
  font-size: 14px;
}

.insight-row .value {
  color: #fff;
  font-size: 22px;
  font-weight: bold;
}

.insight-row .unit {
  font-size: 12px;
  font-weight: normal;
  color: #909399;
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
</style>
