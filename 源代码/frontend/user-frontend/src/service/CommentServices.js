import http from '../service/Request'

export default {
    /** 发布评论 */
    CreateComment(comment) {
        return http.post(`/api/comments`, comment)
    },
    /** 分页查询指定对象的评论列表 */
    GetComments(targetId, type, params) {
        return http.get(`/api/comments/${targetId}`, {
            params: { type, ...params }
        })
    }
}
