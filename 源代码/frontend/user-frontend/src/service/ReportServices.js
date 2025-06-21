import http from '../service/Request'

export default {
    /**
     * 提交举报
     * @param {Object} reportData - 举报数据
     * @param {number} reportData.targetId - 被举报对象ID
     * @param {number} reportData.targetType - 被举报对象类型 (1=博客, 2=评价, 3=评论, 4=用户)
     * @param {string} reportData.reason - 举报原因
     * @param {string} reportData.description - 详细描述
     * @returns {Promise} API响应
     */
    submitReport(reportData) {
        return http.post('/api/reports', reportData)
    }
}
