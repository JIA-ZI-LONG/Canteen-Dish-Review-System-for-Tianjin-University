<template>
  <AuthLayout>
    <div class="form-wrapper">
      <div class="form-header">
        <h3 class="form-title">创建您的账户</h3>
        <p class="form-subtitle">加入我们，分享美食之旅</p>
      </div>
      
      <div class="form-content">
        <el-form :model="registerForm" :rules="rules" ref="registerForm" class="auth-form" label-position="right" label-width="100px">
          <!-- 昵称 -->
          <el-form-item label="昵称" prop="nickname">
            <el-input size="large" v-model="registerForm.nickname" placeholder="请输入您的昵称"></el-input>
          </el-form-item>

          <!-- 校区选择 -->
          <el-form-item label="所属校区" prop="campus">
            <el-select v-model="registerForm.campus" placeholder="请选择校区" style="width: 100%" size="large">
              <el-option
                  v-for="campus in campusOptions"
                  :key="campus.value"
                  :label="campus.label"
                  :value="campus.value">
              </el-option>
            </el-select>
          </el-form-item>

          <!-- 邮箱 -->
          <el-form-item label="邮箱" prop="email">
            <el-input size="large" v-model="registerForm.email" placeholder="请输入您的邮箱"></el-input>
          </el-form-item>

          <!-- 密码 -->
          <el-form-item label="密码" prop="password">
            <el-input size="large" v-model="registerForm.password" type="password" show-password placeholder="请输入至少6位的密码"></el-input>
          </el-form-item>

          <!-- 确认密码 -->
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input size="large" v-model="registerForm.confirmPassword" type="password" show-password placeholder="请再次输入密码"></el-input>
          </el-form-item>

          <el-form-item label="图形验证码" prop="captchaInput">
            <div class="captcha-wrapper">
              <el-input size="large" v-model="registerForm.captchaInput" placeholder="请输入验证码"></el-input>
              <canvas ref="captchaCanvas" @click="generateCaptcha" class="captcha-canvas" width="120" height="40"></canvas>
            </div>
          </el-form-item>

          <el-form-item label="邮箱验证码" prop="code">
            <div class="email-code-wrapper">
              <el-input size="large" v-model="registerForm.code" placeholder="请输入邮箱验证码"></el-input>
              <el-button type="primary" size="large" @click="sendVerificationCode" :disabled="codeButtonDisabled">
                {{ codeButtonText }}
              </el-button>
            </div>
          </el-form-item>

          <el-form-item>
            <el-button :loading="loading" size="large" type="primary" style="width: 100%; margin-top: 10px;" @click="submitForm('registerForm')">立即注册</el-button>
          </el-form-item>
        </el-form>
      </div>
      
      <div class="form-links">
        <el-link type="primary" @click="$router.push('/login')">已有账号？返回登录</el-link>
      </div>
    </div>
  </AuthLayout>
</template>
<script>
import AuthLayout from '@/components/AuthLayout.vue'
import AccountServices from '@/service/AccountServices'

