<template>
  <div id="app">
    <!-- 导航菜单 -->
    <div class="nav-menu">
      <router-link to="/monitor" class="nav-item">运行监测</router-link>
      <router-link to="/risk" class="nav-item">风险隐患</router-link>
      <router-link to="/basic" class="nav-item">基础信息</router-link>
      <router-link to="/old-pipe" class="nav-item">老旧管网</router-link>
      <a class="nav-item" href="http://192.168.1.23:5174/">可视化组件库</a>
    </div>
    <!-- 路由视图，显示当前路由对应的组件 -->
    <router-view />
  </div>
</template>

<script setup>
/**
 * 前端应用主组件
 * 
 * 功能说明：
 * 1. 作为整个应用的根组件
 * 2. 提供顶部导航菜单，包含系统的主要功能模块入口
 * 3. 使用 router-view 显示当前路由对应的页面组件
 * 
 * 导航菜单说明：
 * - 运行监测：显示燃气系统运行状态监测数据和图表
 * - 风险隐患：显示燃气系统的风险隐患信息
 * - 基础信息：显示燃气系统的基础信息数据
 * - 老旧管网：显示老旧管网的相关信息
 * - 可视化组件库：链接到外部的可视化组件库页面
 */
</script>

<style>
/* 全局样式重置 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* 应用根容器样式 */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
  height: 100vh;
  width: 100vw;
  overflow: hidden;
}

/* 导航菜单样式 */
.nav-menu {
  position: fixed;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
  background: rgba(0, 21, 41, 0.8);
  padding: 0 20px;
  border-radius: 0 0 10px 10px;
  display: flex;
  backdrop-filter: blur(5px);
}

/* 导航菜单项样式 */
.nav-item {
  color: #fff;
  text-decoration: none;
  padding: 15px 20px;
  font-size: 14px;
  transition: all 0.3s;
  border-bottom: 2px solid transparent;
}

/* 导航菜单项悬停和激活状态样式 */
.nav-item:hover, .nav-item.router-link-active {
  color: #409eff;
  border-bottom-color: #409eff;
  background: rgba(255, 255, 255, 0.1);
}
</style>
