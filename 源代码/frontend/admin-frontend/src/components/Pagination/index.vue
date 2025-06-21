<template>
  <div class="pagination-container" style="text-align: center; padding: 20px 0;">
    <el-pagination
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :page-size="limit"
      :current-page="page"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
export default {
  name: 'Pagination',
  props: {
    total: {
      type: Number,
      required: true
    },
    page: {
      type: Number,
      required: true
    },
    limit: {
      type: Number,
      required: true
    }
  },
  methods: {
    handleSizeChange(val) {
      this.$emit('update:limit', val)
      this.$emit('pagination', { page: this.page, limit: val })
    },
    handleCurrentChange(val) {
      this.$emit('update:page', val)
      this.$emit('pagination', { page: val, limit: this.limit })
    }
  }
}
</script>

<style scoped>
.pagination-container {
  margin-top: 20px;
}
</style>
