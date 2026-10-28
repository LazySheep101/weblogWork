<template>
    <div v-if="tags && tags.length > 0" class="w-full p-5 mb-3 bg-white border border-gray-200 rounded-lg 
        dark:bg-gray-800 dark:border-gray-700">
        <div class="flex mb-2">
        <!-- 标签标题 -->
        <h2 class="flex items-center font-bold text-gray-900 uppercase dark:text-white">
            <!-- 标签图标 -->
            <svg t="1698980289658" class="icon w-4 h-4 mr-2" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="13858" width="200" height="200"><path d="M646.4512 627.5584m-298.1888 0a298.1888 298.1888 0 1 0 596.3776 0 298.1888 298.1888 0 1 0-596.3776 0Z" fill="#C7ACEF" p-id="13859"></path><path d="M467.6096 962.5088c-34.4064 0-68.7616-13.1072-94.976-39.2704l-276.48-276.48c-52.3776-52.3776-52.3776-137.5744 0-189.9008L465.4592 87.552a105.216 105.216 0 0 1 76.8512-30.6176l308.6336 8.3456c55.3472 1.4848 100.096 46.0288 101.7856 101.376l9.5744 310.1696c0.8704 28.7744-10.2912 56.9344-30.6176 77.2608l-369.2032 369.2032c-26.112 26.112-60.4672 39.2192-94.8736 39.2192z m71.8848-844.1856c-11.4176 0-22.4768 4.5568-30.5664 12.6464L139.6224 500.2752c-28.416 28.416-28.416 74.6496 0 103.0144l276.48 276.48c28.416 28.416 74.6496 28.416 103.0144 0l369.2032-369.2032a43.4176 43.4176 0 0 0 12.6464-31.8976l-9.5744-310.1696c-0.7168-22.8864-19.2-41.2672-42.0352-41.8816l-308.6336-8.3456c-0.4608 0.0512-0.8192 0.0512-1.2288 0.0512z" fill="#4F4F4F" p-id="13860"></path><path d="M676.4032 445.5424c-62.208 0-112.8448-50.6368-112.8448-112.8448s50.6368-112.8448 112.8448-112.8448c62.208 0 112.8448 50.6368 112.8448 112.8448s-50.6368 112.8448-112.8448 112.8448z m0-164.1984c-28.3648 0-51.4048 23.04-51.4048 51.4048s23.04 51.4048 51.4048 51.4048c28.3648 0 51.4048-23.04 51.4048-51.4048s-23.0912-51.4048-51.4048-51.4048z" fill="#4F4F4F" p-id="13861"></path></svg>
            标签
        </h2>

            <span class="grow"></span>
            <a @click="router.push('/tag/list')" 
            class=" bg-gray-100 flex items-center px-2.5 py-1 text-xs font-medium text-center 
            text-gray-900  rounded-lg hover:bg-gray-200 focus:ring-4 focus:outline-none focus:ring-gray-200 
            dark:bg-gray-800 dark:text-white dark:border dark:border-gray-700 dark:hover:bg-gray-700 dark:hover:border-gray-700
             dark:focus:ring-gray-700">
            <svg class="w-[7px] h-[7px] text-gray-400 dark:text-white" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 8 14">
    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 13 5.7-5.326a.909.909 0 0 0 0-1.348L1 1"/>
  </svg>
        </a>


        </div>

        <!-- 标签列表 -->
        <span v-for="(tag, index) in tags" :key="index" @click="goTagArticleListPage(tag.id, tag.name)"
            class="inline-block mb-1.5 cursor-pointer bg-green-100 text-green-800 text-xs font-medium mr-2 px-2.5 py-1 
            rounded hover:bg-green-200 hover:text-green-900 dark:bg-green-900 dark:hover:bg-green-950 dark:text-green-300">
            {{ tag.name }}
        </span>
    </div>
</template>

<script setup>
import { getTagList } from '@/api/frontend/tag'
import { ref } from 'vue'
import { useRouter } from 'vue-router'


const router = useRouter()
// 一次显示的标签数
const size = ref(20)

// 所有标签
const tags = ref([])
getTagList({size: size.value}).then((res) => {
    if (res.success) {
        tags.value = res.data
    }
})

// 跳转标签文章列表页
const goTagArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（标签 ID、标签名称）
    router.push({path: '/tag/article/list', query: {id, name}})
}
</script>
