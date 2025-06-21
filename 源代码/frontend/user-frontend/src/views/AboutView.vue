<template>
  <div class="about-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <h1 class="page-title">关于我们</h1>
      <p class="page-subtitle">天津大学美食点评平台</p>
    </div>

    <div class="content-container">
      <div class="section">
        <h2>项目简介</h2>
        <p>
          天津大学美食点评平台是一个专为天津大学师生打造的美食分享与点评社区。
          我们致力于为校园内的美食爱好者提供一个交流、分享和发现美食的平台。
        </p>
      </div>

      <div class="section">
        <h2>主要功能</h2>
        <div class="features-grid">
          <div class="feature-card">
            <div class="feature-icon">🍽️</div>
            <h3>美食点评</h3>
            <p>分享您对校园美食的真实体验和评价</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">📝</div>
            <h3>美食博客</h3>
            <p>记录您的美食故事，分享烹饪心得</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">🏆</div>
            <h3>排行榜</h3>
            <p>发现校园内最受欢迎的美食和餐厅</p>
          </div>
          <div class="feature-card">
            <div class="feature-icon">👥</div>
            <h3>社区互动</h3>
            <p>与其他美食爱好者交流互动</p>
          </div>
        </div>
      </div>

      <div class="section">
        <h2>开发团队</h2>
        <div class="team-info">
          <div class="team-member">
            <h4>项目负责人</h4>
          </div>
        </div>
      </div>

      <div class="section">
        <h2>技术栈</h2>
        <div class="tech-stack">
          <div class="tech-category">
            <h4>前端技术</h4>
            <ul>
              <li>Vue.js 2.x</li>
              <li>Element UI</li>
              <li>Vue Router</li>
              <li>Vuex</li>
              <li>Axios</li>
            </ul>
          </div>
          <div class="tech-category">
            <h4>后端技术</h4>
            <ul>
              <li>Spring Boot</li>
              <li>MyBatis Plus</li>
              <li>MySQL</li>
              <li>Redis</li>
              <li>JWT</li>
            </ul>
          </div>
        </div>
      </div>

      <div class="section">
        <h2>联系我们</h2>
        <div class="contact-info">
          <p>如果您有任何问题或建议，欢迎通过以下方式联系我们：</p>
          <ul>
            <li>📧 邮箱：3023209091@tju.edu.cn</li>
            <li>🌐 项目地址：<a href="https://gitee.com/zjn79/TJUcomments" target="_blank">https://gitee.com/zjn79/TJUcomments</a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'AboutView',
  mounted() {
    document.title = '关于我们 - 天津大学美食点评平台'
  }
}
</script>

<style scoped>
.about-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}

.page-title {
  font-size: 28px;
  color: #303133;
  margin-bottom: 10px;
}

.page-subtitle {
  color: #909399;
  font-size: 14px;
  margin: 0;
}

.content-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0;
}

.section {
  margin-bottom: 40px;
  background: white;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
}

.section h2 {
  color: #333;
  font-size: 1.5rem;
  margin-bottom: 20px;
  text-align: center;
}

.section p {
  color: #666;
  line-height: 1.8;
  font-size: 1.1rem;
  text-align: center;
  max-width: 800px;
  margin: 0 auto;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 30px;
  margin-top: 40px;
}

.feature-card {
  background: white;
  padding: 30px;
  border-radius: 15px;
  text-align: center;
  box-shadow: 0 10px 30px rgba(0,0,0,0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.feature-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 20px 40px rgba(0,0,0,0.15);
}

.feature-icon {
  font-size: 3rem;
  margin-bottom: 20px;
}

.feature-card h3 {
  color: #333;
  font-size: 1.3rem;
  margin-bottom: 15px;
}

.feature-card p {
  color: #666;
  font-size: 1rem;
  line-height: 1.6;
}

.team-info {
  text-align: center;
  margin-top: 40px;
}

.team-member {
  background: #f8f9fa;
  padding: 30px;
  border-radius: 15px;
  display: inline-block;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.team-member h4 {
  color: #333;
  font-size: 1.2rem;
  margin-bottom: 15px;
}

.team-member p {
  color: #666;
  margin: 5px 0;
  text-align: center;
}

.tech-stack {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 40px;
  margin-top: 40px;
}

.tech-category {
  background: #f8f9fa;
  padding: 30px;
  border-radius: 15px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.tech-category h4 {
  color: #333;
  font-size: 1.3rem;
  margin-bottom: 20px;
  text-align: center;
}

.tech-category ul {
  list-style: none;
  padding: 0;
}

.tech-category li {
  color: #666;
  padding: 8px 0;
  border-bottom: 1px solid #eee;
  text-align: center;
}

.tech-category li:last-child {
  border-bottom: none;
}

.contact-info {
  background: #f8f9fa;
  padding: 30px;
  border-radius: 15px;
  margin-top: 40px;
  box-shadow: 0 5px 15px rgba(0,0,0,0.1);
}

.contact-info p {
  text-align: center;
  margin-bottom: 20px;
}

.contact-info ul {
  list-style: none;
  padding: 0;
  text-align: center;
}

.contact-info li {
  color: #666;
  margin: 10px 0;
  font-size: 1.1rem;
}

.contact-info a {
  color: #409eff;
  text-decoration: none;
}

.contact-info a:hover {
  text-decoration: underline;
}

@media (max-width: 768px) {
  .hero-title {
    font-size: 2rem;
  }
  
  .hero-subtitle {
    font-size: 1.2rem;
  }
  
  .content-container {
    padding: 40px 15px;
  }
  
  .features-grid {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  
  .tech-stack {
    grid-template-columns: 1fr;
    gap: 20px;
  }
}
</style>
