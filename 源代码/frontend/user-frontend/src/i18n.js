import Vue from 'vue'
import VueI18n from 'vue-i18n'

// 引入语言文件
import zh from './locales/zh.json'
import en from './locales/en.json'

Vue.use(VueI18n)

// 从 localStorage 获取语言设置，默认为中文 'zh'
const locale = localStorage.getItem('lang') || 'zh'

const i18n = new VueI18n({
    locale, // 设置当前语言
    messages: {
        zh,
        en
    }
})

export default i18n