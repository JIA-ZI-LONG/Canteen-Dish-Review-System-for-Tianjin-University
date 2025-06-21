<template>
  <div class="voucher-container">
    <div class="page-header">
      <h1 class="page-title">🎫 优惠券秒杀</h1>
      <p class="page-subtitle">限时优惠，先到先得！</p>
    </div>
    <div class="filter-section">
      <el-card class="filter-card">
        <div class="filter-content">
          <div class="filter-item">
            <label>优惠券类型：</label>
            <el-select v-model="filterType" placeholder="全部类型" @change="handleFilterChange" clearable>
              <el-option label="全部类型" value=""></el-option>
              <el-option label="满减券" value="0"></el-option>
              <el-option label="折扣券" value="1"></el-option>
            </el-select>
          </div>
          <div class="filter-item">
            <label>适用食堂：</label>
            <el-select v-model="filterCanteen" placeholder="全部食堂" @change="handleFilterChange" clearable>
              <el-option label="全部食堂" value=""></el-option>
              <el-option
                  v-for="canteen in canteenOptions"
                  :key="canteen.id"
                  :label="canteen.name"
                  :value="canteen.id">
              </el-option>
            </el-select>
          </div>
          <div class="filter-item">
            <label>状态：</label>
            <el-select v-model="filterStatus" placeholder="全部状态" @change="handleFilterChange" clearable>
              <el-option label="全部状态" value=""></el-option>
              <el-option label="进行中" value="1"></el-option>
              <el-option label="未开始" value="0"></el-option>
              <el-option label="已结束" value="2"></el-option>
            </el-select>
          </div>
          <el-button type="primary" @click="loadVouchers" :loading="loading">
            <i class="el-icon-refresh"></i> 刷新
          </el-button>
        </div>
      </el-card>
    </div>

    <div class="voucher-list" v-loading="loading">
      <div class="voucher-grid">
        <el-card
            v-for="voucher in voucherList"
            :key="voucher.id"
            class="voucher-card"
            :class="getVoucherCardClass(voucher)"
        >
          <div class="voucher-header">
            <div class="voucher-type-badge" :class="getTypeBadgeClass(voucher.type)">
              {{ voucher.type === 0 ? '满减券' : '折扣券' }}
            </div>
            <div class="voucher-status" :class="getStatusClass(voucher)">
              {{ getStatusText(voucher) }}
            </div>
          </div>

          <div class="voucher-body">
            <div class="voucher-value">
              <span v-if="voucher.type === 0" class="value-text">¥{{ voucher.price }}</span>
              <span v-else class="value-text">{{ (voucher.discount * 10).toFixed(1) }}折</span>
              <span class="value-label">{{ voucher.type === 0 ? '立减' : '折扣' }}</span>
            </div>
            <div class="voucher-info">
              <h3 class="voucher-title">{{ voucher.title }}</h3>
              <p class="voucher-desc">{{ voucher.description }}</p>
              <div class="voucher-details">
                <div class="detail-item">
                  <span class="detail-label">最低消费：</span>
                  <span class="detail-value">¥{{ voucher.minAmount }}</span>
                </div>
                <div class="detail-item">
                  <span class="detail-label">所需积分：</span>
                  <span class="detail-value">{{ voucher.requiredCredits }} 积分</span>
                </div>
              </div>
            </div>
          </div>

          <div class="voucher-footer">
            <div class="time-info">
              <div class="time-item">
                <span class="time-label">开始时间：</span>
                <span class="time-value">{{ formatTime(voucher.startTime) }}</span>
              </div>
              <div class="time-item">
                <span class="time-label">结束时间：</span>
                <span class="time-value">{{ formatTime(voucher.endTime) }}</span>
              </div>
            </div>
            <div class="stock-info">
              <div class="stock-bar">
                <div class="stock-progress" :style="{ width: getStockPercentage(voucher) + '%' }"></div>
              </div>
              <span class="stock-text">剩余 {{ voucher.stock }} 张</span>
            </div>
            <div class="action-area">
              <el-button
                  type="danger"
                  size="large"
                  :disabled="!canRedeem(voucher)"
                  :loading="redeemingIds.includes(voucher.id)"
                  @click="redeemVoucher(voucher)"
                  class="redeem-btn"
              >
                {{ getButtonText(voucher) }}
              </el-button>
            </div>
          </div>
        </el-card>
      </div>

      <div v-if="!list.length && !loading" class="empty-state">
        <el-empty description="暂无符合条件的优惠券">
          <el-button type="primary" @click="resetFilters">查看全部</el-button>
        </el-empty>
      </div>
    </div>

    <div class="pagination-wrapper" v-if="total > 0">
      <el-pagination
          @current-change="handlePageChange"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total"
          layout="total, prev, pager, next, jumper"
          background
      />
    </div>

  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import VoucherServices from '@/service/VoucherServices'
