<template>
  <div class="incentive-management">
    <el-card class="incentive-card">
      <div slot="header" class="clearfix">
        <h2>激励管理</h2>
      </div>

      <div class="filter-container">
        <el-input
          v-model="query.ruleName"
          placeholder="请输入规则名称"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="handleFilter"
        />
        <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px" class="filter-item">
          <el-option label="启用" :value="1" />
          <el-option label="禁用" :value="0" />
        </el-select>
        <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
          搜索
        </el-button>
        <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
          新增激励规则
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
        <el-table-column label="规则名称">
          <template slot-scope="scope">
            {{ scope.row.ruleName }}
          </template>
        </el-table-column>
        <el-table-column label="规则类型">
          <template slot-scope="scope">
            {{ scope.row.ruleType }}
          </template>
        </el-table-column>
        <el-table-column label="奖励积分" width="100" align="center">
          <template slot-scope="scope">
            {{ scope.row.rewardPoints }}
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
              {{ scope.row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" width="160" align="center">
          <template slot-scope="scope">
            {{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
          <template slot-scope="{row}">
            <el-button type="primary" size="mini" @click="handleUpdate(row)">
              编辑
            </el-button>
            <el-button
              size="mini"
              :type="row.status === 1 ? 'danger' : 'success'"
              @click="handleModifyStatus(row, row.status === 1 ? 0 : 1)"
            >
              {{ row.status === 1 ? '禁用' : '启用' }}
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
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="500px">
      <el-form ref="dataForm" :model="ruleForm" :rules="formRules" label-position="left" label-width="100px">
        <el-form-item label="规则名称" prop="ruleName">
          <el-input v-model="ruleForm.ruleName" placeholder="请输入规则名称" prefix-icon="el-icon-edit"></el-input>
        </el-form-item>
        <el-form-item label="规则类型" prop="ruleType">
          <el-select v-model="ruleForm.ruleType" placeholder="请选择规则类型" style="width:100%">
            <el-option label="登录奖励" value="LOGIN_REWARD" />
            <el-option label="评论奖励" value="COMMENT_REWARD" />
            <el-option label="分享奖励" value="SHARE_REWARD" />
            <el-option label="签到奖励" value="CHECKIN_REWARD" />
          </el-select>
        </el-form-item>
        <el-form-item label="奖励积分" prop="rewardPoints">
          <el-input-number v-model="ruleForm.rewardPoints" :min="1" style="width:100%" placeholder="请输入奖励积分" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="ruleForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="规则描述" prop="description">
          <el-input type="textarea" :rows="3" v-model="ruleForm.description" placeholder="请输入规则描述"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'
import Pagination from '@/components/Pagination'
import { parseTime } from '@/utils'

export default {
  name: 'IncentiveManagement',
  components: { Pagination },
  filters: {
    parseTime
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      query: {
        current: 1,
        size: 10,
        ruleName: '',
        status: null
      },
      ruleForm: {
        id: null,
        ruleName: '',
        ruleType: '',
        rewardPoints: 1,
        status: 1,
        description: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      formRules: {
        ruleName: [{ required: true, message: '请输入规则名称', trigger: 'blur' }],
        ruleType: [{ required: true, message: '请选择规则类型', trigger: 'change' }],
        rewardPoints: [{ required: true, message: '请输入奖励积分', trigger: 'blur' }],
        description: [{ required: true, message: '请输入规则描述', trigger: 'blur' }]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.dialogStatus === 'create' ? '新增激励规则' : '编辑激励规则'
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request({
        url: '/admin/incentive-rules',
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
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.ruleForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleModifyStatus(row, status) {
      const statusText = status === 1 ? '启用' : '禁用'
      this.$confirm(`确认${statusText}该激励规则吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/incentive-rules/${row.id}`,
          method: 'patch',
          data: { status }
        }).then(() => {
          this.$message.success(`${statusText}成功`)
          this.getList()
        })
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          request({
            url: '/admin/incentive-rules',
            method: 'post',
            data: this.ruleForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('创建成功')
            this.getList()
          })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          request({
            url: '/admin/incentive-rules',
            method: 'put',
            data: this.ruleForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.getList()
          })
        }
      })
    },
    resetForm() {
      this.ruleForm = {
        id: null,
        ruleName: '',
        ruleType: '',
        rewardPoints: 1,
        status: 1,
        description: ''
      }
    }
  }
}
</script>

<style scoped>
.incentive-management {
  padding: 20px;
}

.incentive-card {
  margin-bottom: 20px;
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
