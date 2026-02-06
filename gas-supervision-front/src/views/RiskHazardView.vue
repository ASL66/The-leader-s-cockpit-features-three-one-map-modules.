<template>
  <!-- 风险隐患监管中心页面 -->
  <!-- 功能说明：展示燃气系统风险隐患数据，包括隐患总数、重大隐患、整改完成率等核心指标 -->
  <!-- 布局结构：顶部为页面标题和时间显示，主体为多个风险隐患数据面板和图表 -->
  <div class="risk-container dark-theme">
    <!-- 页面头部：包含标题和当前时间 -->
    <div class="header">
      <div class="title">
        <el-icon class="icon-pulse"><WarningFilled /></el-icon> 风险隐患监管中心
      </div>
      <div class="header-right">
        <div class="time"><el-icon><Timer /></el-icon> {{ currentTime }}</div>
      </div>
    </div>
    
    <div class="content-scroll">
      <div class="charts-grid">
        <div class="chart-panel span-4 panel-kpi">
          <div class="panel-header">
            <el-icon><WarnTriangleFilled /></el-icon> 风险态势总览
          </div>
          <div class="kpi-grid">
            <div class="kpi-card clickable kpi-danger" @click="openDialog('DANGER_TOTAL')">
              <div class="kpi-header-row">
                <div class="kpi-label">隐患总数</div>
                <div class="kpi-tag">总</div>
              </div>
              <div class="kpi-value">{{ totalDangerCount }}</div>
              <div class="kpi-footer-row">
                <div class="kpi-foot-label">覆盖企业</div>
                <div class="kpi-foot-value">{{ safetyList.length }}</div>
              </div>
            </div>
            <div class="kpi-card clickable kpi-warn" @click="openDialog('MAJOR_DANGER')">
              <div class="kpi-header-row">
                <div class="kpi-label">重大隐患</div>
                <div class="kpi-tag">重</div>
              </div>
              <div class="kpi-value">{{ majorDangerCount }}</div>
              <div class="kpi-footer-row">
                <div class="kpi-foot-label">占比</div>
                <div class="kpi-foot-value">
                  {{ totalDangerCount ? ((majorDangerCount / totalDangerCount) * 100).toFixed(1) : '0.0' }}%
                </div>
              </div>
            </div>
            <div class="kpi-card clickable kpi-success" @click="openDialog('RECTIFICATION_RATE')">
              <div class="kpi-header-row">
                <div class="kpi-label">整改完成率</div>
                <div class="kpi-tag">率</div>
              </div>
              <div class="kpi-value">{{ rectificationRate }}%</div>
              <div class="kpi-progress">
                <el-progress :percentage="Number(rectificationRate || 0)" :stroke-width="10" :show-text="false" />
              </div>
            </div>
            <div class="kpi-card clickable kpi-primary" @click="openDialog('CHECK_TIMES')">
              <div class="kpi-header-row">
                <div class="kpi-label">监督检查次数</div>
                <div class="kpi-tag">检</div>
              </div>
              <div class="kpi-value">{{ checkTimes }}</div>
              <div class="kpi-footer-row">
                <div class="kpi-foot-label">今日</div>
                <div class="kpi-foot-value">—</div>
              </div>
            </div>
          </div>
        </div>

        <div class="chart-panel span-4 panel-md">
          <div class="panel-header clickable" @click="openDialog('TYPE_ANALYSIS')">
            <el-icon><PieChart /></el-icon> 隐患类型构成 (饼图)
          </div>
          <div ref="typeChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-md">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><TrendCharts /></el-icon> 处置漏斗 (漏斗图)
          </div>
          <div ref="funnelChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-md">
          <div class="panel-header clickable" @click="openDialog('REGION_ANALYSIS')">
            <el-icon><Histogram /></el-icon> 区域隐患整改情况 (柱状图)
          </div>
          <div ref="rectificationChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-md">
          <div class="panel-header clickable" @click="openDialog('TOP_ENTERPRISES')">
            <el-icon><FirstAidKit /></el-icon> 重点监管企业 TOP (柱状图)
          </div>
          <div ref="topEntChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-md">
          <div class="panel-header clickable" @click="openDialog('REGION_ANALYSIS')">
            <el-icon><UserFilled /></el-icon> 区域风险画像 (雷达图)
          </div>
          <div ref="radarChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><List /></el-icon> 企业隐患矩形图 (矩形树图)
          </div>
          <div ref="treemapChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><View /></el-icon> 隐患结构 (旭日图)
          </div>
          <div ref="sunburstChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><Histogram /></el-icon> 类型→区域流向 (桑基图)
          </div>
          <div ref="sankeyChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><TrendCharts /></el-icon> 区域-企业关系 (关系图)
          </div>
          <div ref="graphChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><PieChart /></el-icon> 区域×类型热力 (热力图)
          </div>
          <div ref="heatmapChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><TrendCharts /></el-icon> 指标联动分析 (平行坐标图)
          </div>
          <div ref="parallelChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-md">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><Histogram /></el-icon> 整改率分布 (密度图)
          </div>
          <div ref="boxplotChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><TrendCharts /></el-icon> 类型随时间 (主题河流图)
          </div>
          <div ref="themeRiverChartRef" class="chart-container"></div>
        </div>

        <div class="chart-panel span-4 panel-lg">
          <div class="panel-header clickable" @click="openDialog('LEDGER')">
            <el-icon><Timer /></el-icon> 类型波动 (K线图)
          </div>
          <div ref="candlestickChartRef" class="chart-container calendar-container"></div>
        </div>
      </div>
    </div>

    <!-- 详情弹窗 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="70%"
      class="monitor-dialog"
    >
      <el-table v-if="dialogType === 'DANGER_TOTAL'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="hiddenDangerTotal" label="隐患总数" width="120" sortable />
        <el-table-column prop="regionName" label="所属区域" width="120" />
      </el-table>

      <el-table v-if="dialogType === 'MAJOR_DANGER'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="hiddenDangerLevel1" label="重大隐患数" width="120" sortable />
        <el-table-column prop="regionName" label="所属区域" width="120" />
      </el-table>

      <el-table v-if="dialogType === 'RECTIFICATION_RATE'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="hiddenDangerTotal" label="隐患总数" width="100" />
        <el-table-column prop="hiddenDangerRectified" label="已整改" width="100" />
        <el-table-column label="整改率" width="180" sortable :sort-method="(a, b) => Number(a.hiddenDangerRectificationRate) - Number(b.hiddenDangerRectificationRate)">
          <template #default="scope">
            <el-progress 
              :percentage="Number(scope.row.hiddenDangerRectificationRate || 0)" 
              :status="Number(scope.row.hiddenDangerRectificationRate) === 100 ? 'success' : 'exception'"
            />
          </template>
        </el-table-column>
      </el-table>

      <el-table v-if="dialogType === 'CHECK_TIMES'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="supervisionCheckTimes" label="监督检查次数" width="150" sortable />
        <el-table-column prop="regionName" label="所属区域" width="120" />
      </el-table>

      <el-table v-if="dialogType === 'TYPE_ANALYSIS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="hiddenDangerTypePipe" label="管网隐患" width="100" sortable />
        <el-table-column prop="hiddenDangerTypeStation" label="场站隐患" width="100" sortable />
        <el-table-column prop="hiddenDangerTypeUser" label="用户端隐患" width="120" sortable />
      </el-table>

      <el-table v-if="dialogType === 'REGION_ANALYSIS'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="regionName" label="区域名称" />
        <el-table-column prop="total" label="隐患总数" sortable />
        <el-table-column prop="rectified" label="已整改" sortable />
        <el-table-column label="整改率" sortable :sort-method="(a, b) => a.rate - b.rate">
          <template #default="scope">
            <el-progress 
              :percentage="scope.row.rate" 
              :status="scope.row.rate === 100 ? 'success' : 'exception'"
            />
          </template>
        </el-table-column>
      </el-table>

      <el-table v-if="dialogType === 'TOP_ENTERPRISES' || dialogType === 'LEDGER'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="regionName" label="所属区域" width="100" />
        <el-table-column prop="hiddenDangerTotal" label="隐患总数" width="100" sortable />
        <el-table-column prop="hiddenDangerRectified" label="已整改" width="100" />
        <el-table-column label="整改率" width="150" sortable :sort-method="(a, b) => Number(a.hiddenDangerRectificationRate) - Number(b.hiddenDangerRectificationRate)">
          <template #default="scope">
            <el-progress 
              :percentage="Number(scope.row.hiddenDangerRectificationRate || 0)" 
              :status="Number(scope.row.hiddenDangerRectificationRate) === 100 ? 'success' : 'exception'"
            />
          </template>
        </el-table-column>
      </el-table>
      
      <el-table v-if="dialogType === 'ALARM_LIST'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="regionName" label="区域名称" width="100" />
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="hiddenDangerTotal" label="隐患总数" width="100" sortable />
        <el-table-column prop="hiddenDangerLevel1" label="重大隐患" width="120" sortable />
      </el-table>
      
      <el-table v-if="dialogType === 'INSPECTION_INFO'" :data="dialogData" style="width: 100%" height="500" :stripe="false">
        <el-table-column prop="enterpriseName" label="企业名称" show-overflow-tooltip />
        <el-table-column prop="regionName" label="所属区域" width="120" />
        <el-table-column prop="personnelNames" label="安全/应急管理人员" show-overflow-tooltip />
        <el-table-column prop="supervisionCheckTimes" label="监督检查" width="100" sortable />
        <el-table-column label="巡检明细" align="center">
          <el-table-column prop="homeInspectionTimes" label="入户" width="80" sortable />
          <el-table-column prop="stationInspectionTimes" label="场站" width="80" sortable />
          <el-table-column prop="pipeInspectionTimes" label="管网" width="80" sortable />
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup>
// 导入Vue核心API
import { ref, onMounted, computed, nextTick, onUnmounted } from 'vue'
// 导入API接口
import { gasMapApi } from '@/api/mapApi'
// 导入Element Plus组件
import { ElMessage } from 'element-plus'
// 导入ECharts图表库
import * as echarts from 'echarts'
// 导入Element Plus图标
import { 
  WarningFilled, // 警告填充图标
  Timer, // 计时器图标
  WarnTriangleFilled, // 警告三角形填充图标
  CircleCloseFilled, // 圆形关闭填充图标
  CircleCheckFilled, // 圆形检查填充图标
  View, // 视图图标
  PieChart, // 饼图图标
  Histogram, // 直方图图标
  TrendCharts, // 趋势图表图标
  List, // 列表图标
  FirstAidKit, // 急救箱图标
  UserFilled // 用户填充图标
} from '@element-plus/icons-vue'

