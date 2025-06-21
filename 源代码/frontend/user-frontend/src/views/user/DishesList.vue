<template>
  <div class="dishes-list-container">
    <div class="page-header">

    </div>

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
          <span class="filter-label">食堂：</span>
          <el-select
              v-model="selectedCanteen"
              placeholder="选择食堂"
              clearable
              :disabled="!selectedCampus"
              class="filter-select">
            <el-option
                v-for="canteen in canteenOptions"
                :key="canteen.id"
                :label="canteen.name"
                :value="canteen.id">
            </el-option>
          </el-select>
        </div>

        <div class="filter-group">
          <span class="filter-label">排序：</span>
          <el-select v-model="sortBy" @change="handleSortChange" placeholder="请选择排序方式" clearable class="filter-select">
            <el-option label="评分从高到低" value="rating"></el-option>
            <el-option label="价格从高到低" value="price"></el-option>
            <el-option label="名称排序" value="name"></el-option>
          </el-select>
        </div>

        <div class="filter-group">
          <el-input
              v-model="searchQuery"
              placeholder="搜索菜品"
              prefix-icon="el-icon-search"
              @keyup.enter.native="handleSearch"
              clearable
              class="search-input">
          </el-input>
        </div>
      </div>
    </el-card>

    <div v-loading="loading" style="min-height: 400px;">
      <div v-if="filteredDishes.length === 0 && !loading" class="empty-state">
        <el-empty description="暂无菜品数据"></el-empty>
      </div>
      <el-row :gutter="20" class="dishes-list" v-else>
        <el-col :span="6" v-for="dish in filteredDishes" :key="dish.id">
          <el-card :body-style="{ padding: '0px' }" class="dish-card" @click.native="goToDishDetail(dish)">
            <img :src="dish.imageUrl" :alt="dish.name" class="dish-image">
            <div class="dish-content">
              <h3 class="dish-name">{{ dish.name }}</h3>
              <div class="dish-info">
                <el-rate
                    v-model="dish.rating"
                    disabled
                    :colors="['#99A9BF', '#F7BA2A', '#FF9900']"
                    show-score>
                </el-rate>
                <span class="dish-price">¥{{ dish.price }}</span>
              </div>
              <div class="dish-tags">
                <el-tag size="small" type="info">{{ dish.category }}</el-tag>
                <el-tag size="small" type="warning" v-if="dish.isSpicy">辣</el-tag>
              </div>
              <div class="dish-description">{{ dish.description }}</div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>

    <div class="pagination-container" v-if="total > 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[12, 24, 48, 96]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
  </div>
</template>

<script>
import DishServices from '@/service/DishServices'
import CanteenServices from '@/service/CanteenServices'
import CampusServices from '@/service/CampusServices'

