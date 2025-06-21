<template>
  <div
    class="ai-chatbot"
    :style="{
      bottom: position.y + 'px',
      right: position.x + 'px',
      transform: isDragging ? 'scale(1.05)' : 'scale(1)'
    }"
  >
    <!-- 聊天按钮 -->
    <div
      class="chat-toggle-btn"
      @mousedown="startDrag"
      @click="handleClick"
      :class="{
        'active': chatVisible,
        'dragging': isDragging
      }"
      :title="chatVisible ? '关闭智能助手' : '打开智能助手'"
    >
      <div class="btn-content">
        <div class="btn-icon">
          <i class="el-icon-service"></i>
          <div class="status-dot" :class="connectionStatus"></div>
        </div>
        <div class="btn-info" v-show="!chatVisible">
          <span class="btn-title">智能助手</span>
          <span class="btn-subtitle">{{ connectionText }}</span>
        </div>
        <!-- 拖拽指示器 -->
        <div class="drag-indicator" v-show="!chatVisible">
          <i class="el-icon-rank"></i>
        </div>
      </div>
    </div>

    <!-- 聊天窗口 -->
    <div class="chat-window" v-show="chatVisible">
      <div class="chat-header">
        <div class="header-left">
          <div class="header-avatar">
            <i class="el-icon-service"></i>
          </div>
          <div class="header-info">
            <div class="header-title">天津大学智能助手</div>
            <div class="header-subtitle">
              <div class="connection-status" :class="connectionStatus">
                <span class="status-dot"></span>
                <span class="status-text">{{ connectionText }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="header-actions">
          <el-button
            type="text"
            icon="el-icon-minus"
            @click="minimizeChat"
            class="action-btn minimize-btn"
            title="最小化"
          ></el-button>
          <el-button
            type="text"
            icon="el-icon-close"
            @click="toggleChat"
            class="action-btn close-btn"
            title="关闭"
          ></el-button>
        </div>
      </div>

      <div class="chat-messages" ref="messagesContainer">
        <div 
          v-for="(message, index) in messages" 
          :key="index"
          class="message-item"
          :class="message.type"
        >
          <div class="message-avatar">
            <i :class="message.type === 'user' ? 'el-icon-user' : 'el-icon-service'"></i>
          </div>
          <div class="message-content">
            <div class="message-text" v-html="formatMessage(message.content)"></div>
            <div class="message-time">{{ formatTime(message.timestamp) }}</div>
          </div>
        </div>
        
        <!-- 正在输入指示器 -->
        <div v-if="isTyping" class="message-item bot typing-indicator">
          <div class="message-avatar">
            <i class="el-icon-service"></i>
          </div>
          <div class="message-content">
            <div class="typing-dots">
              <span></span>
              <span></span>
              <span></span>
            </div>
          </div>
        </div>
      </div>

      <div class="chat-input">
        <el-input
          v-model="inputMessage"
          placeholder="请输入您的问题..."
          @keyup.enter.native="sendMessage"
          :disabled="isTyping"
          class="input-field"
        >
          <el-button 
            slot="append" 
            icon="el-icon-s-promotion" 
            @click="sendMessage"
            :disabled="isTyping || !inputMessage.trim()"
            type="primary"
          ></el-button>
        </el-input>
      </div>

      <!-- 快捷问题 -->
      <div class="quick-questions" v-if="messages.length === 1">
        <div class="quick-title">
          <i class="el-icon-lightning"></i>
          快速开始
        </div>
        <div class="quick-grid">
          <div
            v-for="question in quickQuestions"
            :key="question"
            @click="sendQuickQuestion(question)"
            class="quick-item"
          >
            <i class="el-icon-chat-dot-round"></i>
            <span>{{ question }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import ChatServices from '@/service/ChatServices'

export default {
  name: 'AIChatBot',
  data() {
    return {
      chatVisible: false,
      inputMessage: '',
      isTyping: false,
      connectionStatus: 'connecting', // connecting, connected, disconnected
      retryCount: 0,
      maxRetries: 3,
      messages: [
        {
          type: 'bot',
          content: '您好！我是天大食堂小智，专门为您提供食堂预约和咨询服务。有什么可以帮助您的吗？',
          timestamp: new Date()
        }
      ],
      memoryId: 1, // 会话ID，可以根据用户ID生成
      quickQuestions: [
        '我想预约明天的午餐',
        '查询北洋园食堂餐位',
        '如何取消预约',
        '食堂营业时间'
      ],
      // 拖拽相关数据
      isDragging: false,
      dragOffset: { x: 0, y: 0 },
      position: { x: 24, y: 24 }, // 相对于右下角的位置
      startPosition: { x: 0, y: 0 }
    }
  },
  computed: {
    connectionIcon() {
      switch (this.connectionStatus) {
        case 'connected': return 'el-icon-success';
        case 'connecting': return 'el-icon-loading';
        case 'disconnected': return 'el-icon-warning';
        default: return 'el-icon-loading';
      }
    },
    connectionText() {
      switch (this.connectionStatus) {
        case 'connected': return '在线';
        case 'connecting': return '连接中...';
        case 'disconnected': return '离线';
        default: return '连接中...';
      }
    }
  },
  methods: {
    toggleChat() {
      this.chatVisible = !this.chatVisible;
      if (this.chatVisible) {
        this.$nextTick(() => {
          this.scrollToBottom();
        });
      }
    },

    minimizeChat() {
      this.chatVisible = false;
    },

    // 拖拽相关方法
    startDrag(event) {
      // 防止在聊天窗口打开时拖拽
      if (this.chatVisible) return;

      this.isDragging = true;
      this.startPosition = {
        x: event.clientX,
        y: event.clientY
      };

      // 添加全局事件监听
      document.addEventListener('mousemove', this.onDrag);
      document.addEventListener('mouseup', this.stopDrag);

      // 防止页面滚动
      document.body.classList.add('dragging-active');

      // 防止文本选择
      event.preventDefault();
    },

    onDrag(event) {
      if (!this.isDragging) return;

      const deltaX = this.startPosition.x - event.clientX;
      const deltaY = event.clientY - this.startPosition.y;

      // 计算新位置（相对于右下角）
      let newX = this.position.x + deltaX;
      let newY = this.position.y + deltaY;

      // 边界检测
      const windowWidth = window.innerWidth;
      const windowHeight = window.innerHeight;
      const btnWidth = 200; // 按钮最大宽度
      const btnHeight = 60; // 按钮高度

      newX = Math.max(10, Math.min(newX, windowWidth - btnWidth - 10));
      newY = Math.max(10, Math.min(newY, windowHeight - btnHeight - 10));

      this.position.x = newX;
      this.position.y = newY;

      this.startPosition = {
        x: event.clientX,
        y: event.clientY
      };
    },

    stopDrag() {
      this.isDragging = false;

      // 移除全局事件监听
      document.removeEventListener('mousemove', this.onDrag);
      document.removeEventListener('mouseup', this.stopDrag);

      // 恢复页面滚动
      document.body.classList.remove('dragging-active');

      // 保存位置到本地存储
      this.savePosition();
    },

    handleClick(event) {
      // 如果刚刚结束拖拽，不触发点击
      if (this.isDragging) {
        event.preventDefault();
        return;
      }

      // 延迟执行，确保拖拽状态已重置
      setTimeout(() => {
        if (!this.isDragging) {
          this.toggleChat();
        }
      }, 50);
    },

    savePosition() {
      localStorage.setItem('ai-chatbot-position', JSON.stringify(this.position));
    },

    loadPosition() {
      const saved = localStorage.getItem('ai-chatbot-position');
      if (saved) {
        try {
          this.position = JSON.parse(saved);
        } catch (e) {
          console.warn('Failed to load saved position');
        }
      }
    },

    async sendMessage(retryAttempt = 0) {
      if (!this.inputMessage.trim() || this.isTyping) return;

      const userMessage = this.inputMessage.trim();
      this.inputMessage = '';

      // 添加用户消息
      this.messages.push({
        type: 'user',
        content: userMessage,
        timestamp: new Date()
      });

      this.scrollToBottom();
      this.isTyping = true;
      this.connectionStatus = 'connecting';

      try {
        const response = await ChatServices.sendMessage({
          memoryId: this.memoryId,
          message: userMessage
        });

        this.connectionStatus = 'connected';
        this.retryCount = 0;
        let botResponse = '';

        // 添加机器人消息占位符
        const botMessageIndex = this.messages.length;
        this.messages.push({
          type: 'bot',
          content: '',
          timestamp: new Date(),
          status: 'receiving'
        });

        await ChatServices.handleStreamResponse(
          response,
          (chunk) => {
            // 接收到数据块
            botResponse += chunk;
            this.messages[botMessageIndex].content = botResponse;
            this.scrollToBottom();
          },
          () => {
            // 完成
            this.messages[botMessageIndex].status = 'completed';
            this.isTyping = false;
            this.scrollToBottom();
          },
          (error) => {
            // 流式响应错误处理
            console.error('流式响应错误:', error);
            this.messages[botMessageIndex].content = '抱歉，接收消息时出现问题。';
            this.messages[botMessageIndex].status = 'error';
            this.isTyping = false;
            this.connectionStatus = 'disconnected';
            this.$message.error('接收消息失败');
          }
        );

      } catch (error) {
        console.error('发送消息失败:', error);
        this.connectionStatus = 'disconnected';

        // 重试机制
        if (retryAttempt < this.maxRetries) {
          this.retryCount = retryAttempt + 1;
          this.$message.warning(`连接失败，正在重试 (${this.retryCount}/${this.maxRetries})...`);

          setTimeout(() => {
            this.inputMessage = userMessage;
            this.messages.pop();
            this.sendMessage(retryAttempt + 1);
          }, 2000 * (retryAttempt + 1));

        } else {
          this.messages.push({
            type: 'bot',
            content: `抱歉，连接服务器失败。请检查网络连接或稍后再试。\n\n您可以：\n1. 检查后端服务是否启动 (http://localhost:8080)\n2. 刷新页面重试\n3. 联系技术支持`,
            timestamp: new Date(),
            status: 'error'
          });
          this.isTyping = false;
          this.retryCount = 0;
          this.$message.error('连接失败，请检查后端服务');
        }
      }
    },

    sendQuickQuestion(question) {
      this.inputMessage = question;
      this.sendMessage();
    },

    formatMessage(content) {
      // 简单的文本格式化，可以根据需要扩展
      return content.replace(/\n/g, '<br>');
    },

    formatTime(timestamp) {
      return timestamp.toLocaleTimeString('zh-CN', { 
        hour: '2-digit', 
        minute: '2-digit' 
      });
    },

    scrollToBottom() {
      this.$nextTick(() => {
        const container = this.$refs.messagesContainer;
        if (container) {
          container.scrollTop = container.scrollHeight;
        }
      });
    },

    async testConnection() {
      try {
        console.log('测试后端连接...');
        this.connectionStatus = 'connecting';

        // 简单的连接测试
        const response = await fetch('/xiaozhi/chat', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            memoryId: this.memoryId,
            message: '连接测试'
          })
        });

        if (response.ok) {
          this.connectionStatus = 'connected';
          console.log('后端连接成功');
        } else {
          this.connectionStatus = 'disconnected';
          console.error('后端连接失败:', response.status, response.statusText);
        }
      } catch (error) {
        this.connectionStatus = 'disconnected';
        console.error('连接测试失败:', error);
      }
    }
  },

  mounted() {
    // 根据用户ID生成唯一的会话ID
    const userId = this.$store.getters.currentUser?.id || 'anonymous';
    this.memoryId = parseInt(userId) || Math.floor(Math.random() * 1000000);

    // 加载保存的位置
    this.loadPosition();

    // 测试连接
    this.testConnection();
  },

  beforeDestroy() {
    // 清理事件监听器
    document.removeEventListener('mousemove', this.onDrag);
    document.removeEventListener('mouseup', this.stopDrag);
  }
};
</script>

