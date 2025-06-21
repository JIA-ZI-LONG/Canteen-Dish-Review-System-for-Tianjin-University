<template>
  <div class="home-container">
    <div class="welcome-section">
      <h1 class="welcome-title">欢迎来到天津大学美食点评社区！</h1>
      <p class="welcome-subtitle">发现舌尖上的天大，分享你的美食故事。</p>
      <div class="search-bar">
        <el-input placeholder="搜索食堂、菜品或博客" v-model="searchKeyword" class="input-with-select" @input="jumpToSearch">
          <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
        </el-input>
      </div>
    </div>

    <el-card class="nav-card">
      <el-row :gutter="20" class="nav-links">
        <el-col :span="8">
          <router-link to="/main/user/canteenslist" class="nav-item">
            <i class="el-icon-food"></i>
            <span>食堂大全</span>
          </router-link>
        </el-col>
        <el-col :span="8">
          <router-link to="/main/user/blogslist" class="nav-item">
            <i class="el-icon-notebook-2"></i>
            <span>美食博客</span>
          </router-link>
        </el-col>
        <el-col :span="8">
          <router-link to="/main/user/profile" class="nav-item">
            <i class="el-icon-user"></i>
            <span>个人中心</span>
          </router-link>
        </el-col>
      </el-row>
    </el-card>

    <el-row :gutter="20" class="content-sections">
      <el-col :span="12">
        <el-card class="content-card">
          <div slot="header" class="clearfix">
            <span>热门食堂</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push('/main/user/canteenslist')">查看更多</el-button>
          </div>
          <div v-loading="loading.canteens" style="min-height: 200px;">
            <div v-if="hotCanteens.length === 0 && !loading.canteens" class="empty-state">
              <p>暂无食堂数据</p>
            </div>
            <div v-for="canteen in hotCanteens" :key="canteen.id" class="list-item" @click="goToCanteenDetail(canteen.id)">
              <img :src="canteen.image" alt="食堂图片" class="item-image">
              <div class="item-info">
                <span class="item-title">{{ canteen.name }}</span>
                <el-rate v-model="canteen.rating" disabled show-score text-color="#ff9900" score-template="{value}分"></el-rate>
                <p class="item-desc">{{ canteen.desc }}</p>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="content-card">
          <div slot="header" class="clearfix">
            <span>最新博客</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="$router.push('/main/user/blogslist')">查看更多</el-button>
          </div>
          <div v-loading="loading.blogs" style="min-height: 200px;">
            <div v-if="latestBlogs.length === 0 && !loading.blogs" class="empty-state">
              <p>暂无博客数据</p>
            </div>
            <div v-for="blog in latestBlogs" :key="blog.id" class="list-item" @click="goToBlogDetail(blog.id)">
              <img :src="blog.image" alt="博客图片" class="item-image">
              <div class="item-info">
                <span class="item-title">{{ blog.title }}</span>
                <p class="item-author">作者: {{ blog.author }}</p>
                <p class="item-desc">{{ blog.summary }}</p>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <FloatingBlogButton @blog-published="handleBlogPublished" />

  </div>
</template>

<script>
import CanteenServices from '@/service/CanteenServices'
import BlogServices from '@/service/BlogServices'
// 修正：引入悬浮按钮组件
import FloatingBlogButton from '@/components/FloatingBlogButton.vue'

