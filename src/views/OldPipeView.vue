<template>
  <div class="old-pipe-view dark-theme">
    <div class="header">
      <div class="header-title">
        <div class="icon-box">
          <el-icon><Timer /></el-icon>
        </div>
        <h2>老旧管网一张图</h2>
      </div>
      <div class="header-right">
        <div class="time-display">{{ currentTime }}</div>
        <el-button circle type="primary" class="refresh-btn" @click="handleRefresh">
          <el-icon><Refresh /></el-icon>
        </el-button>
      </div>
    </div>

    <div class="content-body">
      
      <div class="stats-row">
        <div class="stat-item">
          <div class="stat-icon-wrapper danger-bg">
            <el-icon><Warning /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value warning-text">{{ totalOldLength }}<span class="unit">km</span></div>
            <div class="stat-label">老旧管网总长</div>
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-icon-wrapper success-bg">
            <el-icon><CircleCheck /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value success-text">{{ renovatedLength }}<span class="unit">km</span></div>
            <div class="stat-label">已改造长度</div>
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-icon-wrapper primary-bg">
            <el-icon><TrendCharts /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value primary-text">{{ renovationRate }}<span class="unit">%</span></div>
            <div class="stat-label">改造完成率</div>
          </div>
        </div>
        <div class="stat-item">
          <div class="stat-icon-wrapper info-bg">
            <el-icon><Clock /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value info-text">{{ earliestYear || '-' }}</div>
            <div class="stat-label">最早敷设年份</div>
          </div>
        </div>
      </div>

      
      <div class="charts-row">
        <div class="panel chart-panel">
          <div class="panel-header">
            <span class="panel-title">管网材质分布 (饼图)</span>
          </div>
          <div ref="materialChartRef" class="chart-container"></div>
        </div>
        <div class="panel chart-panel">
          <div class="panel-header">
            <span class="panel-title">改造进展统计 (饼图)</span>
          </div>
          <div ref="statusChartRef" class="chart-container"></div>
        </div>
      </div>

      
      <div class="table-row">
        <div class="panel table-panel">
          <div class="panel-header">
            <span class="panel-title">区域分布与改造详情</span>
          </div>
          <div class="table-container">
            <el-table 
              :data="regionPipeData" 
              style="width: 100%; height: 100%" 
              :header-cell-style="headerCellStyle"
              height="100%"
            >
              <el-table-column type="index" label="序号" width="60" align="center" />
              <el-table-column prop="regionName" label="区域名称" min-width="120" />
              <el-table-column prop="oldLength" label="老旧管网(km)" min-width="140" align="right">
                <template #default="scope">
                  <span class="num-font">{{ Number(scope.row.oldLength || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="segmentCount" label="管段数量" min-width="100" align="center" />
              <el-table-column prop="rectifiedLength" label="已改造(km)" min-width="140" align="right">
                <template #default="scope">
                  <span class="num-font success-text">{{ Number(scope.row.rectifiedLength || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="rectifyingLength" label="改造中(km)" min-width="140" align="right">
                <template #default="scope">
                  <span class="num-font warning-text">{{ Number(scope.row.rectifyingLength || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column prop="unRectifiedLength" label="未改造(km)" min-width="140" align="right">
                <template #default="scope">
                  <span class="num-font danger-text">{{ Number(scope.row.unRectifiedLength || 0).toFixed(2) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="改造进度" min-width="200">
                <template #default="scope">
                  <el-progress
                    :percentage="Number(scope.row.rectificationRate || 0)"
                    :status="Number(scope.row.rectificationRate) === 100 ? 'success' : (Number(scope.row.rectificationRate) > 50 ? 'warning' : 'exception')"
                    :stroke-width="15"
                    striped
                    striped-flow
                  />
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, nextTick, onUnmounted } from 'vue'
import { gasMapApi } from '@/api/mapApi'
import { ElMessage } from 'element-plus'
import * as echarts from 'echarts'
import { Refresh, Warning, CircleCheck, TrendCharts, Clock, Timer } from '@element-plus/icons-vue'

const pipeList = ref([])
const currentTime = ref('')
let timer = null

const updateTime = () => {
  const now = new Date()
  currentTime.value = now.toLocaleString('zh-CN', {
    hour12: false,
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const handleRefresh = async () => {
  try {
    await gasMapApi.refreshCache()
    const res = await gasMapApi.getTotalData()
    pipeList.value = res.pipeList || []
    nextTick(() => {
      updateCharts()
    })
    ElMessage.success('数据刷新成功')
  } catch (e) {
    console.error(e)
    ElMessage.error('数据刷新失败')
  }
}

const materialChartRef = ref(null)
const statusChartRef = ref(null)
let materialChart = null
let statusChart = null

const getYearFromDate = value => {
  if (!value) return null
  const d = new Date(value)
  if (!isNaN(d.getTime())) return d.getFullYear()
  const str = String(value)
  const match = str.match(/^(\d{4})/)
  if (match) return parseInt(match[1], 10)
  return null
}

const OLD_PIPE_YEARS = 20

const oldPipeList = computed(() => {
  if (!pipeList.value.length) return []
  const nowYear = new Date().getFullYear()
  return pipeList.value.filter(item => {
    const year = getYearFromDate(item.layingDate)
    if (!year) return false
    return nowYear - year >= OLD_PIPE_YEARS
  })
})

const totalOldLength = computed(() => {
  if (!oldPipeList.value.length) return 0
  const sum = oldPipeList.value.reduce((acc, item) => {
    const len = Number(item.pipeLength || 0)
    if (isNaN(len)) return acc
    return acc + len
  }, 0)
  return Number(sum.toFixed(2))
})

const getPipeStatus = item => {
  const total = item.hiddenDangerCount || 0
  const rectified = item.rectifiedDangerCount || 0
  if (total > 0 && rectified >= total) return '已改造'
  if (rectified > 0 && rectified < total) return '改造中'
  return '未改造'
}

const renovatedLength = computed(() => {
  if (!oldPipeList.value.length) return 0
  const sum = oldPipeList.value.reduce((acc, item) => {
    const status = getPipeStatus(item)
    if (status !== '已改造') return acc
    const len = Number(item.pipeLength || 0)
    if (isNaN(len)) return acc
    return acc + len
  }, 0)
  return Number(sum.toFixed(2))
})

const renovationRate = computed(() => {
  if (!totalOldLength.value) return 0
  const rate = (renovatedLength.value / totalOldLength.value) * 100
  return rate.toFixed(1)
})

const earliestYear = computed(() => {
  if (!oldPipeList.value.length) return null
  let minYear = null
  oldPipeList.value.forEach(item => {
    const year = getYearFromDate(item.layingDate)
    if (!isNaN(year)) {
      if (minYear === null || year < minYear) {
        minYear = year
      }
    }
  })
  return minYear
})

const regionPipeData = computed(() => {
  if (!oldPipeList.value.length) return []
  const map = new Map()
  oldPipeList.value.forEach(item => {
    const regionName = item.regionName || '-'
    const status = getPipeStatus(item)
    const len = Number(item.pipeLength || 0)
    const length = isNaN(len) ? 0 : len
    if (!map.has(regionName)) {
      map.set(regionName, {
        regionName,
        oldLength: 0,
        segmentCount: 0,
        rectifiedLength: 0,
        rectifyingLength: 0,
        unRectifiedLength: 0
      })
    }
    const row = map.get(regionName)
    row.oldLength += length
    row.segmentCount += 1
    if (status === '已改造') {
      row.rectifiedLength += length
    } else if (status === '改造中') {
      row.rectifyingLength += length
    } else {
      row.unRectifiedLength += length
    }
  })

  return Array.from(map.values()).map(row => {
    row.rectificationRate = row.oldLength > 0 
      ? ((row.rectifiedLength / row.oldLength) * 100).toFixed(1)
      : 0
    return row
  }).sort((a, b) => b.oldLength - a.oldLength)
})

const updateCharts = () => {
  if (materialChart) materialChart.dispose()
  if (statusChart) statusChart.dispose()

  materialChart = echarts.init(materialChartRef.value)
  statusChart = echarts.init(statusChartRef.value)

  const materialMap = new Map()
  oldPipeList.value.forEach(item => {
    const m = item.pipeMaterial || '未知'
    const len = Number(item.pipeLength || 0)
    materialMap.set(m, (materialMap.get(m) || 0) + len)
  })
  const materialData = Array.from(materialMap.entries())
    .map(([name, value]) => ({ name, value: Number(value.toFixed(2)) }))
    .sort((a, b) => b.value - a.value)

  const materialOption = {
    backgroundColor: 'transparent',
    tooltip: { 
      trigger: 'item',
      formatter: '{b}: {c} km ({d}%)'
    },
    legend: { 
      top: '5%', 
      left: 'center',
      textStyle: { color: '#fff' }
    },
    series: [
      {
        name: '材质',
        type: 'pie',
        radius: ['40%', '65%'],
        center: ['50%', '55%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 5,
          borderColor: '#020f26',
          borderWidth: 2
        },
        label: { show: false },
        labelLine: { show: false },
        data: materialData
      }
    ]
  }

  const statusMap = { '已改造': 0, '改造中': 0, '未改造': 0 }
  oldPipeList.value.forEach(item => {
    const status = getPipeStatus(item)
    const len = Number(item.pipeLength || 0)
    statusMap[status] = (statusMap[status] || 0) + len
  })
  const statusData = [
    { value: Number(statusMap['已改造'].toFixed(2)), name: '已改造', itemStyle: { color: '#67C23A' } },
    { value: Number(statusMap['改造中'].toFixed(2)), name: '改造中', itemStyle: { color: '#E6A23C' } },
    { value: Number(statusMap['未改造'].toFixed(2)), name: '未改造', itemStyle: { color: '#F56C6C' } }
  ]

  const statusOption = {
    backgroundColor: 'transparent',
    tooltip: { 
      trigger: 'item',
      formatter: '{b}: {c} km ({d}%)'
    },
    legend: {
      top: '5%',
      left: 'center',
      textStyle: { color: '#fff' }
    },
    series: [
      {
        name: '改造状态',
        type: 'pie',
        radius: '65%',
        center: ['50%', '55%'],
        data: statusData,
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        },
        label: {
          color: '#fff',
          formatter: '{b}\n{d}%'
        }
      }
    ]
  }

  materialChart.setOption(materialOption)
  statusChart.setOption(statusOption)
}

const headerCellStyle = {
  background: 'rgba(255, 255, 255, 0.05)',
  color: '#00dcff',
  borderBottom: '1px solid rgba(0, 168, 255, 0.2)'
}

onMounted(async () => {
  updateTime()
  timer = setInterval(updateTime, 1000)
  
  const res = await gasMapApi.getTotalData()
  pipeList.value = res.pipeList || []
  nextTick(() => {
    updateCharts()
  })
  window.addEventListener('resize', () => {
    materialChart && materialChart.resize()
    statusChart && statusChart.resize()
  })
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
  window.removeEventListener('resize', () => {
    materialChart && materialChart.resize()
    statusChart && statusChart.resize()
  })
  if (materialChart) materialChart.dispose()
  if (statusChart) statusChart.dispose()
})
</script>

<style scoped>
.old-pipe-view {
  width: 100%;
  height: 100vh;
  background-color: #020f26;
  color: #fff;
  display: flex;
  flex-direction: column;
  overflow: hidden;
}

.header {
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  background: linear-gradient(to right, rgba(0, 81, 255, 0.1), rgba(0, 0, 0, 0));
  border-bottom: 1px solid rgba(0, 168, 255, 0.2);
}

.header-title {
  display: flex;
  align-items: center;
  gap: 10px;
}

.icon-box {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: rgba(0, 220, 255, 0.2);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #00dcff;
}

.header h2 {
  font-size: 24px;
  margin: 0;
  color: #00dcff;
  text-shadow: 0 0 10px rgba(0, 220, 255, 0.5);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 20px;
}

.time-display {
  font-family: 'Segoe UI', monospace;
  font-size: 18px;
  color: #00dcff;
}

.content-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 15px;
  gap: 15px;
  overflow: hidden;
}


.stats-row {
  flex: 0 0 80px;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 15px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 15px;
  padding: 0 20px;
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(0, 168, 255, 0.1);
  border-radius: 8px;
  transition: all 0.3s;
}

.stat-item:hover {
  background: rgba(255, 255, 255, 0.06);
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
}

.stat-icon-wrapper {
  width: 48px;
  height: 48px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
}

.danger-bg { background: rgba(245, 108, 108, 0.2); color: #f56c6c; }
.success-bg { background: rgba(103, 194, 58, 0.2); color: #67c23a; }
.primary-bg { background: rgba(64, 158, 255, 0.2); color: #409eff; }
.info-bg { background: rgba(144, 147, 153, 0.2); color: #909399; }

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  font-family: 'Segoe UI', sans-serif;
  line-height: 1.2;
}

.stat-label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.6);
  margin-top: 4px;
}

.unit {
  font-size: 14px;
  margin-left: 4px;
  font-weight: normal;
}

.warning-text { color: #e6a23c; }
.danger-text { color: #f56c6c; }
.success-text { color: #67c23a; }
.primary-text { color: #409eff; }
.info-text { color: #909399; }


.charts-row {
  flex: 0 0 35%; 
  display: flex;
  gap: 15px;
}

.chart-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
}


.table-row {
  flex: 1; 
  display: flex;
  min-height: 0;
}

.table-panel {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.panel {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(0, 168, 255, 0.1);
  border-radius: 8px;
  overflow: hidden;
}

.panel-header {
  height: 40px;
  display: flex;
  align-items: center;
  padding: 0 15px;
  background: rgba(0, 168, 255, 0.05);
  border-bottom: 1px solid rgba(0, 168, 255, 0.1);
}

.panel-title {
  font-size: 16px;
  font-weight: bold;
  color: #fff;
  border-left: 3px solid #00dcff;
  padding-left: 10px;
}

.chart-container {
  flex: 1;
  width: 100%;
  min-height: 0;
}

.table-container {
  flex: 1;
  padding: 10px 15px;
  overflow: hidden;
}


:deep(.el-table) {
  background-color: transparent !important;
  color: #fff;
  --el-table-border-color: rgba(0, 168, 255, 0.1);
  --el-table-header-bg-color: rgba(255, 255, 255, 0.05);
  --el-table-row-hover-bg-color: rgba(0, 168, 255, 0.1) !important;
  --el-table-tr-bg-color: transparent;
}

:deep(.el-table th), :deep(.el-table tr), :deep(.el-table td) {
  background-color: transparent !important;
  border-bottom: 1px solid rgba(0, 168, 255, 0.1);
}

:deep(.el-table__inner-wrapper::before) {
  display: none;
}

.num-font {
  font-family: 'Segoe UI', monospace;
  font-weight: bold;
}

:deep(.el-progress__text) {
  color: #fff !important;
}


::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}
::-webkit-scrollbar-thumb {
  background: rgba(0, 168, 255, 0.3);
  border-radius: 3px;
}
::-webkit-scrollbar-track {
  background: transparent;
}
</style>
