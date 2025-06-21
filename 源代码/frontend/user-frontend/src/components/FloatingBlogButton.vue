<template>
  <div class="floating-blog-button">
    <div
        class="floating-btn"
        @click="showBlogEditor"
        v-show="!editorVisible"
    >
      <div class="btn-icon">
        <i class="el-icon-edit"></i>
      </div>
      <span class="btn-text">写博客</span>
    </div>

    <el-dialog
        title="发布新博客"
        :visible.sync="editorVisible"
        width="80%"
        :before-close="handleClose"
        class="blog-editor-dialog"
    >
      <el-form :model="blogForm" :rules="rules" ref="blogFormRef" label-width="80px">
        <el-form-item label="标题" prop="title">
          <el-input
              v-model="blogForm.title"
              placeholder="请输入博客标题..."
              maxlength="100"
              show-word-limit
          />
        </el-form-item>

        <el-form-item label="内容" prop="content">
          <div class="editor-wrapper">
            <div class="toolbar" v-if="editor">
              <el-button-group>
                <el-button
                    size="small"
                    :type="editor.isActive('bold') ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleBold().run()"
                >
                  <strong>B</strong>
                </el-button>
                <el-button
                    size="small"
                    :type="editor.isActive('italic') ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleItalic().run()"
                >
                  <em>I</em>
                </el-button>
                <el-button
                    size="small"
                    :type="editor.isActive('underline') ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleUnderline().run()"
                >
                  <u>U</u>
                </el-button>
              </el-button-group>

              <el-button-group style="margin-left: 10px;">
                <el-button
                    size="small"
                    :type="editor.isActive('heading', { level: 1 }) ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
                >
                  H1
                </el-button>
                <el-button
                    size="small"
                    :type="editor.isActive('heading', { level: 2 }) ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
                >
                  H2
                </el-button>
                <el-button
                    size="small"
                    :type="editor.isActive('heading', { level: 3 }) ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
                >
                  H3
                </el-button>
              </el-button-group>

              <el-button-group style="margin-left: 10px;">
                <el-button
                    size="small"
                    :type="editor.isActive('bulletList') ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleBulletList().run()"
                >
                  • 列表
                </el-button>
                <el-button
                    size="small"
                    :type="editor.isActive('orderedList') ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleOrderedList().run()"
                >
                  1. 列表
                </el-button>
                <el-button
                    size="small"
                    :type="editor.isActive('blockquote') ? 'primary' : 'default'"
                    @click="editor.chain().focus().toggleBlockquote().run()"
                >
                  引用
                </el-button>
              </el-button-group>

              <el-button-group style="margin-left: 10px;">
                <el-button
                    size="small"
                    @click="insertImage"
                >
                  插入图片
                </el-button>
                <el-button
                    size="small"
                    @click="insertLink"
                >
                  插入链接
                </el-button>
              </el-button-group>
            </div>

            <div
                ref="editorElement"
                class="editor-content"
            ></div>
          </div>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" @click="publishBlog" :loading="publishing">
          {{ publishing ? '发布中...' : '发布博客' }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { Editor } from '@tiptap/core'
import StarterKit from '@tiptap/starter-kit'
import Underline from '@tiptap/extension-underline'
import Link from '@tiptap/extension-link'
import Image from '@tiptap/extension-image'
import BlogServices from '@/service/BlogServices'

export default {
  name: 'FloatingBlogButton',
  data() {
    return {
      editorVisible: false,
      editor: null,
      publishing: false,
      blogForm: {
        title: '',
        content: ''
      },
      rules: {
        title: [
          { required: true, message: '请输入博客标题', trigger: 'blur' },
          { min: 1, max: 100, message: '标题长度在 1 到 100 个字符', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入博客内容', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    showBlogEditor() {
      this.editorVisible = true
      this.$nextTick(() => {
        this.initEditor()
      })
    },

    handleClose() {
      if (this.blogForm.title || this.blogForm.content) {
        this.$confirm('您有未保存的内容，确定要关闭吗？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.closeEditor()
        }).catch(() => {
          // 用户取消关闭
        })
      } else {
        this.closeEditor()
      }
    },

    closeEditor() {
      this.editorVisible = false
      this.destroyEditor()
      this.resetForm()
    },

    initEditor() {
      if (this.editor || !this.$refs.editorElement) return

      try {
        this.editor = new Editor({
          element: this.$refs.editorElement,
          extensions: [
            StarterKit,
            Underline,
            Link.configure({
              openOnClick: false,
              HTMLAttributes: {
                target: '_blank',
                rel: 'noopener noreferrer'
              }
            }),
            Image.configure({
              HTMLAttributes: {
                style: 'max-width: 100%; height: auto;'
              }
            })
          ],
          content: this.blogForm.content,
          onUpdate: ({ editor }) => {
            this.blogForm.content = editor.getHTML()
          }
        })
      } catch (error) {
        console.error('初始化编辑器失败:', error)
        this.$message.error('编辑器加载失败，请刷新页面重试')
      }
    },

    destroyEditor() {
      if (this.editor) {
        this.editor.destroy()
        this.editor = null
      }
    },

    insertImage() {
      this.$prompt('请输入图片URL:', '插入图片', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^https?:\/\/.+/,
        inputErrorMessage: '请输入有效的图片URL'
      }).then(({ value }) => {
        if (value && this.editor) {
          this.editor.chain().focus().setImage({ src: value }).run()
        }
      }).catch(() => {
        // 用户取消
      })
    },

    insertLink() {
      this.$prompt('请输入链接URL:', '插入链接', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPattern: /^https?:\/\/.+/,
        inputErrorMessage: '请输入有效的链接URL'
      }).then(({ value }) => {
        if (value && this.editor) {
          this.editor.chain().focus().setLink({ href: value }).run()
        }
      }).catch(() => {
        // 用户取消
      })
    },

    publishBlog() {
      this.$refs.blogFormRef.validate((valid) => {
        if (!valid) {
          return false;
        }

        this.publishing = true;

        BlogServices.createBlog({
          title: this.blogForm.title,
          content: this.blogForm.content
        })
            .then(response => {
              if (response.success) {
                this.$message.success('博客发布成功！');
                this.closeEditor();
                this.$emit('blog-published', response.data);
              } else {
                this.$message.error(response.errorMsg || '发布失败');
              }
            })
            .catch(error => {
              console.error('发布博客失败:', error);
              this.$message.error('发布失败，请重试');
            })
            .finally(() => {
              this.publishing = false;
            });
      });
    },

    resetForm() {
      this.blogForm = {
        title: '',
        content: ''
      }
      if (this.editor) {
        this.editor.commands.setContent('')
      }
      if(this.$refs.blogFormRef) {
        this.$refs.blogFormRef.resetFields()
      }
    }
  },

  beforeDestroy() {
    this.destroyEditor()
  }
}
</script>

<style scoped>
/* 浮动按钮样式 */
.floating-btn {
  position: fixed;
  bottom: 30px;
  right: 30px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border-radius: 50px;
  padding: 15px 20px;
  box-shadow: 0 4px 20px rgba(102, 126, 234, 0.4);
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
  /* 修复：设置一个较高的 z-index */
  z-index: 2000;
  user-select: none;
}

.floating-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 25px rgba(102, 126, 234, 0.6);
}

