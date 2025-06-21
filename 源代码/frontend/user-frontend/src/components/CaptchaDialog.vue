<template>
  <el-dialog
      title="安全验证"
      :visible.sync="visible"
      :show-close="false"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      custom-class="captcha-dialog"
      :modal-append-to-body="false"
      :lock-scroll="false"
      @opened="onDialogOpened"
      @closed="onDialogClosed">

    <div class="captcha-container">
      <div v-if="loading" class="loading-container">
        <el-loading-spinner></el-loading-spinner>
        <p>正在加载验证码...</p>
      </div>

      <div v-else class="captcha-content">
        <div class="captcha-background" ref="captchaBackground">
          <img :src="backgroundImage" alt="验证码背景" @load="onBackgroundLoad">

          <div
              class="puzzle-piece"
              ref="puzzlePiece"
              :style="puzzleStyle"
              @mousedown="startDrag"
              @touchstart="startDrag">
            <img :src="puzzleImage" alt="拼图块">
          </div>
        </div>

        <div class="slider-container">
          <div class="slider-track" ref="sliderTrack">
            <div class="slider-fill" :style="{ width: sliderPosition + 'px' }"></div>
            <div
                class="slider-button"
                ref="sliderButton"
                :style="{ left: sliderPosition + 'px' }"
                @mousedown="startSliderDrag"
                @touchstart="startSliderDrag">
              <i class="el-icon-right"></i>
            </div>
          </div>
          <div class="slider-text">{{ sliderText }}</div>
        </div>

        <div class="debug-info" v-if="showDebug">
          <p>滑块位置: {{ sliderPosition }}px</p>
          <p>UUID: {{ uuid }}</p>
          <p>拼图Y坐标: {{ puzzleY }}px</p>
        </div>
      </div>
    </div>

    <div slot="footer" class="dialog-footer">
      <el-button @click="refreshCaptcha" :loading="loading">
        <i class="el-icon-refresh"></i>
        刷新验证码
      </el-button>
      <el-button type="primary" @click="verifyCaptcha" :disabled="!canVerify">
        确认
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import AccountServices from '@/service/AccountServices'

