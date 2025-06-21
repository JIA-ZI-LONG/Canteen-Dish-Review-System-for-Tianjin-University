<template>
  <div class="dashboard-container">
    <!-- 顶部欢迎和统计 -->
    <el-card shadow="hover" class="welcome-card modern-card gradient-border">
      <el-row type="flex" justify="space-between" align="middle">
        <div class="welcome-section">
          <h2>您好, {{ name }}!</h2>
          <p>欢迎回到TjuFood管理系统，祝您工作愉快！</p>
          <p>您当前的角色是:
            <el-tag
                v-for="role in roles"
                :key="role"
                size="small"
                type="primary"
                style="margin-right: 8px;">
              {{ role }}
            </el-tag>
          </p>
        </div>
        <div class="stats-section">
          <div class="stats-card">
            <div class="stats-icon">
              <i class="el-icon-s-order"></i>
            </div>
            <div class="stats-value">{{ pendingTasks }}</div>
            <div class="stats-label">待办事项</div>
          </div>
          <div class="stats-card">
            <div class="stats-icon">
              <i class="el-icon-folder-opened"></i>
            </div>
            <div class="stats-value">{{ projectCount }}</div>
            <div class="stats-label">项目总数</div>
          </div>
        </div>
      </el-row>
    </el-card>

    <!-- 数据概览 -->
    <el-row :gutter="24" style="margin-top: 24px;">
      <el-col :span="6" v-for="(card, index) in dataCards" :key="index">
        <div class="stats-card">
          <div class="stats-icon" :class="card.iconClass">
            <i :class="card.icon"></i>
          </div>
          <div class="stats-value">{{ card.value }}</div>
          <div class="stats-label">{{ card.title }}</div>
          <div class="stats-trend" :class="card.trendClass">
            {{ card.trend }} {{ card.trendText }}
          </div>
        </div>
      </el-col>
    </el-row>

    <!-- 快捷操作和最近活动 -->
    <el-row :gutter="24" style="margin-top: 24px;">
      <el-col :span="16">
        <el-card shadow="hover" class="quick-access-card modern-card">
          <div slot="header">
            <span class="card-header-title">快捷操作</span>
          </div>
          <div class="quick-access">
            <button
                v-for="action in quickActions"
                :key="action.path"
                class="modern-btn btn-gradient"
                @click="$router.push(action.path)">
              <i :class="action.icon"></i> {{ action.label }}
            </button>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover" class="activity-card modern-card">
          <div slot="header">
            <span class="card-header-title">最近操作</span>
          </div>
          <el-timeline :reverse="false">
            <el-timeline-item
                v-for="(activity, index) in activities"
                :key="index"
                :timestamp="activity.timestamp"
                :color="activity.color">
              {{activity.content}}
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
    </el-row>

    <!-- 待办事项 -->
    <el-card shadow="hover" class="todo-card modern-card" style="margin-top: 24px;">
      <div slot="header">
        <span class="card-header-title">待办事项</span>
      </div>
      <el-table :data="todoList" style="width: 100%">
        <el-table-column prop="title" label="任务" min-width="180"></el-table-column>
        <el-table-column prop="type" label="类型" width="120">
          <template slot-scope="scope">
            <span class="status-indicator" :class="'status-' + getTagType(scope.row.type)">
              {{ scope.row.type }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="priority" label="优先级" width="100">
          <template slot-scope="scope">
            <span class="status-indicator" :class="'status-' + getPriorityType(scope.row.priority)">
              {{ scope.row.priority }}
            </span>
          </template>
        </el-table-column>
        <el-table-column prop="deadline" label="截止日期" width="150"></el-table-column>
        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <button class="modern-btn btn-outline" style="margin-right: 8px; padding: 6px 12px; font-size: 12px;" @click="handleTodo(scope.row)">处理</button>
            <button class="modern-btn btn-gradient" style="padding: 6px 12px; font-size: 12px;" @click="completeTodo(scope.row)">完成</button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      pendingTasks: 3,
      totalTasks: 10,
      projectCount: 8,
      dataCards: [
        {
          title: '用户总数',
          value: '1,286',
          trend: '↑ 12%',
          trendText: '较上周',
          trendClass: 'trend-up',
          icon: 'el-icon-user',
          iconClass: 'user-icon'
        },
        {
          title: '博客总数',
          value: '526',
          trend: '↑ 8%',
          trendText: '较上周',
          trendClass: 'trend-up',
          icon: 'el-icon-document',
          iconClass: 'blog-icon'
        },
        {
          title: '评价总数',
          value: '3,128',
          trend: '↑ 15%',
          trendText: '较上周',
          trendClass: 'trend-up',
          icon: 'el-icon-chat-line-square',
          iconClass: 'review-icon'
        },
        {
          title: '菜品总数',
          value: '842',
          trend: '↑ 5%',
          trendText: '较上周',
          trendClass: 'trend-up',
          icon: 'el-icon-dish',
          iconClass: 'dish-icon'
        }
      ],
      quickActions: [
        { label: '食堂管理', icon: 'el-icon-office-building', path: '/content-mgr/canteen', type: 'primary' },
        { label: '审核中心', icon: 'el-icon-s-check', path: '/community-mgr/blog', type: 'success' },
        { label: '发布公告', icon: 'el-icon-bell', path: '/operation-mgr/notice', type: 'warning' },
        { label: '人员管理', icon: 'el-icon-user-solid', path: '/system-mgr/personnel', type: 'danger' },
        { label: '数据大屏', icon: 'el-icon-data-analysis', path: '/toolbox/statistics', type: 'info' }
      ],
      activities: [
        { content: '审核通过了用户 "TJU_Foodie" 的博客', timestamp: '2小时前', color: '#0bbd87' },
        { content: '更新了 "夏日特饮" 轮播图的上线时间', timestamp: '昨天', color: '' },
        { content: '禁用了违规用户 "小广告君"', timestamp: '2025-06-12', color: '' },
      ],
      todoList: [
        { title: '审核新发布的博客', type: '内容审核', priority: '高', deadline: '2025-06-18' },
        { title: '处理用户举报', type: '社区管理', priority: '中', deadline: '2025-06-19' },
        { title: '更新首页轮播图', type: '运营', priority: '低', deadline: '2025-06-20' },
        { title: '发布系统更新公告', type: '系统', priority: '高', deadline: '2025-06-18' }
      ]
    }
  },
  computed: {
    ...mapGetters(['name', 'roles'])
  },
  methods: {
    getTagType(type) {
      const types = {
        '内容审核': 'success',
        '社区管理': 'warning',
        '运营': 'info',
        '系统': 'danger'
      }
      return types[type] || 'primary'
    },
    getPriorityType(priority) {
      const types = {
        '高': 'danger',
        '中': 'warning',
        '低': 'info'
      }
      return types[priority] || 'primary'
    },
    handleTodo(todo) {
      this.$message.success(`开始处理任务: ${todo.title}`)
    },
    completeTodo(todo) {
      this.$message.success(`任务已完成: ${todo.title}`)
      // 实际应用中这里会调用API更新任务状态
      this.todoList = this.todoList.filter(item => item !== todo)
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/variables.scss";

.dashboard-container {
  padding: 24px;
  background: $backgroundGradient;
  min-height: calc(100vh - 50px);
}

.welcome-card {
  .welcome-section {
    h2 {
      margin: 0;
      font-size: 28px;
      font-weight: 700;
      color: $textPrimary;
      background: $primaryGradient;
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }
    p {
      margin-top: 12px;
      color: $textRegular;
      font-size: 15px;
      line-height: 1.6;
    }
  }

  .stats-section {
    display: flex;
    gap: 20px;
  }
}

// 数据卡片样式已移至 modern-enhancements.scss 中的 .stats-card

.quick-access {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(160px, 1fr));
  gap: 16px;

  .modern-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    min-height: 48px;

    i {
      margin-right: 8px;
      font-size: 16px;
    }
  }
}

.card-header-title {
  font-size: 18px;
  font-weight: 600;
  color: $textPrimary;
}

.el-timeline {
  padding-left: 0;

  .el-timeline-item {
    padding-bottom: 20px;

    &:last-child {
      padding-bottom: 0;
    }

    .el-timeline-item__content {
      font-size: 14px;
      color: $textRegular;
      line-height: 1.5;
    }

    .el-timeline-item__timestamp {
      font-size: 12px;
      color: $textSecondary;
    }
  }
}
</style>