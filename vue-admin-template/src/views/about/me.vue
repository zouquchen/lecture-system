
<template>
  <el-card class="box-card" style="margin:20px">
    <!-- class markdown-body 必须加，否则标签样式会出现问题 -->
    <div class="markdown-body">
      <markdown />
    </div>
  </el-card>

</template>

<script>
// 引入 markdown 文件，引入后是一个组件，需要在 components 中注册
import markdown from '@/docs/about_me.md'
// 代码高亮
import 'highlight.js/styles/github.css'
// 其他元素使用 github 的样式
import 'github-markdown-css'
export default {
  components: {
    markdown
  }
}
</script>
