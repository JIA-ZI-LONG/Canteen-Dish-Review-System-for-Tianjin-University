<template>
  <div class="canteen-container">
    <el-card class="canteen-card">
      <div slot="header" class="clearfix">
        <h2>食堂管理</h2>
      </div>

      <div class="toolbar">
        <div class="toolbar-left">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索食堂名称、地址、楼层等信息"
            prefix-icon="el-icon-search"
            style="width: 350px; margin-right: 10px;"
            @input="handleSearch"
            clearable>
          </el-input>
          <el-button @click="handleReset" icon="el-icon-refresh">重置</el-button>
        </div>
        <div class="toolbar-right">
          <el-button type="primary" icon="el-icon-plus" @click="handleCreate">
            新增食堂
          </el-button>
        </div>
      </div>

      <el-table :data="list" border fit highlight-current-row v-loading="listLoading" class="data-table">
        <el-table-column prop="id" label="ID" width="80" align="center"></el-table-column>
        <el-table-column prop="name" label="食堂名称" min-width="120"></el-table-column>
        <el-table-column prop="campusName" label="所属校区" width="120"></el-table-column>
        <el-table-column prop="typeName" label="食堂类型" width="120"></el-table-column>
        <el-table-column prop="address" label="地址" min-width="150"></el-table-column>
        <el-table-column prop="floor" label="楼层" width="100"></el-table-column>
        <el-table-column prop="openHours" label="营业时间" width="120"></el-table-column>
        <el-table-column prop="avgPrice" label="人均消费" width="100" align="center">
          <template slot-scope="{row}">
            <span>{{ row.avgPrice ? '¥' + row.avgPrice : '暂无数据' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="score" label="综合评分" width="100" align="center">
          <template slot-scope="{row}">
            <span>{{ row.score ? row.score.toFixed(1) + '分' : '暂无评分' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" align="center" fixed="right">
          <template slot-scope="{row}">
            <el-button type="primary" size="mini" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(row)">删除</el-button>
          </template> 
        </el-table-column>
      </el-table>

      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="query.current"
          :page-sizes="[10, 20, 50]"
          :page-size="query.size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          style="margin-top: 20px; text-align: right;">
      </el-pagination>
    </el-card>

    <el-dialog :title="dialogTitle" :visible.sync="dialogFormVisible" width="700px" @close="resetForm">
      <el-form ref="dataForm" :model="canteenForm" :rules="formRules" label-position="left" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="食堂名称" prop="name">
              <el-input v-model="canteenForm.name" placeholder="请输入食堂名" prefix-icon="el-icon-office-building"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属校区" prop="campusId">
              <el-select v-model="canteenForm.campusId" placeholder="请选择校区" style="width: 100%">
                <el-option v-for="item in campusList" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="食堂类型" prop="typeId">
          <el-select v-model="canteenForm.typeId" placeholder="请选择食堂类型" style="width: 100%">
            <el-option v-for="item in canteenTypeList" :key="item.id" :label="item.name" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-row :gutter="20">
            <el-col :span="12">
                <el-form-item label="楼层信息" prop="floor">
                <el-input v-model="canteenForm.floor" placeholder="如：共三层" prefix-icon="el-icon-office-building"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="营业时间" prop="openHours">
                <el-input v-model="canteenForm.openHours" placeholder="如：07:00-21:00" prefix-icon="el-icon-time"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="详细地址" prop="address">
          <el-input v-model="canteenForm.address" placeholder="请输入详细地址" prefix-icon="el-icon-location"></el-input>
        </el-form-item>
        <el-form-item label="食堂介绍" prop="introduction">
          <el-input type="textarea" :rows="3" v-model="canteenForm.introduction" placeholder="请输入食堂介绍" />
        </el-form-item>

        <el-form-item label="食堂图片" v-if="dialogStatus === 'edit'">
          <div class="uploaded-files-container">
            <div class="uploaded-files-list">
              <div v-for="file in uploadedFiles" :key="file.id" class="file-item">
                <el-image
                  style="width: 100px; height: 100px; border-radius: 6px;"
                  :src="file.url"
                  fit="cover"
                  :preview-src-list="uploadedFiles.map(f => f.url)">
                </el-image>
                <div class="file-actions">
                  <i class="el-icon-delete" @click="handleFileDelete(file)"></i>
                </div>
              </div>
            </div>

            <el-upload
              class="image-uploader"
              :action="uploadActionUrl"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :before-upload="beforeUploadCheck">
              <i class="el-icon-plus uploader-icon"></i>
              <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过5MB</div>
            </el-upload>
          </div>
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
import { getToken } from '@/utils/auth' // 【新增】导入 getToken

export default {
  name: "CanteenManagement",
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      searchKeyword: '',
      query: {
        current: 1,
        size: 10
      },
      dialogFormVisible: false,
      dialogStatus: '',
      dialogTitle: '',
      canteenForm: { id: undefined, name: '', campusId: '', typeId: '', address: '', floor: '', openHours: '', introduction: '' },
      campusList: [],
      canteenTypeList: [],
      formRules: {
        name: [ { required: true, message: '请输入食堂名', trigger: 'blur' } ],
        campusId: [ { required: true, message: '请选择所属校区', trigger: 'change' } ],
        typeId: [ { required: true, message: '请选择食堂类型', trigger: 'change' } ],
        address: [ { required: true, message: '请输入详细地址', trigger: 'blur' } ]
      },
      // 【新增】文件上传相关数据
      uploadedFiles: [],
    }
  },
  computed: {
    queryModel() {
      const params = {
        current: this.query.current,
        size: this.query.size
      }
      if (this.searchKeyword.trim()) {
        params.name = this.searchKeyword.trim()
      }
      return params
    },
    submitModel() { return this.canteenForm },
    
    // 【新增】动态计算上传API的地址
    uploadActionUrl() {
      if (this.canteenForm && this.canteenForm.id) {
        return `/api/admin/canteens/${this.canteenForm.id}/upload`;
      }
      return '';
    },
    // 【新增】为上传组件提供认证头
    uploadHeaders() {
      return { 'Authorization': `Bearer ${getToken()}` };
    }
  },
  created() {
    this.fetchList()
    this.fetchSupportData()
  },
  methods: {
    fetchList() {
      this.listLoading = true
      request({
        url: '/admin/canteens',
        method: 'get',
        params: this.queryModel
      }).then(response => {
        this.list = response.records || response
        this.total = response.total || 0
        this.listLoading = false
      }).catch(error => {
        console.error('获取食堂列表失败', error)
        this.$message.error('获取食堂列表失败')
        this.listLoading = false
      })
    },
    fetchSupportData() {
        var self = this;
        request({ url: '/admin/campuses', params: { all: true } }).then(function(response){ self.campusList = response || []; });
        request({ url: '/admin/canteen-types' }).then(function(response){ self.canteenTypeList = response || []; });
    },
    handleSizeChange(val) {
      this.query.size = val;
      this.fetchList();
    },
    handleCurrentChange(val) {
      this.query.current = val;
      this.fetchList();
    },
    handleSearch() {
      this.query.current = 1 
      this.fetchList()
    },
    handleReset() {
      this.searchKeyword = ''
      this.query.current = 1
      this.fetchList()
    },
    resetForm() {
      this.canteenForm = { id: undefined, name: '', campusId: '', typeId: '', address: '', floor: '', openHours: '', introduction: '' }
      this.uploadedFiles = []; // 【新增】关闭对话框时清空文件列表
    },
    handleCreate() {
      this.resetForm()
      this.dialogStatus = 'create'
      this.dialogTitle = '新增食堂'
      this.dialogFormVisible = true
    },
    createData() {
        var self = this
        this.$refs.dataForm.validate(function(valid) {
            if (valid) {
                request({ url: '/admin/canteens', method: 'post', data: self.submitModel })
                .then(function() {
                    self.dialogFormVisible = false
                    self.$message.success('新增成功')
                    self.fetchList()
                })
            }
        })
    },
    handleEdit(row) {
      this.canteenForm = Object.assign({}, row)
      this.dialogStatus = 'edit'
      this.dialogTitle = '编辑食堂'
      this.dialogFormVisible = true
      // 【新增】获取该食堂已上传的图片
      this.fetchUploadedFiles(row.id);
    },
    updateData() {
        var self = this
        this.$refs.dataForm.validate(function(valid) {
            if (valid) {
                request({ url: `/admin/canteens/${self.canteenForm.id}`, method: 'put', data: self.submitModel })
                .then(function() {
                    self.dialogFormVisible = false
                    self.$message.success('更新成功')
                    self.fetchList()
                })
            }
        })
    },
    handleDelete(row) {
        var self = this
        this.$confirm('此操作将永久删除食堂 "' + row.name + '", 是否继续?', '提示', { type: 'warning' })
        .then(function() {
            return request({ url: '/admin/canteens/' + row.id, method: 'delete' })
        }).then(function() {
            self.$message.success('删除成功!')
            self.fetchList()
        }).catch(function() {
            self.$message.info('已取消删除');
        });
    },

    // 【新增】以下为文件操作相关方法
    fetchUploadedFiles(canteenId) {
      this.uploadedFiles = [];
      if (!canteenId) return;
      request({
        url: `/api/admin/canteens/${canteenId}/files`,
        method: 'get'
      }).then(response => {
        this.uploadedFiles = response || [];
      }).catch(error => {
        this.$message.error('获取图片列表失败: ' + error.message);
      });
    },
    // eslint-disable-next-line no-unused-vars
    handleUploadSuccess(res, _file) {
      if (res && res.success) {
        this.$message.success('上传成功!');
        this.uploadedFiles.push(res.data);
      } else {
        this.$message.error('上传失败: ' + (res.errorMsg || '请稍后重试'));
      }
    },
    
    beforeUploadCheck(file) {
      const isJPGorPNG = file.type === 'image/jpeg' || file.type === 'image/png';
      const isLt5M = file.size / 1024 / 1024 < 5;
      if (!isJPGorPNG) {
        this.$message.error('上传图片只能是 JPG 或 PNG 格式!');
      }
      if (!isLt5M) {
        this.$message.error('上传图片大小不能超过 5MB!');
      }
      return isJPGorPNG && isLt5M;
    },

    handleFileDelete(file) {
      this.$confirm(`确定要删除这张图片吗?`, '提示', {
        type: 'warning'
      }).then(() => {
        request({
          url: `/api/admin/files/${file.id}`,
          method: 'delete'
        }).then(() => {
          this.$message.success('删除成功!');
          const index = this.uploadedFiles.findIndex(f => f.id === file.id);
          if (index !== -1) {
            this.uploadedFiles.splice(index, 1);
          }
        });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    }
  }
}
</script>
<style scoped>
.canteen-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}

.canteen-card {
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.clearfix h2 {
  margin: 0;
  color: #303133;
  font-weight: 600;
}

.toolbar {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.toolbar-left, .toolbar-right {
  display: flex;
  align-items: center;
}

.data-table {
  margin-bottom: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}

.el-pagination {
  margin-top: 20px;
  text-align: right;
}

/* 【新增】文件上传和列表的样式 */
.uploaded-files-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  width: 100%;
}
.uploaded-files-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}
.file-item {
  position: relative;
  border: 1px solid #dcdfe6;
  border-radius: 6px;
}
.file-item .file-actions {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  color: white;
  font-size: 20px;
  opacity: 0;
  transition: opacity 0.3s;
}
.file-item:hover .file-actions {
  opacity: 1;
}
.file-actions i {
  cursor: pointer;
}
.image-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100px;
  height: 100px;
  line-height: 100px;
  text-align: center;
}
.image-uploader .el-upload:hover {
  border-color: #409EFF;
}
.uploader-icon {
  font-size: 28px;
  color: #8c939d;
}
</style>