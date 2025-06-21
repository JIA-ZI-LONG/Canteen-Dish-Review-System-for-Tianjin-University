<template>
  <div class="personnel-management">
    <div class="filter-container">
      <el-input
        v-model="query.username"
        placeholder="请输入用户名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-input
        v-model="query.name"
        placeholder="请输入姓名"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px" class="filter-item">
        <el-option label="正常" :value="0" />
        <el-option label="禁用" :value="1" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增管理员
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
      <el-table-column label="用户名">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="姓名">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="邮箱">
        <template slot-scope="scope">
          {{ scope.row.email }}
        </template>
      </el-table-column>
      <el-table-column label="角色">
        <template slot-scope="scope">
          <el-tag v-for="role in scope.row.roles" :key="role.id" size="mini" style="margin-right: 5px;">
            {{ role.name }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'danger'">
            {{ scope.row.status === 0 ? '正常' : '禁用' }}
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
            {{ row.status === 0 ? '禁用' : '启用' }}
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
    <el-dialog :title="dialogStatus === 'create' ? '新增管理员' : '编辑管理员'" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="adminForm"
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="adminForm.username" placeholder="请输入用户名" :disabled="dialogStatus === 'update'" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="adminForm.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="adminForm.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item v-if="dialogStatus === 'create'" label="密码" prop="password">
          <el-input v-model="adminForm.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="角色" prop="roleIds">
          <el-select v-model="adminForm.roleIds" multiple placeholder="请选择角色" style="width:100%">
            <el-option
              v-for="role in roleOptions"
              :key="role.id"
              :label="role.name"
              :value="role.id"
            />
          </el-select>
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
  name: 'PersonnelManagement',
  components: { Pagination },
  filters: {
    parseTime
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      roleOptions: [],
      query: {
        current: 1,
        size: 10,
        username: '',
        name: '',
        status: null
      },
      adminForm: {
        id: null,
        username: '',
        name: '',
        email: '',
        password: '',
        roleIds: []
      },
      dialogFormVisible: false,
      dialogStatus: '',
      rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }
        ],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        roleIds: [{ required: true, message: '请选择角色', trigger: 'change' }]
      }
    }
  },
  created() {
    this.getList()
    this.fetchRoleOptions()
  },
  methods: {
    getList() {
      this.listLoading = true
      request({
        url: '/admin/admins',
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
    fetchRoleOptions() {
      request({ url: '/admin/roles', method: 'get' }).then(response => {
        this.roleOptions = response.records || []
      }).catch(error => {
        console.error('获取角色列表失败:', error)
        this.$message.error('获取角色列表失败')
        this.roleOptions = []
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
      this.adminForm = Object.assign({}, row)
      this.adminForm.roleIds = row.roles ? row.roles.map(role => role.id) : []
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleModifyStatus(row, status) {
      const statusText = status === 0 ? '启用' : '禁用'
      this.$confirm(`确认${statusText}该管理员吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/admins/${row.id}`,
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
            url: '/admin/admins',
            method: 'post',
            data: this.adminForm
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
            url: '/admin/admins',
            method: 'put',
            data: this.adminForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.getList()
          })
        }
      })
    },
    resetForm() {
      this.adminForm = {
        id: null,
        username: '',
        name: '',
        email: '',
        password: '',
        roleIds: []
      }
    }
  }
}
</script>

<style scoped>
.personnel-management {
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
