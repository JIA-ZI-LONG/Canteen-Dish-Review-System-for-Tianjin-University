<template>
  <div class="log-management">
    <div class="filter-container">
      <el-input
        v-model="query.operatorName"
        placeholder="请输入操作人"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="query.module"
        placeholder="请输入模块"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px" class="filter-item">
        <el-option label="成功" :value="1" />
        <el-option label="失败" :value="0" />
      </el-select>
      <el-date-picker
        v-model="dateRange"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        class="filter-item"
        @change="handleDateChange"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="操作人">
        <template slot-scope="scope">
          {{ scope.row.operatorName }}
        </template>
      </el-table-column>
      <el-table-column label="模块">
        <template slot-scope="scope">
          {{ scope.row.module }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          {{ scope.row.operation }}
        </template>
      </el-table-column>
      <el-table-column label="请求方法" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="getMethodType(scope.row.method)" size="mini">
            {{ scope.row.method }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '成功' : '失败' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="执行时间" width="100" align="center">
        <template slot-scope="scope">
          {{ scope.row.executeTime }}ms
        </template>
      </el-table-column>
      <el-table-column label="操作时间" width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.createTime | formatDate }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="120" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleViewDetail(row)">
            详情
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="query.current"
      :limit.sync="query.size"
      @pagination="getList"
    />

    <!-- 日志详情对话框 -->
    <el-dialog title="日志详情" :visible.sync="detailDialogVisible" width="800px">
      <div v-if="currentLog">
        <el-row :gutter="20">
          <el-col :span="12">
            <p><strong>日志ID：</strong>{{ currentLog.id }}</p>
            <p><strong>操作人：</strong>{{ currentLog.operatorName }}</p>
            <p><strong>模块：</strong>{{ currentLog.module }}</p>
            <p><strong>操作：</strong>{{ currentLog.operation }}</p>
            <p><strong>状态：</strong>
              <el-tag :type="currentLog.status === 1 ? 'success' : 'danger'" size="mini">
                {{ currentLog.status === 1 ? '成功' : '失败' }}
              </el-tag>
            </p>
          </el-col>
          <el-col :span="12">
            <p><strong>请求方法：</strong>{{ currentLog.method }}</p>
            <p><strong>执行时间：</strong>{{ currentLog.executeTime }}ms</p>
            <p><strong>IP地址：</strong>{{ currentLog.ip }}</p>
            <p><strong>操作时间：</strong>{{ currentLog.createTime | formatDate }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <p><strong>操作描述：</strong></p>
            <div style="border: 1px solid #dcdfe6; padding: 15px; border-radius: 4px; background-color: #fafafa; margin-bottom: 15px;">
              {{ currentLog.description }}
            </div>
            <p v-if="currentLog.errorMsg"><strong>错误信息：</strong></p>
            <div v-if="currentLog.errorMsg" style="border: 1px solid #f56c6c; padding: 15px; border-radius: 4px; background-color: #fef0f0; color: #f56c6c;">
              {{ currentLog.errorMsg }}
            </div>
          </el-col>
        </el-row>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'
import Pagination from '@/components/Pagination'

export default {
  name: 'LogManagement',
  components: { Pagination },
  filters: {
    formatDate(time) {
      if (!time) return ''
      const date = new Date(time)
      return date.toLocaleString()
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      dateRange: [],
      query: {
        current: 1,
        size: 10,
        operatorName: '',
        module: '',
        status: null,
        startTime: '',
        endTime: ''
      },
      currentLog: null,
      detailDialogVisible: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request({
        url: '/admin/logs',
        method: 'get',
        params: this.query
      }).then(response => {
        this.list = response.records || []
        this.total = response.total || 0
        this.listLoading = false
      }).catch(() => {
        this.listLoading = false
      })
    },
    handleFilter() {
      this.query.current = 1
      this.getList()
    },
    handleDateChange(dates) {
      if (dates && dates.length === 2) {
        this.query.startTime = dates[0]
        this.query.endTime = dates[1]
      } else {
        this.query.startTime = ''
        this.query.endTime = ''
      }
    },
    handleViewDetail(row) {
      this.currentLog = row
      this.detailDialogVisible = true
    },
    getMethodType(method) {
      const methodTypes = {
        'GET': 'success',
        'POST': 'primary',
        'PUT': 'warning',
        'DELETE': 'danger',
        'PATCH': 'info'
      }
      return methodTypes[method] || 'info'
    }
  }
}
</script>

<style scoped>
.log-management {
  padding: 20px;
}

.filter-container {
  padding-bottom: 10px;
}

.filter-item {
  display: inline-block;
  vertical-align: middle;
  margin-bottom: 10px;
  margin-right: 10px;
}
</style>