import CanteenServices from '@/service/CanteenServices' // 【新增】引入食堂服务

export default {
  name: 'VoucherView',
  data() {
    return {
      loading: false,
      list: [],
      myVouchers: [],
      redeemingIds: [],
      canteenOptions: [], // 【新增】食堂选项
      filterType: '',
      filterCanteen: '',
      filterStatus: '',
      currentPage: 1,
      pageSize: 12,
      total: 0
    }
  },
  computed: {
    ...mapGetters(['isLoggedIn']),
    voucherList() {
      return this.list.map(v => ({...v, initialStock: v.stock > 100 ? v.stock : 100}));
    }
  },
  mounted() {
    document.title = '优惠券秒杀 - TJUFOOD';
    this.loadVouchers();
    this.loadCanteenOptions(); // 【新增】加载食堂选项
  },
  methods: {
    loadCanteenOptions() {
      CanteenServices.getAllCanteens()
          .then(response => {
            this.canteenOptions = response.data || [];
          })
          .catch(error => console.error('加载食堂列表失败:', error));
    },
    loadVouchers() {
      this.loading = true;
      const params = {
        current: this.currentPage,
        size: this.pageSize,
        type: this.filterType !== '' ? this.filterType : null,
        canteenId: this.filterCanteen !== '' ? this.filterCanteen : null,
        status: this.filterStatus !== '' ? this.filterStatus : null,
      };
      VoucherServices.searchVouchers(params)
          .then(response => {
            this.list = response.data.records || [];
            this.total = response.data.total || 0;
          })
          .catch(error => console.error('加载优惠券列表失败:', error))
          .finally(() => { this.loading = false; });
    },
    handleFilterChange() {
      this.currentPage = 1;
      this.loadVouchers();
    },
    resetFilters() {
      this.filterType = '';
      this.filterCanteen = '';
      this.filterStatus = '1';
      this.handleFilterChange();
    },
    redeemVoucher(voucher) {
      if (!this.isLoggedIn) {
        this.$message.warning('请先登录再参与活动');
        this.$router.push('/login');
        return;
      }
      if (!this.canRedeem(voucher)) {
        this.$message.error('活动未开始、已结束或库存不足');
        return;
      }
      this.redeemingIds.push(voucher.id);
      VoucherServices.RedeemVoucher(voucher.id)
          .then(() => {
            this.$message.success('秒杀请求已提交！请稍后刷新查看结果。');
            voucher.stock--;
            // 刷新全局优惠券数量
            this.$store.dispatch('reloadVoucherCount');
          })
          .catch(() => {})
          .finally(() => {
            this.redeemingIds = this.redeemingIds.filter(id => id !== voucher.id);
          });
    },
    canRedeem(voucher) {
      const now = new Date();
      return new Date(voucher.startTime) <= now && new Date(voucher.endTime) >= now && voucher.stock > 0;
    },
    getButtonText(voucher) {
      if(this.getStatusText(voucher) !== '进行中') return this.getStatusText(voucher);
      return '立即秒杀';
    },
    getVoucherCardClass(voucher) {
      if (voucher.stock <= 0) return 'voucher-sold-out';
      if (!this.canRedeem(voucher)) return 'voucher-expired';
      return 'voucher-available';
    },
    getTypeBadgeClass(type) {
      return type === 0 ? 'type-discount' : 'type-percentage';
    },
    getStatusText(voucher) {
      if (voucher.stock <= 0) return '已抢完';
      const now = new Date();
      if (new Date(voucher.startTime) > now) return '未开始';
      if (new Date(voucher.endTime) < now) return '已结束';
      return '进行中';
    },
    getStatusClass(voucher) {
      const text = this.getStatusText(voucher);
      const map = { '进行中': 'status-active', '未开始': 'status-waiting', '已抢完': 'status-expired', '已结束': 'status-expired'};
      return map[text];
    },
    getStockPercentage(voucher) {
      const initialStock = voucher.initialStock || 1000; // 假设一个较大的初始库存用于展示
      if (initialStock === 0) return 0;
      return Math.max(0, (voucher.stock / initialStock) * 100);
    },
    formatTime(timeStr) {
      if (!timeStr) return '';
      return new Date(timeStr).toLocaleString('zh-CN', { hour12: false }).replace(/\//g, '-');
    },
    handlePageChange(page) {
      this.currentPage = page;
      this.loadVouchers();
    }
  }
}
</script>

<style scoped>
.voucher-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

.page-header {
  text-align: center;
  margin-bottom: 30px;
}

.page-title {
  font-size: 28px;
  color: #303133;
  margin-bottom: 10px;
  font-weight: 600;
}

.page-subtitle {
  color: #909399;
  font-size: 14px;
  margin: 0;
}

.filter-section {
  margin-bottom: 30px;
}

.filter-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.06);
}

