import http from '../service/Request'

export default {
    /** 查询我的通知列表 */
    GetMyNotifications(current = 1) {
        return http.get('/api/notifications/my', { params: { current } })
    },
    /** 查询我的未读通知数量 */
    GetMyUnreadCount() {
        return http.get('/api/notifications/my/unread-count')
    },
    /** 将所有通知标记为已读 */
    ReadAllNotifications() {
        return http.put('/api/notifications/read/all')
    }
}