<template>
  <div class="tiptap-editor">
    <div class="editor-toolbar" v-if="editor">
      <div class="toolbar-group">
        <button @click="editor.chain().focus().toggleBold().run()" :class="{ 'is-active': editor.isActive('bold') }" class="toolbar-button" title="粗体"><i class="el-icon-bold"></i></button>
        <button @click="editor.chain().focus().toggleItalic().run()" :class="{ 'is-active': editor.isActive('italic') }" class="toolbar-button" title="斜体"><i class="el-icon-italic"></i></button>
        <button @click="editor.chain().focus().toggleUnderline().run()" :class="{ 'is-active': editor.isActive('underline') }" class="toolbar-button" title="下划线"><i class="el-icon-underline"></i></button>
        <button @click="editor.chain().focus().toggleStrike().run()" :class="{ 'is-active': editor.isActive('strike') }" class="toolbar-button" title="删除线"><i class="el-icon-strikethrough"></i></button>
      </div>
      <div class="toolbar-divider"></div>
      <div class="toolbar-group">
        <button @click="editor.chain().focus().toggleHeading({ level: 1 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }" class="toolbar-button" title="标题1">H1</button>
        <button @click="editor.chain().focus().toggleHeading({ level: 2 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }" class="toolbar-button" title="标题2">H2</button>
        <button @click="editor.chain().focus().toggleHeading({ level: 3 }).run()" :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }" class="toolbar-button" title="标题3">H3</button>
      </div>
      <div class="toolbar-divider"></div>
      <div class="toolbar-group">
        <button @click="editor.chain().focus().toggleBulletList().run()" :class="{ 'is-active': editor.isActive('bulletList') }" class="toolbar-button" title="无序列表"><i class="el-icon-menu"></i></button>
        <button @click="editor.chain().focus().toggleOrderedList().run()" :class="{ 'is-active': editor.isActive('orderedList') }" class="toolbar-button" title="有序列表"><i class="el-icon-tickets"></i></button>
      </div>
      <div class="toolbar-divider"></div>
      <div class="toolbar-group">
        <button @click="editor.chain().focus().toggleBlockquote().run()" :class="{ 'is-active': editor.isActive('blockquote') }" class="toolbar-button" title="引用"><i class="el-icon-chat-line-square"></i></button>
        <button @click="editor.chain().focus().toggleCodeBlock().run()" :class="{ 'is-active': editor.isActive('codeBlock') }" class="toolbar-button" title="代码块"><i class="el-icon-document-copy"></i></button>
        <button @click="editor.chain().focus().setHorizontalRule().run()" class="toolbar-button" title="分割线"><i class="el-icon-minus"></i></button>
      </div>
      <div class="toolbar-divider"></div>
      <div class="toolbar-group">
        <button @click="showImageDialog" class="toolbar-button" title="插入图片"><i class="el-icon-picture"></i></button>
        <button @click="addLink" class="toolbar-button" title="插入链接"><i class="el-icon-link"></i></button>
      </div>
      <div class="toolbar-divider"></div>
      <div class="toolbar-group">
        <button @click="editor.chain().focus().undo().run()" :disabled="!editor.can().undo()" class="toolbar-button" title="撤销"><i class="el-icon-refresh-left"></i></button>
        <button @click="editor.chain().focus().redo().run()" :disabled="!editor.can().redo()" class="toolbar-button" title="重做"><i class="el-icon-refresh-right"></i></button>
      </div>
      <div class="toolbar-divider"></div>
      <div class="toolbar-group">
        <button @click="importMarkdown" class="toolbar-button" title="导入Markdown"><i class="el-icon-upload2"></i></button>
        <button @click="exportMarkdown" class="toolbar-button" title="导出Markdown"><i class="el-icon-download"></i></button>
        <button @click="toggleHtmlMode" :class="{ 'is-active': isHtmlMode }" class="toolbar-button" title="源码模式">HTML</button>
      </div>
    </div>

    <div class="editor-content-wrapper">
      <div v-show="!isHtmlMode" class="editor-content" ref="editorElement"></div>
      <textarea
          v-if="isHtmlMode"
          v-model="rawHtml"
          class="html-source-editor"
          @input="updateEditorFromHtml"
      ></textarea>
    </div>

    <el-dialog title="插入图片" :visible.sync="imageDialogVisible" width="450px" append-to-body>
      <el-tabs v-model="imageUploadTab">
        <el-tab-pane label="上传新图片" name="upload">
          <el-upload
              class="image-uploader"
              drag
              :action="uploadUrl"
              :headers="uploadHeaders"
              :show-file-list="false"
              :on-success="handleImageUploadSuccess"
              :before-upload="beforeUpload">
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2MB</div>
          </el-upload>
        </el-tab-pane>
        <el-tab-pane label="使用网络图片" name="url">
          <el-form label-position="top" style="margin-top: 10px;">
            <el-form-item label="图片URL">
              <el-input v-model="imageUrl" placeholder="请输入 https://... 格式的图片地址"></el-input>
            </el-form-item>
          </el-form>
          <div style="text-align: right;">
            <el-button @click="imageDialogVisible = false" size="small">取消</el-button>
            <el-button type="primary" @click="insertImageByUrl" size="small">确定插入</el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>

    <el-dialog title="插入链接" :visible.sync="linkDialogVisible" width="400px" append-to-body>
      <el-form>
        <el-form-item label="链接文本">
          <el-input v-model="linkText" placeholder="请输入链接文本"></el-input>
        </el-form-item>
        <el-form-item label="链接地址">
          <el-input v-model="linkUrl" placeholder="请输入链接地址"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button @click="linkDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="insertLink">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="导入Markdown" :visible.sync="markdownImportVisible" width="600px" append-to-body>
      <el-input type="textarea" v-model="markdownContent" placeholder="请粘贴Markdown内容..." :rows="10"></el-input>
      <div slot="footer">
        <el-button @click="markdownImportVisible = false">取消</el-button>
        <el-button type="primary" @click="importMarkdownContent">导入</el-button>
      </div>
    </el-dialog>

    <el-dialog title="导出Markdown" :visible.sync="markdownExportVisible" width="600px" append-to-body>
      <el-input type="textarea" v-model="exportedMarkdown" :rows="10" readonly></el-input>
      <div slot="footer">
        <el-button @click="markdownExportVisible = false">关闭</el-button>
        <el-button type="primary" @click="copyMarkdown">复制</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
