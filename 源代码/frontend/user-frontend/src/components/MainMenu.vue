<template>
  <el-menu mode="horizontal" :router="true" class="main-menu">
    <el-menu-item index="/main/home">
      <img src="../assets/logotju.png" alt="Logo" class="logo">
    </el-menu-item>
    <el-menu-item index="/main/home">{{ $t('nav.home') }}</el-menu-item>
    <el-menu-item index="/main/user/blogslist">{{ $t('nav.blogs') }}</el-menu-item>
    <el-menu-item index="/main/user/canteenslist">{{ $t('nav.canteens') }}</el-menu-item>
    <el-menu-item index="/main/ranking">{{ $t('nav.rankings') }}</el-menu-item>
    <el-menu-item index="/main/voucher">{{ $t('nav.vouchers') }}</el-menu-item>
    <el-menu-item index="/main/notice">{{ $t('nav.notices') }}</el-menu-item>
    <el-menu-item index="/main/user/disheslist">{{ $t('nav.dishes') }}</el-menu-item>
    <el-menu-item index="/main/about">{{ $t('nav.about') }}</el-menu-item>

    <div class="right-menu">
      <!-- 举报按钮 -->
      <el-button
        type="text"
        icon="el-icon-warning"
        @click="showReportDialog"
        style="margin-right: 15px; color: var(--text-color-primary);">
        {{ $t('nav.report') }}
      </el-button>
      <el-switch
          v-model="isDarkTheme"
          @change="toggleTheme"
          active-color="#2c2c2c"
          inactive-color="#dcdfe6"
          active-icon-class="el-icon-moon"
          inactive-icon-class="el-icon-sunny"
          style="margin-right: 20px;">
      </el-switch>

      <el-dropdown @command="handleLanguageChange" style="margin-right: 20px;">
        <span class="el-dropdown-link" style="color: var(--text-color-primary); cursor: pointer;">
          <i class="el-icon-place"></i> {{ $t('lang.switch') }}<i class="el-icon-arrow-down el-icon--right"></i>        
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="zh">简体中文</el-dropdown-item>
          <el-dropdown-item command="en">English</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <template v-if="!isLoggedIn">
        <el-menu-item index="/register">{{ $t('nav.register') }}</el-menu-item>
        <el-menu-item index="/login">{{ $t('nav.login') }}</el-menu-item>
      </template>

      <template v-else>
        <el-dropdown class="user-dropdown" @command="handleCommand">
          <span class="el-dropdown-link" style="color: var(--text-color-primary);">
            <el-avatar :size="32" :src="userIcon || defaultAvatar" style="margin-right: 10px;"></el-avatar>
            {{ userNickname }}
            <i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="profile">
              <i class="el-icon-user"></i> {{ $t('nav.profile') }}
            </el-dropdown-item>
            <el-dropdown-item command="logout" divided>
              <i class="el-icon-switch-button"></i> {{ $t('nav.logout') }}
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </template>
    </div>

    <!-- 举报对话框 -->
    <ReportDialog
      :visible.sync="reportDialogVisible"
      @report-submitted="handleReportSubmitted" />
  </el-menu>
</template>

<script>
import { mapGetters, mapActions } from 'vuex';
import AccountServices from "@/service/AccountServices";
import ReportDialog from "@/components/ReportDialog.vue";

export default {
  components: {
    ReportDialog
  },
  data() {
    return {
      defaultAvatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png',
      reportDialogVisible: false
    }
  },
  computed: {
    ...mapGetters(['isLoggedIn', 'userIcon', 'userNickname', 'currentTheme']),
    // 计算属性，用于 el-switch 的 v-model
    isDarkTheme: {
      get() {
        return this.currentTheme === 'dark';
      },
      set() {
        // @change事件会处理逻辑，这里set可以为空
      }
    }
  },
  methods: {
    ...mapActions(['toggleTheme']), // 从 vuex 引入 action
    
    // 【修复】处理下拉菜单命令 - 添加路由重复导航错误处理
    handleCommand(command) {
      if (command === 'profile') {
        this.navigateToProfile();
      } else if (command === 'logout') {
        this.logout();
      }
    },
    
    // 【新增】安全的路由导航方法
    navigateToProfile() {
      const targetRoute = '/main/user/profile';
      
      // 检查当前路由是否已经是目标路由
      if (this.$route.path === targetRoute) {
        console.log('已经在个人中心页面');
        return;
      }
      
      // 使用 catch 捕获路由重复导航错误
      this.$router.push(targetRoute).catch(err => {
        // 忽略 NavigationDuplicated 错误
        if (err.name !== 'NavigationDuplicated') {
          console.error('路由导航失败:', err);
        }
      });
    },
    
    logout() {
      AccountServices.Logout().then(() => {
        this.$store.dispatch('logout');
        this.$message.success('退出成功');
        this.$router.push('/login').catch(err => {
          if (err.name !== 'NavigationDuplicated') {
            console.error('路由导航失败:', err);
          }
        });
      }).catch(err => {
        this.$store.dispatch('logout');
        console.error("退出登录请求失败", err);
        this.$router.push('/login').catch(routeErr => {
          if (routeErr.name !== 'NavigationDuplicated') {
            console.error('路由导航失败:', routeErr);
          }
        });
      });
    },
    
    // 语言切换方法
    handleLanguageChange(lang) {
      this.$i18n.locale = lang; // 切换 i18n 的语言
      localStorage.setItem('lang', lang); // 持久化语言设置
      this.$message.success('语言切换成功！');
    },
    
    // 显示举报对话框
    showReportDialog() {
      this.reportDialogVisible = true;
    },
    
    // 举报提交成功回调
    handleReportSubmitted(reportId) {
      console.log('举报提交成功，ID:', reportId);
    }
  },
  
  mounted() {
    // 在组件挂载时，确保主题正确应用
    if (this.currentTheme) {
      document.documentElement.className = this.currentTheme;
    }
  }
}
</script>

<style scoped>
.main-menu {
  display: flex;
  align-items: center;
}
.logo {
  height: 40px;
  margin-right: 10px;
}
.right-menu {
  margin-left: auto;
  display: flex;
  align-items: center;
}
.user-dropdown {
  cursor: pointer;
  height: 60px;
  display: flex;
  align-items: center;
  padding: 0 20px;
}
.el-dropdown-link {
  display: flex;
  align-items: center;
  font-size: 14px;
}
</style>
