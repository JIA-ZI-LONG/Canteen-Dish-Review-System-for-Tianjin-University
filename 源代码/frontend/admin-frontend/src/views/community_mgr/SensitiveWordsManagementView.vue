<template>
  <div class="sensitive-word-container">
    <el-card class="sensitive-word-card">
      <div slot="header" class="clearfix">
        <h2>敏感词管理</h2>
      </div>
      <!-- 搜索区域 -->
      <div class="search-container">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-input
              v-model="searchForm.word"
              placeholder="请输入敏感词"
              prefix-icon="el-icon-search"
              clearable
              @keyup.enter.native="handleSearch"
            />
          </el-col>
          <el-col :span="6">
            <el-select v-model="searchForm.category" placeholder="请选择分类" clearable style="width: 100%">
              <el-option label="政治敏感" value="政治敏感"></el-option>
              <el-option label="色情低俗" value="色情低俗"></el-option>
              <el-option label="暴力血腥" value="暴力血腥"></el-option>
              <el-option label="违法犯罪" value="违法犯罪"></el-option>
              <el-option label="其他" value="其他"></el-option>
            </el-select>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" icon="el-icon-search" @click="handleSearch">搜索</el-button>
            <el-button icon="el-icon-refresh" @click="resetSearch">重置</el-button>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" icon="el-icon-plus" @click="handleCreate">新增敏感词</el-button>
          </el-col>
        </el-row>
      </div>

      <!-- 数据表格 -->
      <el-table :data="list" border fit highlight-current-row v-loading="listLoading" class="data-table">
        <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column prop="word" label="敏感词" min-width="150"></el-table-column>
        <el-table-column prop="category" label="分类" width="120" align="center">
          <template slot-scope="{row}">
            <el-tag :type="getCategoryType(row.category)">
              {{ row.category }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="160" align="center">
          <template slot-scope="{row}">
            <span>{{ row.createTime | formatDate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template slot-scope="{row}">
            <el-button size="mini" type="primary" @click="handleUpdate(row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(row)">删除</el-button>
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

    <!-- 新增/编辑对话框 -->
    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="500px">
      <el-form ref="dataForm" :model="wordForm" :rules="formRules" label-width="100px">
        <el-form-item label="敏感词" prop="word">
          <el-input v-model="wordForm.word" placeholder="请输入敏感词"></el-input>
        </el-form-item>
        <el-form-item label="分类" prop="category">
          <el-select v-model="wordForm.category" placeholder="请选择分类" style="width:100%">
            <el-option label="政治敏感" value="政治敏感"></el-option>
            <el-option label="色情低俗" value="色情低俗"></el-option>
            <el-option label="暴力血腥" value="暴力血腥"></el-option>
            <el-option label="违法犯罪" value="违法犯罪"></el-option>
            <el-option label="其他" value="其他"></el-option>
          </el-select>
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
  name: 'SensitiveWordsManagement',
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
        word: '',
        category: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      wordForm: {
        id: null,
        word: '',
        category: ''
      },
      formRules: {
        word: [
          { required: true, message: '请输入敏感词', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在1到50个字符', trigger: 'blur' }
        ],
        category: [
          { required: true, message: '请选择分类', trigger: 'change' }
        ]
      }
    }
  },
  computed: {
    dialogTitle() {
      return this.dialogStatus === 'create' ? '新增敏感词' : '编辑敏感词'
    },
    queryModel() {
      return {
        current: this.query.current,
        size: this.query.size,
        word: this.searchForm.word || undefined,
        category: this.searchForm.category || undefined
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
        url: '/admin/sensitive-words',
        method: 'get',
        params: this.queryModel
      }).then(response => {
        this.list = response.records || response
        this.total = response.total || 0
        this.listLoading = false
      }).catch(error => {
        console.error('获取敏感词列表失败', error)
        this.$message.error('获取敏感词列表失败：' + (error.message || '请稍后重试'))
        this.listLoading = false
      })
    },
    handleSearch() {
      this.query.current = 1
      this.fetchList()
    },
    resetSearch() {
      this.searchForm = {
        word: '',
        category: ''
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
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.wordForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleDelete(row) {
      this.$confirm(`确定要删除敏感词"${row.word}" 吗？`, '提示', {
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/sensitive-words/${row.id}`,
          method: 'delete'
        }).then(() => {
          this.$message.success('删除成功！')
          this.fetchList()
        }).catch(error => {
          this.$message.error('删除失败：' + (error.message || '请稍后重试'))
        })
      }).catch(() => {
        this.$message.info('已取消删除')
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          request({
            url: '/admin/sensitive-words',
            method: 'post',
            data: this.wordForm
          }).then(() => {
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
          request({
            url: '/admin/sensitive-words',
            method: 'put',
            data: this.wordForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功！')
            this.fetchList()
          }).catch(error => {
            this.$message.error('更新失败：' + (error.message || '请稍后重试'))
          })
        }
      })
    },
    resetForm() {
      this.wordForm = {
        id: null,
        word: '',
        category: ''
      }
    },
    getCategoryType(category) {
      const types = {
        '政治敏感': 'danger',
        '色情低俗': 'warning',
        '暴力血腥': 'danger',
        '违法犯罪': 'danger',
        '其他': 'info'
      }
      return types[category] || 'info'
    }
  }
}
</script>

<style scoped>
.sensitive-word-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.sensitive-word-card {
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
