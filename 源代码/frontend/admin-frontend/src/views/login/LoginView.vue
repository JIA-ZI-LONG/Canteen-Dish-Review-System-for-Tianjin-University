<template>
  <div class="login-container">
    <div class="login-left">
      <div class="login-left-logo">
        <img src="@/assets/imgs/logo.png" alt="logo" />
        <span>TjuFood 后台管理管理系统</span>
      </div>
      <div class="login-left-content">
        <img src="@/assets/svgs/login-box-bg.svg" alt="welcome" class="welcome-img" />
        <div class="welcome-title">欢迎使用</div>
        <div class="welcome-message">一个高效、便捷的校园美食管理平台</div>
      </div>
    </div>

    <div class="login-right">
      <div class="login-form-wrapper">
        <h2 class="form-title">管理员登录</h2>
        <el-form
            ref="loginForm"
            :model="loginData"
            :rules="loginRules"
            class="login-form-body"
            label-position="top"
            size="large"
        >
          <el-form-item prop="username">
            <el-input
                v-model="loginData.username"
                placeholder="管理员账号"
                prefix-icon="el-icon-user"
                @keyup.enter.native="handleLogin"
            />
          </el-form-item>

          <el-form-item prop="password">
            <el-input
                v-model="loginData.password"
                type="password"
                placeholder="密码"
                prefix-icon="el-icon-lock"
                show-password
                @keyup.enter.native="handleLogin"
            />
          </el-form-item>

          <el-form-item>
            <el-button
                :loading="loading"
                type="primary"
                style="width: 100%;"
                @click.native.prevent="handleLogin"
            >
              登 录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      loading: false,
      loginData: {
        username: '',
        password: '',
      },
      loginRules: {
        username: [{ required: true, message: '管理员账号不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
      },
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  methods: {
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          this.$store.dispatch('user/login', this.loginData)
              .then(() => {
                this.$router.push({ path: this.redirect || '/' });
                this.loading = false;
              })
              .catch((error) => {
                console.error('登录失败:', error);
                this.$message.error('登录失败: ' + (error.message || '请检查账号和密码'));
                this.loading = false;
              });
        }
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.login-container {
  display: flex;
  width: 100%;
  height: 100vh;
  overflow: hidden;
}

.login-left {
  flex: 1;
  background-color: #304156;
  padding: 30px;
  position: relative;
  display: flex;
  flex-direction: column;
  background-image: url('~@/assets/svgs/login-bg.svg');
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  color: white;
}

.login-left-logo {
  display: flex;
  align-items: center;
}

.login-left-logo img {
  width: 150px; /* 进一步左右拉长 */
  height: 80px;
  margin-right: 12px;
  object-fit: contain; /* 保持图像比例 */
  transition: all 0.3s ease;
}

.login-left-logo span {
  font-size: 24px; /* 与放大后的Logo保持协调 */
  font-weight: 600;
  white-space: nowrap;
}

/* 隐藏整块中部内容 */
.login-left-content {
  display: none;
}

.welcome-img {
  width: 350px;
  margin-bottom: 20px;
}

/* 隐藏中部欢迎文字 */
.welcome-title, .welcome-message {
  display: none;
}

.welcome-title {
  font-size: 32px;
  font-weight: bold;
}


.login-right {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #f5f7fa 0%, #e6e9f0 100%);
  
  /* 添加微妙的网格纹理 */
  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-image: 
      linear-gradient(rgba(0, 0, 0, 0.02) 1px, transparent 1px),
      linear-gradient(90deg, rgba(0, 0, 0, 0.02) 1px, transparent 1px);
    background-size: 20px 20px;
    opacity: 0.8;
    z-index: 0;
  }
  
  /* 添加装饰性元素 */
  &::after {
    content: '';
    position: absolute;
    width: 400px;
    height: 400px;
    border-radius: 50%;
    background: radial-gradient(circle, rgba(64, 158, 255, 0.1) 0%, rgba(64, 158, 255, 0) 70%);
    top: -200px;
    right: -100px;
    z-index: 0;
  }
}

.login-form-wrapper {
  width: 380px;
  background-color: rgba(255, 255, 255, 0.95);
  padding: 40px 48px 32px;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
  position: relative;
  z-index: 1;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
  border: 1px solid rgba(255, 255, 255, 0.5);
  transition: all 0.3s ease;
  
  &:hover {
    box-shadow: 0 15px 35px rgba(0, 0, 0, 0.12);
    transform: translateY(-2px);
  }
}

.form-title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 24px;
  color: #304156;
}

.login-form-body .el-input__inner {
  height: 50px;
  border-radius: 4px;
}

/* 添加登录按钮的悬停效果 */
.login-form-body .el-button {
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
  }
  
  &:active {
    transform: translateY(1px);
  }
  height: 50px;
  font-size: 16px;
  border-radius: 4px;
  margin-top: 10px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-form-wrapper {
    width: 90%;
    padding: 32px 24px;
  }
  .login-container {
    flex-direction: column;
  }

  .login-left, .login-right {
    flex: none;
    width: 100%;
  }

  .login-left {
    height: 30vh;
    padding: 20px;
  }

  .welcome-img {
    width: 200px;
  }

  .login-right {
    height: 70vh;
  }

  .login-form-wrapper {
    width: 90%;
    max-width: 350px;
  }
}
</style>