<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
// 新增：引入 vuex 的 mapGetters
import { mapGetters } from 'vuex';

export default {
  name: 'App',
  computed: {
    // 新增：获取主题状态
    ...mapGetters(['currentTheme'])
  },
  mounted() {
    // 新增：在应用加载时，根据store中的状态设置初始主题
    document.documentElement.className = this.currentTheme;
  }
}
</script>

<style>
/* 新增：定义亮色和暗黑两套颜色变量 */
:root {
  --bg-color-primary: #ffffff;
  --bg-color-secondary: #f5f7fa;
  --bg-color-card: #ffffff;
  --text-color-primary: #303133;
  --text-color-regular: #606266;
  --text-color-secondary: #909399;
  --border-color-base: #dcdfe6;
  --border-color-light: #ebeef5;
  --tju-blue: #005A9C;
  --tju-blue-light: #F0F6FC;
}

html.dark {
  --bg-color-primary: #141414;
  --bg-color-secondary: #1e1e1e;
  --bg-color-card: #282828;
  --text-color-primary: #e5e5e5;
  --text-color-regular: #cfcfcf;
  --text-color-secondary: #a3a3a3;
  --border-color-base: #4d4d4d;
  --border-color-light: #3a3a3a;
  --tju-blue: #409EFF;
  --tju-blue-light: #282828;
}

/* 新增：全局应用颜色变量 */
body {
  background-color: var(--bg-color-secondary);
  color: var(--text-color-primary);
  transition: background-color 0.3s, color 0.3s;
}

#app {
  font-family: 'Inter', 'Noto Sans SC', 'Helvetica Neue', Helvetica, 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', '微软雅黑', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
}

/* 新增：覆盖部分 Element UI 组件的颜色，使其适应主题切换 */
.el-card, .el-menu, .el-dialog, .el-dropdown-menu, .el-pager li, .el-pagination button, .el-pagination .btn-prev, .el-pagination .btn-next {
  background-color: var(--bg-color-card) !important;
  border-color: var(--border-color-light) !important;
  color: var(--text-color-regular) !important;
}

.el-card__header, .el-dialog__title, .el-menu-item, .el-dropdown-menu__item, .el-form-item__label, h1, h2, h3, h4, h5, h6, .el-table th, .el-table td, .el-dialog__body {
  color: var(--text-color-primary) !important;
}

.el-input__inner, .el-textarea__inner {
  background-color: var(--bg-color-primary) !important;
  border-color: var(--border-color-base) !important;
  color: var(--text-color-primary) !important;
}

.el-menu-item:focus, .el-menu-item:hover, .el-submenu__title:hover {
  background-color: var(--bg-color-secondary) !important;
}
</style>