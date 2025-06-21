<template>
  <div class="slide-captcha">
    <div class="captcha-container" :style="{ width: containerWidth + 'px', height: containerHeight + 'px' }">
      <div class="background-image" v-if="backgroundImage">
        <img :src="backgroundImage" :style="{ width: containerWidth + 'px', height: containerHeight + 'px' }" />
        <div
            class="puzzle-piece"
            v-if="puzzleImage"
            :style="{
            left: puzzleX + 'px',
            top: puzzleY + 'px',
            width: puzzleWidth + 'px',
            height: puzzleHeight + 'px'
          }"
        >
          <img :src="puzzleImage" :style="{ width: puzzleWidth + 'px', height: puzzleHeight + 'px' }" />
        </div>
      </div>

      <div class="slide-track">
        <div class="slide-track-bg">
          <span class="slide-text" v-if="!isDragging && !isVerified">{{ slideText }}</span>
          <span class="slide-text success" v-if="isVerified">验证成功</span>
          <span class="slide-text error" v-if="verifyFailed">验证失败，请重试</span>
        </div>

        <div
            class="slide-button"
            :class="{ 'dragging': isDragging, 'verified': isVerified, 'failed': verifyFailed }"
            :style="{ left: sliderLeft + 'px' }"
            @mousedown="startDrag"
            @touchstart="startDrag"
        >
          <i class="el-icon-d-arrow-right" v-if="!isVerified && !verifyFailed"></i>
          <i class="el-icon-check" v-if="isVerified"></i>
          <i class="el-icon-close" v-if="verifyFailed"></i>
        </div>
      </div>
    </div>

    <div class="refresh-btn" @click="refreshCaptcha">
      <i class="el-icon-refresh"></i>
      <span>刷新验证码</span>
    </div>
  </div>
</template>

<script>
import AccountServices from '@/service/AccountServices'

export default {
  name: 'SlideCaptcha',
  props: {
    width: {
      type: Number,
      default: 300
    },
    height: {
      type: Number,
      default: 150
    }
  },
  data() {
    return {
      // 验证码数据
      backgroundImage: '',
      puzzleImage: '',
      puzzleY: 0,
      uuid: '',

      // 组件状态
      isDragging: false,
      isVerified: false,
      verifyFailed: false,

      // 滑块位置
      sliderLeft: 0,
      puzzleX: 0,

      // 拖拽相关
      startX: 0,

      // 尺寸配置
      containerWidth: 300,
      containerHeight: 150,
      puzzleWidth: 50,
      puzzleHeight: 50,
      trackWidth: 300,

      slideText: '向右滑动完成验证'
    }
  },
  mounted() {
    this.containerWidth = this.width
    this.containerHeight = this.height
    this.trackWidth = this.width
    this.loadCaptcha()

    // 添加全局事件监听
    document.addEventListener('mousemove', this.onDrag)
    document.addEventListener('mouseup', this.endDrag)
    document.addEventListener('touchmove', this.onDrag)
    document.addEventListener('touchend', this.endDrag)
  },
  beforeDestroy() {
    // 移除事件监听
    document.removeEventListener('mousemove', this.onDrag)
    document.removeEventListener('mouseup', this.endDrag)
    document.removeEventListener('touchmove', this.onDrag)
    document.removeEventListener('touchend', this.endDrag)
  },
  methods: {
    // 加载验证码 (已修改为 Promise)
    loadCaptcha() {
      AccountServices.GetCaptcha()
          .then(response => {
            if (response.success && response.data) {
              this.backgroundImage = response.data.backgroundImage
              this.puzzleImage = response.data.puzzleImage
              this.puzzleY = response.data.y
              this.uuid = response.data.uuid

              // 重置状态
              this.resetState()
            } else {
              this.$message.error('验证码加载失败')
            }
          })
          .catch(error => {
            console.error('加载验证码失败:', error)
            this.$message.error('验证码加载失败')
          })
    },

    // 重置状态
    resetState() {
      this.isDragging = false
      this.isVerified = false
      this.verifyFailed = false
      this.sliderLeft = 0
      this.puzzleX = 0
    },

    // 开始拖拽
    startDrag(e) {
      if (this.isVerified || this.verifyFailed) return

      this.isDragging = true
      this.startX = e.type === 'mousedown' ? e.clientX : e.touches[0].clientX
      e.preventDefault()
    },

    // 拖拽中
    onDrag(e) {
      if (!this.isDragging) return

      const currentX = e.type === 'mousemove' ? e.clientX : e.touches[0].clientX
      const deltaX = currentX - this.startX

      // 限制滑动范围
      const maxLeft = this.trackWidth - 40 // 40是滑块宽度
      this.sliderLeft = Math.max(0, Math.min(deltaX, maxLeft))
      this.puzzleX = this.sliderLeft

      e.preventDefault()
    },

    // 结束拖拽
    endDrag() {
      if (!this.isDragging) return

      this.isDragging = false
      this.verifyPosition()
    },

    // 验证位置
    verifyPosition() {
      // 发送验证数据给父组件
      this.$emit('verify', {
        x: Math.round(this.sliderLeft),
        uuid: this.uuid
      })
    },

    // 验证成功
    onVerifySuccess() {
      this.isVerified = true
      this.verifyFailed = false
    },

    // 验证失败
    onVerifyFailed() {
      this.verifyFailed = true
      this.isVerified = false

      // 2秒后重置
      setTimeout(() => {
        this.refreshCaptcha()
      }, 2000)
    },

    // 刷新验证码
    refreshCaptcha() {
      this.loadCaptcha()
    },

    // 获取验证数据
    getVerifyData() {
      if (!this.isVerified) {
        return null
      }
      return {
        x: Math.round(this.sliderLeft),
        uuid: this.uuid
      }
    }
  }
}
</script>

<style scoped>
.slide-captcha {
  width: 100%;
}

.captcha-container {
  position: relative;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  overflow: hidden;
  margin-bottom: 10px;
}

.background-image {
  position: relative;
  width: 100%;
  height: 100%;
}

.background-image img {
  display: block;
  object-fit: cover;
}

.puzzle-piece {
  position: absolute;
  z-index: 2;
}

.puzzle-piece img {
  display: block;
}

.slide-track {
  position: relative;
  height: 40px;
  background: #f7f9fa;
  border-top: 1px solid #e4e7ed;
}

.slide-track-bg {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.slide-text {
  font-size: 14px;
  color: #909399;
  user-select: none;
}

.slide-text.success {
  color: #67c23a;
}

.slide-text.error {
  color: #f56c6c;
}

.slide-button {
  position: absolute;
  top: 0;
  left: 0;
  width: 40px;
  height: 40px;
  background: #fff;
  border: 1px solid #e4e7ed;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s;
  z-index: 3;
}

.slide-button:hover {
  border-color: #409eff;
  color: #409eff;
}

.slide-button.dragging {
  border-color: #409eff;
  color: #409eff;
  box-shadow: 0 0 6px rgba(64, 158, 255, 0.3);
}

.slide-button.verified {
  background: #67c23a;
  border-color: #67c23a;
  color: #fff;
}

.slide-button.failed {
  background: #f56c6c;
  border-color: #f56c6c;
  color: #fff;
}

.refresh-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 5px;
  padding: 5px 10px;
  color: #409eff;
  cursor: pointer;
  font-size: 12px;
  user-select: none;
}

.refresh-btn:hover {
  color: #66b1ff;
}

.refresh-btn i {
  font-size: 14px;
}
</style>