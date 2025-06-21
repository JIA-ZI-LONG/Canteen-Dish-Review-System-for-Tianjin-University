<template>
  <div class="ranking-container">
    <!-- 顶部简洁标签 -->
    <el-alert
      class="ranking-alert"
      title="校园热门美食榜单"
      type="success"
      effect="dark"
      show-icon>
    </el-alert>
    <!-- 排行榜类型切换 -->
    <div class="ranking-tabs">
      <el-tabs v-model="activeTab" @tab-click="handleTabClick">
        <el-tab-pane label="🍽️ 食堂排行" name="canteen"></el-tab-pane>
        <el-tab-pane label="🥘 菜品排行" name="dish"></el-tab-pane>
        <el-tab-pane label="📝 博客排行" name="blog"></el-tab-pane>
        <el-tab-pane label="👤 用户排行" name="user"></el-tab-pane>
      </el-tabs>
    </div>

    <!-- 排行榜列表 -->
    <div class="ranking-content">
      <el-card class="ranking-card" v-loading="loading">
        <!-- 食堂排行榜 -->
        <div v-if="activeTab === 'canteen'" class="ranking-list">
          <div class="ranking-item" v-for="(item, index) in canteenRanking" :key="item.id">
            <div class="rank-number" :class="getRankClass(index)">
              <span v-if="index < 3">{{ ['🥇', '🥈', '🥉'][index] }}</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <div class="item-info">
              <h4>{{ item.name }}</h4>
              <p>{{ item.address }}</p>
              <div class="item-stats">
                <span class="score">⭐ {{ item.averageScore || '暂无评分' }}</span>
                <span class="likes">👍 {{ item.liked || 0 }}</span>
                <span class="comments">💬 {{ item.comments || 0 }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 菜品排行榜 -->
        <div v-if="activeTab === 'dish'" class="ranking-list">
          <div class="ranking-item" v-for="(item, index) in dishRanking" :key="item.id">
            <div class="rank-number" :class="getRankClass(index)">
              <span v-if="index < 3">{{ ['🥇', '🥈', '🥉'][index] }}</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <div class="item-info">
              <h4>{{ item.name }}</h4>
              <p>{{ item.description }}</p>
              <div class="item-stats">
                <span class="price">💰 ¥{{ item.price }}</span>
                <span class="score">⭐ {{ item.averageScore || '暂无评分' }}</span>
                <span class="likes">👍 {{ item.liked || 0 }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 博客排行榜 -->
        <div v-if="activeTab === 'blog'" class="ranking-list">
          <div class="ranking-item" v-for="(item, index) in blogRanking" :key="item.id">
            <div class="rank-number" :class="getRankClass(index)">
              <span v-if="index < 3">{{ ['🥇', '🥈', '🥉'][index] }}</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <div class="item-info">
              <h4>{{ item.title }}</h4>
              <p>作者：{{ item.nickname }}</p>
              <div class="item-stats">
                <span class="likes">👍 {{ item.liked || 0 }}</span>
                <span class="comments">💬 {{ item.comments || 0 }}</span>
                <span class="time">🕒 {{ formatTime(item.createTime) }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 用户排行榜 -->
        <div v-if="activeTab === 'user'" class="ranking-list">
          <div class="ranking-item" v-for="(item, index) in userRanking" :key="item.id">
            <div class="rank-number" :class="getRankClass(index)">
              <span v-if="index < 3">{{ ['🥇', '🥈', '🥉'][index] }}</span>
              <span v-else>{{ index + 1 }}</span>
            </div>
            <div class="item-info">
              <h4>{{ item.nickname }}</h4>
              <p>{{ item.intro || '这个用户很神秘，什么都没有留下~' }}</p>
              <div class="item-stats">
                <span class="blogs">📝 {{ item.blogCount || 0 }} 篇博客</span>
                <span class="likes">👍 {{ item.totalLikes || 0 }} 获赞</span>
                <span class="credits">💎 {{ item.credits || 0 }} 积分</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 空状态 -->
        <div v-if="getCurrentRanking().length === 0 && !loading" class="empty-state">
          <el-empty description="暂无排行数据">
            <el-button type="primary" @click="loadRankingData">刷新数据</el-button>
          </el-empty>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
import RankingServices from '@/service/RankingServices'

export default {
  name: 'RankingView',
  data() {
    return {
      activeTab: 'canteen',
      loading: false,
      canteenRanking: [],
      dishRanking: [],
      blogRanking: [],
      userRanking: []
    }
  },
  mounted() {
    document.title = '排行榜 - 天津大学美食点评平台'
    this.loadRankingData()
  },
  methods: {
    handleTabClick(tab) {
      this.activeTab = tab.name
      this.loadRankingData()
    },

    loadRankingData() {
      this.loading = true;
      let rankingPromise;

      if (this.activeTab === 'canteen') {
        rankingPromise = RankingServices.getCanteenRanking('score', 10);
      } else if (this.activeTab === 'dish') {
        rankingPromise = RankingServices.getDishRanking('score', 10);
      } else if (this.activeTab === 'blog') {
        rankingPromise = RankingServices.getBlogRanking('likes', 10, 'all');
      } else if (this.activeTab === 'user') {
        rankingPromise = RankingServices.getUserRanking('credits', 10);
      }

      if (rankingPromise) {
        rankingPromise.then(response => {
          if (response.success) {
            // 根据当前tab更新对应的数据
            this[this.activeTab + 'Ranking'] = response.data;
          } else {
            // 主动抛出错误，进入catch块
            throw new Error(response.errorMsg || `获取${this.activeTab}排行榜失败`);
          }
        })
            .catch(error => {
              console.error('加载排行榜数据失败:', error);
              this.$message.error(error.message || '加载排行榜数据失败');
              this.loadMockData(); // API失败后加载模拟数据
            })
            .finally(() => {
              this.loading = false;
            });
      } else {
        this.loading = false;
      }
    },

    loadMockData() {
      // 模拟API延迟
      return new Promise(resolve => setTimeout(resolve, 1000)).then(() => {
        if (this.activeTab === 'canteen') {
          this.canteenRanking = [
            { id: 1, name: '学三食堂', address: '青年湖西侧', averageScore: 4.8, liked: 1250, comments: 320 },
            { id: 2, name: '学一食堂', address: '主楼附近', averageScore: 4.6, liked: 980, comments: 280 },
            { id: 3, name: '学二食堂', address: '图书馆旁', averageScore: 4.5, liked: 850, comments: 240 }
          ];
        } else if (this.activeTab === 'dish') {
          this.dishRanking = [
            { id: 1, name: '红烧肉', description: '肥而不腻，香甜可口', price: 12.5, averageScore: 4.9, liked: 580 },
            { id: 2, name: '宫保鸡丁', description: '经典川菜，麻辣鲜香', price: 10.0, averageScore: 4.7, liked: 520 },
            { id: 3, name: '麻婆豆腐', description: '嫩滑豆腐，麻辣下饭', price: 8.5, averageScore: 4.6, liked: 480 }
          ];
        } else if (this.activeTab === 'blog') {
          this.blogRanking = [
            { id: 1, title: '天大美食探店指南', nickname: '美食达人', liked: 320, comments: 85, createTime: '2024-01-15T10:30:00' },
            { id: 2, title: '学三食堂深度测评', nickname: '吃货小王', liked: 280, comments: 72, createTime: '2024-01-14T15:20:00' },
            { id: 3, title: '校园美食性价比排行', nickname: '节约小能手', liked: 250, comments: 68, createTime: '2024-01-13T09:15:00' }
          ];
        } else if (this.activeTab === 'user') {
          this.userRanking = [
            { id: 1, nickname: '美食达人', intro: '专业美食博主，分享校园美食', blogCount: 25, totalLikes: 1580, credits: 2500 },
            { id: 2, nickname: '吃货小王', intro: '热爱美食，乐于分享', blogCount: 18, totalLikes: 1200, credits: 1800 },
            { id: 3, nickname: '节约小能手', intro: '追求性价比的美食爱好者', blogCount: 15, totalLikes: 980, credits: 1500 }
          ];
        }
      });
    },

    getRankClass(index) {
      if (index === 0) return 'rank-first';
      if (index === 1) return 'rank-second';
      if (index === 2) return 'rank-third';
      return 'rank-normal';
    },

    getCurrentRanking() {
      switch (this.activeTab) {
        case 'canteen': return this.canteenRanking;
        case 'dish': return this.dishRanking;
        case 'blog': return this.blogRanking;
        case 'user': return this.userRanking;
        default: return [];
      }
    },

    formatTime(timeStr) {
      if (!timeStr) return '';
      const date = new Date(timeStr);
      return date.toLocaleDateString();
    }
  }
}
</script>

<style scoped>
.ranking-container {
  padding: 0 8px 32px;
  max-width: 100%; /* 占满可视宽度 */
  margin: 0 auto;
}

/* 顶部提示条 */
.ranking-alert {
  margin-bottom: 16px;
  border-radius: 8px;
}

.ranking-tabs {
  margin-bottom: 12px;
}

.ranking-card {
  border-radius: 8px;
  box-shadow: 0 2px 8px 0 rgba(0,0,0,0.05);
}

.ranking-content {
  padding: 0;
}

.ranking-list {
  padding: 10px;
}

.ranking-item {
  display: flex;
  align-items: flex-start;
  align-items: center;
  gap: 12px;
  padding: 10px 14px;
  margin-bottom: 10px;
  background: linear-gradient(135deg, rgba(255,255,255,0.8) 0%, rgba(255,255,255,0.95) 100%);
  border-radius: 10px;
  border-left: 4px solid #409eff;
  box-shadow: 0 1px 4px rgba(0,0,0,0.05);
  transition: all 0.25s ease;
}

.ranking-item:hover {
  background: linear-gradient(135deg, #f2f8ff 0%, #ffffff 100%);
  transform: translateY(-3px);
  box-shadow: 0 8px 30px rgba(64,158,255,0.15);
}

.rank-number {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  font-weight: bold;
  margin-right: 16px;
  color: white;
}

.rank-first {
  background: linear-gradient(135deg, #6e8efb, #a6b1ff);
  color: #fff;
}

.rank-second {
  background: linear-gradient(135deg, #8fb3ff, #c5d0ff);
  color: #fff;
}

.rank-third {
  background: linear-gradient(135deg, #bbd9ff, #d6e4ff);
  color: #597ef7;
}

.rank-normal {
  background: linear-gradient(135deg, #409eff, #6e8efb);
}

.item-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 4px;
}

.item-info h4 {
  font-size: 1.1rem;
  color: #303133;
  margin: 0 0 4px 0;
}

.item-info p {
  color: #606266;
  margin: 0 0 8px 0;
  font-size: 0.85rem;
}

.item-stats {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.item-stats span {
  background: #eef5ff;
  color: #409eff;
  padding: 4px 8px;
  border-radius: 6px;
  font-size: 0.85rem;
  font-weight: 500;
}

.empty-state {
  padding: 40px 10px;
  text-align: center;
}

.empty-state .el-button {
  background-image: linear-gradient(135deg,#409eff 0%, #6e8efb 100%);
  border: none;
}

@media (max-width: 768px) {
  .ranking-container {
    margin: -20px auto 0;
    padding: 0 15px 40px;
  }
  
  .ranking-tabs {
    padding: 15px 20px 0;
  }
  
  .ranking-list {
    padding: 20px;
  }
  
  .ranking-item {
    flex-direction: column;
    text-align: center;
    padding: 15px;
  }
  
  .rank-number {
    margin-right: 0;
    margin-bottom: 15px;
  }
  
  .item-stats {
    justify-content: center;
  }
}
</style>