export default {
  name: 'DishesList',
  data() {
    return {
      searchQuery: '',
      selectedCampus: '',
      selectedCanteen: '',
      selectedCategory: '',
      sortBy: 'rating',
      loading: false,
      dishes: [],
      currentPage: 1,
      pageSize: 12, // 3行4列
      total: 0,
      campusOptions: [],
      canteenOptions: [],
      categoryOptions: [
        { value: '', label: '全部分类' },
        { value: '主食', label: '主食' },
        { value: '汤品', label: '汤品' },
        { value: '小菜', label: '小菜' },
        { value: '甜品', label: '甜品' },
        { value: '饮品', label: '饮品' }
      ]
    }
  },
  computed: {
    dishesModel() {
      return (Array.isArray(this.dishes) ? this.dishes : [])
          .map(dish => ({
            id: dish.id,
            name: dish.name,
            imageUrl: dish.imageUrl || `https://via.placeholder.com/300x200?text=${encodeURIComponent(dish.name)}`,
            rating: (dish.liked || 0) / 100, // 将点赞数转换为评分（0-5分）
            price: dish.price || 0,
            category: dish.category || '未分类',
            isSpicy: dish.isSpicy || false,
            description: dish.description || '暂无介绍',
            stallName: dish.stallName,
            canteenName: dish.canteenName
          }))
    },
    filteredDishes() {
      // 直接返回后端处理过的数据，不做前端筛选
      return this.dishesModel
    },
    searchParams() {
      return {
        page: this.currentPage,
        size: this.pageSize,
        sortBy: this.sortBy,
        keyword: this.searchQuery,
        campusId: this.selectedCampus,
        canteenId: this.selectedCanteen
      }
    }
  },
  mounted() {
    const searchParam = this.$route.query.search
    if (searchParam) {
      this.searchQuery = searchParam
    }
    this.loadCampuses()
    this.fetchDishes()
  },
  methods: {
    handleSearch() {
      this.currentPage = 1
      this.fetchDishes()
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.currentPage = 1
      this.fetchDishes()
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchDishes()
    },
    goToDishDetail(dish) {
      if (!dish?.id) {
        this.$message.error('菜品信息不完整')
        return
      }
      this.$router.push({
        path: '/main/user/dish-details',
        query: { id: dish.id }
      })
    },
    handleCampusChange(campusId) {
      this.selectedCanteen = ''
      this.canteenOptions = [];
      this.currentPage = 1;

      if (campusId) {
        this.fetchCanteensByCampus(campusId).then(() => {
          this.fetchDishes();
        });
      } else {
        this.fetchDishes();
      }
    },
    handleSortChange() {
      this.currentPage = 1
      this.fetchDishes()
    },
    loadCampuses() {
      CampusServices.getAllCampuses()
          .then(res => {
            if (res.success && Array.isArray(res.data)) {
              this.campusOptions = res.data.map(c => ({ id: c.id, name: c.name || c.campusName || c.id }))
            }
          })
          .catch(error => {
            console.error('获取校区列表失败:', error)
            this.$message.error('获取校区列表失败')
          })
    },
    fetchCanteensByCampus(campusId) {
      return CanteenServices.GetCanteensByCampus(campusId)
          .then(response => {
            if (response.success) {
              this.canteenOptions = response.data || []
            }
          })
          .catch(error => {
            console.error('获取食堂列表失败:', error)
            this.$message.error('获取食堂列表失败')
          })
    },
    fetchDishes() {
      this.loading = true
      const params = {
        current: this.currentPage,
        size: this.pageSize,
        sortBy: this.sortBy,
        keyword: this.searchQuery,
        campusId: this.selectedCampus,
        canteenId: this.selectedCanteen,
        category: this.selectedCategory
      }
      DishServices.SearchDishes(params)
          .then(response => {
            if (response.success) {
              // 适配后端返回格式：data直接是数组，total在同级
              this.dishes = response.data || []
              this.total = response.total || 0
            } else {
              this.$message.error(response.errorMsg || '获取菜品列表失败')
            }
          })
          .catch(error => {
            console.error('获取菜品列表失败:', error)
            this.$message.error('获取菜品列表失败')
          })
          .finally(() => {
            this.loading = false
          })
    }
  },
  watch: {
    selectedCategory() {
      this.handleSearch()
    },
    sortBy() {
      this.handleSearch()
    }
  }
}
</script>

<style scoped>
@import '@/assets/css/list-common.scss';
.dishes-list-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 20px;
}

.page-header h1 {
  margin: 0;
  color: #303133;
  font-size: 24px;
  font-weight: 600;
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

.dishes-list {
  margin-top: 20px;
}

.dish-card {
  margin-bottom: 20px;
  border-radius: 8px;
  overflow: hidden;
  transition: all 0.3s;
  cursor: pointer;
}

.dish-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 18px 0 rgba(0,0,0,0.15);
}

.dish-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  display: block;
}

.dish-content {
  padding: 15px;
}

.dish-name {
  margin: 0 0 10px 0;
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.dish-info {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.dish-price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: 600;
}

.dish-tags {
  margin-bottom: 10px;
}

.dish-tags .el-tag {
  margin-right: 8px;
}

.dish-description {
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

.pagination-container {
  margin-top: 30px;
  text-align: center;
}
</style>