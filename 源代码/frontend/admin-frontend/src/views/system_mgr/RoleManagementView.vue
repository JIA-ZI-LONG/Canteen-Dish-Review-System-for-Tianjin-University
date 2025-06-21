<template>
  <div class="app-container">
    <div class="filter-container" style="margin-bottom: 20px;">
      <el-input v-model="listQuery.name" placeholder="角色名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新增角色
      </el-button>
    </div>

    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="角色ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="角色名称">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="描述">
        <template slot-scope="scope">
          <span>{{ scope.row.description }}</span>
        </template>
      </el-table-column>
      <el-table-column class-name="status-col" label="状态" width="110" align="center">
        <template slot-scope="{row}">
          <el-switch
            v-model="row.status"
            :active-value="1"
            :inactive-value="0"
            active-color="#13ce66"
            inactive-color="#ff4949"
            @change="handleStatusChange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="创建时间" width="200" align="center">
         <template slot-scope="scope">
          {{ scope.row.createTime | parseTime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button type="danger" size="mini" @click="handleDelete(row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.current" :limit.sync="listQuery.size" @pagination="fetchData" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="80px" style="width: 400px; margin-left:50px;">
        <el-form-item label="角色名称" prop="name">
          <el-input v-model="temp.name" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="temp.description" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" placeholder="请输入描述" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="temp.status">
            <el-radio :label="1">正常</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getRoleList, addRole, updateRole, deleteRole, changeRoleStatus } from '@/api/role'
import Pagination from '@/components/Pagination' // 确保路径正确
import { parseTime } from '@/utils' // 确保路径正确

export default {
  name: 'RoleManagementView',
  components: { Pagination },
  filters: {
    parseTime(time) {
      if (!time) return ''
      return parseTime(time, '{y}-{m}-{d} {h}:{i}:{s}')
    }
  },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        current: 1,
        size: 10,
        name: undefined
      },
      temp: {
        id: undefined,
        name: '',
        description: '',
        status: 1
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑角色',
        create: '新增角色'
      },
      rules: {
        name: [{ required: true, message: '角色名称不能为空', trigger: 'blur' }],
        status: [{ required: true, message: '请选择状态', trigger: 'change' }]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      var self = this
      self.listLoading = true
      getRoleList(self.listQuery).then(function(response) {
        if(response.data && response.data.success){
            self.list = response.data.data.records
            self.total = parseInt(response.data.data.total, 10)
        } else {
            self.$message.error(response.data.errorMsg || '获取数据失败')
        }
        self.listLoading = false
      }).catch(function(){
          self.listLoading = false
          self.$message.error('网络错误')
      })
    },
    handleFilter() {
      this.listQuery.current = 1
      this.fetchData()
    },
    resetTemp() {
      this.temp = {
        id: undefined,
        name: '',
        description: '',
        status: 1
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      var self = this
      this.$nextTick(function() {
        self.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      var self = this
      this.$refs['dataForm'].validate(function(valid) {
        if (valid) {
          addRole(self.temp).then(function() {
            // self.list.unshift(self.temp) // 最好是重新请求，保证数据一致性
            self.fetchData();
            self.dialogFormVisible = false
            self.$notify({
              title: '成功',
              message: '创建成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      var self = this
      this.$nextTick(function() {
        self.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      var self = this
      this.$refs['dataForm'].validate(function(valid) {
        if (valid) {
          var tempData = Object.assign({}, self.temp)
          updateRole(tempData).then(function() {
            self.fetchData() // 重新请求数据
            self.dialogFormVisible = false
            self.$notify({
              title: '成功',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row) {
        var self = this
        this.$confirm('此操作将永久删除角色【' + row.name + '】, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(function() {
            deleteRole(row.id).then(function() {
                self.fetchData()
                self.$message({
                    type: 'success',
                    message: '删除成功!'
                });
            })
        }).catch(function() {
            self.$message({
                type: 'info',
                message: '已取消删除'
            });          
        });
    },
    handleStatusChange(row) {
      var self = this
      var text = row.status === 1 ? '启用' : '禁用'
      this.$confirm('确认要"' + text + '"角色【' + row.name + '】吗?', '警告', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(function() {
        return changeRoleStatus(row.id, row.status)
      }).then(function() {
        self.$message.success(text + '成功')
      }).catch(function(err) {
        // 如果API调用失败或用户取消，恢复原来的状态
        row.status = row.status === 1 ? 0 : 1
        if (err !== 'cancel') {
            self.$message.error('操作失败')
        }
      })
    }
  }
}
</script>