.btn-icon {
  font-size: 18px;
}

.btn-text {
  font-weight: 500;
  font-size: 14px;
}

/* 编辑器样式 */
.editor-wrapper {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  overflow: hidden;
}

.toolbar {
  background: #f5f7fa;
  padding: 10px;
  border-bottom: 1px solid #dcdfe6;
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.editor-content {
  min-height: 300px;
  padding: 15px;
}

/* Tiptap编辑器内容样式 */
.editor-content >>> .ProseMirror {
  outline: none;
  line-height: 1.6;
  min-height: 270px;
}

.editor-content >>> .ProseMirror h1 {
  font-size: 1.8em;
  font-weight: bold;
  margin: 0.5em 0;
}

.editor-content >>> .ProseMirror h2 {
  font-size: 1.5em;
  font-weight: bold;
  margin: 0.5em 0;
}

.editor-content >>> .ProseMirror h3 {
  font-size: 1.3em;
  font-weight: bold;
  margin: 0.5em 0;
}

.editor-content >>> .ProseMirror p {
  margin: 0.5em 0;
}

.editor-content >>> .ProseMirror ul,
.editor-content >>> .ProseMirror ol {
  padding-left: 1.5em;
  margin: 0.5em 0;
}

.editor-content >>> .ProseMirror blockquote {
  border-left: 4px solid #ddd;
  padding-left: 1em;
  margin: 1em 0;
  font-style: italic;
  color: #666;
}

.editor-content >>> .ProseMirror img {
  max-width: 100%;
  height: auto;
  border-radius: 4px;
  margin: 0.5em 0;
}

.editor-content >>> .ProseMirror a {
  color: #409eff;
  text-decoration: underline;
}

.editor-content >>> .ProseMirror code {
  background-color: #f1f1f1;
  padding: 2px 4px;
  border-radius: 3px;
  font-family: 'Courier New', monospace;
}

.editor-content >>> .ProseMirror pre {
  background-color: #f5f5f5;
  padding: 1em;
  border-radius: 4px;
  overflow-x: auto;
  margin: 1em 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .floating-btn {
    bottom: 20px;
    right: 20px;
    padding: 12px 16px;
  }

  .btn-text {
    display: none;
  }

  .toolbar {
    padding: 8px;
  }

  .toolbar .el-button {
    padding: 4px 8px;
    font-size: 11px;
  }
}
</style>