// 响应式数据定义
const statInfo = ref({}) // 统计信息数据
const safetyList = ref([]) // 安全隐患列表数据
const enterpriseList = ref([]) // 企业列表数据
const emergencyList = ref([]) // 应急资源列表数据
const currentTime = ref('') // 当前时间
let timer = null // 定时器引用

// 图表DOM引用
const typeChartRef = ref(null) // 类型图表DOM引用
const rectificationChartRef = ref(null) // 整改图表DOM引用
const topEntChartRef = ref(null) // 重点企业图表DOM引用
const funnelChartRef = ref(null) // 漏斗图表DOM引用
const radarChartRef = ref(null) // 雷达图表DOM引用
const treemapChartRef = ref(null) // 树图图表DOM引用
const sunburstChartRef = ref(null) // 旭日图图表DOM引用
const sankeyChartRef = ref(null) // 桑基图图表DOM引用
const graphChartRef = ref(null) // 关系图图表DOM引用
const heatmapChartRef = ref(null) // 热力图图表DOM引用
const parallelChartRef = ref(null) // 平行坐标图DOM引用
const boxplotChartRef = ref(null) // 盒须图图表DOM引用
const themeRiverChartRef = ref(null) // 主题河流图DOM引用
const candlestickChartRef = ref(null) // K线图DOM引用