/* eslint-disable no-unused-vars */
import { Editor } from '@tiptap/core'
import StarterKit from '@tiptap/starter-kit'
import Underline from '@tiptap/extension-underline'
import Image from '@tiptap/extension-image'
import Link from '@tiptap/extension-link'
import Typography from '@tiptap/extension-typography'
import Placeholder from '@tiptap/extension-placeholder'

export default {
  name: 'TipTapEditor',
  props: {
    value: { type: String, default: '' },
    placeholder: { type: String, default: '开始输入...' },
    minHeight: { type: String, default: '300px' }
  },
  data() {
    return {
      editor: null,
      isHtmlMode: false,
      rawHtml: '',
      imageDialogVisible: false,
      linkDialogVisible: false,
      markdownImportVisible: false,
      markdownExportVisible: false,
      imageUrl: '',
      linkText: '',
      linkUrl: '',
      markdownContent: '',
      exportedMarkdown: '',
      imageUploadTab: 'upload',
      uploadUrl: '/api/upload/image'
    }
  },
  computed: {
    uploadHeaders() {
      return { 'Authorization': this.$store.getters.token }
    }
  },
  watch: {
    value(newValue) {
      if (this.editor && !this.isHtmlMode && this.editor.getHTML() !== newValue) {
        this.editor.commands.setContent(newValue, false);
      }
    }
  },
  mounted() { this.initEditor(); },
  beforeDestroy() { if (this.editor) { this.editor.destroy(); } },
  methods: {
    initEditor() {
      this.editor = new Editor({
        element: this.$refs.editorElement,
        extensions: [
          StarterKit, Underline, Typography, Placeholder.configure({ placeholder: this.placeholder }),
          Image.configure({ inline: true, allowBase64: true, HTMLAttributes: { class: 'editor-image' } }),
          Link.configure({ openOnClick: false, HTMLAttributes: { class: 'editor-link', target: '_blank', rel: 'noopener noreferrer' } })
        ],
        content: this.value,
        onUpdate: ({ editor }) => {
          const html = editor.getHTML();
          this.rawHtml = html;
          this.$emit('input', html);
        },
        editorProps: { attributes: { class: 'prose', style: `min-height: ${this.minHeight};` } }
      });
      this.rawHtml = this.editor.getHTML();
    },
    toggleHtmlMode() {
      this.isHtmlMode = !this.isHtmlMode;
      if (!this.isHtmlMode) {
        this.$nextTick(() => { this.editor.commands.setContent(this.rawHtml, false); });
      }
    },
    updateEditorFromHtml() { this.$emit('input', this.rawHtml); },
    showImageDialog() { this.imageDialogVisible = true; },
    handleImageUploadSuccess(res) {
      if (res.success && res.data) {
        this.editor.chain().focus().setImage({ src: res.data }).run();
        this.$message.success('图片插入成功！');
        this.imageDialogVisible = false;
      } else { this.$message.error(res.errorMsg || '图片插入失败'); }
    },
    insertImageByUrl() {
      if (this.imageUrl) {
        this.editor.chain().focus().setImage({ src: this.imageUrl }).run();
        this.imageDialogVisible = false; this.imageUrl = '';
      } else { this.$message.warning('请输入图片URL'); }
    },
    beforeUpload(file) {
      const isImage = file.type.startsWith('image/');
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isImage) this.$message.error('只能上传图片文件!');
      if (!isLt2M) this.$message.error('图片大小不能超过 2MB!');
      return isImage && isLt2M;
    },
    addLink() {
      const { from, to } = this.editor.state.selection;
      this.linkText = this.editor.state.doc.textBetween(from, to) || '';
      this.linkUrl = this.editor.getAttributes('link').href || '';
      this.linkDialogVisible = true;
    },
    insertLink() {
      if (this.linkUrl) {
        this.editor.chain().focus().extendMarkRange('link').setLink({ href: this.linkUrl }).run();
        this.linkDialogVisible = false;
      }
    },
    importMarkdown() { this.markdownImportVisible = true; },
    exportMarkdown() {
      this.exportedMarkdown = this.htmlToMarkdown(this.editor.getHTML());
      this.markdownExportVisible = true;
    },
    importMarkdownContent() {
      if (this.markdownContent) {
        const html = this.markdownToHtml(this.markdownContent);
        this.editor.commands.setContent(html);
        this.markdownImportVisible = false;
        this.$message.success('Markdown导入成功');
      }
    },
    copyMarkdown() {
      navigator.clipboard.writeText(this.exportedMarkdown)
          .then(() => { this.$message.success('Markdown已复制到剪贴板'); })
          .catch(() => { this.$message.error('复制失败，请手动复制'); });
    },
    htmlToMarkdown(html) { /* ... 您原有的转换逻辑 ... */ },
    markdownToHtml(markdown) { /* ... 您原有的转换逻辑 ... */ }
  }
}
</script>

