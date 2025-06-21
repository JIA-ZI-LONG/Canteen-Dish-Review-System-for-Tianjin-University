<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>操作日志导出 (Excel)</span></div>
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="时间范围">
          <el-date-picker
              v-model="searchForm.dateRange"
              type="datetimerange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd HH:mm:ss">
          </el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
              type="primary"
              style="margin-left: 10px;"
              @click="handleExport"
              :loading="loading"
              icon="el-icon-download">
            {{ loading ? '正在导出...' : '查询并导出' }}
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'ExportLog',
  data() {
    return {
      loading: false,
      searchForm: {
        dateRange: []
      }
    }
  },
  methods: {
    handleExport() {
      this.loading = true;

      const params = {};
      if (this.searchForm.dateRange && this.searchForm.dateRange.length === 2) {
        params.startTime = this.searchForm.dateRange[0];
        params.endTime = this.searchForm.dateRange[1];
      }

      // 使用 request 发起请求，并指明响应类型为 'blob'
      request({
        url: '/admin/audit-logs/export',
        method: 'get',
        params: params,
        responseType: 'blob' // 关键！告诉axios期望接收一个二进制对象
      }).then(response => {
        // 【核心修改】response现在是完整的axios响应对象
        // response.data 是我们需要的Blob对象
        const blob = new Blob([response.data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

        // 【核心修改】尝试从响应头中解析文件名
        const contentDisposition = response.headers['content-disposition'];
        let fileName = '审计日志.xlsx'; // 默认文件名
        if (contentDisposition) {
            const fileNameMatch = contentDisposition.match(/filename\*?=(?:UTF-8'')?([^;]+)/);
            if (fileNameMatch && fileNameMatch[1]) {
                // 解码URL编码的文件名
                fileName = decodeURIComponent(fileNameMatch[1].replace(/['"]/g, ''));
            }
        }

        // 创建一个临时的URL指向该Blob对象
        const url = window.URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', fileName);
        document.body.appendChild(link);
        link.click();

        // 清理
        document.body.removeChild(link);
        window.URL.revokeObjectURL(url);

        this.$message.success('导出成功！');
        this.loading = false;
      }).catch(error => {
        this.$message.error('导出失败：' + (error.message || '请稍后重试'));
        this.loading = false;
      });
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}
</style>