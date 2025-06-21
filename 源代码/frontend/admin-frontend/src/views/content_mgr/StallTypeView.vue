<template>
  <div class="stall-type-container">
    <el-card class="stall-type-card">
      <div slot="header" class="clearfix">
        <h2>窗口类型管理</h2>
      </div>

      <div class="toolbar">
        <div class="toolbar-left">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索类型名称或图标URL"
            prefix-icon="el-icon-search"
            style="width: 300px; margin-right: 10px;"
            @input="handleSearch"
            clearable>
          </el-input>
          <el-button @click="handleReset" icon="el-icon-refresh">重置</el-button>
        </div>
        <div class="toolbar-right">
          <el-button type="primary" icon="el-icon-plus" @click="handleCreate">
            新增窗口类型
          </el-button>
        </div>
      </div>

      <el-table :data="filteredList" border fit highlight-current-row v-loading="listLoading">
        <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column prop="name" label="类型名称" min-width="120"></el-table-column>
        <el-table-column label="图标" width="120" align="center">
          <template slot-scope="{row}">
            <div v-if="row.icon" class="icon-preview">
              <img :src="row.icon" :alt="row.name" class="icon-image" @error="handleImageError" />
            </div>
            <span v-else class="no-icon">暂无图标</span>
          </template>
        </el-table-column>
        <el-table-column prop="icon" label="图标URL" min-width="200" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sort" label="排序" width="100" align="center"></el-table-column>
        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="{row}">
            <el-button type="primary" size="mini" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
        <template slot="empty">
          <div class="empty-state">
            <i class="el-icon-folder-opened" style="font-size: 48px; color: #c0c4cc;"></i>
            <p>暂无窗口类型数据</p>
            <el-button type="primary" size="small" @click="handleCreate">新增窗口类型</el-button>
          </div>
        </template>
      </el-table>
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="500px">
      <el-form ref="dataForm" :model="temp" :rules="formRules" label-position="left" label-width="80px" style="width: 400px; margin-left:50px;">
        <el-form-item label="类型名称" prop="name">
          <el-input v-model="temp.name" placeholder="请输入窗口类型名" />
        </el-form-item>
        <el-form-item label="图标URL" prop="icon">
          <el-input v-model="temp.icon" placeholder="请输入图标的URL" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model="temp.sort" :min="0" :max="999" placeholder="数字越小，排序越靠前" style="width: 100%;" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">确认</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: "StallTypeManagement",
  data() {
    return {
      list: [],
      filteredList: [],
      listLoading: true,
      searchKeyword: '',
      dialogFormVisible: false,
      dialogStatus: '',
      dialogTitle: '',
      temp: {
        id: undefined,
        name: '',
        icon: '',
        sort: 0
      },
      formRules: {
        name: [
          { required: true, message: '请输入类型名', trigger: 'blur' },
          { min: 2, max: 20, message: '长度2-20个字', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入排序', trigger: 'blur' },
          { type: 'number', message: '排序值必须为数字', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 检查用户是否已登录，如果已登录则获取数据    
    if (this.$store.getters.token) {
      this.fetchList()
    }
  },
  watch: {
    // 监听token变化，当用户登录后自动加载数据    
    '$store.getters.token'(newToken) {
      if (newToken && this.list.length === 0) {
        this.fetchList()
      }
    }
  },
  methods: {
    fetchList() {
      this.listLoading = true
      request({
        url: '/admin/stall-types',
        method: 'get'
      }).then(response => {
        this.list = response || []
        this.filteredList = [...this.list]
        this.listLoading = false
      }).catch(error => {
        console.error('获取窗口类型列表失败', error)
        this.$message.error('获取窗口类型列表失败' + (error.message || '请稍后重试'))
        this.list = []
        this.filteredList = []
        this.listLoading = false
      })
    },

    // 搜索功能
    handleSearch() {
      if (!this.searchKeyword.trim()) {
        this.filteredList = [...this.list]
        return
      }

      const keyword = this.searchKeyword.toLowerCase()
      this.filteredList = this.list.filter(item => {
        return (
          (item.name && item.name.toLowerCase().includes(keyword)) ||
          (item.icon && item.icon.toLowerCase().includes(keyword)) ||
          (item.sort && item.sort.toString().includes(keyword)) ||
          (item.id && item.id.toString().includes(keyword))
        )
      })
    },

    // 重置搜索
    handleReset() {
      this.searchKeyword = ''
      this.filteredList = [...this.list]
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        icon: '',
        sort: 0
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogTitle = '新增窗口类型'
      this.dialogFormVisible = true
    },
    createData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          request({
            url: '/admin/stall-types',
            method: 'post',
            data: this.temp
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('新增成功')
            this.fetchList()
          }).catch(error => {
            this.$message.error('新增失败' + (error.message || '请稍后重试'))
          })
        } else {
          return false
        }
      })
    },
    handleEdit(row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'edit'
      this.dialogTitle = '编辑窗口类型'
      this.dialogFormVisible = true
    },
    updateData() {
      this.$refs.dataForm.validate((valid) => {
        if (valid) {
          request({
            url: '/admin/stall-types',
            method: 'put',
            data: this.temp
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.fetchList()
          }).catch(error => {
            this.$message.error('更新失败' + (error.message || '请稍后重试'))
          })
        } else {
          return false
        }
      })
    },
    handleDelete(row) {
      this.$confirm(`此操作将永久删除窗口类型 "${row.name}", 是否继续?`, '提示', {
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/stall-types/${row.id}`,
          method: 'delete'
        }).then(() => {
          this.$message.success('删除成功!')
          this.fetchList()
        }).catch(error => {
          this.$message.error('删除失败' + (error.message || '请稍后重试'))
        })
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    handleImageError(event) {
      // 图片加载失败时的处理
      event.target.style.display = 'none'
      event.target.nextElementSibling.style.display = 'inline'
    }
  }
}
</script>

<style scoped>
.stall-type-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.stall-type-card {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.toolbar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.toolbar-left {
  display: flex;
  align-items: center;
}

.toolbar-right {
  display: flex;
  align-items: center;
}

.dialog-footer {
  text-align: right;
}

.clearfix h2 {
  margin: 0;
  color: #303133;
  font-weight: 600;
}

.icon-preview {
  display: flex;
  justify-content: center;
  align-items: center;
}

.icon-image {
  width: 32px;
  height: 32px;
  object-fit: cover;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
}

.no-icon {
  color: #909399;
  font-size: 12px;
}

.empty-state {
  text-align: center;
  padding: 40px 0;
}

.empty-state p {
  margin: 16px 0;
  color: #909399;
  font-size: 14px;
}
</style>