export default {
  name: 'CaptchaDialog',
  props: {
    value: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      visible: this.value,
      loading: false,
      backgroundImage: '',
      puzzleImage: '',
      puzzleY: 0,
      uuid: '',
      sliderPosition: 0,
      isDragging: false,
      startX: 0,
      maxSliderPosition: 300,
      sliderText: '向右滑动完成验证',
      showDebug: true // 显示调试信息
    }
  },
  computed: {
    puzzleStyle() {
      return {
        left: this.sliderPosition + 'px',
        top: this.puzzleY + 'px'
      }
    },
    canVerify() {
      return this.sliderPosition > 50 && !this.loading
    }
  },
  watch: {
    value(newVal) {
      this.visible = newVal
      if (newVal) {
        this.loadCaptcha()
        this.lockBodyScroll(true)
      } else {
        this.lockBodyScroll(false)
      }
    },
    visible(newVal) {
      this.$emit('input', newVal)
      if (!newVal) {
        this.lockBodyScroll(false)
      }
    }
  },
  methods: {
    lockBodyScroll(lock) {
      if (lock) {
        document.body.style.overflow = 'hidden';
        document.body.style.position = 'fixed';
        document.body.style.width = '100%';
      } else {
        document.body.style.overflow = '';
        document.body.style.position = '';
        document.body.style.width = '';
      }
    },
    onDialogOpened() {
      // 确保对话框完全显示后重新计算位置
      this.$nextTick(() => {
        if (this.$refs.captchaBackground) {
          const rect = this.$refs.captchaBackground.getBoundingClientRect();
          this.maxSliderPosition = rect.width - 60;
        }
      });
    },
    onDialogClosed() {
      this.lockBodyScroll(false);
    },
    handleResize() {
      if (this.visible) {
        this.onDialogOpened();
      }
    },
    // 加载验证码
    loadCaptcha() {
      this.loading = true
      AccountServices.GetCaptcha()
          .then(response => {
            console.log('验证码响应:', response)

            if (response.success) {
              this.backgroundImage = response.data.backgroundImage
              this.puzzleImage = response.data.puzzleImage
              this.puzzleY = response.data.y
              this.uuid = response.data.uuid
              this.resetSlider()
            } else {
              this.$message.error('加载验证码失败：' + response.errorMsg)
            }
          })
          .catch(error => {
            console.error('加载验证码错误:', error)
            this.$message.error('网络错误，请稍后重试')
          })
          .finally(() => {
            this.loading = false
          })
    },

    // 背景图加载完成
    onBackgroundLoad() {
      this.$nextTick(() => {
        if (this.$refs.captchaBackground) {
          const rect = this.$refs.captchaBackground.getBoundingClientRect()
          this.maxSliderPosition = rect.width - 60 // 减去拼图块宽度
        }
      })
    },

    // 重置滑块
    resetSlider() {
      this.sliderPosition = 0
      this.sliderText = '向右滑动完成验证'
    },

    // 开始拖拽拼图
    startDrag(event) {
      this.startSliderDrag(event)
    },

    // 开始拖拽滑块
    startSliderDrag(event) {
      event.preventDefault()
      this.isDragging = true

      const clientX = event.touches ? event.touches[0].clientX : event.clientX
      this.startX = clientX - this.sliderPosition

      document.addEventListener('mousemove', this.onDrag)
      document.addEventListener('mouseup', this.stopDrag)
      document.addEventListener('touchmove', this.onDrag)
      document.addEventListener('touchend', this.stopDrag)
    },

    // 拖拽过程
    onDrag(event) {
      if (!this.isDragging) return

      const clientX = event.touches ? event.touches[0].clientX : event.clientX
      let newPosition = clientX - this.startX

      // 限制滑块范围
      newPosition = Math.max(0, Math.min(newPosition, this.maxSliderPosition))
      this.sliderPosition = newPosition

      // 更新提示文字
      if (newPosition > this.maxSliderPosition * 0.8) {
        this.sliderText = '松开完成验证'
      } else {
        this.sliderText = '向右滑动完成验证'
      }
    },

    // 停止拖拽
    stopDrag() {
      if (!this.isDragging) return

      this.isDragging = false
      document.removeEventListener('mousemove', this.onDrag)
      document.removeEventListener('mouseup', this.stopDrag)
      document.removeEventListener('touchmove', this.onDrag)
      document.removeEventListener('touchend', this.stopDrag)
    },

    // 验证验证码
    verifyCaptcha() {
      if (!this.canVerify) {
        this.$message.warning('请先完成滑动验证')
        return
      }

      // 直接返回验证码数据给父组件，不进行独立验证
      const captchaData = {
        uuid: this.uuid,
        x: this.sliderPosition
      }

      console.log('验证码数据:', captchaData)
      this.$message.success('验证码已确认')
      this.$emit('success', captchaData)
      this.visible = false
    },

    // 刷新验证码
    refreshCaptcha() {
      this.loadCaptcha()
    }
  },

  mounted() {
    if (this.visible) {
      this.loadCaptcha()
    }
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    // 清理事件监听器
    document.removeEventListener('mousemove', this.onDrag);
    document.removeEventListener('mouseup', this.stopDrag);
    document.removeEventListener('touchmove', this.onDrag);
    document.removeEventListener('touchend', this.stopDrag);
    window.removeEventListener('resize', this.handleResize);
    this.lockBodyScroll(false);
  }
}
</script>

<style scoped>
:deep(.captcha-dialog) {
  width: 400px !important;
  max-width: 90vw !important;
  margin: 20px auto !important;
  position: fixed !important;
  top: 50% !important;
  left: 50% !important;
  transform: translate(-50%, -50%) !important;
  -webkit-transform: translate(-50%, -50%) !important;
}

:deep(.el-dialog__body) {
  padding: 20px !important;
}

:deep(.el-dialog__header) {
  padding: 15px 20px 10px !important;
  border-bottom: 1px solid #f0f0f0;
}

:deep(.el-dialog__title) {
  font-size: 18px !important;
  font-weight: 600 !important;
  color: #333 !important;
}

:deep(.el-dialog__headerbtn) {
  top: 15px !important;
  right: 20px !important;
}

.captcha-container {
  width: 100% !important;
  max-width: 100% !important;
  margin: 0 auto !important;
  padding: 0 !important;
}

.captcha-content {
  width: 100% !important;
  max-width: 100% !important;
  margin: 0 !important;
  padding: 0 !important;
}