// 图表实例
let typeChart = null // 类型图表实例
let rectificationChart = null // 整改图表实例
let topEntChart = null // 重点企业图表实例
let funnelChart = null // 漏斗图表实例
let radarChart = null // 雷达图表实例
let treemapChart = null // 树图图表实例
let sunburstChart = null // 旭日图图表实例
let sankeyChart = null // 桑基图图表实例
let graphChart = null // 关系图图表实例
let heatmapChart = null // 热力图图表实例
let parallelChart = null // 平行坐标图实例
let boxplotChart = null // 盒须图图表实例
let themeRiverChart = null // 主题河流图实例
let candlestickChart = null // K线图实例

// 对话框状态
const dialogVisible = ref(false) // 对话框显示状态
const dialogTitle = ref('') // 对话框标题
const dialogData = ref([]) // 对话框数据
const dialogType = ref('') // 对话框类型

// 计算属性：隐患告警列表
// 过滤出存在隐患或重大隐患的企业
const alarmList = computed(() => {
  return safetyList.value.filter(item => (item.hiddenDangerTotal || 0) > 0 || (item.hiddenDangerLevel1 || 0) > 0)
})

// 计算属性：巡检信息列表
// 过滤出有监督检查记录的企业，并添加安全/应急管理人员信息
const inspectionList = computed(() => {
  return safetyList.value
    .filter(item => (item.supervisionCheckTimes || 0) > 0)
    .map(item => {
      const ent = enterpriseList.value.find(e => e.enterpriseName === item.enterpriseName)
      
      const safetyNames = ent?.safetyManagerNames ? ent.safetyManagerNames.split(',').map(s => s.trim()).filter(Boolean) : []
      const emergencyNames = ent?.emergencyPersonnelNames ? ent.emergencyPersonnelNames.split(',').map(s => s.trim()).filter(Boolean) : []

      const allNames = [...safetyNames, ...emergencyNames]
      const displayNames = allNames.slice(0, 3).join(', ')
      
      return {
        ...item,
        personnelNames: displayNames
      }
    })
})

// 打开详情对话框函数
// 参数：type - 对话框类型，决定显示的内容和标题
const openDialog = (type) => {
  dialogType.value = type
  dialogVisible.value = true
  
  if (type === 'DANGER_TOTAL') {
    dialogTitle.value = '隐患总数详情'
    dialogData.value = safetyList.value
  } else if (type === 'MAJOR_DANGER') {
    dialogTitle.value = '重大隐患详情'
    dialogData.value = safetyList.value.filter(item => item.hiddenDangerLevel1 > 0)
  } else if (type === 'RECTIFICATION_RATE') {
    dialogTitle.value = '整改完成率详情'
    dialogData.value = safetyList.value
  } else if (type === 'CHECK_TIMES') {
    dialogTitle.value = '监督检查次数详情'
    dialogData.value = safetyList.value
  } else if (type === 'TYPE_ANALYSIS') {
    dialogTitle.value = '隐患类型构成详情'
    dialogData.value = safetyList.value
  } else if (type === 'REGION_ANALYSIS') {
    dialogTitle.value = '区域隐患整改详情'
    const regionMap = new Map()
    safetyList.value.forEach(item => {
      const region = item.regionName || '—'
      if (!regionMap.has(region)) regionMap.set(region, { total: 0, rectified: 0 })
      const entry = regionMap.get(region)
      entry.total += (item.hiddenDangerTotal || 0)
      entry.rectified += (item.hiddenDangerRectified || 0)
    })
    dialogData.value = Array.from(regionMap.entries()).map(([name, val]) => ({
      regionName: name,
      total: val.total,
      rectified: val.rectified,
      rate: val.total ? Number(((val.rectified / val.total) * 100).toFixed(1)) : 0
    }))
  } else if (type === 'TOP_ENTERPRISES') {
    dialogTitle.value = '重点监管企业详情'
    dialogData.value = [...safetyList.value].sort((a, b) => (b.hiddenDangerTotal || 0) - (a.hiddenDangerTotal || 0))
  } else if (type === 'LEDGER') {
    dialogTitle.value = '隐患排查治理台账'
    dialogData.value = safetyList.value
  } else if (type === 'ALARM_LIST') {
    dialogTitle.value = '隐患告警列表'
    dialogData.value = alarmList.value
  } else if (type === 'INSPECTION_INFO') {
    dialogTitle.value = '巡检信息'
    dialogData.value = inspectionList.value
  }
}

// 计算属性：隐患总数
// 汇总所有企业的隐患数量
const totalDangerCount = computed(() => {
  if (!safetyList.value.length) return 0
  return safetyList.value.reduce((sum, item) => sum + (item.hiddenDangerTotal || 0), 0)
})

// 计算属性：重大隐患数
// 汇总所有企业的重大隐患数量
const majorDangerCount = computed(() => {
  if (!safetyList.value.length) return 0
  return safetyList.value.reduce((sum, item) => sum + (item.hiddenDangerLevel1 || 0), 0)
})

// 计算属性：整改完成率
// 计算公式：(已整改隐患数 / 隐患总数) * 100%
const rectificationRate = computed(() => {
  const total = totalDangerCount.value
  if (!total) return 0
  const rectified = safetyList.value.reduce((sum, item) => sum + (item.hiddenDangerRectified || 0), 0)
  return ((rectified / total) * 100).toFixed(1)
})

// 计算属性：监督检查次数
// 汇总所有企业的监督检查次数
const checkTimes = computed(() => {
  if (!safetyList.value.length) return 0
  return safetyList.value.reduce((sum, item) => sum + (item.supervisionCheckTimes || 0), 0)
})

// 更新当前时间函数
// 将currentTime更新为当前系统时间的本地字符串表示
const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString()
}

