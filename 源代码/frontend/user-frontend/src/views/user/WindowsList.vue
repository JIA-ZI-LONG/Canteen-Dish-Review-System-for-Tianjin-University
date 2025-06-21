<template>
  <div class="window-details-container">
    <!-- 返回按钮 -->
    <div class="back-button">
      <el-button icon="el-icon-arrow-left" @click="$router.push({ name: 'canteendetails', query: { id: parentCanteenId } })">返回食堂详情</el-button>
    </div>

    <el-skeleton :loading="loading" animated>
      <template slot="template">
        <div style="padding: 20px">
          <el-skeleton-item variant="image" style="width: 100%; height: 300px" />
          <div style="padding: 20px">
            <el-skeleton-item variant="h1" style="width: 50%" />
            <div style="display: flex; align-items: center; margin-top: 20px">
              <el-skeleton-item variant="text" style="margin-right: 16px" />
              <el-skeleton-item variant="text" style="width: 30%" />
            </div>
          </div>
        </div>
      </template>
      <template>
        <!-- 窗口简介卡片 -->
        <el-card v-if="windowModel" class="window-intro-card" :body-style="{ padding: '0px' }">
          <img :src="windowModel.imageUrl" :alt="windowModel.name" class="window-image-intro">
          <div class="window-content-intro">
            <h1 class="window-name-intro">{{ windowModel.name }}</h1>
            <div class="window-rating-intro">
              <el-rate
                v-model="windowModel.rating"
                disabled
                :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                show-score>
              </el-rate>
              <span class="window-price-intro">人均 ¥{{ windowModel.averagePrice }}</span>
            </div>
            <p class="window-specialty-intro">特色：{{ windowModel.specialty }}</p>
            <p class="window-description-intro">{{ windowModel.description }}</p>
          </div>
        </el-card>

        <!-- 窗口内所有菜品 -->
        <div class="section-title">窗口菜品</div>
        <div class="dishes-container">
          <el-card
            v-for="dish in dishesModel"
            :key="dish.id"
            class="dish-card"
            :body-style="{ padding: '0px' }">
            <div class="dish-image">
              <img :src="dish.imageUrl" :alt="dish.name">
            </div>
            <div class="dish-info">
              <h3 class="dish-name">{{ dish.name }}</h3>
              <div class="dish-rating">
                <el-rate
                  v-model="dish.rating"
                  disabled
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                  show-score>
                </el-rate>
                <span class="dish-price">¥{{ dish.price }}</span>
              </div>
              <p class="dish-description">{{ dish.description }}</p>
              <div class="dish-tags">
                <el-tag
                  v-for="tag in dish.tags"
                  :key="tag"
                  size="small"
                  type="info">
                  {{ tag }}
                </el-tag>
              </div>
            </div>
          </el-card>
        </div>

        <!-- 用户评价 -->
        <div class="section-title">用户评价</div>
        <div class="reviews-container">
          <el-card v-for="review in reviewsModel" :key="review.id" class="review-card">
            <div class="review-header">
              <span class="username">{{ review.username }}</span>
              <el-rate v-model="review.rating" disabled></el-rate>
            </div>
            <div class="review-content">
              <p>{{ review.comment }}</p>
              <span class="review-date">{{ review.date }}</span>
            </div>
          </el-card>
          <div v-if="reviewsModel.length === 0" class="no-data-tip">暂无用户评价</div>
        </div>

        <!-- 相关博客 -->
        <div class="section-title">相关博客</div>
        <div class="related-blogs-container">
          <el-card v-for="blog in relatedBlogsModel" :key="blog.id" class="blog-card">
            <div class="blog-header">
              <h3 class="blog-title">{{ blog.title }}</h3>
              <span class="blog-author">{{ blog.author }}</span>
            </div>
            <p class="blog-summary">{{ blog.summary }}</p>
            <div class="blog-meta">
              <span>{{ blog.date }}</span>
              <span>{{ blog.views }} 阅读</span>
            </div>
          </el-card>
          <div v-if="relatedBlogsModel.length === 0" class="no-data-tip">暂无相关博客</div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script>
import StallServices from '@/service/StallServices'
import DishServices from '@/service/DishServices'
import ReviewServices from '@/service/ReviewServices'

