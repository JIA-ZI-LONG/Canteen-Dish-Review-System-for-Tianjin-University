<template>
  <div class="task-management">
    <div class="filter-container">
      <el-input
        v-model="query.taskName"
        placeholder="请输入任务名称"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="query.taskGroup"
        placeholder="请输入任务分组"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px" class="filter-item">
        <el-option label="正常" :value="0" />
        <el-option label="暂停" :value="1" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增任务
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
      <el-table-column label="任务名称">
        <template slot-scope="scope">
          {{ scope.row.taskName }}
        </template>
      </el-table-column>
      <el-table-column label="任务分组">
        <template slot-scope="scope">
          {{ scope.row.taskGroup }}
        </template>
      </el-table-column>
      <el-table-column label="CRON表达式">
        <template slot-scope="scope">
          {{ scope.row.cronExpression }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
            {{ scope.row.status === 0 ? '正常' : '暂停' }}
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
            :type="row.status === 0 ? 'danger' : 'success'"
            @click="handleModifyStatus(row, row.status === 0 ? 1 : 0)"
          >
            {{ row.status === 0 ? '暂停' : '启动' }}
          </el-button>
          <el-button
            size="mini"
            type="warning"
            @click="handleExecute(row)"
          >
            立即执行
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

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="dialogStatus === 'create' ? '新增任务' : '编辑任务'" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="taskForm"
        label-position="left"
        label-width="120px"
        style="width: 450px; margin-left:50px;"
      >
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="taskForm.taskName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务分组" prop="taskGroup">
          <el-input v-model="taskForm.taskGroup" placeholder="请输入任务分组" />
        </el-form-item>
        <el-form-item label="CRON表达式" prop="cronExpression">
          <el-input v-model="taskForm.cronExpression" placeholder="例如：0 0 1 * * ?" />
          <el-button type="text" @click="showCronHelp = !showCronHelp">查看CRON表达式帮助</el-button>
          <div class="cron-help" v-if="showCronHelp">
            <p><strong>CRON表达式格式：</strong>秒 分 时 日 月 周</p>
            <p><strong>示例：</strong></p>
            <ul>
              <li>0 0 1 * * ? - 每天凌晨1点执行</li>
              <li>0 */30 * * * ? - 每30分钟执行一次</li>
              <li>0 0 12 * * MON-FRI - 工作日中午12点执行</li>
            </ul>
          </div>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" :rows="3" v-model="taskForm.description" placeholder="请输入任务描述" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="taskForm.status">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1">暂停</el-radio>
          </el-radio-group>
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
  name: 'TaskManagement',
  components: { Pagination },
  filters: {
    parseTime
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      showCronHelp: false,
      query: {
        current: 1,
        size: 10,
        taskName: '',
        taskGroup: '',
        status: null
      },
      taskForm: {
        id: null,
        taskName: '',
        taskGroup: '',
        cronExpression: '',
        description: '',
        status: 0
      },
      dialogFormVisible: false,
      dialogStatus: '',
      rules: {
        taskName: [{ required: true, message: '请输入任务名称', trigger: 'blur' }],
        taskGroup: [{ required: true, message: '请输入任务分组', trigger: 'blur' }],
        cronExpression: [{ required: true, message: '请输入CRON表达式', trigger: 'blur' }],
        description: [{ required: true, message: '请输入任务描述', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request({
        url: '/admin/tasks',
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
    handleExecute(row) {
      this.$confirm('确认立即执行该任务吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/tasks/${row.id}/execute`,
          method: 'post'
        }).then(() => {
          this.$message.success('任务已触发执行')
        })
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
      this.taskForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleModifyStatus(row, status) {
      const statusText = status === 0 ? '启动' : '暂停'
      this.$confirm(`确认${statusText}该任务吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/tasks/${row.id}`,
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
            url: '/admin/tasks',
            method: 'post',
            data: this.taskForm
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
            url: '/admin/tasks',
            method: 'put',
            data: this.taskForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.getList()
          })
        }
      })
    },
    resetForm() {
      this.taskForm = {
        id: null,
        taskName: '',
        taskGroup: '',
        cronExpression: '',
        description: '',
        status: 0
      }
    }
  }
}
</script>

<style scoped>
.task-management {
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

.cron-help {
  margin-top: 10px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
  font-size: 12px;
}

.cron-help ul {
  margin: 5px 0;
  padding-left: 20px;
}

.cron-help li {
  margin: 3px 0;
}
</style>
