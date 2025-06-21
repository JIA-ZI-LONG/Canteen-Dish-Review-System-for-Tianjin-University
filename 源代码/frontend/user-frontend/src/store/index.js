import Vue from 'vue'
import Vuex from 'vuex'
import VoucherServices from '../service/VoucherServices' // 新增

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 存储Token
    token: localStorage.getItem('token') || null,
    // 存储用户信息对象
    user: JSON.parse(localStorage.getItem('user')) || null,
    // 新增：主题状态，从 localStorage 读取，默认为 'light'
    theme: localStorage.getItem('theme') || 'light',
    // 新增：我的优惠券数量
    voucherCount: parseInt(localStorage.getItem('voucherCount') || '0')
  },
  getters: {
    // 获取Token
    token: state => state.token,
    // 是否已登录
    isLoggedIn: state => !!state.token,
    // 获取用户信息
    currentUser: state => state.user,
    // 获取用户头像
    userIcon: state => (state.user ? state.user.icon : null),
    // 获取用户昵称
    userNickname: state => (state.user ? state.user.nickname : '用户'),
    // 新增：获取主题
    currentTheme: state => state.theme,
    // 新增：我的优惠券数量
    voucherCount: state => state.voucherCount,
  },
  mutations: {
    // 设置用户信息和Token
    SET_USER_DATA(state, userData) {
      state.token = userData.token;
      state.user = {
        id: userData.id,
        nickname: userData.nickname,
        icon: userData.icon
      };
      // 持久化存储
      localStorage.setItem('token', userData.token);
      localStorage.setItem('user', JSON.stringify(state.user));
    },
    // 清除用户信息和Token
    CLEAR_USER_DATA(state) {
      state.token = null;
      state.user = null;
      localStorage.removeItem('token');
      localStorage.removeItem('user');
    },
    // 新增：设置主题
    // 新增：设置优惠券数量
    SET_VOUCHER_COUNT(state, count) {
      state.voucherCount = count;
      localStorage.setItem('voucherCount', count);
    },
    SET_THEME(state, theme) {
      state.theme = theme;
      localStorage.setItem('theme', theme);
      // 同时更新 <html> 标签的 class，触发CSS变量变化
      document.documentElement.className = theme;
    }
  },
  actions: {
    // 登录Action
    login({ commit }, userData) {
      commit('SET_USER_DATA', userData);
    },
    // 退出登录Action
    logout({ commit }) {
      commit('CLEAR_USER_DATA');
    },
    // 新增：切换主题的 action
    // 新增：重新加载优惠券数量（从后端查询）
    async reloadVoucherCount({ commit }) {
      try {
        const res = await VoucherServices.GetMyVouchers();
        if (res && res.success) {
          const count = Array.isArray(res.data) ? res.data.length : (res.total || 0);
          commit('SET_VOUCHER_COUNT', count);
        }
      } catch (e) { console.error('刷新优惠券数量失败', e); }
    },
    toggleTheme({ commit, state }) {
      const newTheme = state.theme === 'light' ? 'dark' : 'light';
      commit('SET_THEME', newTheme);
    }
  },
  modules: {}
})