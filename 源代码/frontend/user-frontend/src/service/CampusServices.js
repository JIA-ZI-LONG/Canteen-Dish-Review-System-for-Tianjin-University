import http from '../service/Request'

export default {
  /** 查询所有校区列�?*/
  getAllCampuses() {
    return http.get('/api/campuses')
  },
  /** 根据ID查询单个校区 */
  getCampusById(id) {
    return http.get(`/api/campuses/${id}`)
  }
}
