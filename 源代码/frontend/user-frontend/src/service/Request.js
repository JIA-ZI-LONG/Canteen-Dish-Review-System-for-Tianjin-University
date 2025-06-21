import axios from 'axios';
import store from '@/store'

const service = axios.create({
    // 开发阶段交由 vue.config.js 代理处理，因此 baseURL 置空
    baseURL: '',
    timeout: 5000000
});

// 请求拦截器
service.interceptors.request.use(
    config => {
        const token = store.getters.token;
        if (token) {
            const formatted = token.startsWith('Bearer ') ? token : `Bearer ${token}`;
            config.headers['Authorization'] = formatted; // 统一使用首字母大写的 Authorization
        }
        return config;
    },
    error => {
        console.error('请求错误：', error);
        return Promise.reject(error);
    }
);

// 响应拦截器
service.interceptors.response.use(
    response => {
        return response.data;
    },
    error => {
        console.error('请求异常：', error);
        return Promise.reject(error);
    }
);

export default service;