export default {
  name: 'WindowDetails',
  data() {
    return {
      windowData: null,
      parentCanteenId: null,
      dishesData: [],
      reviewsData: [],
      loading: false,
      currentPage: 1,
      pageSize: 10,
      total: 0
    }
  },
  computed: {
    windowModel() {
      if (!this.windowData) return null
      return {
        id: this.windowData.id,
        name: this.windowData.name,
        imageUrl: this.windowData.imageUrl || '/default-window-image.jpg',
        rating: this.windowData.score || 0,
        averagePrice: this.windowData.averagePrice || 0,
        specialty: this.windowData.specialty || '暂无特色',
        description: this.windowData.description || '暂无描述',
        dishes: this.dishesModel,
        reviews: this.reviewsModel,
        relatedBlogs: this.relatedBlogsModel
      }
    },
    dishesModel() {
      return this.dishesData.map(dish => ({
        id: dish.id,
        name: dish.name,
        imageUrl: dish.imageUrl || '/default-dish-image.jpg',
        rating: dish.score || 0,
        price: dish.price || 0,
        description: dish.description || '暂无描述',
        tags: dish.tags || []
      }))
    },
    reviewsModel() {
      return this.reviewsData.map(review => ({
        id: review.id,
        username: review.user?.nickname || '匿名用户',
        rating: review.score || 0,
        comment: review.content || '',
        date: this.formatDate(review.createTime)
      }))
    },
    relatedBlogsModel() {
      return (this.windowData?.relatedBlogs || []).map(blog => ({
        id: blog.id,
        title: blog.title,
        author: blog.authorName || '匿名用户',
        summary: this.extractSummary(blog.content),
        date: this.formatDate(blog.createTime),
        views: blog.views || 0
      }))
    }
  },
  created() {
    const windowId = this.$route.params.windowId || this.$route.query.windowId
    this.parentCanteenId = this.$route.params.canteenId || this.$route.query.canteenId

    if (windowId) {
      this.loadWindowData(windowId)
    } else {
      this.$message.error('未找到窗口信息')
      this.$router.push({ name: 'canteenslist' })
    }
  },
  methods: {
    loadWindowData(windowId) {
      this.loading = true
      StallServices.GetStallById(windowId)
        .then(response => {
          if (response.success) {
            this.windowData = response.data
            this.loadDishes(windowId)
            this.loadReviews(windowId)
          } else {
            this.$message.error(response.errorMsg || '获取窗口信息失败')
          }
        })
        .catch(error => {
          console.error('获取窗口信息失败:', error)
          this.$message.error('获取窗口信息失败：' + (error.message || '网络错误'))
        })
        .finally(() => {
          this.loading = false
        })
    },
    loadDishes(windowId) {
      DishServices.SearchDishes({ stallId: windowId })
        .then(response => {
          if (response.success) {
            this.dishesData = response.data || []
          } else {
            this.$message.error(response.errorMsg || '获取菜品列表失败')
          }
        })
        .catch(error => {
          console.error('获取菜品列表失败:', error)
          this.$message.error('获取菜品列表失败：' + (error.message || '网络错误'))
        })
    },
    loadReviews(windowId) {
      ReviewServices.GetStallReviews(windowId, {
        current: this.currentPage,
        size: this.pageSize
      })
        .then(response => {
          if (response.success) {
            this.reviewsData = response.data || []
            this.total = response.total || 0
          } else {
            this.$message.error(response.errorMsg || '获取评价列表失败')
          }
        })
        .catch(error => {
          console.error('获取评价列表失败:', error)
          this.$message.error('获取评价列表失败：' + (error.message || '网络错误'))
        })
    },
    formatDate(dateString) {
      if (!dateString) return ''
      const date = new Date(dateString)
      return date.toLocaleDateString('zh-CN')
    },
    extractSummary(content) {
      if (!content) return '暂无内容'
      return content.length > 100 ? content.substring(0, 100) + '...' : content
    }
  }
}
</script>

<style scoped>
.window-details-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.back-button {
  margin-bottom: 20px;
}

/* 窗口简介卡片样式，参考食堂详情页中的窗口卡片 */
.window-intro-card {
  margin-bottom: 30px;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.window-image-intro {
  width: 100%;
  height: 300px; /* 调整图片高度 */
  object-fit: cover;
  display: block;
}

.window-content-intro {
  padding: 20px;
}

.window-name-intro {
  font-size: 28px;
  font-weight: 600;
  margin: 0 0 15px 0;
  color: #303133;
}

.window-rating-intro {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.window-price-intro {
  margin-left: 15px;
  color: #f56c6c;
  font-size: 20px;
  font-weight: 500;
}

.window-specialty-intro {
  font-size: 16px;
  color: #606266;
  margin-bottom: 10px;
}

.window-description-intro {
  font-size: 14px;
  color: #606266;
  line-height: 1.6;
}

/* 各个部分的标题 */
.section-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  margin-top: 40px;
  margin-bottom: 20px;
  border-left: 5px solid #409EFF;
  padding-left: 10px;
}

/* 菜品容器样式 */
.dishes-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  padding: 10px 0;
}

/* 菜品卡片样式 (沿用之前CanteenDetails.vue中的) */
.dish-card {
  transition: all 0.3s;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.05);
}

.dish-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 18px 0 rgba(0,0,0,0.1);
}

.dish-image {
  height: 180px; /* 调整菜品图片高度 */
  overflow: hidden;
}

.dish-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.dish-info {
  padding: 15px;
}

.dish-name {
  margin: 0 0 10px 0;
  font-size: 18px;
  font-weight: 500;
  color: #303133;
}

.dish-rating {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.dish-price {
  color: #f56c6c;
  font-size: 16px;
  font-weight: 500;
}

.dish-description {
  color: #606266;
  font-size: 14px;
  line-height: 1.4;
  margin-bottom: 10px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

.dish-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 5px;
}

.dish-tags .el-tag {
  margin-right: 5px;
}

/* 用户评价样式 */
.reviews-container {
  padding: 10px 0;
}

.review-card {
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
}

.review-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.username {
  font-weight: 600;
  color: #303133;
}

.review-content p {
  font-size: 14px;
  color: #606266;
  line-height: 1.5;
  margin-bottom: 8px;
}

.review-date {
  font-size: 12px;
  color: #909399;
  text-align: right;
  display: block;
}

/* 相关博客样式 */
.related-blogs-container {
  padding: 10px 0;
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.blog-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
  transition: all 0.3s;
  cursor: pointer;
}

.blog-card:hover {
  transform: translateY(-3px);
  box-shadow: 0 4px 12px 0 rgba(0,0,0,0.1);
}

.blog-header {
  margin-bottom: 10px;
}

.blog-title {
  font-size: 18px;
  font-weight: 500;
  color: #303133;
  margin: 0 0 5px 0;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  overflow: hidden;
}

.blog-author {
  font-size: 14px;
  color: #909399;
}

.blog-summary {
  font-size: 14px;
  color: #606266;
  line-height: 1.5;
  margin-bottom: 10px;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
  overflow: hidden;
}

.blog-meta {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  color: #909399;
}

.no-data-tip {
  text-align: center;
  color: #909399;
  padding: 20px;
  font-size: 16px;
}
</style>
