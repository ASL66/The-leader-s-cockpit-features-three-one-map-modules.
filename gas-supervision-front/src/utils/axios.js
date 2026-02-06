/**
 * Axios 配置和封装
 * 用于与后端 API 进行通信，处理请求和响应的拦截
 */
import axios from 'axios'
import { ElMessage } from 'element-plus'


/**
 * 创建 Axios 实例
 * 配置基础 URL、超时时间和请求头
 */
const service = axios.create({
  // 基础 URL，用于所有请求
  baseURL: '/gas-api',
  // 请求超时时间，10秒
  timeout: 10000,
  // 请求头配置
  headers: {
    'Content-Type': 'application/json;charset=utf-8'
  }
})


/**
 * 请求拦截器
 * 用于在发送请求前进行处理，如添加认证 token 等
 */
service.interceptors.request.use(
  (config) => {
    // 可以在这里添加认证 token 等请求前处理逻辑
    return config
  },
  (error) => {
    // 处理请求错误
    console.error('请求错误：', error)
    return Promise.reject(error)
  }
)


/**
 * 响应拦截器
 * 用于处理响应数据，如统一错误处理等
 */
service.interceptors.response.use(
  (response) => {
    // 从响应中获取数据
    const res = response.data
    
    // 检查响应码，200 表示成功
    if (res.code === 200) {
      // 返回响应数据中的 data 字段
      return res.data
    } else {
      // 处理错误响应
      ElMessage.error(res.msg || '接口请求失败')
      return Promise.reject(new Error(res.msg || 'Error'))
    }
  },
  (error) => {
    // 处理网络错误等
    console.error('响应错误：', error)
    ElMessage.error('网络异常，请稍后重试')
    return Promise.reject(error)
  }
)

// 导出配置好的 Axios 实例
export default service