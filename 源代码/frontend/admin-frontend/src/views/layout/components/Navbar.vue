<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    <breadcrumb class="breadcrumb-container" />
    <div class="right-menu">
      <header-search class="right-menu-item" />
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <span class="user-name">{{ name }}</span>
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item @click.native="showProfile">
            <i class="el-icon-user"></i>
            <span>个人信息</span>
          </el-dropdown-item>
          <el-dropdown-item @click.native="showResetPassword">
            <i class="el-icon-key"></i>
            <span>重置密码</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <i class="el-icon-switch-button"></i>
            <span>退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-dialog
        title="个人信息"
        :visible.sync="profileDialogVisible"
        width="500px"
        @close="resetProfileForm"
        :modal-append-to-body="false"
        :close-on-click-modal="false">
      <el-form ref="profileForm" :model="profileForm" :rules="profileRules" label-width="100px">
        <el-form-item label="头像" prop="avatar">
          <el-upload
              class="avatar-uploader"
              action="/api/admin/avatar/upload"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload">
            <img v-if="profileForm.avatar" :src="profileForm.avatar" class="avatar-preview" alt="头像预览">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
          <div class="el-upload__tip">点击上传新头像，只能上传jpg/png文件，且不超过2MB</div>
        </el-form-item>

        <el-form-item label="用户名" prop="username">
          <el-input v-model="profileForm.username" placeholder="请输入用户名" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="profileForm.name" placeholder="请输入姓名"></el-input>
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="profileForm.roleName" disabled></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="profileForm.createTime" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="profileDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updateProfile">保存</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="重置密码"
        :visible.sync="passwordDialogVisible"
        width="450px"
        @close="resetPasswordForm"
        :modal-append-to-body="false"
        :close-on-click-modal="false">
      <el-form ref="passwordForm" :model="passwordForm" :rules="passwordRules" label-width="100px">
        <el-form-item label="当前密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" placeholder="请输入当前密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" placeholder="请输入新密码" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" placeholder="请再次输入新密码" show-password></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="passwordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="updatePassword">确认修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import HeaderSearch from '@/components/HeaderSearch'
import request from '@/utils/request'

