
<template>
   <!-- 设置语言为中文 -->
   <el-config-provider :locale="locale">
      <div class="flex flex-col min-h-screen">
         <router-view></router-view>
      </div>
   </el-config-provider>
</template>

<script setup>
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
const locale = zhCn

console.log(import.meta.env)
</script>

<style>
/* 自定义顶部加载 Loading 颜色 */
#nprogress .bar {
   background: #409eff!important;
}

/* 暗黑模式 body 背景色 */
.dark body {
   --tw-bg-opacity: 1;
    background-color: rgb(17 24 39 / var(--tw-bg-opacity));
}

/* 滚动条 */
::-webkit-scrollbar-track-piece {
    border-radius: 6px;
    background: rgba(0,0,0,.1);
}

::-webkit-scrollbar-thumb {
    border-radius: 6px;
    background: #2980b9;
}

::-webkit-scrollbar {
    width: 6px;
    height: 6px;
}
</style>
