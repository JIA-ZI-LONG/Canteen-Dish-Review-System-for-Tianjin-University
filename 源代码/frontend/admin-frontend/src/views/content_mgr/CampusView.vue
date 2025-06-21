<template>
  <div class="campus-container">
    <el-card class="campus-card">
      <div slot="header" class="clearfix">
        <h2>校区管理</h2>
      </div>
      <div class="search-container">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input v-model="searchForm.name" placeholder="请输入校区名称" clearable @keyup.enter.native="handleSearch" />
          </el-col>
          <el-col :span="6">
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" icon="el-icon-plus" @click="handleCreate">新增校区</el-button>
          </el-col>
        </el-row>
      </div>
      <el-table :data="list" border fit highlight-current-row v-loading="listLoading" class="data-table">
        <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column prop="name" label="校区名称" min-width="150"></el-table-column>
        <el-table-column prop="address" label="地址" min-width="200"></el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" align="center">
          <template slot-scope="{row}">
            <span>{{ row.createTime | formatDate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="{row}">
            <el-button size="mini" type="primary" @click="handleUpdate(row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="query.current" :page-sizes="[10, 20, 50, 100]" :page-size="query.size" layout="total, sizes, prev, pager, next, jumper" :total="total"></el-pagination>
    </el-card>
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="500px">
      <el-form ref="dataForm" :model="campusForm" :rules="formRules" label-width="100px">
        <el-form-item label="校区名称" prop="name">
          <el-input v-model="campusForm.name" placeholder="请输入校区名称"></el-input>
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="campusForm.address" placeholder="请输入地址"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'CampusView',
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      query: { current: 1, size: 10 },
      searchForm: { name: '' },
      dialogFormVisible: false,
      dialogStatus: '',
      campusForm: { id: null, name: '', address: '' },
      formRules: {
        name: [{ required: true, message: '请输入校区名称', trigger: 'blur' }],
        address: [{ required: true, message: '请输入地址', trigger: 'blur' }]
      }
    }
  },
  computed: {
    dialogTitle() { return this.dialogStatus === 'create' ? '新增校区' : '编辑校区' },
    queryModel() {
      return {
        current: this.query.current,
        size: this.query.size,
        name: this.searchForm.name || undefined
      }
    }
  },
  created() {
    if (this.$store.getters.token) { this.fetchList() }
  },
  watch: {
    '$store.getters.token'(newToken) {
      if (newToken && this.list.length === 0) { this.fetchList() }
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
      request({ url: '/admin/campuses', method: 'get', params: this.queryModel }).then(response => {
        this.list = response.records || response
        this.total = response.total || 0
        this.listLoading = false
      }).catch(error => {
        console.error('获取校区列表失败', error)
        this.$message.error('获取校区列表失败：' + (error.message || '请稍后重试'))
        this.listLoading = false
      })
    },
    handleSearch() { this.query.current = 1; this.fetchList() },
    resetSearch() { this.searchForm = { name: '' }; this.query.current = 1; this.fetchList() },
    handleSizeChange(val) { this.query.size = val; this.fetchList() },
    handleCurrentChange(val) { this.query.current = val; this.fetchList() },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => { this.$refs['dataForm'].clearValidate() })
    },
    handleUpdate(row) {
      this.campusForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => { this.$refs['dataForm'].clearValidate() })
    },
    handleDelete(row) {
      this.$confirm(`确定要删除校区"${row.name}" 吗？`, '提示', { type: 'warning' }).then(() => {
        request({ url: `/admin/campuses/${row.id}`, method: 'delete' }).then(() => {
          this.$message.success('删除成功！')
          this.fetchList()
        }).catch(error => {
          this.$message.error('删除失败：' + (error.message || '请稍后重试'))
        })
      }).catch(() => { this.$message.info('已取消删除') })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          request({ url: '/admin/campuses', method: 'post', data: this.campusForm }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('创建成功！')
            this.fetchList()
          }).catch(error => {
            this.$message.error('创建失败：' + (error.message || '请稍后重试'))
          })
        }
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          request({ url: `/admin/campuses/${this.campusForm.id}`, method: 'put', data: this.campusForm }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功！')
            this.fetchList()
          }).catch(error => {
            this.$message.error('更新失败：' + (error.message || '请稍后重试'))
          })
        }
      })
    },
    resetForm() { this.campusForm = { id: null, name: '', address: '' } }
  }
}
</script>

<style scoped>
.campus-container { padding: 20px; background-color: #f5f7fa; min-height: 100vh; }
.campus-card { border-radius: 8px; box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1); }
.clearfix h2 { margin: 0; font-size: 20px; color: #303133; text-align: center; }
.search-container { margin-bottom: 20px; }
.data-table { margin-bottom: 20px; }
.el-pagination { margin-top: 20px; text-align: right; }
.dialog-footer { text-align: right; }
</style>