// 初始化图表实例函数
// 为每个图表DOM引用创建对应的ECharts实例
const initCharts = () => {
  if (typeChartRef.value) typeChart = echarts.init(typeChartRef.value)
  if (rectificationChartRef.value) rectificationChart = echarts.init(rectificationChartRef.value)
  if (topEntChartRef.value) topEntChart = echarts.init(topEntChartRef.value)
  if (funnelChartRef.value) funnelChart = echarts.init(funnelChartRef.value)
  if (radarChartRef.value) radarChart = echarts.init(radarChartRef.value)
  if (treemapChartRef.value) treemapChart = echarts.init(treemapChartRef.value)
  if (sunburstChartRef.value) sunburstChart = echarts.init(sunburstChartRef.value)
  if (sankeyChartRef.value) sankeyChart = echarts.init(sankeyChartRef.value)
  if (graphChartRef.value) graphChart = echarts.init(graphChartRef.value)
  if (heatmapChartRef.value) heatmapChart = echarts.init(heatmapChartRef.value)
  if (parallelChartRef.value) parallelChart = echarts.init(parallelChartRef.value)
  if (boxplotChartRef.value) boxplotChart = echarts.init(boxplotChartRef.value)
  if (themeRiverChartRef.value) themeRiverChart = echarts.init(themeRiverChartRef.value)
  if (candlestickChartRef.value) candlestickChart = echarts.init(candlestickChartRef.value)
}

