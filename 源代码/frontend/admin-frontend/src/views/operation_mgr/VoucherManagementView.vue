<template>
  <div class="voucher-management">
    <div class="filter-container">
      <el-input
        v-model="query.title"
        placeholder="请输入优惠券标题"
        style="width: 200px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="query.type" placeholder="优惠券类型" clearable style="width: 150px" class="filter-item">
        <el-option label="满减券" :value="0" />
        <el-option label="折扣券" :value="1" />
      </el-select>
      <el-select v-model="query.status" placeholder="状态" clearable style="width: 150px" class="filter-item">
        <el-option label="未开启" :value="0" />
        <el-option label="进行中" :value="1" />
        <el-option label="已结束" :value="2" />
      </el-select>
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        搜索
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        新增优惠券?      </el-button>
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
          <el-tag :type="scope.row.type === 0 ? 'success' : 'warning'">
            {{ scope.row.type === 0 ? '满减券' : '折扣券' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="优惠金额/折扣" width="120" align="center">
        <template slot-scope="scope">
          {{ scope.row.type === 0 ? scope.row.price + '元' : scope.row.discount + '折' }}
        </template>
      </el-table-column>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : scope.row.status === 0 ? 'info' : 'danger'">
            {{ scope.row.status === 0 ? '未开启' : scope.row.status === 1 ? '进行中' : '已结束' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200" class-name="small-padding fixed-width">
        <template slot-scope="{row}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button
            v-if="row.status !== 2"
            size="mini"
            :type="row.status === 1 ? 'danger' : 'success'"
            @click="handleModifyStatus(row, row.status === 1 ? 2 : 1)"
          >
            {{ row.status === 1 ? '结束' : '开启' }}
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

    <!-- 新增/编辑对话框-->
    <el-dialog :title="dialogStatus === 'create' ? '新增优惠券' : '编辑优惠券'" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="voucherForm"
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="标题" prop="title">
          <el-input v-model="voucherForm.title" placeholder="请输入优惠券标题" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="voucherForm.description" placeholder="请输入优惠券描述" />
        </el-form-item>
        <el-form-item label="优惠券类型" prop="type">
          <el-radio-group v-model="voucherForm.type">
            <el-radio :label="0">满减券</el-radio>
            <el-radio :label="1">折扣券</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="voucherForm.type === 0" label="优惠金额" prop="price">
          <el-input-number v-model="voucherForm.price" :min="0.01" :precision="2" style="width:100%" placeholder="例如�?0.00" />
        </el-form-item>
        <el-form-item v-if="voucherForm.type === 1" label="折扣" prop="discount">
          <el-input-number v-model="voucherForm.discount" :min="0.1" :max="9.9" :precision="1" style="width:100%" placeholder="例如�?.5" />
        </el-form-item>
        <el-form-item label="适用食堂" prop="canteenId">
          <el-select v-model="voucherForm.canteenId" placeholder="请选择食堂" style="width:100%">
            <el-option
              v-for="canteen in canteenOptions"
              :key="canteen.id"
              :label="canteen.name"
              :value="canteen.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="库存数量" prop="stock">
          <el-input-number v-model="voucherForm.stock" :min="1" style="width:100%" placeholder="请输入库存数量" />
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

export default {
  name: 'VoucherManagement',
  components: { Pagination },
  data() {
    return {
      list: null,
      total: 0,
      listLoading: true,
      canteenOptions: [],
      query: {
        current: 1,
        size: 10,
        title: '',
        type: null,
        status: null
      },
      voucherForm: {
        id: null,
        title: '',
        description: '',
        type: 0,
        price: null,
        discount: null,
        canteenId: null,
        stock: 100
      },
      dialogFormVisible: false,
      dialogStatus: '',
      rules: {
        title: [{ required: true, message: '请输入优惠券标题', trigger: 'blur' }],
        description: [{ required: true, message: '请输入描述信息', trigger: 'blur' }],
        type: [{ required: true, message: '请选择优惠券类型', trigger: 'change' }],
        canteenId: [{ required: true, message: '请选择适用食堂', trigger: 'change' }],
        stock: [{ required: true, message: '请输入库存数量', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getList()
    this.fetchCanteenOptions()
  },
  methods: {
    getList() {
      this.listLoading = true
      request({
        url: '/admin/vouchers',
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
    fetchCanteenOptions() {
      request({ url: '/admin/canteens/all', method: 'get' }).then(response => {
        this.canteenOptions = response || []
      }).catch(error => {
        console.error('获取食堂列表失败:', error)
        this.$message.error('获取食堂列表失败')
        this.canteenOptions = []
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
      this.voucherForm = Object.assign({}, row)
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleModifyStatus(row, status) {
      const statusText = status === 1 ? '开启' : '结束'
      this.$confirm(`确认${statusText}该优惠券吗？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request({
          url: `/admin/vouchers/${row.id}`,
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
            url: '/admin/vouchers',
            method: 'post',
            data: this.voucherForm
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
            url: `/admin/vouchers/${this.voucherForm.id}`,
            method: 'put',
            data: this.voucherForm
          }).then(() => {
            this.dialogFormVisible = false
            this.$message.success('更新成功')
            this.getList()
          })
        }
      })
    },
    resetForm() {
      this.voucherForm = {
        id: null,
        title: '',
        description: '',
        type: 0,
        price: null,
        discount: null,
        canteenId: null,
        stock: 100
      }
    }
  }
}
</script>

<style scoped>
.voucher-management {
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
