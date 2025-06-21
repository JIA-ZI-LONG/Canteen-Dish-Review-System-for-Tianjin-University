<template>
  <div class="auth-container">
    <!-- 顶部滚动横幅 -->
    <div class="top-banner">
      <span class="scroll-text">欢迎来到天津大学美食点评平台 · Welcome to TJU Food Review Community · 欢迎来到天津大学美食点评平台</span>
    </div>
    <div class="auth-box">
      <div class="left-panel">
        <div class="brand-info">
          <img src="@/assets/logotju-white.png" alt="Logo" class="logo">
          <h1 class="platform-name">TJU-FOOD</h1>
          <p class="platform-slogan">天津大学美食点评社区</p>
        </div>
        <ul class="features-list">
          <li>· 海量真实用户点评</li>
          <li>· 高效食堂菜品查询</li>
          <li>· 活跃校园美食社区</li>
        </ul>
      </div>
      <div class="right-panel">
        <slot></slot>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AuthLayout'
}
</script>

<style scoped>
.auth-container {
  display: flex;
  position: relative; /* for overlay */
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: #f0f2f5;
  background-image: linear-gradient(rgba(0,0,0,0.45),rgba(0,0,0,0.45)), url('~@/assets/auth-bg.jpg');
  background-size: cover;
  background-position: center;
}

.auth-box {
  display: flex;
  width: 900px;
  height: 550px;
  border-radius: 12px;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.12);
  overflow: hidden;
}

.left-panel {
  width: 50%;
  background: linear-gradient(135deg, #409eff, #6e8efb);
  color: #fff;
  padding: 60px 40px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
}

.brand-info {
  text-align: center;
  margin-bottom: 20px;
}

.logo {
  width: 200px;
  height: auto;
  margin-bottom: 25px;
}

.platform-name {
  font-size: 40px;
  font-weight: 600;
  margin: 0;
}

.platform-slogan {
  font-size: 18px;
  opacity: 0.8;
  margin-top: 5px;
}

.features-list {
  list-style: none;
  padding: 0; /* 取消左缩进 */
  margin: 60px auto 0 40px; /* 向上并水平居中 */
  font-size: 18px;
  line-height: 2.2;
  width: 100%;
  max-width: 300px;
  align-self: center;
  box-sizing: border-box;
}

.right-panel {
  width: 55%;
  background: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 40px 30px;
  box-sizing: border-box;
}

.top-banner {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 40px;
  background-color: #409eff;
  color: #fff;
  font-size: 16px;
  text-align: center;
  line-height: 40px;
  overflow: hidden;
}

.scroll-text {
  white-space: nowrap;
  animation: scroll 20s linear infinite;
}

@keyframes scroll {
  0% {
    transform: translateX(0);
  }
  100% {
    transform: translateX(-100%);
  }
}

@media (max-width: 1024px) {
  .logo { width: 160px; margin-bottom:20px; }
  .platform-name { font-size:32px; }
  .platform-slogan { font-size:16px; }
}
@media (max-width: 768px) {
  .auth-box {
    flex-direction: column;
    width: 100%;
    height: auto;
    box-shadow: none;
  }
  .left-panel {
    width: 100%;
    flex-direction: column;
    justify-content: center;
    padding: 40px 20px;
  }
  .right-panel {
    width: 100%;
    padding: 30px 20px;
  }
}
</style>

<!-- 引入全局高级样式 -->
<style src="@/assets/auth-responsive.css"></style>