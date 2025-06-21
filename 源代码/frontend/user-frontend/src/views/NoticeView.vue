<template>
  <div class="notice-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">📢 系统公告</h1>
      <p class="page-subtitle">了解平台最新动态和重要通知</p>
    </div>

    <!-- 重要公告轮播 -->
    <div class="important-notices" v-if="importantNotices.length > 0">
      <h2 class="section-title">
        <i class="el-icon-warning"></i>
        重要公告
      </h2>
      <el-carousel height="120px" direction="vertical" :autoplay="true" :interval="4000">
        <el-carousel-item v-for="notice in importantNotices" :key="notice.id">
          <div class="important-notice-item" @click="viewNoticeDetail(notice)">
            <div class="notice-title">{{ notice.title }}</div>
            <div class="notice-time">{{ formatTime(notice.publishTime) }}</div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <!-- 公告筛选 -->
    <div class="notice-filter">
      <el-radio-group v-model="filterType" @change="handleFilterChange">
        <el-radio-button :label="null">全部公告</el-radio-button>
        <el-radio-button :label="0">普通公告</el-radio-button>
        <el-radio-button :label="1">重要公告</el-radio-button>
      </el-radio-group>
    </div>

    <!-- 公告列表 -->
    <div class="notice-list">
      <el-card v-for="notice in noticeList" :key="notice.id" class="notice-card" @click.native="viewNoticeDetail(notice)">
        <div class="notice-header">
          <div class="notice-title-wrapper">
            <el-tag v-if="notice.type === 1" type="danger" size="mini">重要</el-tag>
            <h3 class="notice-title">{{ notice.title }}</h3>
          </div>
          <div class="notice-time">{{ formatTime(notice.publishTime) }}</div>
        </div>
        <div class="notice-content" v-html="getNoticePreview(notice.content)"></div>
      </el-card>
    </div>

    <!-- 分页 -->
    <div class="pagination-wrapper" v-if="total > 0">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>

    <!-- 空状态 -->
    <div class="empty-state" v-if="noticeList.length === 0 && !loading">
      <el-empty description="暂无公告">
        <el-button type="primary" @click="loadNotices">刷新</el-button>
      </el-empty>
    </div>

    <!-- 加载状态 -->
    <div class="loading-wrapper" v-if="loading">
      <el-loading-spinner></el-loading-spinner>
      <p>正在加载公告...</p>
    </div>

    <!-- 公告详情弹窗 -->
    <el-dialog
      title="公告详情"
      :visible.sync="showDetailDialog"
      width="800px"
      :before-close="handleCloseDetail"
      class="notice-detail-dialog">
      <div v-if="selectedNotice">
        <div class="detail-header">
          <h2>{{ selectedNotice.title }}</h2>
          <div class="detail-meta">
            <el-tag v-if="selectedNotice.type === 1" type="danger" size="small">重要公告</el-tag>
            <el-tag v-else type="info" size="small">普通公告</el-tag>
            <span class="publish-time">发布时间：{{ formatTime(selectedNotice.publishTime) }}</span>
          </div>
        </div>
        <div class="detail-content" v-html="selectedNotice.content"></div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import NoticeServices from '@/service/NoticeServices'