.filter-content {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-wrap: wrap;
}

.filter-item {
  display: flex;
  align-items: center;
  gap: 8px;
}

.filter-item label {
  font-weight: 500;
  color: #606266;
  white-space: nowrap;
  font-size: 14px;
}

.voucher-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.voucher-card {
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s ease;
  border-width: 1px;
  border-style: solid;
  box-shadow: 0 4px 8px rgba(0,0,0,0.04);
}

.voucher-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0,0,0,0.08);
}

.voucher-available {
  border-color: #e6f7ff;
  background-color: #f7feff;
}

.voucher-not-started {
  border-color: #fffbe6;
  background-color: #fffefa;
}

.voucher-sold-out {
  border-color: #fff1f0;
  background-color: #fffafa;
  opacity: 0.8;
}

.voucher-expired {
  border-color: #f5f5f5;
  background-color: #fafafa;
  opacity: 0.6;
}

.voucher-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 12px 18px;
  border-bottom: 1px dashed #e8e8e8;
}

.voucher-type-badge {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.type-discount {
  background: #fff2e8;
  color: #d46b08;
}

.type-percentage {
  background: #f6ffed;
  color: #389e0d;
}

.voucher-status {
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.status-waiting {
  background: #fffbe6;
  color: #d48806;
}

.status-active {
  background: #e6f7ff;
  color: #096dd9;
}

.status-expired {
  background: #f5f5f5;
  color: #8c8c8c;
}

.voucher-body {
  padding: 18px;
  display: flex;
  gap: 18px;
}

.voucher-value {
  text-align: center;
  min-width: 80px;
  color: #cf1322;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.value-text {
  display: block;
  font-size: 32px;
  font-weight: bold;
  line-height: 1.1;
}

.value-label {
  font-size: 14px;
  color: #d4380d;
}

.voucher-info {
  flex: 1;
}

.voucher-title {
  font-size: 16px;
  color: #333;
  margin: 0 0 8px 0;
  font-weight: 600;
}

.voucher-desc {
  color: #666;
  font-size: 13px;
  margin: 0 0 12px 0;
  line-height: 1.5;
}

.voucher-details {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.detail-item {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
}

.detail-label {
  color: #909399;
}

.detail-value {
  color: #303133;
  font-weight: 500;
}

.voucher-footer {
  padding: 15px 18px;
  background: rgba(250, 250, 250, 0.5);
  border-top: 1px dashed #e8e8e8;
}

.time-info {
  margin-bottom: 12px;
}

.time-item {
  display: flex;
  justify-content: space-between;
  font-size: 12px;
  margin-bottom: 5px;
}

.time-label {
  color: #909399;
}

.time-value {
  color: #606266;
}

.stock-info {
  margin-bottom: 12px;
}

.stock-bar {
  height: 8px;
  background: #ebeef5;
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 5px;
}

.stock-progress {
  height: 100%;
  background: linear-gradient(90deg, #67c23a, #a0e67d);
  transition: width 0.4s ease;
}
.voucher-sold-out .stock-progress, .voucher-expired .stock-progress {
  background: #dcdfe6;
}

.stock-text {
  font-size: 12px;
  color: #909399;
}

.action-area {
  margin-top: 10px;
}

.redeem-btn {
  width: 100%;
  height: 45px;
  font-size: 16px;
  font-weight: bold;
  border-radius: 8px;
}

.empty-state {
  padding: 60px 20px;
  text-align: center;
}

.pagination-wrapper {
  display: flex;
  justify-content: center;
  margin-top: 30px;
}

.my-vouchers-content {
  max-height: 50vh;
  overflow-y: auto;
  padding-right: 10px;
}

.my-voucher-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  border: 1px solid #f0f0f0;
  border-radius: 8px;
  margin-bottom: 10px;
  transition: box-shadow 0.2s;
}
.my-voucher-item:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.06);
}

.my-voucher-info h4 {
  margin: 0 0 5px 0;
  color: #333;
}

.my-voucher-info p {
  margin: 0 0 5px 0;
  color: #666;
  font-size: 14px;
}

.expire-time {
  font-size: 12px;
  color: #999;
}

.my-voucher-value {
  font-size: 24px;
  font-weight: bold;
  color: #ff4d4f;
  min-width: 80px;
  text-align: right;
}

@media (max-width: 768px) {
  .voucher-grid {
    grid-template-columns: 1fr;
  }

  .filter-content {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-item {
    justify-content: space-between;
  }

  .voucher-body {
    flex-direction: column;
    gap: 15px;
  }

  .voucher-value {
    text-align: left;
    align-items: flex-start;
  }
}
</style>