<template>
  <div class="user-container">
    <el-card class="user-card">
      <div slot="header" class="clearfix">
        <h2>用户管理</h2>
      </div>
      <!-- 搜索区域 -->
      <div class="search-container">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input
              v-model="searchForm.nickname"
              placeholder="请输入用户昵称"
              prefix-icon="el-icon-user"
              clearable
              @keyup.enter.native="handleSearch"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="searchForm.email"
              placeholder="请输入邮箱"
              prefix-icon="el-icon-message"
              clearable
              @keyup.enter.native="handleSearch"
            />
          </el-col>
          <el-col :span="6">
            <el-select v-model="searchForm.status" placeholder="请选择状态" clearable style="width: 100%">
              <el-option label="正常" :value="0"></el-option>
              <el-option label="禁用" :value="1"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button>
          </el-col>
        </el-row>
      </div>

      <!-- 数据表格 -->
      <el-table :data="list" border fit highlight-current-row v-loading="listLoading" class="data-table">
        <el-table-column prop="id" label="用户ID" width="100" align="center"></el-table-column>
        <el-table-column prop="nickname" label="昵称" min-width="120"></el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="180"></el-table-column>
        <el-table-column prop="phone" label="手机号" width="120" align="center">
          <template slot-scope="{row}">
            {{ row.phone || '未填写' }}
          </template>
        </el-table-column>
        <el-table-column prop="gender" label="性别" width="80" align="center">
          <template slot-scope="{row}">
            {{ row.gender === 0 ? '女' : row.gender === 1 ? '男' : '未知' }}
          </template>
        </el-table-column>
        <el-table-column prop="campus" label="校区" width="100" align="center"></el-table-column>
        <el-table-column prop="credits" label="积分" width="80" align="center"></el-table-column>
        <el-table-column prop="level" label="等级" width="80" align="center"></el-table-column>
        <el-table-column prop="status" label="状态" width="100" align="center">
          <template slot-scope="{row}">
            <el-tag :type="row.status === 0 ? 'success' : 'danger'">
              {{ row.status === 0 ? '正常' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="注册时间" width="160" align="center">
          <template slot-scope="{row}">
            <span>{{ row.createTime | formatDate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template slot-scope="{row}">
            <el-button size="mini" type="info" @click="handleView(row)">详情</el-button>
            <el-button
              size="mini"
              :type="row.status === 0 ? 'danger' : 'success'"
              @click="handleToggleStatus(row)"
            >
              {{ row.status === 0 ? '禁用' : '启用' }}
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="query.current"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="query.size"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </el-card>

    <!-- 用户详情对话框 -->
    <el-dialog title="用户详情" :visible.sync="detailDialogVisible" width="700px">
      <div v-if="currentUser">
        <el-row :gutter="20">
          <el-col :span="12">
            <p><strong>用户ID：</strong>{{ currentUser.id }}</p>
            <p><strong>昵称：</strong>{{ currentUser.nickname }}</p>
            <p><strong>邮箱：</strong>{{ currentUser.email }}</p>
            <p><strong>手机号：</strong>{{ currentUser.phone || '未填写' }}</p>
          </el-col>
          <el-col :span="12">
            <p><strong>性别：</strong>{{ currentUser.gender === 0 ? '女' : currentUser.gender === 1 ? '男' : '未知' }}</p>
            <p><strong>校区：</strong>{{ currentUser.campus }}</p>
            <p><strong>积分：</strong>{{ currentUser.credits }}</p>
            <p><strong>等级：</strong>{{ currentUser.level }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <p><strong>个人简介：</strong>{{ currentUser.bio || '暂无简介' }}</p>
            <p><strong>注册时间：</strong>{{ currentUser.createTime | formatDate }}</p>
            <p><strong>最后更新：</strong>{{ currentUser.updateTime | formatDate }}</p>
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

export default {
  name: 'UserManagement',
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      query: {
        current: 1,
        size: 10
      },
      searchForm: {
        nickname: '',
        email: '',
        status: ''
      },
      detailDialogVisible: false,
      currentUser: null
    }
  },
  computed: {
    queryModel() {
      return {
        current: this.query.current,
        size: this.query.size,
        nickname: this.searchForm.nickname || undefined,
        email: this.searchForm.email || undefined,
        status: this.searchForm.status !== '' ? this.searchForm.status : undefined
      }
    }
  },
  created() {
    if (this.$store.getters.token) {
      this.fetchList()
    }
  },
  watch: {
    '$store.getters.token'(newToken) {
      if (newToken && this.list.length === 0) {
        this.fetchList()
      }
    }
  },
  filters: {
    formatDate(value) {
      if (!value) return ''
      return new Date(value).toLocaleString()
    }
  },
  methods: {
    fetchList() {
      this.listLoading = true
      request({
        url: '/admin/users',
        method: 'get',
        params: this.queryModel
      }).then(response => {
        this.list = response.records || response
        this.total = response.total || 0
        this.listLoading = false
      }).catch(error => {
        console.error('获取用户列表失败', error)
        this.$message.error('获取用户列表失败：' + (error.message || '请稍后重试'))
        this.listLoading = false
      })
    },
    handleSearch() {
      this.query.current = 1
      this.fetchList()
    },
    resetSearch() {
      this.searchForm = {
        nickname: '',
        email: '',
        status: ''
      }
      this.query.current = 1
      this.fetchList()
    },
    handleSizeChange(val) {
      this.query.size = val
      this.fetchList()
    },
    handleCurrentChange(val) {
      this.query.current = val
      this.fetchList()
    },
    handleView(row) {
      this.currentUser = row
      this.detailDialogVisible = true
    },
    handleToggleStatus(row) {
      const action = row.status === 0 ? '禁用' : '启用'
      const newStatus = row.status === 0 ? 1 : 0

      this.$confirm(`确定要${action}用户"${row.nickname}" 吗？`, '提示', {
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/users/${row.id}/status`,
          method: 'put',
          data: { status: newStatus }
        }).then(() => {
          this.$message.success(`${action}成功！`)
          this.fetchList()
        }).catch(error => {
          this.$message.error(`${action}失败：` + (error.message || '请稍后重试'))
        })
      }).catch(() => {
        this.$message.info('已取消操作')
      })
    }
  }
}
</script>

<style scoped>
.user-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.user-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.clearfix h2 {
  margin: 0;
  font-size: 20px;
  color: #303133;
  text-align: center;
}

.search-container {
  margin-bottom: 20px;
}

.data-table {
  margin-bottom: 20px;
}

.el-pagination {
  margin-top: 20px;
  text-align: right;
}

.dialog-footer {
  text-align: right;
}
</style>