export default {
  name: 'HomeView',
  // 修正：注册组件
  components: {
    FloatingBlogButton
  },
  data() {
    return {
      searchKeyword: '',
      hotCanteens: [],
      latestBlogs: [],
      loading: {
        canteens: false,
        blogs: false
      }
    }
  },
  mounted() {
    this.loadHotCanteens()
    this.loadLatestBlogs()
  },
  computed: {
    hotCanteensModel() {
      return this.hotCanteens.map(canteen => ({
        id: canteen.id,
        name: canteen.name,
        rating: canteen.score || 0,
        desc: canteen.introduction || '暂无介绍',
        image: canteen.imageUrl || 'https://via.placeholder.com/100x100?text=' + encodeURIComponent(canteen.name)
      }))
    },
    latestBlogsModel() {
      return this.latestBlogs.map(blog => ({
        id: blog.id,
        title: blog.title,
        author: blog.authorName || '匿名用户',
        summary: this.extractSummary(blog.content),
        image: blog.imageUrl || 'https://via.placeholder.com/100x100?text=' + encodeURIComponent(blog.title)
      }))
    }
  },
  methods: {
    // 修正：添加事件处理方法，当博客发布成功后，刷新本页的博客列表
    handleBlogPublished() {
      this.$message.success('博客发布成功！');
      this.loadLatestBlogs(); // 重新加载最新博客
    },
    // 加载热门食堂数据
    loadHotCanteens() {
      this.loading.canteens = true
      CanteenServices.GetAllCanteens()
          .then(response => {
            if (response.success && response.data) {
              this.hotCanteens = (Array.isArray(response.data) ? response.data : [])
                  .slice(0, 3)
            } else {
              this.$message.error('获取食堂数据失败：' + (response.errorMsg || '未知错误'))
            }
          })
          .catch(error => {
            console.error('加载热门食堂失败：', error)
            this.$message.error('加载热门食堂失败：' + (error.message || '网络错误'))
          })
          .finally(() => {
            this.loading.canteens = false
          })
    },
    // 加载最新博客数据
    loadLatestBlogs() {
      this.loading.blogs = true
      BlogServices.getHotBlogs(1)
          .then(response => {
            if (response.success && response.data) {
              const blogs = Array.isArray(response.data) ? response.data :
                  (response.data.records ? response.data.records : [])
              this.latestBlogs = blogs.slice(0, 3)
            } else {
              this.$message.error('获取博客数据失败：' + (response.errorMsg || '未知错误'))
            }
          })
          .catch(error => {
            console.error('加载最新博客失败：', error)
            this.$message.error('加载最新博客失败：' + (error.message || '网络错误'))
          })
          .finally(() => {
            this.loading.blogs = false
          })
    },
    // 从博客内容中提取摘要
    extractSummary(content) {
      if (!content) return '暂无内容'
      // 移除HTML标签并截取前50个字符
      const plainText = content.replace(/<[^>]*>/g, '')
      return plainText.length > 50 ? plainText.substring(0, 50) + '...' : plainText
    },
    // 处理搜索
    jumpToSearch() {
      if (!this.$route.path.startsWith('/search')) {
        this.$router.push({ path: '/search', query: { q: this.searchKeyword } })
      }
    },
    handleSearch() {
      if (!this.searchKeyword.trim()) {
        this.$message.warning('请输入搜索关键词')
        return
      }

      // 根据搜索内容跳转到相应页面
      this.$message.info(`正在搜索: ${this.searchKeyword}`)

      // 可以根据关键词类型跳转到不同页面
      // 这里简单实现跳转到菜品搜索页面
      this.$router.push({
        path: '/search',
        query: { q: this.searchKeyword }
      })
    },
    // 跳转到食堂详情页
    goToCanteenDetail(canteenId) {
      this.$router.push(`/main/user/canteens/${canteenId}`)
    },
    // 跳转到博客详情页
    goToBlogDetail(blogId) {
      this.$router.push(`/main/user/blogs/${blogId}`)
    }
  }
}
</script>

<style scoped>
.home-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: calc(100vh - 60px); /* 减去MainMenu的高度 */
}

.welcome-section {
  background-image: url('@/assets/building9.jpg'); /* 替换为天津大学相关背景图 */
  background-size: cover;
  background-position: center;
  color: #fff;
  text-align: center;
  padding: 80px 20px;
  border-radius: 8px;
  margin-bottom: 30px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.welcome-title {
  font-size: 38px;
  margin-bottom: 15px;
  font-weight: bold;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}

.welcome-subtitle {
  font-size: 18px;
  margin-bottom: 30px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.2);
}

.search-bar {
  max-width: 600px;
  margin: 0 auto;
}

.search-bar .el-input {
  border-radius: 50px;
  overflow: hidden;
}

.search-bar .el-input /deep/ .el-input__inner {
  border-radius: 50px 0 0 50px;
  height: 45px;
  padding-left: 20px;
}

.search-bar .el-input /deep/ .el-input-group__append {
  background-color: #409EFF;
  color: #fff;
  border-radius: 0 50px 50px 0;
  border: none;
  height: 45px;
  line-height: 45px;
  padding: 0 20px;
}

.nav-card {
  margin-bottom: 30px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.nav-links {
  text-align: center;
  padding: 10px 0;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  color: #333;
  padding: 15px 0;
  transition: all 0.3s ease;
  border-radius: 8px;
}

.nav-item:hover {
  background-color: #ecf5ff;
  color: #409EFF;
  transform: translateY(-3px);
}

.nav-item i {
  font-size: 36px;
  margin-bottom: 10px;
  color: #409EFF; /* 图标颜色 */
}

.nav-item span {
  font-size: 16px;
  font-weight: bold;
}

.content-sections {
  margin-bottom: 30px;
}

.content-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  min-height: 350px; /* 统一卡片高度 */
}

.content-card .clearfix:before,
.content-card .clearfix:after {
  display: table;
  content: "";
}
.content-card .clearfix:after {
  clear: both
}

.content-card .el-card__header {
  padding: 15px 20px;
  font-size: 18px;
  font-weight: bold;
  color: #303133;
}

.list-item {
  display: flex;
  align-items: center;
  padding: 15px 0;
  border-bottom: 1px solid #ebeef5;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.list-item:hover {
  background-color: #f5f7fa;
}

.list-item:last-child {
  border-bottom: none;
}

.item-image {
  width: 80px;
  height: 80px;
  border-radius: 4px;
  object-fit: cover;
  margin-right: 15px;
  flex-shrink: 0;
}

.item-info {
  flex-grow: 1;
}

.item-title {
  font-size: 16px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
  display: block;
}

.item-desc {
  font-size: 14px;
  color: #909399;
  line-height: 1.4;
  margin-top: 5px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 限制2行 */
  line-clamp: 2; /* 标准属性 */
  -webkit-box-orient: vertical;
}

.el-rate {
  line-height: 1; /* 解决评分组件垂直对齐问题 */
}

.empty-state {
  text-align: center;
  padding: 40px 0;
  color: #909399;
}

.item-author {
  font-size: 12px;
  color: #909399;
  margin: 5px 0;
}
</style>