<style scoped>
.ai-chatbot {
  position: fixed;
  z-index: 9999;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', sans-serif;
  transition: transform 0.2s ease;
}

.chat-toggle-btn {
  background: #ffffff;
  color: #262626;
  padding: 0;
  border-radius: 12px;
  cursor: move;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08), 0 2px 8px rgba(0, 0, 0, 0.04);
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  border: 1px solid #e8e8e8;
  overflow: hidden;
  min-width: 64px;
  backdrop-filter: blur(8px);
  user-select: none;
}

.btn-content {
  display: flex;
  align-items: center;
  padding: 12px 16px;
  gap: 12px;
}

.chat-toggle-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 8px 28px rgba(0, 0, 0, 0.12), 0 4px 12px rgba(0, 0, 0, 0.08);
  border-color: #d9d9d9;
}

.chat-toggle-btn.active {
  background: #f0f9ff;
  border-color: #1890ff;
  box-shadow: 0 4px 20px rgba(24, 144, 255, 0.15);
}

.chat-toggle-btn.active .btn-content {
  padding: 16px;
}

.chat-toggle-btn.dragging {
  cursor: grabbing;
  transform: rotate(2deg);
  box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15), 0 6px 16px rgba(0, 0, 0, 0.1);
  z-index: 10000;
}

