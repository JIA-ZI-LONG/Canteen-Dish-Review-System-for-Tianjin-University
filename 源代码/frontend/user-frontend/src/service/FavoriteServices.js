import http from '../service/Request'

export default {
    /** 添加收藏 */
    AddFavorite(favorite) {
        return http.post(`/api/favorites`, favorite)
    },
    /** 取消收藏 */
    RemoveFavorite(favoriteId) {
        return http.delete(`/api/favorites/${favoriteId}`)
    },
    /** 分页查询用户的收藏列表 */
    GetUserFavorites(type, params) {
        return http.get(`/api/favorites/user`, {
            params: { type, ...params }
        })
    }
}
