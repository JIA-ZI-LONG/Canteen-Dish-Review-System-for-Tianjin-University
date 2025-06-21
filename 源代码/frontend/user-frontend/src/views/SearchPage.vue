<template>
  <div class="search-page">
    <el-affix :offset="0" class="search-affix">
      <div class="search-header">
      <el-input
        v-model="keyword"
        placeholder="搜索食堂 / 菜品 / 博客 / 窗口"
        @keyup.enter.native="doSearch"
        class="search-input"
        clearable
      >
        <el-button slot="append" icon="el-icon-search" @click="doSearch" />
      </el-input>
      </div>
    </el-affix>

    <div v-if="historyList.length" class="history-section">
      <div class="history-header">
        <span>搜索历史</span>
        <el-button type="text" @click="handleClearHistory">清空</el-button>
      </div>
      <div class="history-list">
        <el-tag
          v-for="item in historyList"
          :key="item.id"
          closable
          @close="handleDelete(item.id)"
          @click="selectHistory(item.keyword)"
        >
          {{ item.keyword }}
        </el-tag>
      </div>
    </div>

    <el-tabs v-model="type" @tab-click="handleTabClick" class="result-tabs">
      <el-tab-pane label="食堂" name="canteen" />
      <el-tab-pane label="菜品" name="dish" />
      <el-tab-pane label="窗口" name="stall" />
      <el-tab-pane label="博客" name="blog" />
    </el-tabs>

    <div v-loading="loading" class="result-list">
      <div v-if="!records.length && !loading" class="empty">暂无结果</div>

      <el-card
        v-for="item in records"
        :key="item.id"
        class="result-item"
        shadow="hover"
      >
        <div v-html="item.name || item.title" class="result-title" />
        <p v-if="item.introduction || item.summary" class="result-desc">
          {{ item.introduction || item.summary || '' }}
        </p>
        <span class="result-time">{{ formatTime(item.createTime) }}</span>
      </el-card>

      <el-pagination
        v-if="total > size"
        :current-page.sync="current"
        :page-size="size"
        layout="prev, pager, next"
        :total="total"
        @current-change="doSearch"
        class="pagination"
      />
    </div>
  </div>
</template>

<script>
import { search, getHistory, deleteHistory, clearHistory } from '@/service/SearchService'
import dayjs from 'dayjs'

export default {
  name: 'SearchPage',
  data() {
    return {
      keyword: this.$route.query.q || '',
      type: 'canteen',
      records: [],
      total: 0,
      current: 1,
      size: 10,
      historyList: [],
      loading: false
    }
  },
  created() {
    this.loadHistory()
    if (this.keyword) {
      this.doSearch()
    }
  },
  methods: {
    loadHistory() {
      getHistory().then(res => {
        if (res.success) {
          this.historyList = res.data || []
        }
      })
    },
    handleDelete(id) {
      deleteHistory(id).then(() => {
        this.loadHistory()
      })
    },
    handleClearHistory() {
      clearHistory().then(() => {
        this.historyList = []
      })
    },
    selectHistory(k) {
      this.keyword = k
      this.current = 1
      this.doSearch()
    },
    handleTabClick(tab) {
      this.type = tab.name
      this.current = 1
      this.doSearch()
    },
    doSearch() {
      if (!this.keyword.trim()) return
      this.loading = true
      search({ keyword: this.keyword, type: this.type, current: this.current, size: this.size }).then(res => {
        if (res.success) {
          if (this.type === 'canteen' || this.type === 'dish' || this.type === 'stall' || this.type === 'blog') {
            this.records = res.data.records || []
            this.total = res.data.total || 0
          } else {
            // all 类型未用到
            this.records = []
            this.total = 0
          }
        }
      }).finally(() => {
        this.loading = false
      })
    },
    formatTime(t) {
      if (!t) return ''
      return dayjs(t).format('YYYY-MM-DD')
    }
  }
}
</script>

<style scoped>
.search-page {
  padding: 80px 20px 20px; /* 留出 affix 高度 */
  padding: 20px;
}
.search-affix {
  width: 100%;
  background: #fff;
  box-shadow: 0 2px 8px rgba(0,0,0,0.05);
  z-index: 100;
}
.search-header {
  max-width: 600px;
  margin: 0 auto 20px;
}
.search-input.input-with-select /deep/ .el-input__inner, .search-input /deep/ .el-input__inner {
  height: 45px;
}
.history-section {
  max-width: 800px;
  margin: 0 auto 20px;
}
.history-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  color: #606266;
}
.history-list .el-tag {
  margin: 4px;
  cursor: pointer;
}
.result-tabs {
  max-width: 800px;
  margin: 0 auto 20px;
}
.result-list {
  max-width: 800px;
  margin: 0 auto;
}
.result-item {
  margin-bottom: 15px;
}
.result-title em.highlight {
  color: #f56c6c;
  background: #fffbe6;
}
.empty {
  text-align: center;
  color: #909399;
  padding: 40px 0;
}
.pagination {
  text-align: center;
  margin-top: 20px;
}
</style>
