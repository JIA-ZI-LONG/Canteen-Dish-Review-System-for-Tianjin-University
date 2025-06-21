<template>
  <AuthLayout>
    <div class="form-wrapper">
      <h3 class="form-title">重置密码</h3>
      <p class="form-subtitle">通过邮箱验证来设置您的新密码</p>

      <el-form :model="resetForm" :rules="rules" ref="resetForm" class="auth-form" label-position="right" label-width="100px">
        <el-form-item label="注册邮箱" prop="email">
          <el-input size="large" v-model="resetForm.email" placeholder="请输入注册时使用的邮箱"></el-input>
        </el-form-item>

        <el-form-item label="邮箱验证码" prop="code">
          <div class="email-code-wrapper">
            <el-input size="large" v-model="resetForm.code" placeholder="请输入邮箱验证码"></el-input>
            <el-button type="primary" size="large" @click="sendVerificationCode" :disabled="codeButtonDisabled">
              {{ codeButtonText }}
            </el-button>
          </div>
        </el-form-item>

        <el-form-item label="新密码" prop="newPassword">
          <el-input size="large" v-model="resetForm.newPassword" type="password" show-password placeholder="请输入新密码"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button :loading="loading" size="large" type="primary" style="width: 100%; margin-top: 10px;" @click="submitForm">确认重置</el-button>
        </el-form-item>
      </el-form>

      <div class="form-links">
        <el-link type="primary" @click="$router.push('/login')">返回登录</el-link>
      </div>
    </div>
  </AuthLayout>
</template>

<script>
import AuthLayout from '@/components/AuthLayout.vue'
import AccountServices from '@/service/AccountServices'

export default {
  name: 'ResetPasswordView',
  components: { AuthLayout },
  data() {
    return {
      loading: false,
      resetForm: {
        email: '',
        code: '',
        newPassword: '',
      },
      rules: {
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        code: [{ required: true, message: '请输入邮箱验证码', trigger: 'blur' }],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
      },
      codeButtonText: '获取验证码',
      codeButtonDisabled: false,
      countdown: 60,
      timer: null
    }
  },
  methods: {
    sendVerificationCode() {
      // 先对邮箱格式进行校验
      this.$refs.resetForm.validateField('email', (error) => {
        if (!error) {
          this.startCountdown();
          AccountServices.SendVerificationCode({
            email: this.resetForm.email,
            type: 2 // 关键：忘记密码类型为 2
          }).then(res => {
            if (res.success) {
              this.$message.success('验证码已发送，请查收您的邮箱');
            } else {
              this.$message.error(res.errorMsg || '发送失败，请确认邮箱是否已注册');
              this.stopCountdown();
            }
          }).catch(() => {
            this.$message.error('发送请求异常');
            this.stopCountdown();
          });
        } else {
          this.$message.warning('请输入有效的邮箱地址');
        }
      });
    },
    submitForm() {
      this.$refs.resetForm.validate((valid) => {
        if (valid) {
          this.loading = true;
          AccountServices.ResetPassword(this.resetForm).then(res => {
            if (res.success) {
              this.$message.success('密码重置成功！即将跳转到登录页');
              setTimeout(() => this.$router.push('/login'), 1500);
            } else {
              this.$message.error(res.errorMsg || '重置失败');
            }
          }).catch(() => {
            this.$message.error('重置请求异常');
          }).finally(() => {
            this.loading = false;
          });
        }
      });
    },
    startCountdown() {
      this.codeButtonDisabled = true;
      this.countdown = 60;
      this.codeButtonText = `${this.countdown}秒后重试`;
      this.timer = setInterval(() => {
        if (this.countdown > 1) {
          this.countdown--;
          this.codeButtonText = `${this.countdown}秒后重试`;
        } else {
          this.stopCountdown();
        }
      }, 1000);
    },
    stopCountdown() {
      clearInterval(this.timer);
      this.codeButtonText = '获取验证码';
      this.codeButtonDisabled = false;
      this.countdown = 60;
    },
  },
  beforeDestroy() {
    // 组件销毁前清除定时器，防止内存泄漏
    if (this.timer) {
      clearInterval(this.timer);
    }
  }
}
</script>

<style scoped>
/* 样式与注册页保持一致 */
.form-wrapper {
  width: 360px;
}
.form-title {
  font-size: 26px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
  text-align: center;
}
.form-subtitle {
  color: #999;
  margin-bottom: 30px;
  font-size: 14px;
  text-align: center;
}
.auth-form .el-form-item {
  margin-bottom: 22px;
}
.email-code-wrapper {
  display: flex;
  gap: 10px;
}
.form-links {
  margin-top: 15px;
  text-align: center;
}
</style>