<template>
  <div class="app-container">
    <el-card>
      <div slot="header"><span>数据库数据导出</span></div>
      <p>点击按钮将导出数据库备份。此过程可能需要一些时间，请耐心等待。</p>
      <el-button
          type="danger"
          @click="handleExport"
          :loading="loading">
        {{ loading ? '正在导出...' : '立即导出' }}
      </el-button>
    </el-card>
  </div>
</template>

<script>
import request from '@/utils/request'
import { downloadFile } from '@/utils/download' // 引入下载工具

export default {
  name: 'ExportDb',
  data() {
    return {
      loading: false
    }
  },
  methods: {
    handleExport() {
      this.loading = true
      this.$confirm('确认要立即备份并导出数据库吗？', '提示', {
        type: 'warning'
      }).then(() => {
        request({
          url: '/admin/toolbox/export-db',
          method: 'post',
          timeout: 60000 // 将超时时间延长到60秒
        }).then(fileUrl => {
          this.$message.success('数据库备份文件已生成，开始下载！');
          downloadFile(fileUrl); // 调用下载方法
          this.loading = false;
        }).catch(error => {
          this.$message.error('导出失败：' + (error.message || '请稍后重试'));
          this.loading = false;
        });
      }).catch(() => {
        this.$message.info('已取消导出');
        this.loading = false;
      });
    }
  }
}
</script>