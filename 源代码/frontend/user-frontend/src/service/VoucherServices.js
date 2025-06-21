import http from '../service/Request'

export default {
    /** 【修正新增】根据条件搜索优惠券列表 */
    searchVouchers(params) {
        // 直接使用后台管理提供的列表接口，因为它支持筛选
        return http.get('/api/vouchers', { params });
    },

    /** 查询指定窗口可用的优惠券列表 (此方法可以保留或删除) */
    GetVouchersByStallId(stallId) {
        return http.get(`/api/vouchers/stalls/${stallId}`)
    },

    /** 兑换一张优惠券 */
    RedeemVoucher(id) {
        return http.patch(`/api/vouchers/${id}/redeem`)
    },

    /** 查询我的优惠券*/
    GetMyVouchers() {
        return http.get(`/api/vouchers/my`)
    }
}