export default {
  name: 'NoticeView',
  data() {
    return {
      loading: false,
      noticeList: [],
      importantNotices: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      filterType: null,
      showDetailDialog: false,
      selectedNotice: null
    }
  },
  mounted() {
    this.loadNotices()
    this.loadImportantNotices()
  },
  methods: {
    loadNotices() {
      this.loading = true
      const params = {
        current: this.currentPage,
        size: this.pageSize
      };
      if (this.filterType !== null) {
        params.type = this.filterType
      }

      NoticeServices.getNoticeList(params)
          .then(response => {
            if (response.success) {
              const records = Array.isArray(response.data)
                ? response.data
                : (response.data.records || [])
              this.noticeList = records
              this.total = response.data.total || records.length
            } else {
              this.$message.error('加载公告失败：' + response.errorMsg)
            }
          })
          .catch(error => {
            console.error('加载公告失败:', error)
            this.$message.error('加载公告失败，请重试')
          })
          .finally(() => {
            this.loading = false
          })
    },

    loadImportantNotices() {
      NoticeServices.getImportantNotices()
        .then(response => {
          if (response.success) {
            // 既兼容 data 为数组，也兼容分页结构 data.records
            const list = Array.isArray(response.data)
              ? response.data
              : (response.data.records || [])
            this.importantNotices = list.slice(0, 5)
          } else {
            this.$message.error('加载重要公告失败：' + response.errorMsg)
          }
        })
        .catch(error => {
          console.error('加载重要公告失败:', error)
        })
    }, // <--- Added comma here

    handleFilterChange() {
      this.currentPage = 1
      this.loadNotices()
    },

    handleCurrentChange(page) {
      // ...
      this.currentPage = page
      this.loadNotices()
    },

    viewNoticeDetail(notice) {
      NoticeServices.getNoticeById(notice.id)
          .then(response => {
            if (response.success) {
              this.selectedNotice = response.data
              this.showDetailDialog = true
            } else {
              this.$message.error('获取公告详情失败：' + response.errorMsg)
            }
          })
          .catch(error => {
            console.error('获取公告详情失败:', error)
            this.$message.error('获取公告详情失败，请重试')
          })
    },

    handleCloseDetail() {
      this.showDetailDialog = false
      this.selectedNotice = null
    },

    formatTime(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString('zh-CN', {
        year: 'numeric', month: '2-digit', day: '2-digit',
        hour: '2-digit', minute: '2-digit'
      })
    },

    getNoticePreview(content) {
      if (!content) return ''
      const text = content.replace(/<[^>]*>/g, '')
      return text.length > 100 ? text.substring(0, 100) + '...' : text
    }
  }
}
</script>

<style scoped>
.notice-container {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}

.page-title {
  font-size: 28px;
  color: #303133;
  margin-bottom: 10px;
}

.page-subtitle {
  color: #909399;
  font-size: 14px;
  margin: 0;
}

.important-notices {
  margin-bottom: 30px;
}

.important-notices .el-card {
  border-radius: 8px;
  border-left: 4px solid #ff4d4f;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
}

.section-title {
  font-size: 18px;
  color: #ff4d4f;
  margin-bottom: 15px;
  display: flex;
  align-items: center;
  font-weight: bold;
}

.section-title i {
  margin-right: 8px;
  color: #ff4d4f;
}

.important-notice-item {
  background: linear-gradient(135deg, #ff6b6b, #ee5a24);
  color: white;
  padding: 20px;
  border-radius: 12px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.important-notice-item:hover {
  transform: translateY(-2px);
  background: linear-gradient(135deg, #ff5252, #e53935);
}

.notice-filter {
  margin-bottom: 20px;
  text-align: center;
  background: white;
  padding: 16px 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
}

.notice-list {
  margin-bottom: 30px;
}

.notice-card {
  margin-bottom: 15px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-radius: 8px;
  border-left: 4px solid #e6f7ff;
}

.notice-card:hover {
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
  transform: translateY(-2px);
}

.notice-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 10px;
}

.notice-title-wrapper {
  display: flex;
  align-items: center;
  flex: 1;
}

.notice-title {
  margin: 0 0 0 10px;
  font-size: 16px;
  color: #303133;
  font-weight: 500;
}

.notice-time {
  color: #909399;
  font-size: 12px;
  white-space: nowrap;
}

.notice-content {
  color: #606266;
  font-size: 14px;
  line-height: 1.6;
}

.pagination-wrapper {
  text-align: center;
  margin-top: 30px;
}

.empty-state {
  text-align: center;
  margin: 50px 0;
}

.loading-wrapper {
  text-align: center;
  margin: 50px 0;
  color: #909399;
}

.detail-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  border-bottom: 1px solid #ebeef5;
}

.detail-header h2 {
  margin: 0 0 10px 0;
  color: #303133;
}

.detail-meta {
  display: flex;
  align-items: center;
  gap: 15px;
}

.publish-time {
  color: #909399;
  font-size: 14px;
}

.detail-content {
  line-height: 1.8;
  color: #606266;
}

.detail-content >>> img {
  max-width: 100%;
  height: auto;
}

.detail-content >>> p {
  margin-bottom: 15px;
}
</style>