<style scoped>
.tiptap-editor { border: 1px solid #dcdfe6; border-radius: 8px; overflow: hidden; }
.editor-toolbar { display: flex; flex-wrap: wrap; gap: 8px; padding: 10px 12px; background-color: #f5f7fa; border-bottom: 1px solid #e4e7ed; }
.toolbar-group { display: flex; align-items: center; }
.toolbar-button { border: 1px solid #dcdfe6; background-color: white; border-radius: 4px; padding: 5px 10px; cursor: pointer; transition: all 0.2s; }
.toolbar-button:hover { border-color: #409EFF; color: #409EFF; }
.toolbar-button.is-active { background-color: #ecf5ff; color: #409EFF; border-color: #b3d8ff; }
.toolbar-divider { width: 1px; height: 20px; background-color: #e4e7ed; margin: 0 8px; }
.editor-content-wrapper { min-height: 300px; }
.editor-content { padding: 15px; }
.html-source-editor { width: 100%; box-sizing: border-box; min-height: 300px; border: none; resize: vertical; padding: 15px; font-family: 'Courier New', monospace; background-color: #2d2d2d; color: #f8f8f2; outline: none; }
.image-uploader { text-align: center; }
.image-uploader >>> .el-upload-dragger { width: 100%; }
</style>

<style>
.ProseMirror { outline: none; padding: 15px; min-height: 300px; line-height: 1.7; color: #303133; }
.ProseMirror p.is-editor-empty:first-child::before { content: attr(data-placeholder); float: left; color: #adb5bd; pointer-events: none; height: 0; }
.ProseMirror h1, .ProseMirror h2, .ProseMirror h3 { font-weight: 600; margin-top: 1.2em; margin-bottom: 0.6em; }
.ProseMirror h1 { font-size: 1.8em; } .ProseMirror h2 { font-size: 1.5em; } .ProseMirror h3 { font-size: 1.25em; }
.ProseMirror ul, .ProseMirror ol { padding-left: 2em; }
.ProseMirror blockquote { border-left: 3px solid #dcdfe6; padding-left: 1em; margin-left: 0; font-style: italic; color: #888; }
.ProseMirror pre { background: #f5f7fa; border-radius: 4px; padding: 1em; font-family: 'Courier New', monospace; }
.ProseMirror pre code { color: inherit; padding: 0; background: none; font-size: inherit; }
</style>