// 博客相关路由配置 - Vue2版本
// 将这些路由添加到您的主路由配置中

const blogRoutes = [
  {
    path: '/blog/:id',
    name: 'BlogDetail',
    component: () => import('@/views/BlogDetail.vue'),
    meta: {
      title: '博客详情',
      requiresAuth: false // 博客详情页不需要登录即可查看
    }
  },
  {
    path: '/blogs',
    name: 'BlogList',
    component: () => import('@/views/BlogList.vue'),
    meta: {
      title: '博客列表',
      requiresAuth: false
    }
  },
  {
    path: '/user/add-blog',
    name: 'AddBlog',
    component: () => import('@/views/user/AddBlog.vue'),
    meta: {
      title: '发布博客',
      requiresAuth: true // 发布博客需要登录
    }
  },
  {
    path: '/user/blogs',
    name: 'MyBlogs',
    component: () => import('@/views/user/MyBlogs.vue'),
    meta: {
      title: '我的博客',
      requiresAuth: true
    }
  }
]

export default blogRoutes

// 使用示例：
// 在您的 router/index.js 中：
/*
import Vue from 'vue'
import VueRouter from 'vue-router'
import blogRoutes from './blog-routes'

Vue.use(VueRouter)

const routes = [
  // 您现有的路由
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/HomeView.vue')
  },
  
  // 添加博客路由
  ...blogRoutes,
  
  // 其他路由...
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
*/
