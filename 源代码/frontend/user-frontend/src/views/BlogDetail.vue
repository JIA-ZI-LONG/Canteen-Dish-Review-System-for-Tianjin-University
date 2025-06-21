<template>
  <div class="blog-detail-page">
    <div class="container">
      <div v-if="blog" class="blog-content">
        <div class="blog-body">
          <div class="rich-content" v-html="blog.content"></div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
// ... <script>部分无需修改，保持原样 ...
</script>

<style scoped>
/* ... 省略已有样式 ... */

/* 新增：富文本内容样式 */
.rich-content {
  line-height: 1.8;
  color: #303133;
  font-size: 16px;
  word-wrap: break-word;
}

.rich-content >>> h1,
.rich-content >>> h2,
.rich-content >>> h3 {
  font-weight: 600;
  color: #303133;
  margin: 24px 0 16px;
}
.rich-content >>> h1 { font-size: 24px; }
.rich-content >>> h2 { font-size: 20px; }
.rich-content >>> h3 { font-size: 18px; }

.rich-content >>> p {
  margin-bottom: 16px;
}

.rich-content >>> ul,
.rich-content >>> ol {
  padding-left: 24px;
  margin-bottom: 16px;
}

.rich-content >>> blockquote {
  border-left: 4px solid #409eff;
  padding: 12px 16px;
  margin: 16px 0;
  background-color: #ecf5ff;
  color: #606266;
  font-style: italic;
}

.rich-content >>> img {
  max-width: 100%;
  height: auto;
  border-radius: 8px;
  margin: 16px 0;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.rich-content >>> a {
  color: #409eff;
  text-decoration: none;
  border-bottom: 1px solid #409eff;
}
.rich-content >>> a:hover {
  color: #66b1ff;
  border-bottom-color: #66b1ff;
}

.rich-content >>> code {
  background-color: #f5f7fa;
  padding: 2px 6px;
  border-radius: 4px;
  font-family: 'Courier New', monospace;
  font-size: 14px;
  border: 1px solid #ebeef5;
}

.rich-content >>> pre {
  background-color: #2d2d2d;
  color: #f8f8f2;
  padding: 16px;
  border-radius: 6px;
  overflow-x: auto;
  margin: 16px 0;
}
.rich-content >>> pre code {
  background: none;
  border: none;
  padding: 0;
}
</style>