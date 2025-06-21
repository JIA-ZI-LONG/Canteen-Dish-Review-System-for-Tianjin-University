<template>
  <AuthLayout>
    <div class="form-wrapper">
      <h3 class="form-title">欢迎回来</h3>
      <p class="form-subtitle">使用您的ID或邮箱登录</p>
      <el-form ref="loginForm" :model="loginForm" :rules="rules" class="auth-form">
        <el-form-item prop="account">
          <el-input size="large" v-model="loginForm.account" placeholder="用户ID / 邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input size="large" type="password" v-model="loginForm.password" placeholder="密码" @keyup.enter.native="handleLogin"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button
              :loading="loading"
              size="large"
              type="primary"
              style="width:100%;"
              @click.native.prevent="handleLogin"
          >
            登 录
          </el-button>
        </el-form-item>
      </el-form>
      <div class="form-links">
        <el-link type="primary" @click="$router.push('/reset-password')">忘记密码？</el-link>
        <el-link type="primary" @click="$router.push('/register')">立即注册</el-link>
      </div>

      <div class="debug-info" v-if="debugMode">
        <h4>调试信息</h4>
        <p>当前用户状态: {{ this.$store.getters.isLoggedIn ? '已登录' : '未登录' }}</p>
        <p>Token: {{ this.$store.getters.token ? '已设置' : '未设置' }}</p>
        <p>用户信息: {{ JSON.stringify(this.$store.getters.currentUser) }}</p>
      </div>
    </div>

    <CaptchaDialog
        v-model="showCaptcha"
        @success="onCaptchaSuccess"
        @cancel="onCaptchaCancel"
    />
  </AuthLayout>
</template>

<script>
import AuthLayout from '@/components/AuthLayout.vue'
import CaptchaDialog from '@/components/CaptchaDialog.vue'
import AccountServices from "@/service/AccountServices";

export default {
  name: "LoginView",
  components: {
    AuthLayout,
    CaptchaDialog
  },
  data() {
    return {
      loading: false,
      showCaptcha: false,
      captchaData: null,
      debugMode: true, // 开启调试模式
      loginForm: {
        account: '',
        password: ''
      },
      rules: {
        account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    };
  },
  methods: {
    // 登录处理 - 第一步：验证表单并显示验证码
    handleLogin() {
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
          // 表单验证通过，显示验证码弹窗
          this.showCaptcha = true
        } else {
          this.$message.warning('请完成所有必填项')
        }
      });
    },

    // 验证码验证成功回调
    onCaptchaSuccess(captchaData) {
      this.captchaData = captchaData
      this.performLogin()
    },

    // 验证码取消回调
    onCaptchaCancel() {
      this.captchaData = null
      this.$message.info('登录已取消')
    },

    // 执行实际登录 - 第二步：发送完整登录请求
    performLogin() {
      if (!this.captchaData) {
        this.$message.error('验证码数据异常')
        return
      }

      this.loading = true

      const loginData = {
        account: this.loginForm.account,
        password: this.loginForm.password,
        x: this.captchaData.x,
        uuid: this.captchaData.uuid
      }

      AccountServices.Login(loginData)
          .then(response => {
            if (response.success === true && response.data && response.data.token) {
              const userData = {
                token: response.data.token,
                id: response.data.id,
                nickname: response.data.nickname,
                icon: response.data.icon
              };

              // dispatch返回一个Promise，可以继续链式调用
              return this.$store.dispatch('login', userData);
            } else {
              // 如果登录不成功，则创建一个失败的Promise以进入catch块
              return Promise.reject(new Error(response.errorMsg || '登录失败，请检查账号、密码或验证码'));
            }
          })
          .then(() => {
            // dispatch成功后执行
            this.$message.success('登录成功！');
            setTimeout(() => {
              this.$router.push('/main/home');
            }, 500);
          })
          .catch(error => {
            // 捕获所有错误，包括登录失败和网络异常
            console.error('登录失败或请求异常:', error);
            this.$message.error(error.message || '登录请求异常，请重试');
            this.resetCaptcha();
          })
          .finally(() => {
            this.loading = false;
          });
    },

    // 重置验证码状态
    resetCaptcha() {
      this.captchaData = null
      this.showCaptcha = false
    }
  }
}
</script>

<style scoped>
.form-wrapper {
  width: 320px;
}
.form-title {
  font-size: 26px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}
.form-subtitle {
  color: #999;
  margin-bottom: 30px;
}
.auth-form .el-form-item {
  margin-bottom: 25px;
}
.form-links {
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
}
.debug-info {
  margin-top: 20px;
  padding: 15px;
  background: #f5f7fa;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  font-size: 12px;
}
.debug-info h4 {
  margin: 0 0 10px 0;
  color: #409eff;
}
.debug-info p {
  margin: 5px 0;
  word-break: break-all;
}
</style>