.chat-toggle-btn.active {
  cursor: pointer;
}

.btn-icon {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  background: #f0f9ff;
  border-radius: 8px;
  color: #1890ff;
  font-size: 20px;
  flex-shrink: 0;
}

.status-dot {
  position: absolute;
  top: -2px;
  right: -2px;
  width: 12px;
  height: 12px;
  border-radius: 50%;
  border: 2px solid white;
  transition: all 0.3s ease;
}

.status-dot.connected {
  background: #52c41a;
}

.status-dot.connecting {
  background: #faad14;
  animation: pulse-connecting 1.5s infinite;
}

.status-dot.disconnected {
  background: #ff4d4f;
}

.btn-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 2px;
}

.btn-title {
  font-size: 14px;
  font-weight: 600;
  color: #262626;
  line-height: 1.2;
}

.btn-subtitle {
  font-size: 12px;
  color: #8c8c8c;
  line-height: 1.2;
}

.drag-indicator {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
  height: 20px;
  color: #d9d9d9;
  font-size: 12px;
  opacity: 0.6;
  transition: all 0.2s ease;
}

.chat-toggle-btn:hover .drag-indicator {
  color: #8c8c8c;
  opacity: 1;
}

@keyframes pulse-connecting {
  0%, 100% {
    transform: scale(1);
    opacity: 1;
  }
  50% {
    transform: scale(1.2);
    opacity: 0.7;
  }
}

