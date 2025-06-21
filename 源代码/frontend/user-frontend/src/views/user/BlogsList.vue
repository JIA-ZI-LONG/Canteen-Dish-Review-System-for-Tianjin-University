<template>
  <div class="blogs-list-container">
    <el-card class="filter-card">
      <div class="filter-row">
        <div class="filter-group">
          <span class="filter-label">排序：</span>
          <el-select v-model="sortBy" @change="handleSearch" placeholder="请选择排序方式" clearable class="filter-select">
            <el-option label="最新发布" value="time"></el-option>
            <el-option label="最多点赞" value="liked"></el-option>
            <el-option label="最多评论" value="comments"></el-option>
            <el-option label="最多收藏" value="favorites"></el-option>
          </el-select>
        </div>

        <div class="filter-group">
          <el-input
              v-model="searchQuery"
              placeholder="搜索博客"
              prefix-icon="el-icon-search"
              @keyup.enter.native="handleSearch"
              clearable
              class="search-input">
          </el-input>
        </div>
      </div>
    </el-card>

    <el-row :gutter="20" class="blog-cards">
      <el-col :span="6" v-for="blog in blogsModel" :key="blog.id">
        <el-card :body-style="{ padding: '0px' }" class="single-blog">
          <div class="blog-header">
            <el-avatar :size="40" :src="blog.author.avatar"></el-avatar>
            <div class="blog-info">
              <div class="author-name">{{ blog.author.name }}</div>
              <div class="blog-date">{{ blog.date }}</div>
            </div>
          </div>
          <img :src="blog.imageUrl" class="image">
          <div class="blog-content">
            <h3 class="blog-title">{{ blog.title }}</h3>
            <div class="blog-rating">
              <el-rate
                  v-model="blog.rating"
                  disabled
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                  show-score>
              </el-rate>
              <span class="price">¥{{ blog.price }}/人</span>
            </div>
            <div class="blog-tags">
              <el-tag v-for="tag in blog.tags" :key="tag" size="small" type="info">{{ tag }}</el-tag>
            </div>
            <div class="blog-footer">
              <el-button type="text" class="button" @click="showBlogDetail(blog)">查看详情</el-button>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <div class="pagination-container">
      <el-pagination
          @current-change="handlePageChange"
          :current-page.sync="currentPage"
          :page-size="pageSize"
          layout="prev, pager, next, jumper"
          :total="total"
          background>
      </el-pagination>
    </div>

    <el-dialog
        :title="currentBlog.title"
        :visible.sync="dialogVisible"
        width="60%"
        class="blog-dialog">
      <div class="blog-detail-content">
        <div class="blog-detail-header">
          <el-avatar :size="50" :src="currentBlog.author?.avatar"></el-avatar>
          <div class="blog-detail-info">
            <div class="blog-detail-author">{{ currentBlog.author?.name }}</div>
            <div class="blog-detail-date">{{ currentBlog.date }}</div>
          </div>
        </div>
        <div class="blog-detail-image">
          <img :src="currentBlog.imageUrl" alt="博客图片">
        </div>
        <div class="blog-detail-rating">
          <el-rate
              v-model="currentBlog.rating"
              disabled
              :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
              show-score>
          </el-rate>
          <span class="blog-detail-price">¥{{ currentBlog.price }}/人</span>
        </div>
        <div class="blog-detail-tags">
          <el-tag v-for="tag in currentBlog.tags" :key="tag" size="small" type="info">{{ tag }}</el-tag>
        </div>
        <div class="blog-detail-text">
          {{ currentBlog.content || '暂无详细内容' }}
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import BlogServices from '@/service/BlogServices'