export default {
  components: { Breadcrumb, Hamburger, HeaderSearch },
  data() {
    return {
      profileDialogVisible: false,
      passwordDialogVisible: false,
      profileForm: {
        id: '',
        username: '',
        name: '',
        avatar: '',
        roleName: '',
        createTime: ''
      },
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      profileRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入姓名', trigger: 'blur' },
          { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
        ],
        // 头像URL不再是必填项，因为可以通过上传更新
        avatar: [
          { type: 'url', message: '请输入正确的URL地址', trigger: 'blur' }
        ]
      },
      passwordRules: {
        oldPassword: [
          { required: true, message: '请输入当前密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请再次输入新密码', trigger: 'blur' },
          { validator: this.validateConfirmPassword, trigger: 'blur' }
        ]
      }
    }
  },
  computed: {
    ...mapGetters(['sidebar', 'avatar', 'name']),

    /**
     * 【新增】为el-upload组件提供请求头
     * @returns {{Authorization: (string|null)}}
     */
    uploadHeaders() {
      return {
        'Authorization': this.$store.getters.token
      }
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    logout() {
      this.$store.dispatch('user/logout').then(() => {
        this.$router.push(`/login?redirect=${this.$route.fullPath}`)
      }).catch(error => {
        console.error('登出失败:', error)
        this.$message.error('登出失败，请稍后再试')
      })
    },
    // 显示个人信息对话框
    showProfile() {
      request({
        url: '/admin/me',
        method: 'get'
      }).then(response => {
        this.profileForm = {
          id: response.id,
          username: response.username,
          name: response.name,
          avatar: response.avatar || '',
          roleName: response.roles && response.roles.length > 0 ? response.roles.join(', ') : '未分配角色',
          createTime: response.createTime ? new Date(response.createTime).toLocaleString() : ''
        }
        this.profileDialogVisible = true
      }).catch(error => {
        this.$message.error('获取个人信息失败：' + (error.message || '请稍后重试'))
      })
    },
    // 显示重置密码对话框
    showResetPassword() {
      this.passwordDialogVisible = true
    },
    // 更新个人信息
    updateProfile() {
      this.$refs.profileForm.validate((valid) => {
        if (valid) {
          request({
            url: '/admin/profile',
            method: 'put',
            data: {
              username: this.profileForm.username,
              name: this.profileForm.name,
              avatar: this.profileForm.avatar
            }
          }).then(() => {
            this.$message.success('个人信息更新成功！')
            this.profileDialogVisible = false
            // 更新store中的用户信息
            this.$store.commit('user/SET_NAME', this.profileForm.name)
            this.$store.commit('user/SET_AVATAR', this.profileForm.avatar)
          }).catch(error => {
            this.$message.error('更新失败：' + (error.message || '请稍后重试'))
          })
        }
      })
    },
    // 更新密码
    updatePassword() {
      this.$refs.passwordForm.validate((valid) => {
        if (valid) {
          request({
            url: '/admin/password',
            method: 'put',
            data: {
              oldPassword: this.passwordForm.oldPassword,
              newPassword: this.passwordForm.newPassword
            }
          }).then(() => {
            this.$message.success('密码修改成功！')
            this.passwordDialogVisible = false
            this.resetPasswordForm()
          }).catch(error => {
            this.$message.error('密码修改失败：' + (error.message || '请稍后重试'))
          })
        }
      })
    },
    // 验证确认密码
    validateConfirmPassword(rule, value, callback) {
      if (value !== this.passwordForm.newPassword) {
        callback(new Error('两次输入的密码不一致'))
      } else {
        callback()
      }
    },
    // 重置个人信息表单
    resetProfileForm() {
      this.profileForm = {
        id: '',
        username: '',
        name: '',
        avatar: '',
        roleName: '',
        createTime: ''
      }
      if (this.$refs.profileForm) {
        this.$refs.profileForm.clearValidate()
      }
    },
    // 重置密码表单
    resetPasswordForm() {
      this.passwordForm = {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
      if (this.$refs.passwordForm) {
        this.$refs.passwordForm.clearValidate()
      }
    },
    /**
     * 【新增】头像上传成功后的回调函数
     * @param res - 后端返回的Result对象
     * @param _file - 上传的文件对象（未使用，但Element UI要求此参数）
     */
    // eslint-disable-next-line no-unused-vars
    handleAvatarSuccess(res,_file) {
      if (res.success) {
        this.$message.success('头像上传成功！');
        // 后端返回了新的头像URL
        const newAvatarUrl = res.data;
        // 更新表单中的头像URL，用于预览
        this.profileForm.avatar = newAvatarUrl;
        // 更新Vuex store中的头像，让导航栏右上角的头像也实时更新
        this.$store.commit('user/SET_AVATAR', newAvatarUrl);
      } else {
        this.$message.error('头像上传失败: ' + res.errorMsg);
      }
    },

    /**
     * 【新增】上传头像前的校验
     * @param file - 待上传的文件
     * @returns {boolean}
     */
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG && !isPNG) {
        this.$message.error('上传头像图片只能是 JPG 或 PNG 格式!');
        return false;
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
        return false;
      }
      return true;
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: linear-gradient(135deg, #ffffff 0%, #f8fafc 100%);
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.06);
  border-bottom: 1px solid rgba(102, 126, 234, 0.1);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: all 0.3s;
    border-radius: 8px;
    margin: 4px;

    &:hover {
      background: rgba(102, 126, 234, 0.1);
      transform: scale(1.05);
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    display: flex;
    align-items: center;

    .right-menu-item {
      display: inline-block;
      padding: 0 12px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: middle;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        display: flex;
        align-items: center;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 12px;
          margin-right: 8px;
          object-fit: cover;
          box-shadow: 0 4px 8px rgba(102, 126, 234, 0.2);
          border: 2px solid rgba(102, 126, 234, 0.1);
          transition: all 0.3s;

          &:hover {
            transform: scale(1.05);
            box-shadow: 0 6px 12px rgba(102, 126, 234, 0.3);
          }
        }

        .user-name {
          font-size: 14px;
          color: #606266;
          margin-right: 5px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          font-size: 12px;
          color: #909399;
        }
      }
    }
  }
}

// 下拉菜单样式
.user-dropdown {
  .el-dropdown-menu__item {
    padding: 14px 20px;
    font-size: 14px;
    line-height: 1.4;
    margin: 0;
    border-bottom: 1px solid rgba(0, 0, 0, 0.05);

    &:last-child {
      border-bottom: none;
    }

    i {
      margin-right: 10px;
      font-size: 16px;
      width: 16px;
      text-align: center;
    }

    &:hover {
      background-color: rgba(102, 126, 234, 0.1);
      color: #667eea;
    }
  }
}

// 对话框样式增强
.el-dialog {
  .el-form-item {
    margin-bottom: 20px;
  }

  .dialog-footer {
    text-align: right;

    .el-button {
      margin-left: 10px;
    }
  }
}

/* 【新增】头像上传样式 */
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 50%; /* 圆形上传框 */
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 100px;
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: border-color 0.3s;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
}
.avatar-preview {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
}
</style>