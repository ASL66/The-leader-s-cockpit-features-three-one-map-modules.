import { createApp } from 'vue'
import './style.css'
import App from './App.vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import axios from '@/utils/axios'

import router from '@/router'



const app = createApp(App)
app.use(router)

app.use(ElementPlus)

app.config.globalProperties.$axios = axios

app.mount('#app')