// 安全设置图表选项函数
// 参数：chart - 图表实例，option - 图表配置，emptyText - 无数据时显示的文本
// 功能：在try-catch块中设置图表选项，出错时显示无数据提示
const safeSetOption = (chart, option, emptyText = '暂无数据') => {
  if (!chart) return
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

// 日期格式化函数
// 参数：d - 日期对象或日期字符串
// 返回值：格式化后的日期字符串（YYYY-MM-DD），如果输入无效则返回null
const formatDate = (d) => {
  const date = new Date(d)
  if (!Number.isFinite(date.getTime())) return null
  const y = date.getFullYear()
  const m = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  return `${y}-${m}-${day}`
}

// 分位数计算函数
// 参数：sorted - 已排序的数组，p - 分位数（0-1之间）
// 返回值：计算得到的分位数值
const quantile = (sorted, p) => {
  if (!sorted.length) return 0
  const idx = (sorted.length - 1) * p
  const lo = Math.floor(idx)
  const hi = Math.ceil(idx)
  if (lo === hi) return sorted[lo]
  return sorted[lo] + (sorted[hi] - sorted[lo]) * (idx - lo)
}

// 核密度估计函数
// 参数：samples - 样本数据数组，xList - 计算点数组
// 返回值：包含[x, 密度值]对的数组
const kde = (samples, xList) => {
  const n = samples.length
  if (!n) return xList.map(x => [x, 0])
  const mean = samples.reduce((s, v) => s + v, 0) / n
  const variance = samples.reduce((s, v) => s + (v - mean) * (v - mean), 0) / Math.max(1, n - 1)
  const sd = Math.sqrt(Math.max(variance, 0))
  const h = Math.max(1, 1.06 * (sd || 1) * Math.pow(n, -0.2))
  const invSqrt2Pi = 1 / Math.sqrt(2 * Math.PI)
  const kernel = (u) => invSqrt2Pi * Math.exp(-0.5 * u * u)
  return xList.map(x => {
    let sum = 0
    for (let i = 0; i < n; i++) sum += kernel((x - samples[i]) / h)
    return [x, sum / (n * h)]
  })
}

// 更新所有图表函数
// 功能：根据safetyList数据更新所有ECharts图表的配置和数据
// 包含：隐患类型构成、区域整改情况、重点企业、处置漏斗、区域风险画像等多种图表
const updateCharts = () => {
  const source = safetyList.value || []
  if (!source.length) return

  
  let typePipe = 0, typeStation = 0, typeUser = 0
  source.forEach(item => {
    typePipe += (item.hiddenDangerTypePipe || 0)
    typeStation += (item.hiddenDangerTypeStation || 0)
    typeUser += (item.hiddenDangerTypeUser || 0)
  })

  const typeOption = {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'item' },
    legend: { bottom: 6, left: 'center', itemWidth: 10, itemHeight: 10, textStyle: { color: '#a0cfff', fontSize: 12 } },
    series: [
      {
        name: '隐患类型',
        type: 'pie',
        radius: ['45%', '70%'],
        center: ['50%', '44%'],
        itemStyle: {
          borderRadius: 5,
          borderColor: '#020f26',
          borderWidth: 2
        },
        label: { show: false },
        data: [
          { value: typePipe, name: '管网隐患', itemStyle: { color: '#409EFF' } },
          { value: typeStation, name: '场站隐患', itemStyle: { color: '#E6A23C' } },
          { value: typeUser, name: '用户端隐患', itemStyle: { color: '#F56C6C' } }
        ]
      }
    ]
  }
  typeChart?.setOption(typeOption)

  
  const regionMap = new Map()
  source.forEach(item => {
    const region = item.regionName || '—'
    if (!regionMap.has(region)) regionMap.set(region, { total: 0, rectified: 0 })
    const entry = regionMap.get(region)
    entry.total += (item.hiddenDangerTotal || 0)
    entry.rectified += (item.hiddenDangerRectified || 0)
  })

  const regions = Array.from(regionMap.keys())
  const totalValues = regions.map(r => regionMap.get(r).total)
  const rectifiedValues = regions.map(r => regionMap.get(r).rectified)

  const rectOption = {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    legend: { data: ['隐患总数', '已整改'], textStyle: { color: '#fff' }, top: 0 },
    grid: { left: '3%', right: '4%', bottom: '3%', top: '30px', containLabel: true },
    xAxis: { 
      type: 'category', 
      data: regions, 
      axisLabel: { color: '#a0cfff', rotate: 30 },
      axisLine: { lineStyle: { color: '#409EFF' } }
    },
    yAxis: { 
      type: 'value',
      axisLabel: { color: '#a0cfff' },
      splitLine: { lineStyle: { color: 'rgba(64,158,255,0.2)' } }
    },
    series: [
      {
        name: '隐患总数',
        type: 'bar',
        data: totalValues,
        itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'#F56C6C'},{offset:1,color:'rgba(245,108,108,0.3)'}]) },
        barWidth: 15
      },
      {
        name: '已整改',
        type: 'bar',
        data: rectifiedValues,
        itemStyle: { color: new echarts.graphic.LinearGradient(0,0,0,1,[{offset:0,color:'#67C23A'},{offset:1,color:'rgba(103,194,58,0.3)'}]) },
        barWidth: 15
      }
    ]
  }
  rectificationChart?.setOption(rectOption)

  
  const sortedEnts = [...source]
    .sort((a, b) => (b.hiddenDangerTotal || 0) - (a.hiddenDangerTotal || 0))
    .slice(0, 5)
  
  const entNames = sortedEnts.map(i => i.enterpriseName).reverse()
  const entVals = sortedEnts.map(i => i.hiddenDangerTotal).reverse()

  const topEntOption = {
    backgroundColor: 'transparent',
    tooltip: { trigger: 'axis', axisPointer: { type: 'shadow' } },
    grid: { left: '3%', right: '8%', bottom: '3%', top: '10px', containLabel: true },
    xAxis: { 
      type: 'value', 
      axisLabel: { color: '#a0cfff' },
      splitLine: { lineStyle: { color: 'rgba(64,158,255,0.2)' } }
    },
    yAxis: { 
      type: 'category', 
      data: entNames,
      axisLabel: { color: '#fff', width: 100, overflow: 'truncate' },
      axisLine: { lineStyle: { color: '#409EFF' } }
    },
    series: [
      {
        name: '隐患数',
        type: 'bar',
        data: entVals,
        itemStyle: { 
          color: new echarts.graphic.LinearGradient(1,0,0,0,[
            {offset:0,color:'#E6A23C'},
            {offset:1,color:'rgba(230,162,60,0.3)'}
          ]),
          borderRadius: [0, 10, 10, 0]
        },
        label: { show: true, position: 'right', color: '#fff' },
        barWidth: 15
      }
    ]
  }
  topEntChart?.setOption(topEntOption)

  const total = totalDangerCount.value
  const major = majorDangerCount.value
  const rectified = source.reduce((sum, item) => sum + (item.hiddenDangerRectified || 0), 0)
  const remaining = Math.max(0, total - rectified)

  funnelChart?.setOption({
    backgroundColor: 'transparent',
    tooltip: { trigger: 'item' },
    series: [{
      type: 'funnel',
      left: '8%',
      top: 30,
      bottom: 10,
      width: '84%',
      min: 0,
      max: Math.max(1, total),
      sort: 'descending',
      gap: 2,
      label: { color: '#fff', fontSize: 12, formatter: '{b}\n{c}' },
      itemStyle: { borderColor: '#020f26', borderWidth: 1 },
      data: [
        { name: '隐患总数', value: total },
        { name: '已整改', value: rectified },
        { name: '待整改', value: remaining },
        { name: '重大隐患', value: major }
      ]
    }]
  })

  const regionAgg = regions.map(r => {
    const v = regionMap.get(r)
    const totalVal = v?.total || 0
    const rectVal = v?.rectified || 0
    const rateVal = totalVal ? (rectVal / totalVal) * 100 : 0
    const majors = source
      .filter(i => (i.regionName || '—') === r)
      .reduce((s, i) => s + (i.hiddenDangerLevel1 || 0), 0)
    const checks = source
      .filter(i => (i.regionName || '—') === r)
      .reduce((s, i) => s + (i.supervisionCheckTimes || 0), 0)
    return { r, totalVal, rectVal, rateVal, majors, checks }
  })

  radarChart?.setOption({
    tooltip: { trigger: 'item' },
    radar: {
      indicator: [
        { name: '隐患总数', max: Math.max(1, Math.max(...regionAgg.map(i => i.totalVal), 10)) },
        { name: '重大隐患', max: Math.max(1, Math.max(...regionAgg.map(i => i.majors), 5)) },
        { name: '已整改', max: Math.max(1, Math.max(...regionAgg.map(i => i.rectVal), 10)) },
        { name: '整改率', max: 100 },
        { name: '检查次数', max: Math.max(1, Math.max(...regionAgg.map(i => i.checks), 10)) }
      ],
      center: ['50%', '55%'],
      radius: '70%',
      axisName: { color: '#a0cfff', fontSize: 10 },
      splitLine: { lineStyle: { color: 'rgba(64,158,255,0.2)' } },
      splitArea: { areaStyle: { color: ['rgba(64,158,255,0.03)', 'rgba(64,158,255,0.06)'] } }
    },
    series: [{
      type: 'radar',
      data: regionAgg.slice(0, 6).map(i => ({
        name: i.r,
        value: [i.totalVal, i.majors, i.rectVal, Number(i.rateVal.toFixed(1)), i.checks]
      }))
    }]
  })

  treemapChart?.setOption({
    tooltip: { formatter: '{b}<br/>隐患: {c}' },
    series: [{
      type: 'treemap',
      nodeClick: false,
      roam: false,
      label: { show: true, color: '#fff', formatter: '{b}\n{c}' },
      itemStyle: { borderColor: '#020f26', borderWidth: 1 },
      data: [...source]
        .sort((a, b) => (b.hiddenDangerTotal || 0) - (a.hiddenDangerTotal || 0))
        .slice(0, 24)
        .map(i => ({ name: i.enterpriseName || '—', value: i.hiddenDangerTotal || 0 }))
    }]
  })

  sunburstChart?.setOption({
    tooltip: { trigger: 'item' },
    series: [{
      type: 'sunburst',
      radius: [0, '92%'],
      label: { rotate: 'radial', color: '#fff', fontSize: 10 },
      itemStyle: { borderColor: '#020f26', borderWidth: 1 },
      data: regions.slice(0, 10).map(r => {
        const ents = source.filter(i => (i.regionName || '—') === r).slice(0, 10)
        return {
          name: r,
          children: ents.map(e => ({
            name: e.enterpriseName || '—',
            value: e.hiddenDangerTotal || 0,
            children: [
              { name: '管网', value: e.hiddenDangerTypePipe || 0 },
              { name: '场站', value: e.hiddenDangerTypeStation || 0 },
              { name: '用户端', value: e.hiddenDangerTypeUser || 0 }
            ]
          }))
        }
      })
    }]
  })

  const sankeyNodes = new Map()
  const addNode = (name) => {
    if (!sankeyNodes.has(name)) sankeyNodes.set(name, { name })
  }
  ;['管网隐患', '场站隐患', '用户端隐患'].forEach(addNode)
  regions.forEach(addNode)
  const links = []
  source.forEach(i => {
    const r = i.regionName || '—'
    addNode(r)
    const p = i.hiddenDangerTypePipe || 0
    const s = i.hiddenDangerTypeStation || 0
    const u = i.hiddenDangerTypeUser || 0
    if (p) links.push({ source: '管网隐患', target: r, value: p })
    if (s) links.push({ source: '场站隐患', target: r, value: s })
    if (u) links.push({ source: '用户端隐患', target: r, value: u })
  })
  sankeyChart?.setOption({
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
  })

  const regionNodeId = (r) => `region:${r}`
  const entNodeId = (e) => `ent:${e}`
  const regionRanked = regions
    .map(r => ({ r, total: regionMap.get(r)?.total || 0 }))
    .sort((a, b) => b.total - a.total)
    .slice(0, 10)
    .map(i => i.r)

  const graphNodeMap = new Map()
  const graphLinkKey = new Set()
  const graphLinks = []
  const putNode = (node) => {
    if (!graphNodeMap.has(node.id)) graphNodeMap.set(node.id, node)
  }
  regionRanked.forEach(r => {
    putNode({ id: regionNodeId(r), name: r, category: 0, symbolSize: 34 })
  })
  source
    .filter(i => regionRanked.includes(i.regionName || '—'))
    .sort((a, b) => (b.hiddenDangerTotal || 0) - (a.hiddenDangerTotal || 0))
    .slice(0, 50)
    .forEach(i => {
      const r = i.regionName || '—'
      const e = i.enterpriseName || '—'
      putNode({
        id: entNodeId(e),
        name: e,
        category: 1,
        symbolSize: Math.max(12, Math.min(42, (i.hiddenDangerTotal || 0) * 2 + 12))
      })
      const key = `${regionNodeId(r)}->${entNodeId(e)}`
      if (!graphLinkKey.has(key)) {
        graphLinkKey.add(key)
        graphLinks.push({ source: regionNodeId(r), target: entNodeId(e), value: i.hiddenDangerTotal || 0 })
      }
    })
  safeSetOption(graphChart, {
    tooltip: {
      formatter: (p) => {
        if (p.dataType === 'edge') return `隐患: ${p.data.value}`
        return p.data.name
      }
    },
    series: [{
      type: 'graph',
      layout: 'force',
      roam: true,
      force: { repulsion: 220, edgeLength: 70 },
      label: { show: false },
      categories: [{ name: '区域' }, { name: '企业' }],
      data: Array.from(graphNodeMap.values()),
      links: graphLinks,
      lineStyle: { color: 'rgba(64,158,255,0.35)' }
    }]
  })

  const heatRegions = regionRanked.length ? regionRanked : regions.slice(0, 12)
  const heatTypes = ['管网', '场站', '用户端']
  const heatData = []
  heatRegions.forEach((r, xi) => {
    const items = source.filter(i => (i.regionName || '—') === r)
    const sums = {
      pipe: items.reduce((s, i) => s + (i.hiddenDangerTypePipe || 0), 0),
      station: items.reduce((s, i) => s + (i.hiddenDangerTypeStation || 0), 0),
      user: items.reduce((s, i) => s + (i.hiddenDangerTypeUser || 0), 0)
    }
    ;[sums.pipe, sums.station, sums.user].forEach((v, yi) => heatData.push([xi, yi, v]))
  })
  const heatMax = Math.max(1, ...heatData.map(d => d[2]))
  safeSetOption(heatmapChart, {
    tooltip: { formatter: (p) => `${heatRegions[p.value[0]]}<br/>${heatTypes[p.value[1]]}: ${p.value[2]}` },
    grid: { left: 80, right: 20, top: 30, bottom: 30 },
    xAxis: { type: 'category', data: heatRegions, axisLabel: { color: '#a0cfff', rotate: 30 } },
    yAxis: { type: 'category', data: heatTypes, axisLabel: { color: '#a0cfff' } },
    visualMap: {
      min: 0,
      max: heatMax,
      calculable: true,
      orient: 'horizontal',
      left: 'center',
      bottom: 0,
      textStyle: { color: '#a0cfff', fontSize: 10 },
      inRange: { color: ['#1f3a5e', '#409EFF', '#67C23A', '#E6A23C', '#F56C6C'] }
    },
    series: [{ type: 'heatmap', data: heatData, label: { show: false } }]
  })

  const parallelData = [...source]
    .sort((a, b) => (b.hiddenDangerTotal || 0) - (a.hiddenDangerTotal || 0))
    .slice(0, 40)
    .map(i => [
      Number(i.hiddenDangerTotal || 0),
      Number(i.hiddenDangerLevel1 || 0),
      Number(i.hiddenDangerRectified || 0),
      Number(i.hiddenDangerRectificationRate || 0),
      Number(i.supervisionCheckTimes || 0),
      Number(i.hiddenDangerTypePipe || 0) + Number(i.hiddenDangerTypeStation || 0) + Number(i.hiddenDangerTypeUser || 0)
    ])
  const pMax = {
    total: Math.max(1, ...parallelData.map(d => d[0])),
    major: Math.max(1, ...parallelData.map(d => d[1])),
    rect: Math.max(1, ...parallelData.map(d => d[2])),
    rate: 100,
    checks: Math.max(1, ...parallelData.map(d => d[4])),
    types: Math.max(1, ...parallelData.map(d => d[5]))
  }
  safeSetOption(parallelChart, {
    parallelAxis: [
      { dim: 0, name: '隐患', min: 0, max: pMax.total },
      { dim: 1, name: '重大', min: 0, max: pMax.major },
      { dim: 2, name: '整改', min: 0, max: pMax.rect },
      { dim: 3, name: '整改率', min: 0, max: pMax.rate },
      { dim: 4, name: '检查', min: 0, max: pMax.checks },
      { dim: 5, name: '类型合计', min: 0, max: pMax.types }
    ],
    parallel: { left: 30, right: 30, top: 30, bottom: 30 },
    series: [{
      type: 'parallel',
      lineStyle: { width: 1.5, opacity: 0.5, color: '#409EFF' },
      data: parallelData.length ? parallelData : [[0, 0, 0, 0, 0, 0]]
    }]
  })

  const rates = source
    .map(i => Number(i.hiddenDangerRectificationRate))
    .filter(v => Number.isFinite(v))
    .map(v => Math.max(0, Math.min(100, v)))
  if (!rates.length) {
    safeSetOption(boxplotChart, {
      graphic: {
        type: 'text',
        left: 'center',
        top: 'middle',
        style: { text: '暂无数据', fill: '#a0cfff', fontSize: 14 }
      }
    })
  } else {
    const xList = Array.from({ length: 101 }, (_, i) => i)
    const kd = kde(rates, xList)
    const yMax = Math.max(1e-6, ...kd.map(i => i[1]))
    safeSetOption(boxplotChart, {
      tooltip: {
        trigger: 'axis',
        axisPointer: { type: 'line' },
        formatter: (params) => {
          const p = params?.[0]
          if (!p) return ''
          return `整改率: ${Number(p.value[0]).toFixed(0)}%<br/>密度: ${Number(p.value[1]).toFixed(4)}`
        }
      },
      grid: { left: 50, right: 20, top: 25, bottom: 35, containLabel: true },
      xAxis: {
        type: 'value',
        min: 0,
        max: 100,
        axisLabel: { color: '#a0cfff', formatter: '{value}%' },
        axisLine: { lineStyle: { color: '#1f3a5e' } },
        splitLine: { lineStyle: { color: 'rgba(64,158,255,0.10)' } }
      },
      yAxis: {
        type: 'value',
        min: 0,
        max: yMax * 1.15,
        axisLabel: { color: '#a0cfff' },
        axisLine: { lineStyle: { color: '#1f3a5e' } },
        splitLine: { lineStyle: { color: 'rgba(64,158,255,0.15)' } }
      },
      series: [{
        type: 'line',
        smooth: true,
        showSymbol: false,
        data: kd,
        lineStyle: { width: 2, color: '#67C23A' },
        areaStyle: { opacity: 0.35, color: 'rgba(103,194,58,0.35)' }
      }]
    })
  }

  const dateMap = new Map()
  source.forEach(i => {
    const d = formatDate(i.statisticsDate)
    if (!d) return
    if (!dateMap.has(d)) dateMap.set(d, { pipe: 0, station: 0, user: 0, total: 0 })
    const v = dateMap.get(d)
    v.pipe += (i.hiddenDangerTypePipe || 0)
    v.station += (i.hiddenDangerTypeStation || 0)
    v.user += (i.hiddenDangerTypeUser || 0)
    v.total += (i.hiddenDangerTotal || 0)
  })
  const dates = Array.from(dateMap.keys()).sort()
  const xDates = dates.length ? dates : [formatDate(new Date()) || '2026-01-01']
  const pipeSeries = xDates.map(d => dateMap.get(d)?.pipe || 0)
  const stationSeries = xDates.map(d => dateMap.get(d)?.station || 0)
  const userSeries = xDates.map(d => dateMap.get(d)?.user || 0)
  const totalSeries = xDates.map(d => dateMap.get(d)?.total || 0)

  safeSetOption(themeRiverChart, {
    tooltip: { trigger: 'axis' },
    legend: { top: 0, textStyle: { color: '#a0cfff' } },
    grid: { left: 40, right: 20, top: 35, bottom: 25, containLabel: true },
    xAxis: { type: 'category', data: xDates, axisLabel: { color: '#a0cfff', rotate: 30 } },
    yAxis: { type: 'value', axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.15)' } } },
    series: [
      { name: '管网', type: 'line', stack: 'types', smooth: true, showSymbol: false, areaStyle: { opacity: 0.25 }, data: pipeSeries },
      { name: '场站', type: 'line', stack: 'types', smooth: true, showSymbol: false, areaStyle: { opacity: 0.25 }, data: stationSeries },
      { name: '用户端', type: 'line', stack: 'types', smooth: true, showSymbol: false, areaStyle: { opacity: 0.25 }, data: userSeries }
    ]
  })

  const candle = xDates.map((d, idx) => {
    const p = pipeSeries[idx]
    const s = stationSeries[idx]
    const u = userSeries[idx]
    const low = Math.min(p, s, u)
    const high = Math.max(p, s, u)
    return [p, u, low, high]
  })
  const yMax = Math.max(1, ...candle.map(v => v[3]), ...totalSeries)
  safeSetOption(candlestickChart, {
    tooltip: { trigger: 'axis' },
    grid: { left: 40, right: 20, top: 20, bottom: 30, containLabel: true },
    xAxis: { type: 'category', data: xDates, axisLabel: { color: '#a0cfff', rotate: 30 } },
    yAxis: { type: 'value', min: 0, max: yMax, axisLabel: { color: '#a0cfff' }, splitLine: { lineStyle: { color: 'rgba(64,158,255,0.15)' } } },
    series: [
      {
        name: 'K线(管网→用户端)',
        type: 'candlestick',
        data: candle,
        itemStyle: {
          color: '#F56C6C',
          color0: '#67C23A',
          borderColor: '#F56C6C',
          borderColor0: '#67C23A'
        }
      },
      {
        name: '总隐患',
        type: 'line',
        smooth: true,
        showSymbol: false,
        lineStyle: { color: '#409EFF', width: 2 },
        data: totalSeries
      }
    ]
  })
}

