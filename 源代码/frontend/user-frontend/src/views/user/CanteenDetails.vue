<template>
  <div class="canteen-details-container" v-loading="loading">
    <!-- 返回按钮 -->
    <div class="back-button">
      <el-button icon="el-icon-arrow-left" @click="$router.push({ name: 'canteenslist' })">返回食堂列表</el-button>
    </div>

    <!-- 食堂简介卡片 -->
    <el-card class="canteen-intro-card" v-if="canteenModel">
      <div class="intro-header">
        <div class="intro-left">
          <h1 class="canteen-name">{{ canteenModel.name }}</h1>
          <div class="canteen-tags">
            <el-tag size="medium" type="info">{{ canteenModel.campus }}</el-tag>
            <el-tag size="medium" type="success" v-if="canteenModel.isOpen">营业中</el-tag>
            <el-tag size="medium" type="danger" v-else>已关闭</el-tag>
          </div>
        </div>
        <div class="intro-right">
          <div class="rating-price">
            <el-rate
              v-model="canteenModel.rating"
              disabled
              :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
              show-score>
            </el-rate>
            <span class="average-price">人均 ¥{{ canteenModel.averagePrice }}</span>
          </div>
          <div class="business-hours">
            <i class="el-icon-time"></i>
            <span>{{ canteenModel.businessHours }}</span>
          </div>
          <div class="location">
            <i class="el-icon-location"></i>
            <span>{{ canteenModel.location }}</span>
          </div>
        </div>
      </div>
      <div class="intro-description">
        <p>{{ canteenModel.description }}</p>
      </div>
    </el-card>

    <!-- 窗口和菜品展示 -->
    <div class="windows-section">
      <div class="window-cards-wrapper">
        <div class="window-info-container"
             v-for="window in windowsModel"
             :key="window.id"
             @click="goToWindowDetails(window)">
          <el-card class="window-card" :body-style="{ padding: '0px' }">
            <img :src="window.imageUrl" :alt="window.name" class="window-image">
            <div class="window-content">
              <h3 class="window-name-card">{{ window.name }}</h3>
              <div class="window-rating">
                <el-rate
                  v-model="window.rating"
                  disabled
                  :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                  show-score>
                </el-rate>
                <span class="window-price">人均 ¥{{ window.averagePrice }}</span>
              </div>
              <p class="window-specialty-description">{{ window.specialty }} - {{ window.description }}</p>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CanteenServices from '@/service/CanteenServices'
import StallServices from '@/service/StallServices'

export default {
  name: 'CanteenDetails',
  data() {
    return {
      canteen: null,
      loading: false,
      canteenId: null
    }
  },
  computed: {
    canteenModel() {
      if (!this.canteen) return null
      return {
        id: this.canteen.id,
        name: this.canteen.name,
        campus: this.canteen.campusName || '未知校区',
        rating: this.canteen.score || 0,
        averagePrice: this.canteen.avgPrice || 0,
        isOpen: this.canteen.openStatus === 1,
        businessHours: this.canteen.openHours || '营业时间未知',
        location: this.canteen.address || '地址未知',
        description: this.canteen.introduction || '暂无介绍'
      }
    },
    windowsModel() {
      if (!this.canteen?.windows) return []
      return this.canteen.windows.map(stall => ({
        id: stall.id,
        name: stall.name,
        specialty: stall.specialty || '特色菜品',
        imageUrl: stall.imageUrl || `https://via.placeholder.com/300x200?text=${encodeURIComponent(stall.name)}`,
        rating: stall.score || 0,
        averagePrice: stall.avgPrice || 0,
        description: stall.introduction || '暂无介绍'
      }))
    }
  },
  created() {
    this.canteenId = this.$route.query.id
    if (this.canteenId) {
      this.loadCanteenData()
    } else {
      this.$message.error('未找到食堂信息')
      this.$router.push({ name: 'canteenslist' })
    }
  },
  methods: {
    loadCanteenData() {
      this.loading = true
      Promise.all([
        CanteenServices.GetCanteenById(this.canteenId),
        StallServices.GetStallsByCanteenId(this.canteenId)
      ])
        .then(([canteenResponse, stallsResponse]) => {
          if (!canteenResponse.success || !canteenResponse.data) {
            throw new Error(canteenResponse.errorMsg || '获取食堂信息失败')
          }

          const canteenData = canteenResponse.data
          this.canteen = {
            ...canteenData,
            windows: []
          }

          if (stallsResponse.success && stallsResponse.data) {
            this.canteen.windows = Array.isArray(stallsResponse.data) ? stallsResponse.data : []
          }
        })
        .catch(error => {
          console.error('加载食堂详情失败：', error)
          this.$message.error('加载食堂详情失败：' + (error.message || '网络错误'))
          this.$router.push({ name: 'canteenslist' })
        })
        .finally(() => {
          this.loading = false
        })
    },
    goToWindowDetails(window) {
      if (!window?.id || !this.canteen?.id) {
        this.$message.error('窗口信息不完整')
        return
      }
      this.$router.push({
        path: `/main/user/window-details/${window.id}/${this.canteen.id}`
      })
    }
  }
}
</script>

<style scoped>
.canteen-details-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.back-button {
  margin-bottom: 20px;
}

.canteen-intro-card {
  margin-bottom: 30px;
}

.intro-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.intro-left {
  flex: 1;
}

.canteen-name {
  margin: 0 0 15px 0;
  font-size: 28px;
  font-weight: 600;
}

.canteen-tags {
  margin-bottom: 15px;
}

.canteen-tags .el-tag {
  margin-right: 10px;
}

.intro-right {
  text-align: right;
}

.rating-price {
  margin-bottom: 10px;
}

.average-price {
  margin-left: 15px;
  color: #f56c6c;
  font-size: 18px;
  font-weight: 500;
}

.business-hours,
.location {
  margin-top: 8px;
  color: #606266;
}

.business-hours i,
.location i {
  margin-right: 5px;
}

.intro-description {
  color: #606266;
  line-height: 1.6;
}

.windows-section {
  margin-top: 30px;
}

.window-cards-wrapper {
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* Space between cards */
  justify-content: center; /* Center the cards if there are few */
  padding: 20px 0; /* Consistent padding */
}

.window-info-container {
  padding: 0; /* Remove padding from here */
  /* Remove display:flex and justify-content:center if it was here for single card */
}

.window-card {
  width: 300px; /* Fixed card width */
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  transition: all 0.3s;
  cursor: pointer;
}

.window-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 18px 0 rgba(0,0,0,0.15);
}

.window-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  display: block;
}

.window-content {
  padding: 15px;
}

.window-name-card {
  font-size: 20px;
  font-weight: 600;
  margin: 0 0 10px 0;
  color: #303133;
}

.window-rating {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.window-price {
  margin-left: 10px;
  color: #f56c6c;
  font-size: 16px;
  font-weight: 500;
}

.window-specialty-description {
  font-size: 14px;
  color: #606266;
  line-height: 1.5;
  margin-top: 10px;
  min-height: 42px; /* 确保有足够高度，防止内容过短时卡片变形 */
}
</style>
