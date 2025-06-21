<!-- 主页组件 - Vue2版本，集成浮动博客发布按钮 -->
<template>
  <div class="home-view">
    <!-- 现有的主页内容 -->
    <div class="main-content">
      <!-- 这里是您现有的主页内容 -->
      <!-- 博客列表、食堂信息、排行榜等 -->
      
      <!-- 示例内容区域 -->
      <div class="content-section">
        <h2>🍽️ TjuFood 美食分享平台</h2>
        <p>发现校园美食，分享美好时光</p>
        
        <!-- 博客列表区域 -->
        <div class="blog-list-section">
          <h3>最新博客</h3>
          <div class="blog-list" v-loading="loading">
            <div 
              v-for="blog in blogList" 
              :key="blog.id" 
              class="blog-card"
              @click="viewBlog(blog.id)"
            >
              <div class="blog-header">
                <div class="author-info">
                  <el-avatar :src="blog.authorIcon" :size="32">
                    {{ blog.authorName && blog.authorName.charAt(0) }}
                  </el-avatar>
                  <div class="author-details">
                    <span class="author-name">{{ blog.authorName }}</span>
                    <span class="publish-time">{{ formatTime(blog.createTime) }}</span>
                  </div>
                </div>
              </div>

              <div class="blog-content">
                <h4 class="blog-title">{{ blog.title }}</h4>
                <div class="blog-excerpt" v-html="getExcerpt(blog.content)"></div>
              </div>

              <div class="blog-footer">
                <div class="blog-stats">
                  <span class="stat-item">
                    <i class="el-icon-view"></i>
                    {{ blog.views || 0 }}
                  </span>
                  <span class="stat-item" :class="{ liked: blog.isLiked }">
                    <i class="el-icon-heart"></i>
                    {{ blog.liked || 0 }}
                  </span>
                  <span class="stat-item">
                    <i class="el-icon-chat-dot-round"></i>
                    {{ blog.comments || 0 }}
                  </span>
                </div>
              </div>
            </div>

            <!-- 空状态 -->
            <el-empty v-if="!loading && blogList.length === 0" description="暂无博客内容">
              <el-button type="primary" @click="showBlogEditor">
                发布第一篇博客
              </el-button>
            </el-empty>
          </div>
        </div>
      </div>
    </div>

    <!-- 浮动博客发布按钮 -->
    <FloatingBlogButton @blog-published="handleBlogPublished" />
  </div>
</template>

<script>
import FloatingBlogButton from '@/components/FloatingBlogButton.vue'
import BlogServices from '@/service/BlogServices'

export default {
  name: 'HomeView',
  components: {
    FloatingBlogButton
  },
  data() {
    return {
      loading: false,
      blogList: []
    }
  },
  methods: {
    // 博客发布成功后的处理
    handleBlogPublished(blogData) {
      console.log('博客发布成功:', blogData)
      this.$message.success('博客发布成功！')
      // 刷新博客列表
      this.loadBlogList()
    },

    // 加载博客列表
    async loadBlogList() {
      try {
        this.loading = true
        const response = await BlogServices.getBlogList({
          current: 1,
          size: 10,
          sortBy: 'time'
        })

        if (response.success) {
          this.blogList = response.data || []
        } else {
          this.$message.error(response.errorMsg || '获取博客列表失败')
        }
      } catch (error) {
        console.error('获取博客列表失败:', error)
        this.$message.error('获取博客列表失败')
      } finally {
        this.loading = false
      }
    },

    // 查看博客详情
    viewBlog(blogId) {
      // 跳转到博客详情页
      this.$router.push(`/blog/${blogId}`)
    },

    // 显示博客编辑器
    showBlogEditor() {
      // 这个方法可以被其他组件调用来显示编辑器
      // FloatingBlogButton组件会自动处理显示逻辑
    },

    // 格式化时间
    formatTime(time) {
      if (!time) return ''
      try {
        const date = new Date(time)
        const now = new Date()
        const diff = now - date
        const days = Math.floor(diff / (1000 * 60 * 60 * 24))
        
        if (days === 0) {
          const hours = Math.floor(diff / (1000 * 60 * 60))
          if (hours === 0) {
            const minutes = Math.floor(diff / (1000 * 60))
            return minutes <= 0 ? '刚刚' : `${minutes}分钟前`
          }
          return `${hours}小时前`
        } else if (days === 1) {
          return '昨天'
        } else if (days < 7) {
          return `${days}天前`
        } else {
          return date.toLocaleDateString()
        }
      } catch {
        return time
      }
    },

    // 获取博客摘要
    getExcerpt(content) {
      if (!content) return ''
      // 移除HTML标签并截取前100个字符
      const text = content.replace(/<[^>]*>/g, '')
      return text.length > 100 ? text.substring(0, 100) + '...' : text
    }
  },

  mounted() {
    // 组件挂载后加载博客列表
    this.loadBlogList()
  }
}
</script>

<style scoped>
.home-view {
  min-height: 100vh;
  background-color: #f5f7fa;
}

.main-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.content-section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 20px;
}

.content-section h2 {
  text-align: center;
  color: #303133;
  margin-bottom: 10px;
}

.content-section p {
  text-align: center;
  color: #606266;
  margin-bottom: 30px;
}

.blog-list-section h3 {
  color: #303133;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #ebeef5;
}

.blog-card {
  border: 1px solid #ebeef5;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 15px;
  cursor: pointer;
  transition: all 0.3s;
}

.blog-card:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-2px);
}

.blog-header {
  margin-bottom: 15px;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 10px;
}

.author-details {
  display: flex;
  flex-direction: column;
}

.author-name {
  font-weight: 500;
  color: #303133;
  font-size: 14px;
}

.publish-time {
  font-size: 12px;
  color: #909399;
}

.blog-title {
  margin: 0 0 10px 0;
  color: #303133;
  font-size: 16px;
  font-weight: 600;
}

.blog-excerpt {
  color: #606266;
  line-height: 1.6;
  margin-bottom: 15px;
  font-size: 14px;
}

.blog-footer {
  border-top: 1px solid #f0f0f0;
  padding-top: 15px;
}

.blog-stats {
  display: flex;
  gap: 20px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 4px;
  color: #909399;
  font-size: 14px;
}

.stat-item i {
  font-size: 16px;
}

.stat-item.liked {
  color: #f56c6c;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .main-content {
    padding: 10px;
  }
  
  .content-section {
    padding: 15px;
  }
  
  .blog-card {
    padding: 15px;
  }
}
</style>
