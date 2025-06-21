<template>
  <div class="bg-[#f7f8fa] min-h-screen p-4 md:p-8">
    <el-skeleton :loading="loading" animated>
      <template slot="template">
        <div class="max-w-7xl mx-auto">
          <el-skeleton :rows="10" />
        </div>
      </template>
      
      <div class="max-w-7xl mx-auto">
        <!-- 使用 flex 布局确保左右两侧高度一致 -->
        <div class="flex flex-col lg:flex-row gap-8">
          <!-- 左侧个人信息卡片 -->
          <div class="lg:w-1/3">
            <div class="sticky top-8">
              <!-- 个人信息卡片 -->
              <div class="bg-white rounded-2xl shadow-lg p-6 flex flex-col items-center transform hover:-translate-y-1 transition-transform duration-300">
                <!-- 头像上传区域 -->
                <div class="relative mb-4">
                  <AvatarUpload 
                    v-model="userInfo.avatarUrl"
                    size="large"
                    :show-buttons="false"
                    :show-refresh="true"
                    placeholder-text="上传头像"
                    overlay-text="更换头像"
                    @success="handleAvatarSuccess"
                    @error="handleAvatarError"
                  />
              </div>
              
              <!-- 头像操作按钮 -->
              <div class="avatar-actions mb-4">
                <el-button 
                  size="small" 
                  type="primary" 
                  @click="triggerAvatarUpload"
                  icon="el-icon-camera"
                >
                  更换头像
                </el-button>
              </div>
              
              <h2 class="text-2xl font-bold mt-4 text-gray-800">{{ userInfo.nickname || '用户昵称' }}</h2>
              <p class="text-sm text-gray-500 mt-1">{{ userInfo.campus || '校区未设置' }}</p>
              <p class="text-center text-gray-600 mt-4 text-sm px-4 italic">"{{ userInfo.intro || '用美食记录校园的每一个瞬间。' }}"</p>
              
              <!-- 用户统计信息 -->
              <div class="user-stats mt-6 w-full">
                <div class="stats-grid">
                  <div class="stat-item">
                    <span class="stat-number">{{ userStats.credits || 0 }}</span>
                    <span class="stat-label">积分</span>
                  </div>
                  <div class="stat-item">
                    <span class="stat-number">{{ userStats.blogCount || 0 }}</span>
                    <span class="stat-label">博客</span>
                  </div>
                  <div class="stat-item">
                    <span class="stat-number">{{ userStats.likeCount || 0 }}</span>
                    <span class="stat-label">获赞</span>
                  </div>
                  <div class="stat-item">
                    <span class="stat-number">{{ userStats.signDays || 0 }}</span>
                    <span class="stat-label">签到</span>
                  </div>
                </div>
              </div>
              
              <div class="w-full mt-6">
                <button 
                  class="w-full text-white font-bold py-3 px-4 rounded-xl transition-transform duration-200" 
                  style="background-color: var(--tju-blue);" 
                  @click="handleSign"
                  :disabled="signing"
                  onmouseover="this.style.transform='scale(1.03)'" 
                  onmouseout="this.style.transform='scale(1)'"
                >
                  <i class="ri-calendar-check-line mr-2"></i>
                  {{ signing ? '签到中...' : '每日签到' }}
                </button>
                <button 
                  class="w-full mt-3 bg-gray-100 text-gray-700 font-bold py-3 px-4 rounded-xl hover:bg-gray-200 transition-colors" 
                  @click="activeTab = 'basic'"
                >
                  <i class="ri-edit-2-line mr-2"></i>
                  编辑个人资料
                </button>
                </div>
              </div>
            </div>
          </div>
          
          <!-- 右侧内容区域 -->
          <div class="lg:w-2/3">
            <div class="bg-white rounded-2xl shadow-lg overflow-hidden h-full">
              <!-- 标签页导航 -->
            <div class="border-b border-gray-200">
              <nav class="flex space-x-8 px-6">
                <button
                  v-for="tab in tabs"
                  :key="tab.key"
                  @click="activeTab = tab.key"
                  :class="[
                    'py-4 px-1 border-b-2 font-medium text-sm transition-colors',
                    activeTab === tab.key
                      ? 'border-[var(--tju-blue)] text-[var(--tju-blue)]'
                      : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
                  ]"
                >
                  <i :class="tab.icon" class="mr-2"></i>
                  {{ tab.label }}
                </button>
              </nav>
            </div>

            <!-- 标签页内容 -->
            <div class="p-6">
              <!-- 基本信息编辑 -->
              <div v-if="activeTab === 'basic'" class="space-y-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">基本信息</h3>
                
                <el-form 
                  ref="profileForm" 
                  :model="editForm" 
                  :rules="profileRules"
                  label-width="100px"
                  class="profile-form"
                >
                  <el-form-item label="昵称" prop="nickname">
                    <el-input
                      v-model="editForm.nickname"
                      placeholder="请输入昵称"
                      maxlength="20"
                      show-word-limit
                    />
                  </el-form-item>
                  
                  <el-form-item label="性别" prop="gender">
                    <el-radio-group v-model="editForm.gender">
                      <el-radio :label="0">保密</el-radio>
                      <el-radio :label="1">男</el-radio>
                      <el-radio :label="2">女</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  
                  <el-form-item label="校区" prop="campus">
                    <el-select 
                      v-model="editForm.campus" 
                      placeholder="请选择校区"
                      style="width: 100%"
                    >
                      <el-option label="卫津路校区" value="卫津路校区" />
                      <el-option label="北洋园校区" value="北洋园校区" />
                      <el-option label="滨海工业研究院" value="滨海工业研究院" />
                    </el-select>
                  </el-form-item>
                  
                  <el-form-item label="手机号" prop="phone">
                    <el-input
                      v-model="editForm.phone"
                      placeholder="请输入手机号"
                      maxlength="11"
                    />
                  </el-form-item>
                  
                  <el-form-item label="个人简介" prop="intro">
                    <el-input
                      v-model="editForm.intro"
                      type="textarea"
                      placeholder="介绍一下自己吧..."
                      :rows="4"
                      maxlength="200"
                      show-word-limit
                    />
                  </el-form-item>
                  
                  <el-form-item>
                    <el-button 
                      type="primary" 
                      @click="saveProfile"
                      :loading="saving"
                    >
                      保存修改
                    </el-button>
                    <el-button @click="resetForm">重置</el-button>
                  </el-form-item>
                </el-form>
              </div>

              <!-- 我的博客 -->
              <div v-if="activeTab === 'blogs'" class="space-y-4">
                <div class="flex justify-between items-center">
                  <h3 class="text-lg font-semibold text-gray-900">我的博客</h3>
                  <el-button type="primary" size="small" @click="$router.push('/main/user/add-blog')">
                    <i class="el-icon-plus mr-1"></i>
                    发表博客
                  </el-button>
                </div>
                
                <div v-if="userBlogs.length === 0" class="text-center py-8 text-gray-500">
                  <i class="el-icon-document text-4xl mb-4"></i>
                  <p>还没有发表过博客</p>
                  <el-button type="text" @click="$router.push('/main/user/add-blog')">
                    立即发表第一篇博客
                  </el-button>
                </div>
                
                <div v-else class="space-y-4">
                  <div 
                    v-for="blog in userBlogs" 
                    :key="blog.id"
                    class="blog-item p-4 border border-gray-200 rounded-lg hover:shadow-md transition-shadow"
                  >
                    <h4 class="font-semibold text-gray-900 mb-2">{{ blog.title }}</h4>
                    <p class="text-gray-600 text-sm mb-2">{{ blog.content.substring(0, 100) }}...</p>
                    <div class="flex justify-between items-center text-sm text-gray-500">
                      <span>{{ formatDate(blog.createTime) }}</span>
                      <div class="flex items-center space-x-4">
                        <span><i class="el-icon-view mr-1"></i>{{ blog.views || 0 }}</span>
                        <span><i class="el-icon-star-off mr-1"></i>{{ blog.liked || 0 }}</span>
                        <span><i class="el-icon-chat-line-square mr-1"></i>{{ blog.comments || 0 }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- 账户安全 -->
              <div v-if="activeTab === 'security'" class="space-y-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-4">账户安全</h3>
                
                <div class="security-items space-y-4">
                  <div class="security-item flex justify-between items-center p-4 border border-gray-200 rounded-lg">
                    <div class="flex items-center">
                      <i class="el-icon-lock text-xl text-blue-500 mr-3"></i>
                      <div>
                        <h4 class="font-medium">登录密码</h4>
                        <p class="text-sm text-gray-500">定期更换密码，保护账户安全</p>
                      </div>
                    </div>
                    <el-button size="small" @click="showChangePassword">修改密码</el-button>
                  </div>
                  
                  <div class="security-item flex justify-between items-center p-4 border border-gray-200 rounded-lg">
                    <div class="flex items-center">
                      <i class="el-icon-message text-xl text-green-500 mr-3"></i>
                      <div>
                        <h4 class="font-medium">邮箱验证</h4>
                        <p class="text-sm text-gray-500">{{ userInfo.email || '未绑定邮箱' }}</p>
                      </div>
                    </div>
                    <el-tag :type="userInfo.email ? 'success' : 'warning'" size="small">
                      {{ userInfo.email ? '已验证' : '未验证' }}
                    </el-tag>
                  </div>
                </div>
              </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </el-skeleton>
  </div>
</template>

<script>
import AvatarUpload from '@/components/AvatarUpload.vue'
import { getCurrentUser, updateUserProfile, userSign } from '@/service/UserServices'

export default {
  name: 'UserProfile',
  components: {
    AvatarUpload
  },
  data() {
    return {
      loading: true,
      signing: false,
      saving: false,
      
      // 用户信息
      userInfo: {},
      userStats: {},
      userBlogs: [],
      
      // 标签页
      activeTab: 'basic',
      tabs: [
        { key: 'basic', label: '基本信息', icon: 'ri-user-line' },
        { key: 'blogs', label: '我的博客', icon: 'ri-article-line' },
        { key: 'security', label: '账户安全', icon: 'ri-shield-line' }
      ],
      
      // 编辑表单
      editForm: {
        nickname: '',
        gender: 0,
        campus: '',
        phone: '',
        intro: ''
      },
      
      // 密码修改
      passwordDialogVisible: false,
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      changingPassword: false,
      
      // 表单验证规则
      profileRules: {
        nickname: [
          { required: true, message: '请输入昵称', trigger: 'blur' },
          { min: 2, max: 20, message: '昵称长度在2到20个字符', trigger: 'blur' }
        ],
        phone: [
          { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
        ]
      },
      
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入原密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度在6到20个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请确认新密码', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  
  computed: {
    defaultAvatar() {
      return `https://ui-avatars.com/api/?name=${encodeURIComponent(this.userInfo.nickname || 'U')}&background=409eff&color=fff&size=200`
    }
  },
  
  async created() {
    await this.loadUserInfo()
    this.loading = false
  },
  
  methods: {
    // 加载用户信息
    async loadUserInfo() {
      try {
        const response = await getCurrentUser()
        if (response.success) {
          this.userInfo = response.data
          this.userInfo.avatarUrl = response.data.icon || ''
          
          // 初始化编辑表单
          this.editForm = {
            nickname: response.data.nickname || '',
            gender: response.data.gender || 0,
            campus: response.data.campus || '',
            phone: response.data.phone || '',
            intro: response.data.intro || ''
          }
          
          // 加载用户统计信息
          await this.loadUserStats()
        }
      } catch (error) {
        console.error('加载用户信息失败:', error)
        this.$message.error('加载用户信息失败')
      }
    },
    
    // 加载用户统计信息
    async loadUserStats() {
      try {
        // 这里可以调用获取用户统计信息的API
        this.userStats = {
          credits: this.userInfo.credits || 0,
          blogCount: 5,
          likeCount: 23,
          signDays: 15
        }
      } catch (error) {
        console.error('加载用户统计失败:', error)
      }
    },
    
    // 触发头像上传
    triggerAvatarUpload() {
      // 通过ref调用AvatarUpload组件的方法
      this.$refs.avatarUpload?.triggerFileInput()
    },
    
    // 头像上传成功
    handleAvatarSuccess(avatarUrl) {
      this.userInfo.avatarUrl = avatarUrl
      this.userInfo.icon = avatarUrl
      this.$message.success('头像更新成功！')
    },
    
    // 头像上传失败
    handleAvatarError(error) {
      console.error('头像上传失败:', error)
    },
    
    // 用户签到
    async handleSign() {
      try {
        this.signing = true
        const response = await userSign()
        if (response.success) {
          this.$message.success('签到成功！')
          // 更新积分
          this.userStats.credits += 5
          this.userStats.signDays += 1
        } else {
          this.$message.error(response.errorMsg || '签到失败')
        }
      } catch (error) {
        console.error('签到失败:', error)
        this.$message.error('签到失败，请稍后重试')
      } finally {
        this.signing = false
      }
    },
    
    // 保存个人资料
    async saveProfile() {
      try {
        await this.$refs.profileForm.validate()
        
        this.saving = true
        const response = await updateUserProfile(this.editForm)
        
        if (response.success) {
          // 更新本地用户信息
          Object.assign(this.userInfo, this.editForm)
          this.$message.success('个人资料更新成功！')
        } else {
          this.$message.error(response.errorMsg || '更新失败')
        }
      } catch (error) {
        console.error('保存个人资料失败:', error)
        this.$message.error('保存失败，请稍后重试')
      } finally {
        this.saving = false
      }
    },
    
    // 重置表单
    resetForm() {
      this.editForm = {
        nickname: this.userInfo.nickname || '',
        gender: this.userInfo.gender || 0,
        campus: this.userInfo.campus || '',
        phone: this.userInfo.phone || '',
        intro: this.userInfo.intro || ''
      }
    },
    
    // 显示修改密码对话框
    showChangePassword() {
      this.passwordDialogVisible = true
      this.passwordForm = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    },
    
    // 修改密码
    async changePassword() {
      try {
        await this.$refs.passwordForm.validate()
        
        this.changingPassword = true
        
        // 调用修改密码API
        // const response = await changePassword(this.passwordForm)
        
        // 模拟API调用
        await new Promise(resolve => setTimeout(resolve, 1000))
        
        this.$message.success('密码修改成功！')
        this.passwordDialogVisible = false
        
      } catch (error) {
        console.error('修改密码失败:', error)
        this.$message.error('修改密码失败，请稍后重试')
      } finally {
        this.changingPassword = false
      }
    },
    
    // 验证确认密码
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    },
    
    // 格式化日期
    formatDate(dateString) {
      if (!dateString) return '未知'
      return new Date(dateString).toLocaleDateString('zh-CN')
    }
  }
}
</script>

<style scoped>
/* 主容器样式 */
:deep(.el-skeleton) {
  width: 100%;
}

/* 个人信息卡片容器 */
.user-profile-container {
  display: flex;
  flex-direction: column;
  min-height: calc(100vh - 4rem);
}

/* 表单样式 */
.profile-form {
  width: 100%;
  display: flex;
  flex-direction: column;
}

/* 确保表单内容区域可以滚动 */
:deep(.el-form) {
  display: flex;
  flex-direction: column;
}

:deep(.el-form-item:last-child) {
  margin-top: auto;
  margin-bottom: 0;
}

/* 标签页内容区域 */
.tab-content {
  display: flex;
  flex-direction: column;
  height: 100%;
}

:deep(.el-tabs) {
  display: flex;
  flex-direction: column;
  height: 100%;
}

:deep(.el-tabs__content) {
  flex: 1;
  overflow: auto;
  padding: 20px;
}

:deep(.el-tab-pane) {
  min-height: 400px;
}

/* 自定义滚动条 */
:deep(::-webkit-scrollbar) {
  width: 6px;
  height: 6px;
}

:deep(::-webkit-scrollbar-track) {
  background: #f1f1f1;
  border-radius: 3px;
}

:deep(::-webkit-scrollbar-thumb) {
  background: #c1c1c1;
  border-radius: 3px;
}

:deep(::-webkit-scrollbar-thumb:hover) {
  background: #a8a8a8;
}

/* 响应式调整 */
@media (max-width: 1024px) {
  .user-profile-container {
    flex-direction: column;
  }
  
  .lg\:w-1\/3 {
    width: 100% !important;
    margin-bottom: 1.5rem;
  }
  
  .lg\:w-2\/3 {
    width: 100% !important;
  }
  
  .sticky {
    position: static !important;
  }
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.stat-item {
  text-align: center;
  padding: 12px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.stat-number {
  display: block;
  font-size: 20px;
  font-weight: 600;
  color: #333;
  margin-bottom: 4px;
}

.stat-label {
  font-size: 12px;
  color: #666;
}

.avatar-actions {
  display: flex;
  gap: 8px;
}

.blog-item {
  transition: all 0.3s ease;
}

.blog-item:hover {
  transform: translateY(-2px);
}

.security-item {
  transition: all 0.3s ease;
}

.security-item:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: repeat(4, 1fr);
    gap: 8px;
  }
  
  .stat-item {
    padding: 8px;
  }
  
  .stat-number {
    font-size: 16px;
  }
  
  .stat-label {
    font-size: 10px;
  }
}

/* 修复标签页内容区域高度 */
:deep(.el-tabs__content) {
  flex: 1;
  overflow: auto;
  padding: 20px;
  min-height: 400px;
}
</style>
