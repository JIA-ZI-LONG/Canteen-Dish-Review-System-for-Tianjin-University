<template>
  <div class="add-blog-page">
    <el-card class="blog-form-card">
      <div slot="header" class="clearfix">
        <h2 class="form-title">发布新博客</h2>
        <p class="form-subtitle">分享您的美食体验，点亮校园生活</p>
      </div>

      <!-- 作者信息区域 -->
      <div class="author-section">
        <div class="author-info">
          <!-- 头像上传组件 -->
          <div class="avatar-section">
            <label class="form-label">作者头像</label>
            <AvatarUpload 
              v-model="userAvatar"
              size="medium"
              :show-buttons="true"
              :show-refresh="true"
              placeholder-text="上传头像"
              @success="handleAvatarSuccess"
              @error="handleAvatarError"
            />
            <p class="avatar-tip">建议上传清晰的个人头像，支持JPG、PNG等格式</p>
          </div>
          
          <div class="user-info">
            <div class="user-name">{{ currentUser.nickname || '匿名用户' }}</div>
            <div class="user-meta">
              <span class="campus">{{ currentUser.campus || '未知校区' }}</span>
              <span class="separator">·</span>
              <span class="date">{{ currentDate }}</span>
            </div>
          </div>
        </div>
      </div>

      <el-form :model="blogForm" :rules="rules" ref="blogFormRef" label-position="top">
        <el-form-item label="标题" prop="title">
          <el-input v-model="blogForm.title" placeholder="请输入一个吸引人的标题.." size="large"/>
        </el-form-item>

        <el-form-item label="封面图" prop="imageUrl">
          <el-upload
              class="cover-uploader"
              :action="uploadUrl"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleCoverUploadSuccess"
              :before-upload="beforeUpload"
              drag>
            <img v-if="blogForm.imageUrl" :src="blogForm.imageUrl" class="cover-image" alt="封面图">
            <div v-else class="upload-placeholder">
              <i class="el-icon-upload"></i>
              <div class="el-upload__text">将封面图拖到此处，或<em>点击上传</em></div>
            </div>
          </el-upload>
          <div class="el-upload__tip">为您的博客选择一张漂亮的封面图吧！(仅支持JPG/PNG, 小于2MB)</div>
        </el-form-item>

        <el-form-item label="正文内容" prop="content">
          <TipTapEditor v-model="blogForm.content" placeholder="在这里开始您的创作..."/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="publishBlog" :loading="publishing" size="large" style="width: 100%;">       
            {{ publishing ? '发布中..' : '确认发布' }}
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import TipTapEditor from '@/components/TipTapEditor.vue'
import AvatarUpload from '@/components/AvatarUpload.vue'
import BlogServices from '@/service/BlogServices'
import { getCurrentUser } from '@/service/UserServices'

export default {
  name: 'AddBlog',
  components: {
    TipTapEditor,
    AvatarUpload
  },
  data() {
    return {
      // 当前用户信息
      currentUser: {},
      userAvatar: '',
      
      blogForm: {
        title: '',
        content: '',
        imageUrl: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入博客标题', trigger: 'blur' },
          { min: 5, max: 100, message: '标题长度在5到100个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入博客内容', trigger: 'blur' },
          { min: 20, max: 5000, message: '内容长度在20到5000个字符', trigger: 'blur' }
        ]
      },
      publishing: false,
      uploadUrl: process.env.VUE_APP_BASE_API + '/api/files/upload',
      uploadHeaders: {
        'Authorization': 'Bearer ' + localStorage.getItem('token')
      }
    }
  },
  
  computed: {
    currentDate() {
      return new Date().toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric'
      })
    }
  },
  
  async created() {
    await this.loadUserInfo()
  },
  
  methods: {
    // 加载用户信息
    async loadUserInfo() {
      try {
        const response = await getCurrentUser()
        if (response.success) {
          this.currentUser = response.data
          this.userAvatar = response.data.icon || ''
        }
      } catch (error) {
        console.error('加载用户信息失败:', error)
      }
    },
    
    // 头像上传成功
    handleAvatarSuccess(avatarUrl) {
      this.userAvatar = avatarUrl
      this.$message.success('头像更新成功！')
    },
    
    // 头像上传失败
    handleAvatarError(error) {
      console.error('头像上传失败:', error)
    },

    beforeUpload(file) {
      const isJPGOrPNG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPGOrPNG) {
        this.$message.error('上传图片只能是 JPG/PNG 格式!')
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 2MB!')
        return false
      }
      return true
    },

    handleCoverUploadSuccess(response) {
      if (response.success) {
        this.blogForm.imageUrl = response.data
        this.$message.success('封面图上传成功!')
      } else {
        this.$message.error('封面图上传失败: ' + response.errorMsg)
      }
    },

    async publishBlog() {
      try {
        await this.$refs.blogFormRef.validate()
        
        this.publishing = true
        
        const response = await BlogServices.CreateBlog(this.blogForm)
        
        if (response.success) {
          this.$message.success('博客发布成功！等待管理员审核...')
          // 清空表单
          this.resetForm()
          // 跳转到博客列表
          this.$router.push('/main/user/blogs-list')
        } else {
          this.$message.error(response.errorMsg || '博客发布失败')
        }
      } catch (error) {
        console.error('博客发布失败:', error)
        this.$message.error('博客发布失败，请稍后重试')
      } finally {
        this.publishing = false
      }
    },
    
    // 重置表单
    resetForm() {
      this.$refs.blogFormRef.resetFields()
      this.blogForm = {
        title: '',
        content: '',
        imageUrl: ''
      }
    }
  }
}
</script>

<style scoped>
.add-blog-page {
  min-height: 100vh;
  background-color: #f5f7fa;
  padding: 20px;
}

.blog-form-card {
  max-width: 800px;
  margin: 0 auto;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.form-title {
  margin: 0;
  color: #333;
  font-size: 24px;
  font-weight: 600;
}

.form-subtitle {
  margin: 8px 0 0 0;
  color: #666;
  font-size: 14px;
}

.author-section {
  margin-bottom: 30px;
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.author-info {
  display: flex;
  align-items: center;
  gap: 20px;
}

.avatar-section {
  text-align: center;
}

.form-label {
  display: block;
  margin-bottom: 10px;
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

.avatar-tip {
  margin-top: 8px;
  font-size: 12px;
  color: #666;
  line-height: 1.4;
}

.user-info {
  flex: 1;
}

.user-name {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin-bottom: 5px;
}

.user-meta {
  color: #666;
  font-size: 14px;
}

.separator {
  margin: 0 8px;
}

.cover-uploader {
  width: 100%;
}

.cover-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.cover-uploader .el-upload:hover {
  border-color: #409EFF;
}

.cover-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.upload-placeholder {
  text-align: center;
  color: #8c939d;
}

.upload-placeholder i {
  font-size: 28px;
  color: #8c939d;
  margin-bottom: 16px;
}

.el-upload__text {
  color: #606266;
  font-size: 14px;
}

.el-upload__text em {
  color: #409EFF;
}

.el-upload__tip {
  font-size: 12px;
  color: #606266;
  margin-top: 7px;
}

@media (max-width: 768px) {
  .add-blog-page {
    padding: 10px;
  }
  
  .author-info {
    flex-direction: column;
    text-align: center;
    gap: 15px;
  }
}
</style>
