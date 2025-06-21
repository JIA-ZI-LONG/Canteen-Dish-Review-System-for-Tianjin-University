<template>
  <div class="notice-management">
    <div class="filter-container">
      <el-input
        v-model="query.title"
        placeholder="请输入公告标题"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="query.type" placeholder="公告类型" clearable style="width: 150px" class="filter-item">
        <el-option label="系统公告" :value="1" />
        <el-option label="活动公告" :value="2" />
        <el-option label="维护公告" :value="3" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px" class="filter-item">
        <el-option label="草稿" :value="0" />
        <el-option label="已发布" :value="1" />
        <el-option label="已下线" :value="2" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增公告
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
      <el-table-column label="标题">
        <template slot-scope="scope">
          {{ scope.row.title }}
        </template>
      </el-table-column>
      <el-table-column label="类型" width="100" align="center">
        <template slot-scope="scope">
          {{ getTypeText(scope.row.type) }}
        </template>
      </el-table-column>
      <el-table-column label="优先级" width="100" align="center">
        <template slot-scope="scope">
          {{ getPriorityText(scope.row.priority) }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.status)">
            {{ getStatusText(scope.row.status) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="置顶" width="80" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isTop === 1 ? 'success' : 'info'" size="mini">
            {{ scope.row.isTop === 1 ? '是' : '否' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" width="160" align="center">
        <template slot-scope="scope">
          {{ scope.row.publishTime | formatDate }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="info" size="mini" @click="handleViewDetail(row)">
            详情
          </el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button
            v-if="row.status !== 2"
            size="mini"
            :type="row.status === 1 ? 'danger' : 'success'"
            @click="handleModifyStatus(row, row.status === 1 ? 2 : 1)"
          >
            {{ row.status === 1 ? '下线' : '发布' }}
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
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px">
      <el-form ref="dataForm" :model="noticeForm" :rules="formRules" label-position="left" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="公告标题" prop="title">
              <el-input v-model="noticeForm.title" placeholder="请输入公告标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="公告类型" prop="type">
              <el-select v-model="noticeForm.type" placeholder="请选择公告类型" style="width:100%">
                <el-option label="系统公告" :value="1" />
                <el-option label="活动公告" :value="2" />
                <el-option label="维护公告" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="优先级" prop="priority">
              <el-select v-model="noticeForm.priority" placeholder="请选择优先级" style="width:100%">
                <el-option label="低" :value="1" />
                <el-option label="中" :value="2" />
                <el-option label="高" :value="3" />
                <el-option label="紧急" :value="4" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="发布时间" prop="publishTime">
              <el-date-picker
                v-model="noticeForm.publishTime"
                type="datetime"
                placeholder="选择发布时间"
                style="width:100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="noticeForm.status">
                <el-radio :label="0">草稿</el-radio>
                <el-radio :label="1">发布</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="是否置顶" prop="isTop">
              <el-radio-group v-model="noticeForm.isTop">
                <el-radio :label="1">是</el-radio>
                <el-radio :label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="公告内容" prop="content">
          <el-input type="textarea" :rows="6" v-model="noticeForm.content" placeholder="请输入公告内容"></el-input>
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

    <!-- 公告详情对话框 -->
    <el-dialog title="公告详情" :visible.sync="detailDialogVisible" width="600px">
      <div v-if="currentNotice">
        <el-row :gutter="20">
          <el-col :span="12">
            <p><strong>公告ID：</strong>{{ currentNotice.id }}</p>
            <p><strong>标题：</strong>{{ currentNotice.title }}</p>
            <p><strong>类型：</strong>{{ getTypeText(currentNotice.type) }}</p>
            <p><strong>优先级：</strong>{{ getPriorityText(currentNotice.priority) }}</p>
          </el-col>
          <el-col :span="12">
            <p><strong>状态：</strong>
              <el-tag :type="getStatusType(currentNotice.status)">
                {{ getStatusText(currentNotice.status) }}
              </el-tag>
            </p>
            <p><strong>置顶：</strong>{{ currentNotice.isTop === 1 ? '是' : '否' }}</p>
            <p><strong>发布时间：</strong>{{ currentNotice.publishTime | formatDate }}</p>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <div style="margin-top: 20px;">
              <strong>公告内容：</strong>
              <div style="border: 1px solid #dcdfe6; padding: 15px; margin-top: 10px; border-radius: 4px; background-color: #fafafa; white-space: pre-wrap;">{{ currentNotice.content }}</div>
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
  name: 'NoticeManagement',
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
      query: {
        current: 1,
        size: 10,
        title: '',
        type: null,
        status: null
      },
      noticeForm: {
        id: null,
        title: '',
        type: 1,
        priority: 2,
        status: 0,
        isTop: 0,
        content: '',
        publishTime: null
      },
      currentNotice: null,
      dialogFormVisible: false,
      detailDialogVisible: false,
      dialogStatus: '',
      formRules: {
        title: [{ required: true, message: '请输入公告标题', trigger: 'blur' }],
        type: [{ required: true, message: '请选择公告类型', trigger: 'change' }],
        priority: [{ required: true, message: '请选择优先级', trigger: 'change' }],
        content: [{ required: true, message: '请输入公告内容', trigger: 'blur' }],
        publishTime: [{ required: true, message: '请选择发布时间', trigger: 'change' }]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.dialogStatus === 'create' ? '新增公告' : '编辑公告'
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request({
        url: '/admin/notices',
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
      this.noticeForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleViewDetail(row) {
      this.currentNotice = row
      this.detailDialogVisible = true
    },
    handleModifyStatus(row, status) {
      const statusText = status === 1 ? '发布' : '下线'
      this.$confirm(`确认${statusText}该公告吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/notices/${row.id}`,
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
            url: '/admin/notices',
            method: 'post',
            data: this.noticeForm
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
            url: `/admin/notices/${this.noticeForm.id}`,
            method: 'put',
            data: this.noticeForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.getList()
          })
        }
      })
    },
    resetForm() {
      this.noticeForm = {
        id: null,
        title: '',
        type: 1,
        priority: 2,
        status: 0,
        isTop: 0,
        content: '',
        publishTime: null
      }
    },
    getTypeText(type) {
      const types = { 1: '系统公告', 2: '活动公告', 3: '维护公告' }
      return types[type] || '未知'
    },
    getPriorityText(priority) {
      const priorities = { 1: '低', 2: '中', 3: '高', 4: '紧急' }
      return priorities[priority] || '未知'
    },
    getStatusText(status) {
      const statuses = { 0: '草稿', 1: '已发布', 2: '已下线' }
      return statuses[status] || '未知'
    },
    getStatusType(status) {
      const types = { 0: 'info', 1: 'success', 2: 'danger' }
      return types[status] || 'info'
    }
  }
}
</script>

<style scoped>
.notice-management {
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