.chat-window {
  position: absolute;
  bottom: 76px;
  right: 0;
  width: 400px;
  height: 600px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 16px 48px rgba(0, 0, 0, 0.12), 0 8px 24px rgba(0, 0, 0, 0.08);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  border: 1px solid #e8e8e8;
  animation: slideUp 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
}

@keyframes slideUp {
  from {
    opacity: 0;
    transform: translateY(24px) scale(0.96);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

.chat-header {
  background: #ffffff;
  color: #262626;
  padding: 20px 24px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid #f0f0f0;
  position: relative;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.header-avatar {
  width: 40px;
  height: 40px;
  background: #f0f9ff;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #1890ff;
  font-size: 18px;
  flex-shrink: 0;
}

.header-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
  flex: 1;
}

.header-title {
  font-size: 16px;
  font-weight: 600;
  color: #262626;
  line-height: 1.2;
}

.header-subtitle {
  display: flex;
  align-items: center;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 4px;
}

.connection-status {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 12px;
}

.connection-status .status-dot {
  width: 6px;
  height: 6px;
  border-radius: 50%;
  border: none;
  position: static;
}

.connection-status.connected {
  color: #52c41a;
}

.connection-status.connecting {
  color: #faad14;
}

.connection-status.disconnected {
  color: #ff4d4f;
}

.status-text {
  font-size: 12px;
  font-weight: 400;
  color: #8c8c8c;
}

.action-btn {
  width: 32px;
  height: 32px;
  border-radius: 6px;
  color: #8c8c8c !important;
  transition: all 0.2s ease;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.action-btn:hover {
  background: #f5f5f5 !important;
  color: #262626 !important;
}

.close-btn:hover {
  background: #fff2f0 !important;
  color: #ff4d4f !important;
}

.close-btn {
  color: white !important;
}

.chat-messages {
  flex: 1;
  padding: 15px;
  overflow-y: auto;
  background: #f8f9fa;
}

.message-item {
  display: flex;
  margin-bottom: 15px;
  align-items: flex-start;
  gap: 10px;
}

.message-item.user {
  flex-direction: row-reverse;
}

.message-avatar {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  flex-shrink: 0;
}

.message-item.user .message-avatar {
  background: #005A9C;
  color: white;
}

.message-item.bot .message-avatar {
  background: #e9ecef;
  color: #6c757d;
}

.message-content {
  max-width: 70%;
}

.message-item.user .message-content {
  text-align: right;
}

.message-text {
  background: white;
  padding: 10px 12px;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  line-height: 1.4;
}

.message-item.user .message-text {
  background: #005A9C;
  color: white;
}

.message-time {
  font-size: 11px;
  color: #6c757d;
  margin-top: 4px;
}

.typing-indicator .typing-dots {
  display: flex;
  gap: 4px;
  padding: 10px 12px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

.typing-dots span {
  width: 6px;
  height: 6px;
  background: #6c757d;
  border-radius: 50%;
  animation: typing 1.4s infinite ease-in-out;
}

.typing-dots span:nth-child(2) {
  animation-delay: 0.2s;
}

.typing-dots span:nth-child(3) {
  animation-delay: 0.4s;
}

@keyframes typing {
  0%, 60%, 100% {
    transform: translateY(0);
    opacity: 0.5;
  }
  30% {
    transform: translateY(-10px);
    opacity: 1;
  }
}

.chat-input {
  padding: 20px 24px;
  background: white;
  border-top: 1px solid #f0f0f0;
}

.input-field {
  width: 100%;
}

.input-field .el-input__inner {
  border-radius: 8px;
  border: 1px solid #e8e8e8;
  padding: 12px 16px;
  font-size: 14px;
  transition: all 0.3s ease;
  background: #fafafa;
}

.input-field .el-input__inner:focus {
  border-color: #1890ff;
  background: white;
  box-shadow: 0 0 0 2px rgba(24, 144, 255, 0.1);
}

.input-field .el-input__inner::placeholder {
  color: #bfbfbf;
}

.input-field .el-input-group__append {
  border-radius: 0 8px 8px 0;
  border: 1px solid #1890ff;
  background: #1890ff;
  padding: 0;
}

.input-field .el-input-group__append .el-button {
  border: none;
  background: transparent;
  color: white;
  font-size: 16px;
  padding: 12px 16px;
  border-radius: 0 8px 8px 0;
}

.input-field .el-input-group__append .el-button:hover {
  background: rgba(255, 255, 255, 0.1);
}

.quick-questions {
  padding: 20px 24px;
  background: #fafafa;
  border-top: 1px solid #f0f0f0;
}

.quick-title {
  font-size: 14px;
  color: #262626;
  margin-bottom: 16px;
  font-weight: 600;
  display: flex;
  align-items: center;
  gap: 8px;
}

.quick-title i {
  color: #1890ff;
  font-size: 16px;
}

.quick-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 8px;
}

.quick-item {
  background: white;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  padding: 12px;
  cursor: pointer;
  transition: all 0.2s ease;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: #262626;
}

.quick-item:hover {
  background: #f0f9ff;
  border-color: #1890ff;
  color: #1890ff;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(24, 144, 255, 0.1);
}

.quick-item i {
  color: #8c8c8c;
  font-size: 14px;
  flex-shrink: 0;
}

.quick-item:hover i {
  color: #1890ff;
}

/* 滚动条样式 */
.chat-messages::-webkit-scrollbar {
  width: 4px;
}

.chat-messages::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.chat-messages::-webkit-scrollbar-thumb {
  background: #c1c1c1;
  border-radius: 2px;
}

.chat-messages::-webkit-scrollbar-thumb:hover {
  background: #a8a8a8;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .chat-window {
    width: calc(100vw - 40px);
    height: calc(100vh - 120px);
    right: -10px;
    bottom: 70px;
  }

  .btn-content {
    padding: 10px 14px;
  }

  .btn-title {
    font-size: 13px;
  }

  .btn-subtitle {
    font-size: 11px;
  }

  .drag-indicator {
    display: none;
  }
}

@media (max-width: 480px) {
  .chat-window {
    width: calc(100vw - 20px);
    height: calc(100vh - 100px);
    right: -20px;
    bottom: 70px;
    border-radius: 8px;
  }

  .btn-info {
    display: none;
  }

  .btn-content {
    padding: 12px;
  }

  .chat-header {
    padding: 16px 20px;
  }

  .header-title {
    font-size: 15px;
  }

  .drag-indicator {
    display: none;
  }
}

/* 拖拽时禁用页面滚动 */
.dragging-active {
  overflow: hidden;
}

/* 企业级主题色彩 */
:root {
  --primary-color: #1890ff;
  --primary-hover: #40a9ff;
  --primary-active: #096dd9;
  --success-color: #52c41a;
  --warning-color: #faad14;
  --error-color: #ff4d4f;
  --text-primary: #262626;
  --text-secondary: #8c8c8c;
  --border-color: #e8e8e8;
  --background-light: #f0f9ff;
}
</style>
