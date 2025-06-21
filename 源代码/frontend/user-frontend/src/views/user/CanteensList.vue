<template>
  <div class="canteens-container">
    <el-card class="filter-card">
      <div class="filter-row">
        <div class="filter-group">
          <span class="filter-label">校区：</span>
          <el-select
              v-model="selectedCampus"
              placeholder="选择校区"
              clearable
              @change="handleCampusChange"
              class="filter-select">
            <el-option
                v-for="campus in campusOptions"
                :key="campus.id"
                :label="campus.name"
                :value="campus.id">
            </el-option>
          </el-select>
        </div>

        <div class="filter-group">
          <el-input
              v-model="searchQuery"
              placeholder="搜索食堂"
              prefix-icon="el-icon-search"
              @keyup.enter.native="handleSearch"
              clearable
              class="search-input">
          </el-input>
        </div>
      </div>
    </el-card>

    <div v-loading="loading" style="min-height: 400px;">
      <div v-if="filteredCanteens.length === 0 && !loading" class="empty-state">
        <el-empty description="暂无食堂数据"></el-empty>
      </div>
      <el-row :gutter="20" class="canteen-list" v-else>
        <el-col :span="8" v-for="canteen in filteredCanteens" :key="canteen.id">
          <el-card :body-style="{ padding: '0px' }" class="canteen-card" @click.native="goToCanteenDetail(canteen)">
            <img :src="canteen.imageUrl" :alt="canteen.name" class="canteen-image">
            <div class="canteen-content">
              <h3 class="canteen-name">{{ canteen.name }}</h3>
              <div class="canteen-info">
                <el-rate
                    v-model="canteen.rating"
                    disabled
                    :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                    show-score>
                </el-rate>
                <span class="canteen-price">人均 ¥{{ canteen.averagePrice }}</span>
              </div>
              <div class="canteen-tags">
                <el-tag size="small" type="info">{{ canteen.campus }}</el-tag>
                <el-tag size="small" type="success" v-if="canteen.isOpen">营业中</el-tag>
                <el-tag size="small" type="danger" v-else>已关闭</el-tag>
              </div>
              <div class="canteen-description">{{ canteen.description }}</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import CanteenServices from '@/service/CanteenServices'
import CampusServices from '@/service/CampusServices'

export default {
  name: 'CanteensList',
  data() {
    return {
      searchQuery: '',
      selectedCampus: '',
      loading: false,
      canteens: [],
      currentPage: 1,
      pageSize: 10,
      total: 0,
      campusOptions: []
    }
  },
  computed: {
    canteensModel() {
      return (Array.isArray(this.canteens) ? this.canteens : [])
          .map(canteen => ({
            id: canteen.id,
            name: canteen.name,
            campusId: canteen.campusId,
            campus: canteen.campusName || '未知校区',
            imageUrl: canteen.imageUrl || `https://via.placeholder.com/400x300?text=${encodeURIComponent(canteen.name)}`,
            rating: canteen.score || 0,
            averagePrice: canteen.avgPrice || 0,
            isOpen: canteen.openStatus === 1,
            businessHours: canteen.openHours || '营业时间未知',
            location: canteen.address || '地址未知',
            description: canteen.introduction || '暂无介绍'
          }))
    },
    filteredCanteens() {
      let result = this.canteensModel

      // 搜索过滤
      if (this.searchQuery) {
        const query = this.searchQuery.toLowerCase()
        result = result.filter(canteen =>
            canteen.name.toLowerCase().includes(query) ||
            canteen.description.toLowerCase().includes(query)
        )
      }

      // 校区过滤
      if (this.selectedCampus) {
        result = result.filter(canteen =>
            canteen.campusId === this.selectedCampus
        )
      }

      return result
    }
  },
  mounted() {
    this.loadCampuses()
    this.loadCanteens()
  },
  methods: {
    loadCampuses() {
      CampusServices.getAllCampuses()
          .then(res => {
            if (res.success && Array.isArray(res.data)) {
              const list = res.data.map(c => ({ id: c.id, name: c.name || c.campusName || c.id }))
              this.campusOptions = [{ id: '', name: '全部校区' }, ...list]
            }
          })
          .catch(error => {
            console.error('加载校区列表失败:', error)
            this.$message.error('加载校区列表失败')
          })
    },
    loadCanteens() {
      this.loading = true
      const params = {
        page: this.currentPage,
        size: this.pageSize,
        keyword: this.searchQuery,
        campusId: this.selectedCampus
      }

      CanteenServices.getCanteens(params)
          .then(response => {
            if (response.success && response.data) {
              // 适配后端返回格式：data直接是数组，total在同级
              this.canteens = response.data || []
              this.total = response.total || 0
            } else {
              this.$message.error(response.errorMsg || '获取食堂列表失败')
            }
          })
          .catch(error => {
            console.error('加载食堂列表失败:', error)
            this.$message.error('加载食堂列表失败')
          })
          .finally(() => {
            this.loading = false
          })
    },
    handleSearch() {
      // 搜索逻辑已通过计算属性实现
    },
    goToCanteenDetail(canteen) {
      if (!canteen?.id) {
        this.$message.error('食堂信息不完整')
        return
      }
      this.$router.push({
        path: '/main/user/canteen-details',
        query: { id: canteen.id }
      })
    }
  }
}
</script>

<style scoped>
@import '@/assets/css/list-common.scss';
.canteens-container {
  padding: 20px;
}

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

.canteen-list {
  margin-top: 20px;
}

.canteen-card {
  margin-bottom: 20px;
  cursor: pointer;
  transition: all 0.3s;
}

.canteen-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.canteen-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.canteen-content {
  padding: 15px;
}

.canteen-name {
  margin: 0 0 10px 0;
  font-size: 18px;
  font-weight: 500;
}

.canteen-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.canteen-price {
  color: #f56c6c;
  font-size: 16px;
  font-weight: 500;
}

.canteen-tags {
  margin-bottom: 10px;
}

.canteen-tags .el-tag {
  margin-right: 8px;
}

.canteen-description {
  font-size: 14px;
  color: #606266;
  line-height: 1.4;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  overflow: hidden;
}

.empty-state {
  text-align: center;
  padding: 60px 0;
  color: #909399;
  font-size: 16px;
}
</style>