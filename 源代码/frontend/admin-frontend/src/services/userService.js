import * as userApi from '@/api/user';
import { setToken, removeToken, getToken } from '@/utils/auth';

class UserService {
    /**
     * 用户登录业务
     * @param {Object} loginData - 包含用户名和密码的登录数据
     * @returns {Promise} - 返回登录结果的Promise
     */
    login(loginData) {
        // 调用API
        return userApi.login(loginData).then(response => {
            // 后端成功时返回 AdminInfoVO 对象，包含token
            if (response && response.token) {
                setToken(response.token);
                // 返回整个用户信息对象，以便存入store
                return Promise.resolve(response);
            } else {
                return Promise.reject(new Error('登录失败，未收到Token'));
            }
        }).catch(error => {
            console.error('登录失败:', error);
            return Promise.reject(error);
        });
    }

    /**
     * 获取当前登录管理员的信息
     * @returns {Promise} - 返回用户信息的Promise
     */
    getUserInfo() {
        // 调用API
        return userApi.getInfo().then(response => {
            return response;
        }).catch(error => {
            console.error('获取用户信息失败:', error);
            return Promise.reject(error);
        });
    }

    /**
     * 处理用户登出业务
     * @returns {Promise} - 返回登出结果的Promise
     */
    logout() {
        const token = getToken();
        if (token) {
            // 调用后端的登出接口
            return userApi.logout(token).then(() => {
                removeToken();
                return Promise.resolve();
            }).catch(error => {
                console.error('登出失败:', error);
                // 即使API调用失败，也要清除本地token
                removeToken();
                return Promise.reject(error);
            });
        } else {
            removeToken();
            return Promise.resolve();
        }
    }
}

export default new UserService();