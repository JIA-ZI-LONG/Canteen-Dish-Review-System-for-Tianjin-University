import http from '../service/Request'

export default {
    // 登录
    Login(loginFormDTO) {
        return http.post(`/api/users/login`, loginFormDTO)
    },
    // 注册
    Register(registerFormDTO) {
        return http.post(`/api/users/register`, registerFormDTO)
    },
    // 退出登录
    Logout() {
        return http.post(`/api/users/logout`)
    },
    // 重置密码
    ResetPassword(passwordResetDTO) {
        return http.post(`/api/users/password`, passwordResetDTO)
    },
    // 获取当前登录用户信息
    GetCurrentUser() {
        return http.get(`/api/users/me`)
    },
    // 根据ID查询用户公开信息
    GetUserById(id) {
        return http.get(`/api/users/${id}`)
    },
    // 更新个人资料
    UpdateProfile(userDTO) {
        return http.put(`/api/users/me`, userDTO)
    },
    // 用户签到
    SignIn() {
        return http.post(`/api/users/sign`)
    },
    // 获取图形验证码
    GetCaptcha() {
        return http.get(`/api/verifications/captcha`)
    },
    // 发送邮箱验证码
    SendVerificationCode(sendCodeDTO) {
        return http.post(`/api/verifications/code`, sendCodeDTO)
    }
}