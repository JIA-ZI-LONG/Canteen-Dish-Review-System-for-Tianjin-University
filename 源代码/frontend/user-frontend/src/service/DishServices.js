import http from '../service/Request'

export default {
    /** 查询单个菜品详情 */
    GetDishById(id) {
        return http.get(`/api/dishes/${id}`)
    },
    /** 分页搜索菜品 */
    SearchDishes(params = {}) {
        // 根据前端传入的通用参数，映射成后端期望的查询参数
        const {
            page,
            current,
            size = 10,
            keyword,
            name,
            sortBy = 'liked',
            sortOrder = 'desc',
            campusId,
            canteenId
        } = params
        const query = {
            current: current || page || 1,
            size,
            sortBy,
            sortOrder,
            name: name || keyword || '',
            campusId,
            canteenId
        }
        return http.get(`/api/dishes/search`, { params: query })
    }
}