// 调整图表大小函数
// 当窗口大小改变时，调用所有图表实例的resize方法以适应新的容器大小
const resizeCharts = () => {
  typeChart?.resize()
  rectificationChart?.resize()
  topEntChart?.resize()
  funnelChart?.resize()
  radarChart?.resize()
  treemapChart?.resize()
  sunburstChart?.resize()
  sankeyChart?.resize()
  graphChart?.resize()
  heatmapChart?.resize()
  parallelChart?.resize()
  boxplotChart?.resize()
  themeRiverChart?.resize()
  candlestickChart?.resize()
}

// 表格行样式函数
// 参数：row - 表格行数据
// 返回值：如果行数据中存在重大隐患，则返回'warning-row'类名，否则返回空字符串
const tableRowClassName = ({ row }) => {
  if (row.hiddenDangerLevel1 > 0) {
    return 'warning-row'
  }
  return ''
}

// 组件挂载生命周期钩子
// 功能：初始化时间更新、图表实例、窗口大小监听，并获取风险隐患数据
onMounted(async () => {
  updateTime()
  timer = setInterval(updateTime, 1000)
  initCharts()
  window.addEventListener('resize', resizeCharts)
  try {
    const res = await gasMapApi.getTotalData()
    statInfo.value = res.statInfo || {}
    safetyList.value = res.safetyList || []
    enterpriseList.value = res.enterpriseList || []
    emergencyList.value = res.emergencyList || []
    nextTick(() => {
      updateCharts()
      setTimeout(() => resizeCharts(), 0)
    })
  } catch (e) {
    console.error(e)
    ElMessage.error('获取风险隐患数据失败')
  }
})

