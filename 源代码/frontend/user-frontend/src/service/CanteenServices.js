import http from '../service/Request'

export default {
  /**
   * 查询食堂列表（支持按校区过滤）
   * @param {Object} params - 查询参数
   * @param {number} params.campusId - 校区ID
   * @returns {Promise} API响应
   */
  getCanteens(params = {}) {
    // 后端目前仅支持 campusId 过滤，其余参数忽略即可
    const { campusId } = params
    return http.get('/api/canteens', { params: { campusId } })
  },

  /**
   * 查询所有食堂列表
   * @returns {Promise} API响应
   */
  getAllCanteens() {
    return http.get('/api/canteens')
  },

  /**
   * 查询单个食堂详情
   * @param {number} id - 食堂ID
   * @returns {Promise} API响应
   */
  getCanteenById(id) {
    return http.get(`/api/canteens/${id}`)
  },

  /**
   * 按校区查询食堂列表
   * @param {number} campusId - 校区ID
   * @returns {Promise} API响应
   */
  getCanteensByCampus(campusId) {
    return http.get('/api/canteens', { params: { campusId } })
  },

  /**
   * 查询食堂下的所有窗口列表
   * @param {number} canteenId - 食堂ID
   * @returns {Promise} API响应
   */
  getStallsByCanteenId(canteenId) {
    return http.get(`/api/stalls/canteen/${canteenId}`)
  },

  /**
   * 获取食堂评分
   * @param {number} id - 食堂ID
   * @returns {Promise} API响应
   */
  getCanteenRating(id) {
    return http.get(`/api/canteens/${id}/rating`)
  },

  /**
   * 评价食堂
   * @param {number} id - 食堂ID
   * @param {Object} ratingData - 评价数据
   * @returns {Promise} API响应
   */
  rateCanteen(id, ratingData) {
    return http.post(`/api/canteens/${id}/rating`, ratingData)
  },

  /**
   * 获取食堂评价列表
   * @param {number} id - 食堂ID
   * @param {Object} params - 查询参数
   * @returns {Promise} API响应
   */
  getCanteenReviews(id, params = {}) {
    return http.get(`/api/canteens/${id}/reviews`, { params })
  },

  // 为了兼容现有代码，添加别名方法
  GetAllCanteens() {
    return this.getAllCanteens()
  },

  GetCanteenById(id) {
    return this.getCanteenById(id)
  },

  GetCanteensByCampus(campusId) {
    return this.getCanteensByCampus(campusId)
  },

  GetStallsByCanteenId(canteenId) {
    return this.getStallsByCanteenId(canteenId)
  },

  GetCanteens(params) {
    return this.getCanteens(params)
  },

  GetCanteenRating(id) {
    return this.getCanteenRating(id)
  },

  RateCanteen(id, ratingData) {
    return this.rateCanteen(id, ratingData)
  },

  GetCanteenReviews(id, params) {
    return this.getCanteenReviews(id, params)
  }
}