export default {
  data() {
    return {
      blogs: [],
      currentPage: 1,
      pageSize: 12,
      total: 0,
      searchQuery: '',
      sortBy: 'time',
      dialogVisible: false,
      currentBlog: {},
      loading: false
    }
  },
  computed: {
    blogsModel() {
      return (Array.isArray(this.blogs) ? this.blogs : [])
          .map(blog => ({
            id: blog.id,
            title: blog.title,
            content: blog.content,
            author: {
              name: blog.authorName,
              avatar: blog.authorIcon
            },
            date: this.formatDate(blog.createTime),
            imageUrl: `https://via.placeholder.com/300x200?text=${encodeURIComponent(blog.title)}`,
            rating: (blog.liked || 0) / 100, // 将点赞数转换为评分
            price: Math.floor(Math.random() * 50) + 10, // 随机价格
            tags: ['美食', '推荐'] // 默认标签
          }))
    }
  },
  methods: {
    // 加载博客列表
    loadBlogs() {
      this.loading = true
      const params = {
        current: this.currentPage,
        size: this.pageSize,
        sortBy: this.sortBy,
        keyword: this.searchQuery
      }

      BlogServices.GetBlogs(params)
          .then(response => {
            if (response.success && response.data) {
              // 适配后端返回格式：data直接是数组，total在同级
              this.blogs = response.data || []
              this.total = response.total || 0
            } else {
              this.$message.error(response.errorMsg || '获取博客列表失败')
            }
          })
          .catch(error => {
            console.error('加载博客列表失败:', error)
            this.$message.error('加载博客列表失败')
          })
          .finally(() => {
            this.loading = false
          })
    },

    // 处理搜索
    handleSearch() {
      this.currentPage = 1
      this.loadBlogs()
    },

    // 处理排序变化
    handleSortChange(val) {
      this.sortBy = val
      this.loadBlogs()
    },

    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page
      this.loadBlogs()
      // 滚动到顶部
      window.scrollTo({ top: 0, behavior: 'smooth' })
    },
    showBlogDetail(blog) {
      this.currentBlog = blog
      this.dialogVisible = true
    },

    // 格式化日期
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour: '2-digit',
        minute: '2-digit'
      })
    }
  },
  mounted() {
    this.loadBlogs()
  }
}
</script>

<style scoped>
@import '@/assets/css/list-common.scss';
.blogs-list-container {
  padding: 20px;
}
/* 排序控件样式 */
.filter-card {
  margin-bottom: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
  padding: 16px 20px;
}

.filter-row {
  display: flex;
  align-items: center;
  flex-wrap: wrap;
}

.filter-group {
  display: flex;
  align-items: center;
  margin-right: 20px;
  margin-bottom: 10px;
}

.filter-label {
  font-size: 14px;
  color: #606266;
  white-space: nowrap;
  margin-right: 8px;
}

.filter-select {
  min-width: 180px;
}

.search-input {
  width: 300px;
}



/* 博客卡片容器 */
.blog-cards {
  margin: 20px 0;
  min-height: 300px; /* 防止加载时页面跳动 */
}

/* 分页样式 */
.pagination-container {
  margin: 30px 0;
  text-align: center;
}

/* 修复Element UI分页器样式 */
.pagination-container .el-pagination {
  padding: 10px 0;
}

/* 加载中样式 */
.loading-container {
  text-align: center;
  padding: 50px 0;
  min-height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}


.single-blog {
  margin-bottom: 20px;
  transition: all 0.3s;
  height: 100%;
  display: flex;
  flex-direction: column;
}
.single-blog:hover {
  transform: translateY(-5px);
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}
.blog-header {
  padding: 15px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #f0f0f0;
  flex-shrink: 0;
}
.blog-info {
  margin-left: 10px;
}
.author-name {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
}
.blog-date {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
}
.image {
  width: 100%;
  display: block;
  height: 200px;
  object-fit: cover;
  flex-shrink: 0;
}
.blog-content {
  padding: 15px;
  flex-grow: 1;
  display: flex;
  flex-direction: column;
}
.blog-title {
  margin: 0 0 10px 0;
  font-size: 18px;
  font-weight: 500;
  color: #303133;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  overflow: hidden;
}
.blog-rating {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
}
.price {
  color: #f56c6c;
  font-size: 16px;
  font-weight: 500;
}
.blog-tags {
  margin: 10px 0;
  flex-grow: 1;
}
.blog-tags .el-tag {
  margin-right: 8px;
  margin-bottom: 8px;
}
.blog-footer {
  margin-top: 15px;
  text-align: right;
  flex-shrink: 0;
}
.button {
  padding: 0;
  font-size: 14px;
}

/* 博客详情弹窗样式 */
.blog-dialog :deep(.el-dialog__body) {
  padding: 20px;
}

.blog-detail-content {
  padding: 20px;
}

.blog-detail-header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.blog-detail-info {
  margin-left: 15px;
}

.blog-detail-author {
  font-size: 18px;
  font-weight: 500;
  color: #303133;
}

.blog-detail-date {
  font-size: 14px;
  color: #909399;
  margin-top: 5px;
}

.blog-detail-image {
  margin-bottom: 20px;
}

.blog-detail-image img {
  width: 100%;
  max-height: 400px;
  object-fit: cover;
  border-radius: 8px;
}

.blog-detail-rating {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 15px;
}

.blog-detail-price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: 500;
}

.blog-detail-tags {
  margin-bottom: 20px;
}

.blog-detail-tags .el-tag {
  margin-right: 8px;
  margin-bottom: 8px;
}

.blog-detail-text {
  font-size: 16px;
  line-height: 1.6;
  color: #606266;
  white-space: pre-line;
}
</style>