// 组件卸载生命周期钩子
// 功能：清理定时器、移除窗口大小监听、销毁所有图表实例
onUnmounted(() => {
  if (timer) clearInterval(timer)
  window.removeEventListener('resize', resizeCharts)
  typeChart?.dispose()
  rectificationChart?.dispose()
  topEntChart?.dispose()
  funnelChart?.dispose()
  radarChart?.dispose()
  treemapChart?.dispose()
  sunburstChart?.dispose()
  sankeyChart?.dispose()
  graphChart?.dispose()
  heatmapChart?.dispose()
  parallelChart?.dispose()
  boxplotChart?.dispose()
  themeRiverChart?.dispose()
  candlestickChart?.dispose()
})
</script>

<style scoped>
.risk-container {
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
  color: #F56C6C;
  display: flex;
  align-items: center;
  gap: 10px;
  text-shadow: 0 0 10px rgba(245, 108, 108, 0.5);
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
  padding: 20px;
  overflow-y: auto;
}

.charts-grid {
  display: grid;
  grid-template-columns: repeat(12, 1fr);
  gap: 20px;
}

.span-12 { grid-column: span 12; }
.span-3 { grid-column: span 3; }
.span-8 { grid-column: span 8; }
.span-6 { grid-column: span 6; }
.span-4 { grid-column: span 4; }

