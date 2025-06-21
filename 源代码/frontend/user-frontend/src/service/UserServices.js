// 用户相关API服务
import Request from './Request'

/**
 * 上传并更新用户头像
 * @param {File} file - 头像图片文件
 * @returns {Promise} API响应
 */
export const uploadAvatar = (file) => {
  const formData = new FormData()
  formData.append('file', file)
  
  return Request.post('/api/user/avatar/upload', formData, {
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    // 上传进度回调
    onUploadProgress: (progressEvent) => {
      const percentCompleted = Math.round((progressEvent.loaded * 100) / progressEvent.total)
      console.log(`上传进度: ${percentCompleted}%`)
    }
  })
}

/**
 * 刷新用户头像URL
 * @returns {Promise} API响应
 */
export const refreshAvatarUrl = () => {
  return Request.post('/api/user/avatar/refresh')
}

/**
 * 获取当前用户信息（包含头像）
 * @returns {Promise} API响应
 */
export const getCurrentUser = () => {
  return Request.get('/api/user/me')
}

/**
 * 更新用户资料（包含头像）
 * @param {Object} userInfo - 用户信息
 * @returns {Promise} API响应
 */
export const updateUserProfile = (userInfo) => {
  return Request.put('/api/user/profile', userInfo)
}

/**
 * 用户签到
 * @returns {Promise} API响应
 */
export const userSign = () => {
  return Request.post('/api/user/sign')
}

/**
 * 头像上传相关的工具函数
 */
export const AvatarUtils = {
  /**
   * 验证图片文件
   * @param {File} file - 文件对象
   * @returns {Object} 验证结果
   */
  validateImageFile(file) {
    const result = {
      valid: true,
      message: ''
    }

    if (!file) {
      result.valid = false
      result.message = '请选择要上传的文件'
      return result
    }

    // 检查文件类型
    const allowedTypes = ['image/jpeg', 'image/jpg', 'image/png', 'image/gif', 'image/bmp', 'image/webp']
    if (!allowedTypes.includes(file.type)) {
      result.valid = false
      result.message = '只支持 JPG、PNG、GIF、BMP、WEBP 格式的图片'
      return result
    }

    // 检查文件大小（5MB）
    const maxSize = 5 * 1024 * 1024
    if (file.size > maxSize) {
      result.valid = false
      result.message = '图片文件大小不能超过5MB'
      return result
    }

    return result
  },

  /**
   * 压缩图片文件
   * @param {File} file - 原始文件
   * @param {Object} options - 压缩选项
   * @returns {Promise<File>} 压缩后的文件
   */
  compressImage(file, options = {}) {
    return new Promise((resolve, reject) => {
      const {
        maxWidth = 800,
        maxHeight = 800,
        quality = 0.8
      } = options

      const canvas = document.createElement('canvas')
      const ctx = canvas.getContext('2d')
      const img = new Image()

      img.onload = () => {
        // 计算新的尺寸
        let { width, height } = img
        
        if (width > maxWidth || height > maxHeight) {
          const ratio = Math.min(maxWidth / width, maxHeight / height)
          width *= ratio
          height *= ratio
        }

        canvas.width = width
        canvas.height = height

        // 绘制图片
        ctx.drawImage(img, 0, 0, width, height)

        // 转换为Blob
        canvas.toBlob((blob) => {
          if (blob) {
            const compressedFile = new File([blob], file.name, {
              type: file.type,
              lastModified: Date.now()
            })
            resolve(compressedFile)
          } else {
            reject(new Error('图片压缩失败'))
          }
        }, file.type, quality)
      }

      img.onerror = () => {
        reject(new Error('图片加载失败'))
      }

      img.src = URL.createObjectURL(file)
    })
  },

  /**
   * 生成头像预览URL
   * @param {File} file - 图片文件
   * @returns {string} 预览URL
   */
  createPreviewUrl(file) {
    return URL.createObjectURL(file)
  },

  /**
   * 释放预览URL
   * @param {string} url - 预览URL
   */
  revokePreviewUrl(url) {
    URL.revokeObjectURL(url)
  },

  /**
   * 获取默认头像URL
   * @param {string} nickname - 用户昵称
   * @returns {string} 默认头像URL
   */
  getDefaultAvatar(nickname = '用户') {
    // 使用第三方头像生成服务或本地默认头像
    const firstChar = nickname.charAt(0).toUpperCase()
    return `https://ui-avatars.com/api/?name=${encodeURIComponent(firstChar)}&background=409eff&color=fff&size=200`
  }
}

export default {
  uploadAvatar,
  refreshAvatarUrl,
  getCurrentUser,
  updateUserProfile,
  userSign,
  AvatarUtils
}
