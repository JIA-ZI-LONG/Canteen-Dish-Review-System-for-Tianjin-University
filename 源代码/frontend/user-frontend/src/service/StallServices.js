import http from '../service/Request'

export default {
    /** 查询单个窗口详情 */
    GetStallById(id) {
        return http.get(`/api/stalls/${id}`)
    },
    /** 查询食堂下的所有窗口 */
    GetStallsByCanteenId(canteenId) {
        return http.get(`/api/stalls/canteen/${canteenId}`)
    }
}
