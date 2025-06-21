import Request from './Request'

const NoticeServices = {
  // 获取系统公告列表
  getNoticeList(params = {}) {
    return Request.get('/api/notices', { params })
  },

  // 根据ID获取公告详情
  getNoticeById(id) {
    return Request.get(`/api/notices/${id}`)
  },

  // 获取最新公告
  getLatestNotices(limit = 5) {
    return Request.get('/api/notices/latest', { 
      params: { limit } 
    })
  },

  // 获取重要公告
  getImportantNotices() {
    return Request.get('/api/notices/important')
  }
}

export default NoticeServices
