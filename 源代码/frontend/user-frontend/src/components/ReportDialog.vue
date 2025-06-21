<template>
  <el-dialog
      title="内容举报"
      :visible.sync="show"
      width="500px"
      @close="resetForm"
  >
    <el-form :model="form" :rules="rules" ref="reportForm" label-width="80px">
      <el-form-item label="举报类型" prop="reason">
        <el-radio-group v-model="form.reason">
          <el-radio label="违法违规"></el-radio>
          <el-radio label="低俗色情"></el-radio>
          <el-radio label="人身攻击"></el-radio>
          <el-radio label="垃圾广告"></el-radio>
          <el-radio label="其他"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="详细说明" prop="description">
        <el-input
            type="textarea"
            v-model="form.description"
            :rows="4"
            placeholder="请详细描述举报原因（选填）"
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="show = false">取 消</el-button>
      <el-button type="primary" @click="submitReport" :loading="loading">提 交</el-button>
    </div>
  </el-dialog>
</template>

<script>
// 预留举报服务的引入位置
import ReportServices from '@/service/ReportServices'

export default {
  name: 'ReportDialog',
  props: {
    // 父组件通过 :visible.sync 传递
    visible: {
      type: Boolean,
      default: false
    },
    // 被举报的对象信息
    targetInfo: {
      type: Object,
      default: () => ({ id: null, type: null })
    }
  },
  data() {
    return {
      loading: false,
      form: {
        reason: '违法违规',
        description: ''
      },
      rules: {
        reason: [
          { required: true, message: '请选择举报类型', trigger: 'change' }
        ]
      }
    };
  },
  computed: {
    // 【核心修复】使用计算属性的 get/set 避免直接修改 prop
    show: {
      get() {
        return this.visible;
      },
      set(value) {
        // 当内部状态改变时，通过 emit 'update:visible' 事件通知父组件
        this.$emit('update:visible', value);
      }
    }
  },
  methods: {
    submitReport() {
      this.$refs.reportForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          const reportData = {
            targetId: this.targetInfo.id,
            targetType: this.targetInfo.type,
            reason: this.form.reason,
            description: this.form.description
          };
          console.log('提交举报数据:', reportData);

          // 调用后端举报 API
          ReportServices.submitReport(reportData)
            .then(res => {
              const id = res?.data?.id ?? res?.id ?? null;
              this.$message.success('举报已提交，感谢您的反馈！');
              this.$emit('report-submitted', id);
              this.show = false; // 关闭对话框
            })
            .catch(err => {
              console.error('举报提交失败:', err);
              this.$message.error(err?.message || '提交失败，请稍后再试');
            })
            .finally(() => {
              this.loading = false;
            });
        }
      });
    },
    resetForm() {
      this.$refs.reportForm.resetFields();
    }
  }
};
</script>

<style scoped>
.dialog-footer {
  text-align: right;
}
</style>