.kpi-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
  flex: 1;
  min-height: 0;
}

.kpi-card {
  background: linear-gradient(180deg, rgba(13, 37, 70, 0.55) 0%, rgba(6, 22, 48, 0.45) 100%);
  border: 1px solid rgba(64, 158, 255, 0.14);
  border-radius: 12px;
  padding: 14px 14px 12px;
  overflow: hidden;
  display: flex;
  flex-direction: column;
  min-height: 132px;
}

.kpi-header-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 10px;
  min-height: 20px;
}

.kpi-label {
  color: #a0cfff;
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
  background: rgba(64, 158, 255, 0.2);
  border: 1px solid rgba(64, 158, 255, 0.25);
}

.kpi-value {
  margin-top: 10px;
  font-size: 30px;
  line-height: 1.05;
  font-weight: 800;
  color: #fff;
  flex: 1;
  display: flex;
  align-items: flex-end;
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

.kpi-foot-label {
  color: rgba(160, 207, 255, 0.85);
}

.kpi-foot-value {
  color: #d7ebff;
  font-weight: 700;
}

.kpi-progress {
  margin-top: 10px;
}

:deep(.kpi-progress .el-progress-bar__outer) {
  background: rgba(64, 158, 255, 0.12);
}

:deep(.kpi-progress .el-progress-bar__inner) {
  background: linear-gradient(90deg, rgba(103, 194, 58, 0.95) 0%, rgba(103, 194, 58, 0.55) 100%);
}

.kpi-danger {
  border-color: rgba(245, 108, 108, 0.22);
}
.kpi-danger .kpi-tag {
  background: rgba(245, 108, 108, 0.22);
  border-color: rgba(245, 108, 108, 0.26);
}

.kpi-warn {
  border-color: rgba(230, 162, 60, 0.22);
}
.kpi-warn .kpi-tag {
  background: rgba(230, 162, 60, 0.22);
  border-color: rgba(230, 162, 60, 0.26);
}

.kpi-success {
  border-color: rgba(103, 194, 58, 0.22);
}
.kpi-success .kpi-tag {
  background: rgba(103, 194, 58, 0.22);
  border-color: rgba(103, 194, 58, 0.26);
}

.kpi-primary {
  border-color: rgba(64, 158, 255, 0.22);
}
.kpi-primary .kpi-tag {
  background: rgba(64, 158, 255, 0.22);
  border-color: rgba(64, 158, 255, 0.26);
}

.chart-panel {
  background: linear-gradient(180deg, rgba(13, 37, 70, 0.62) 0%, rgba(5, 18, 40, 0.52) 100%);
  border: 1px solid rgba(64, 158, 255, 0.14);
  border-radius: 12px;
  padding: 14px;
  display: flex;
  flex-direction: column;
  backdrop-filter: blur(8px);
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.35);
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
}

.chart-panel:hover {
  transform: translateY(-2px);
  border-color: rgba(64, 158, 255, 0.28);
  box-shadow: 0 14px 30px rgba(0, 0, 0, 0.45);
}

.panel-md { height: 320px; }
.panel-lg { height: 420px; }
.panel-xl { height: 520px; }
.panel-kpi { height: 340px; }

.panel-header {
  font-size: 16px;
  font-weight: bold;
  color: #d7ebff;
  margin-bottom: 10px;
  display: flex;
  align-items: center;
  gap: 8px;
  border-left: 4px solid #F56C6C;
  padding-left: 10px;
}

.chart-container {
  flex: 1;
  min-height: 0;
  width: 100%;
  height: 100%;
}

.calendar-container {
  height: 320px;
}

@media (max-width: 1200px) {
  .span-8, .span-6, .span-4, .span-3 { grid-column: span 12; }
  .kpi-grid { grid-template-columns: repeat(2, 1fr); }
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
</style>
