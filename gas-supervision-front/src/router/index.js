import { createRouter, createWebHistory } from 'vue-router'
import OperationMonitorView from '@/views/OperationMonitorView.vue'
import RiskHazardView from '@/views/RiskHazardView.vue'
import BasicInfoView from '@/views/BasicInfoView.vue'
import OldPipeView from '@/views/OldPipeView.vue'

const routes = [
  {
    path: '/',
    redirect: '/monitor'
  },
  {
    path: '/monitor',
    name: 'OperationMonitor',
    component: OperationMonitorView,
    meta: { title: '运行监测一张图' }
  },
  {
    path: '/risk',
    name: 'RiskHazard',
    component: RiskHazardView,
    meta: { title: '风险隐患一张图' }
  },
  {
    path: '/basic',
    name: 'BasicInfo',
    component: BasicInfoView,
    meta: { title: '基础信息一张图' }
  },
  {
    path: '/old-pipe',
    name: 'OldPipe',
    component: OldPipeView,
    meta: { title: '老旧管网一张图' }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})


router.beforeEach((to, from, next) => {
  document.title = to.meta.title || '燃气监管领导驾驶舱'
  next()
})

export default router