export default {
  name: 'RegisterView',
  components: {
    AuthLayout
  },
  data() {
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.registerForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    };
    return {
      // 校区选项
      campusOptions: [
        { value: '卫津路校区', label: '卫津路校区' },
        { value: '北洋园校区', label: '北洋园校区' },
        { value: '滨海工业研究院', label: '滨海工业研究院' },
        { value: '深圳学院', label: '深圳学院' },
        { value: '福州国际校区', label: '福州国际校区' }
      ],
      loading: false,
      registerForm: {
        nickname: '',
        campus: '',
        email: '',
        password: '',
        confirmPassword: '',
        code: '',
        captchaInput: '',
      },
      captchaCorrectCode: '',
      rules: {
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
        ],
        campus: [
          { required: true, message: '请选择校区', trigger: 'change' }
        ],
        email: [
          { required: true, message: '请输入邮箱地址', trigger: 'blur' },
          { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, validator: validatePass2, trigger: 'blur' }
        ],
        code: [
          { required: true, message: '请输入邮箱验证码', trigger: 'blur' }
        ],
        captchaInput: [
          { required: true, message: '请输入图形验证码', trigger: 'blur' }
        ],
      },
      codeButtonText: '获取验证码',
      codeButtonDisabled: false,
      countdown: 60,
      timer: null
    }
  },
  mounted() {
    this.generateCaptcha();
  },
  methods: {
    generateCaptcha() {
      const canvas = this.$refs.captchaCanvas;
      if (!canvas) return;
      const ctx = canvas.getContext('2d');
      const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789';
      let captchaCode = '';

      ctx.clearRect(0, 0, canvas.width, canvas.height);
      ctx.fillStyle = '#f9f9f9';
      ctx.fillRect(0, 0, canvas.width, canvas.height);

      for (let i = 0; i < 4; i++) {
        const char = chars.charAt(Math.floor(Math.random() * chars.length));
        captchaCode += char;
        ctx.font = `bold ${22 + Math.random() * 5}px Arial`;
        ctx.fillStyle = this.getRandomColor();
        ctx.textBaseline = 'middle';
        const rotation = (Math.random() - 0.5) * 0.5;
        const x = 20 + i * 25;
        const y = canvas.height / 2 + (Math.random() - 0.5) * 5;
        ctx.save();
        ctx.translate(x, y);
        ctx.rotate(rotation);
        ctx.fillText(char, 0, 0);
        ctx.restore();
      }

      for (let i = 0; i < 5; i++) {
        ctx.strokeStyle = this.getRandomColor();
        ctx.beginPath();
        ctx.moveTo(Math.random() * canvas.width, Math.random() * canvas.height);
        ctx.lineTo(Math.random() * canvas.width, Math.random() * canvas.height);
        ctx.stroke();
      }

      this.captchaCorrectCode = captchaCode;
    },
    getRandomColor() {
      const r = Math.floor(Math.random() * 180);
      const g = Math.floor(Math.random() * 180);
      const b = Math.floor(Math.random() * 180);
      return `rgb(${r},${g},${b})`;
    },
    sendVerificationCode() {
      if (!this.registerForm.captchaInput || this.registerForm.captchaInput.toLowerCase() !== this.captchaCorrectCode.toLowerCase()) {
        this.$message.error('图形验证码错误');
        this.generateCaptcha();
        return;
      }

      // validateField 回调的参数为错误信息（undefined/"" 表示校验通过）
      this.$refs.registerForm.validateField('email', (errorMessage) => {
        if (!errorMessage) {
          this.startCountdown();
          AccountServices.SendVerificationCode({
            email: this.registerForm.email,
            type: 0 // 注册类型
          }).then(response => {
            if (response.success) {
              this.$message.success('验证码已发送到您的邮箱');
            } else {
              this.$message.error(response.errorMsg || '发送失败，该邮箱可能已被注册');
              this.stopCountdown();
            }
          }).catch(() => {
            this.$message.error('发送请求异常');
            this.stopCountdown();
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
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loading = true;
          AccountServices.Register(this.registerForm)
              .then(response => {
                if (response.success) {
                  this.$message.success('注册成功！即将跳转到登录页');
                  setTimeout(() => this.$router.push('/login'), 1500);
                } else {
                  this.$message.error(response.errorMsg || '注册失败');
                }
              }).catch(() => {
            this.$message.error('注册请求异常');
          }).finally(() => {
            this.loading = false;
          });
        }
      });
    },
  },
  beforeDestroy() {
    if (this.timer) {
      clearInterval(this.timer);
    }
  }
}
</script>

<style scoped>
.form-wrapper {
  width: 400px; /* 调整表单宽度以适应标签 */
  display: flex;
  flex-direction: column;
  height: 100%;
}

.form-header {
  flex-shrink: 0;
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
  margin-bottom: 20px;
  font-size: 14px;
  text-align: center;
}

.form-content {
  flex: 1;
  overflow-y: auto;
  padding-right: 10px;
  margin-bottom: 15px;
  scrollbar-width: thin;
  scrollbar-color: #c1c1c1 #f5f5f5;
}

/* 自定义滚动条样式 */
.form-content::-webkit-scrollbar {
  width: 6px;
}

.form-content::-webkit-scrollbar-track {
  background: #f5f5f5;
  border-radius: 3px;
}

.form-content::-webkit-scrollbar-thumb {
  background-color: #c1c1c1;
  border-radius: 3px;
}

.form-content::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}

.auth-form {
  padding: 10px 0;
}

.auth-form .el-form-item {
  margin-bottom: 18px;
}

.captcha-wrapper, .email-code-wrapper {
  display: flex;
  align-items: center;
  gap: 10px;
}

.captcha-canvas {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
  height: 40px; /* 与输入框高度一致 */
  flex-shrink: 0; /* 防止图片被压缩 */
}

.form-links {
  margin-top: 15px;
  text-align: center;
  flex-shrink: 0;
}
</style>