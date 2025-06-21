<template>
  <div class="avatar-upload">
    <!-- 头像显示区域 -->
    <div class="avatar-container" @click="triggerFileInput">
      <div class="avatar-wrapper">
        <img 
          v-if="avatarUrl" 
          :src="avatarUrl" 
          :alt="altText"
          class="avatar-image"
          @error="handleImageError"
        />
        <div v-else class="avatar-placeholder">
          <i class="el-icon-plus"></i>
          <span>{{ placeholderText }}</span>
        </div>
        
        <!-- 上传遮罩层 -->
        <div class="avatar-overlay">
          <i class="el-icon-camera"></i>
          <span>{{ overlayText }}</span>
        </div>
      </div>
    </div>

    <!-- 隐藏的文件输入框 -->
    <input
      ref="fileInput"
      type="file"
      accept="image/jpeg,image/jpg,image/png,image/gif,image/bmp,image/webp"
      style="display: none"
      @change="handleFileSelect"
    />

    <!-- 上传进度 -->
    <div v-if="uploading" class="upload-progress">
      <el-progress 
        :percentage="uploadProgress" 
        :show-text="false"
        stroke-width="3"
      ></el-progress>
      <span class="progress-text">上传中... {{ uploadProgress }}%</span>
    </div>

    <!-- 操作按钮 -->
    <div v-if="showButtons" class="avatar-actions">
      <el-button 
        size="small" 
        type="primary" 
        @click="triggerFileInput"
        :disabled="uploading"
      >
        <i class="el-icon-upload"></i>
        {{ avatarUrl ? '更换头像' : '上传头像' }}
      </el-button>
      
      <el-button 
        v-if="avatarUrl && showRefresh" 
        size="small" 
        @click="refreshAvatar"
        :disabled="uploading"
      >
        <i class="el-icon-refresh"></i>
        刷新
      </el-button>
    </div>
  </div>
</template>

<script>
import { uploadAvatar, refreshAvatarUrl } from '@/service/UserServices'

export default {
  name: 'AvatarUpload',
  props: {
    // 当前头像URL
    value: {
      type: String,
      default: ''
    },
    // 头像大小
    size: {
      type: String,
      default: 'large', // small, medium, large
      validator: value => ['small', 'medium', 'large'].includes(value)
    },
    // 是否显示操作按钮
    showButtons: {
      type: Boolean,
      default: true
    },
    // 是否显示刷新按钮
    showRefresh: {
      type: Boolean,
      default: true
    },
    // 占位符文本
    placeholderText: {
      type: String,
      default: '点击上传头像'
    },
    // 遮罩层文本
    overlayText: {
      type: String,
      default: '点击更换'
    },
    // 图片alt属性
    altText: {
      type: String,
      default: '用户头像'
    }
  },
  data() {
    return {
      avatarUrl: this.value,
      uploading: false,
      uploadProgress: 0
    }
  },
  computed: {
    avatarSize() {
      const sizeMap = {
        small: '60px',
        medium: '80px',
        large: '120px'
      }
      return sizeMap[this.size]
    }
  },
  watch: {
    value(newVal) {
      this.avatarUrl = newVal
    }
  },
  methods: {
    // 触发文件选择
    triggerFileInput() {
      if (this.uploading) return
      this.$refs.fileInput.click()
    },

    // 处理文件选择
    handleFileSelect(event) {
      const file = event.target.files[0]
      if (!file) return

      // 验证文件类型
      const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/bmp', 'image/webp']
      if (!allowedTypes.includes(file.type)) {
        this.$message.error('只支持 JPG、PNG、GIF、BMP、WEBP 格式的图片！')
        return
      }

      // 验证文件大小（5MB）
      if (file.size > 5 * 1024 * 1024) {
        this.$message.error('头像文件大小不能超过5MB！')
        return
      }

      this.uploadFile(file)
    },

    // 上传文件
    async uploadFile(file) {
      this.uploading = true
      this.uploadProgress = 0

      try {
        // 模拟上传进度
        const progressInterval = setInterval(() => {
          if (this.uploadProgress < 90) {
            this.uploadProgress += Math.random() * 30
          }
        }, 200)

        const response = await uploadAvatar(file)

        clearInterval(progressInterval)
        this.uploadProgress = 100

        if (response.success) {
          this.avatarUrl = response.data
          this.$emit('input', response.data)
          this.$emit('success', response.data)
          this.$message.success('头像上传成功！')
        } else {
          this.$message.error(response.errorMsg || '头像上传失败')
          this.$emit('error', response.errorMsg)
        }
      } catch (error) {
        console.error('头像上传失败:', error)
        this.$message.error('头像上传失败，请稍后重试')
        this.$emit('error', error.message)
      } finally {
        this.uploading = false
        this.uploadProgress = 0
        // 清空文件输入框
        this.$refs.fileInput.value = ''
      }
    },

    // 刷新头像URL
    async refreshAvatar() {
      if (this.uploading) return

      try {
        this.uploading = true
        const response = await refreshAvatarUrl()

        if (response.success) {
          this.avatarUrl = response.data
          this.$emit('input', response.data)
          this.$emit('refresh', response.data)
          this.$message.success('头像刷新成功！')
        } else {
          this.$message.error(response.errorMsg || '头像刷新失败')
        }
      } catch (error) {
        console.error('头像刷新失败:', error)
        this.$message.error('头像刷新失败，请稍后重试')
      } finally {
        this.uploading = false
      }
    },

    // 处理图片加载错误
    handleImageError() {
      console.warn('头像图片加载失败')
      this.$emit('error', '头像图片加载失败')
    }
  }
}
</script>

<style scoped>
.avatar-upload {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.avatar-container {
  cursor: pointer;
  position: relative;
}

.avatar-wrapper {
  width: v-bind(avatarSize);
  height: v-bind(avatarSize);
  border-radius: 50%;
  overflow: hidden;
  position: relative;
  border: 2px solid #dcdfe6;
  transition: all 0.3s ease;
}

.avatar-wrapper:hover {
  border-color: #409eff;
  transform: scale(1.05);
}

.avatar-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  display: block;
}

.avatar-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: #fafafa;
  color: #909399;
  font-size: 14px;
}

.avatar-placeholder i {
  font-size: 24px;
  margin-bottom: 8px;
}

.avatar-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  color: white;
  opacity: 0;
  transition: opacity 0.3s ease;
  font-size: 12px;
}

.avatar-wrapper:hover .avatar-overlay {
  opacity: 1;
}

.avatar-overlay i {
  font-size: 20px;
  margin-bottom: 4px;
}

.upload-progress {
  width: 100%;
  max-width: 200px;
  text-align: center;
}

.progress-text {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
  display: block;
}

.avatar-actions {
  display: flex;
  gap: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .avatar-upload {
    gap: 8px;
  }
  
  .avatar-actions {
    flex-direction: column;
    width: 100%;
  }
  
  .avatar-actions .el-button {
    width: 100%;
  }
}
</style>
