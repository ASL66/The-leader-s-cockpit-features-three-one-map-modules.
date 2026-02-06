import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  // 配置路径别名（已有可保留，无则添加）
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src') // 让@指向src目录，与前端代码中的@/api一致
    }
  },
  // 核心：添加代理配置
  server: {
    host: '0.0.0.0', // 允许本地IP访问
    proxy: {
      // 匹配所有以 /gas-api 开头的请求
      '/gas-api': {
        target: 'http://localhost:8080', // 后端服务的实际地址（核心，必须正确）
        changeOrigin: true, // 开启跨域代理（关键，解决浏览器跨域拦截）
        ws: true, // 支持WebSocket（可选，燃气项目无WS可保留）
        rewrite: (path) => path // 后端接口本身为 /gas-api/xxx（context-path），直接转发
      }
    }
  }
})