.captcha-background {
  position: relative;
  width: 100% !important;
  height: 200px !important;
  margin: 0 auto 20px !important;
  border-radius: 8px !important;
  overflow: hidden !important;
  background-color: #f5f7fa !important;
  border: 1px solid #e6e8eb !important;
}

.captcha-background img {
  width: 100% !important;
  height: 100% !important;
  object-fit: cover !important;
}

.puzzle-piece {
  position: absolute !important;
  width: 60px !important;
  height: 60px !important;
  cursor: move !important;
  z-index: 10 !important;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3) !important;
  border-radius: 4px !important;
  overflow: hidden !important;
}

.puzzle-piece img {
  width: 100% !important;
  height: 100% !important;
  object-fit: cover !important;
}

.slider-container {
  width: 100% !important;
  margin: 25px auto 15px !important;
  padding: 0 !important;
}

.slider-track {
  position: relative !important;
  width: 100% !important;
  height: 40px !important;
  background-color: #f5f7fa !important;
  border-radius: 20px !important;
  border: 1px solid #e6e8eb !important;
  overflow: hidden !important;
}

.slider-fill {
  position: absolute !important;
  left: 0 !important;
  top: 0 !important;
  height: 100% !important;
  background-color: #409EFF !important;
  border-radius: 20px 0 0 20px !important;
  transition: width 0.1s ease !important;
}

.slider-button {
  position: absolute !important;
  top: 0 !important;
  left: 0 !important;
  width: 40px !important;
  height: 38px !important;
  background-color: #fff !important;
  border-radius: 50% !important;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.2) !important;
  cursor: pointer !important;
  display: flex !important;
  align-items: center !important;
  justify-content: center !important;
  z-index: 20 !important;
  border: 1px solid #e6e8eb !important;
}

.slider-button i {
  font-size: 16px !important;
  color: #409EFF !important;
}

.slider-text {
  text-align: center !important;
  margin-top: 10px !important;
  font-size: 14px !important;
  color: #606266 !important;
}

/* 移动端优化 */
@media (max-width: 768px) {
  :deep(.captcha-dialog) {
    width: 90% !important;
    max-width: 400px !important;
  }
  
  .captcha-background {
    height: 180px !important;
  }
  
  .slider-container {
    margin: 20px auto 10px !important;
  }
  
  .slider-track {
    height: 36px !important;
  }
  
  .slider-button {
    width: 36px !important;
    height: 34px !important;
  }
  
  .slider-text {
    font-size: 13px !important;
  }
}
.captcha-container {
  text-align: center;
}

.loading-container {
  padding: 40px;
  color: #666;
}

.captcha-content {
  position: relative;
}

.captcha-background {
  position: relative;
  display: inline-block;
  margin-bottom: 20px;
  border: 1px solid #ddd;
  border-radius: 4px;
  overflow: hidden;
}

.captcha-background img {
  display: block;
  width: 300px;
  height: 150px;
}

.puzzle-piece {
  position: absolute;
  top: 0;
  cursor: pointer;
  transition: none;
  z-index: 10;
}

.puzzle-piece img {
  width: 60px;
  height: 60px;
  display: block;
}

.slider-container {
  position: relative;
  margin: 20px auto;
  width: 300px;
}

.slider-track {
  position: relative;
  height: 40px;
  background: #f7f7f7;
  border: 1px solid #ddd;
  border-radius: 20px;
  overflow: hidden;
}

.slider-fill {
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  background: linear-gradient(90deg, #4CAF50, #45a049);
  transition: width 0.1s ease;
}

.slider-button {
  position: absolute;
  top: 2px;
  width: 36px;
  height: 36px;
  background: white;
  border: 1px solid #ddd;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: none;
  z-index: 20;
}

.slider-button:hover {
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.slider-button i {
  color: #666;
  font-size: 14px;
}

.slider-text {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  color: #999;
  font-size: 14px;
  pointer-events: none;
  z-index: 15;
}

.debug-info {
  margin-top: 15px;
  padding: 10px;
  background: #f5f5f5;
  border-radius: 4px;
  font-size: 12px;
  color: #666;
  text-align: left;
}

.debug-info p {
  margin: 5px 0;
}

.dialog-footer {
  text-align: center;
}
</style>