// 博客相关API服务 - Vue2版本
import Request from './Request'

const BlogServices = {
  /**
   * 发布新博客
   * @param {Object} blogData - 博客数据
   * @param {string} blogData.title - 博客标题
   * @param {string} blogData.content - 博客内容（富文本HTML）
   * @returns {Promise} API响应
   */
  createBlog(blogData) {
    return Request.post('/api/blogs', blogData)
  },

  /**
   * 获取博客详情
   * @param {number} id - 博客ID
   * @returns {Promise} API响应
   */
  getBlogById(id) {
    return Request.get(`/api/blogs/${id}`)
  },

  /**
   * 获取热门博客列表
   * @param {number} current - 当前页码
   * @returns {Promise} API响应
   */
  getHotBlogs(current = 1) {
    return Request.get('/api/blogs/hot', { current })
  },

  /**
   * 获取博客列表
   * @param {Object} params - 查询参数
   * @param {number} params.current - 当前页码
   * @param {number} params.size - 每页数量
   * @param {string} params.sortBy - 排序方式 (time/liked/comments/favorites)
   * @returns {Promise} API响应
   */
  getBlogList(params) {
    // 确保查询参数通过 axios 的 params 选项正确传递
    return Request.get('/api/blogs', { params })
  },

  /**
   * 点赞或取消点赞博客
   * @param {number} id - 博客ID
   * @returns {Promise} API响应
   */
  likeBlog(id) {
    return Request.put(`/api/blogs/like/${id}`)
  },

  /**
   * 获取博客点赞用户列表
   * @param {number} id - 博客ID
   * @returns {Promise} API响应
   */
  getBlogLikes(id) {
    return Request.get(`/api/blogs/likes/${id}`)
  },

  /**
   * 收藏或取消收藏博客
   * @param {number} id - 博客ID
   * @returns {Promise} API响应
   */
  favoriteBlog(id) {
    return Request.put(`/api/blogs/favorite/${id}`)
  },

  /**
   * 获取用户收藏的博客列表
   * @param {Object} params - 查询参数
   * @returns {Promise} API响应
   */
  getFavoriteBlogs(params) {
    return Request.get('/api/blogs/favorites', { params })
  },

  /**
   * 删除博客
   * @param {number} id - 博客ID
   * @returns {Promise} API响应
   */
  deleteBlog(id) {
    return Request.delete(`/api/blogs/${id}`)
  },

  /**
   * 更新博客
   * @param {number} id - 博客ID
   * @param {Object} blogData - 博客数据
   * @returns {Promise} API响应
   */
  updateBlog(id, blogData) {
    return Request.put(`/api/blogs/${id}`, blogData)
  },

  /**
   * 举报博客
   * 实际调用统一举报接口 /api/reports
   * @param {number} id - 博客ID
   * @param {Object} reportData - 举报数据 (reason, description)
   * @returns {Promise} API响应
   */
  reportBlog(id, reportData) {
    const payload = {
      targetId: id,
      targetType: 1, // 1 = 博客
      ...reportData
    }
    return Request.post('/api/reports', payload)
  },

  // 为了兼容现有代码，添加别名方法
  GetBlogs(params) {
    return this.getBlogList(params)
  },

  CreateBlog(blogData) {
    return this.createBlog(blogData)
  },

  GetBlogById(id) {
    return this.getBlogById(id)
  },

  LikeBlog(id) {
    return this.likeBlog(id)
  },

  FavoriteBlog(id) {
    return this.favoriteBlog(id)
  },

  GetHotBlogs(current) {
    return this.getHotBlogs(current)
  },

  GetFavoriteBlogs(params) {
    return this.getFavoriteBlogs(params)
  },

  DeleteBlog(id) {
    return this.deleteBlog(id)
  },

  UpdateBlog(id, blogData) {
    return this.updateBlog(id, blogData)
  },

  ReportBlog(id, reportData) {
    return this.reportBlog(id, reportData)
  }
}

export default BlogServices
