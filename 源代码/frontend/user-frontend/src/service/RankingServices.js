import http from '../service/Request'

export default {
    /** 获取食堂排行榜 */
    getCanteenRanking(sortBy = 'score', limit = 10) {
        return http.get('/api/rankings/canteens', {
            params: { sortBy, limit }
        })
    },

    /** 获取菜品排行榜 */
    getDishRanking(sortBy = 'score', limit = 10, canteenId = null) {
        const params = { sortBy, limit }
        if (canteenId) {
            params.canteenId = canteenId
        }
        return http.get('/api/rankings/dishes', { params })
    },

    /** 获取博客排行榜 */
    getBlogRanking(sortBy = 'likes', limit = 10, timeRange = 'all') {
        return http.get('/api/rankings/blogs', {
            params: { sortBy, limit, timeRange }
        })
    },

    /** 获取用户排行榜 */
    getUserRanking(sortBy = 'credits', limit = 10) {
        return http.get('/api/rankings/users', {
            params: { sortBy, limit }
        })
    },

    /** 获取综合排行榜数据 */
    getRankingSummary() {
        return http.get('/api/rankings/summary')
    }
}
