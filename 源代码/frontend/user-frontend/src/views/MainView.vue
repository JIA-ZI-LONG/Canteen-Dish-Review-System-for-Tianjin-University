<template>
  <div>
    <MainMenu />

    <div class="main-content">
      <router-view/>
    </div>

    <AIChatBot />
  </div>
</template>

<script>
// 导入主菜单组件
import MainMenu from '../components/MainMenu.vue'
// 导入AI聊天机器人组件
import AIChatBot from '@/components/AIChatBot.vue'

export default {
  name: 'MainView',
  // 注册需要用到的子组件
  components: {
    MainMenu,
    AIChatBot // 在这里注册 AIChatBot 组件
  }
}
</script>

<style scoped>
.main-content {
  padding: 20px;
}
</style>