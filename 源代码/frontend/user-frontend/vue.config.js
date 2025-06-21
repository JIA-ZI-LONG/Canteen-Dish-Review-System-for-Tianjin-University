const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      // 关键：代理所有以 /api 开头的请求
      '/api': {
        target: 'http://localhost:8090', // 目标后端地址
        changeOrigin: true, // 必须为 true
        // 注意：此处不再需要 pathRewrite
      },
      '/api1':{
        target: 'http://localhost:8199', // 目标后端地址
        changeOrigin: true, // 必须为 true
        // 注意：此处不再需要 pathRewrite
      }
    }
  }
})