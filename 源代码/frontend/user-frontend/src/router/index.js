import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/main/home'
    },

    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('../views/RegisterView.vue')
    },
    // --- 鍦ㄨ繖閲屾坊鍔犱簡鏂扮殑鈥滃繕璁板瘑鐮佲€濊矾鐢?---
    {
      path: '/reset-password',
      name: 'reset-password',
      component: () => import('../views/ResetPasswordView.vue')
    },
    {
      path: '/search',
      name: 'search',
      component: () => import('../views/SearchPage.vue')
    },
    {
      path: '/main',
      name: 'main',
      component: () => import('../views/MainView.vue'),
      children: [
        {
          path: 'home',
          name: 'home',
          component: () => import('../views/HomeView.vue')
        },
        {
          path: 'user/profile',
          name: 'profile',
          component: () => import('../views/user/UserProfile.vue')
        },
        {
          path: 'user/disheslist',
          name: 'disheslist',
          component: () => import('../views/user/DishesList.vue')
        },
        {
          path: 'user/canteenslist',
          name: 'canteenslist',
          component: () => import('../views/user/CanteensList.vue')
        },
        {
          path: 'user/blogslist',
          name: 'blogslist',
          component: () => import('../views/user/BlogsList.vue')
        },
        {
          path: 'user/add-blog',
          name: 'addblog',
          component: () => import('../views/user/AddBlog.vue')
        },
        {
          path: 'user/canteen-details',
          name: 'canteendetails',
          component: () => import('../views/user/CanteenDetails.vue')
        },
        {
          path: 'user/window-details/:windowId/:canteenId',
          name: 'windowdetails',
          component: () => import('../views/user/WindowsList.vue')
        },
        {
          path: 'ranking',
          name: 'ranking',
          component: () => import('../views/RankingView.vue')
        },
        {
          path: 'voucher',
          name: 'voucher',
          component: () => import('../views/VoucherView.vue')
        },
        {
          path: 'notice',
          name: 'notice',
          component: () => import('../views/NoticeView.vue')
        },
        {
          path: 'about',
          name: 'about',
          component: () => import('../views/AboutView.vue')
        }
      ]
    }
  ]
})
