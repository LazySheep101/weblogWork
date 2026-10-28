<template>

    <Header></Header>

    <!-- 主内容区域 -->
    <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-4">
        <!-- grid 表格布局，分为 4 列 -->
        <div class="grid grid-cols-12 gap-7">
            <!-- 左边栏，占用 3 列 -->
            <div class="col-span-12 md:col-span-8 lg:col-span-9 mb-3">

                <!-- 公告 -->
                <Notice></Notice>

                <!-- 文章列表，grid 表格布局，分为 2 列 -->
                <div class="grid grid-cols-2 gap-4">
                    <div v-for="(article, index) in articles" :key="index" class="col-span-2 lg:col-span-1 animate__animated animate__fadeInUp">
                        <div class="relative bg-white h-full border border-gray-200 rounded-lg hover:scale-[1.03] dark:bg-gray-800 dark:border-gray-700">
                            <!-- 文章封面 -->
                            <a @click="goArticleDetailPage(article.id)" class="cursor-pointer">
                                <img class="rounded-t-lg h-48 w-full"
                                    :src="article.cover" />
                            </a>
                            <div class="p-5">
                                <!-- 标签 -->
                                <div class="mb-3" v-if="article.tags?.length">
                                    <span v-for="(tag, tagIndex) in article.tags" :key="tagIndex" @click="goTagArticleListPage(tag.id, tag.name)"
                                        class="cursor-pointer bg-green-100 text-green-800 text-xs font-medium mr-2 
                                        px-2.5 py-0.5 rounded hover:bg-green-200 hover:text-green-900 dark:bg-green-900 
                                        dark:hover:bg-green-950
                                        dark:text-green-300">
                                        {{ tag.name }}
                                    </span>
                                </div>
                                <!-- 文章标题 -->
                                <a @click="goArticleDetailPage(article.id)" class="cursor-pointer">
                                    <h2 class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white">
                                        <span class="hover:border-gray-400 hover:border-b-2 dark:hover:border-gray-400">{{ article.title }}</span>
                                    </h2>
                                </a>
                                <!-- 文章摘要 -->
                                <p v-if="article.summary" class="mb-3 font-normal text-gray-500 dark:text-gray-400">{{ article.summary }}</p>
                                <!-- 文章发布时间、所属分类 -->
                                <p class="flex items-center font-normal text-gray-400 text-sm dark:text-gray-400">
                                    <!-- 发布时间 -->
                                    <svg class="inline w-3 h-3 mr-2 text-gray-400" aria-hidden="true"
                                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="2"
                                            d="M5 1v3m5-3v3m5-3v3M1 7h18M5 11h10M2 3h16a1 1 0 0 1 1 1v14a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V4a1 1 0 0 1 1-1Z" />
                                    </svg>
                                    {{ article.createDate }}

                                    <!-- 所属分类 -->
                                    <svg class="inline w-3 h-3 ml-5 mr-2 text-gray-400" aria-hidden="true"
                                        xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 18 18">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="2"
                                            d="M1 5v11a1 1 0 0 0 1 1h14a1 1 0 0 0 1-1V6a1 1 0 0 0-1-1H1Zm0 0V2a1 1 0 0 1 1-1h5.443a1 1 0 0 1 .8.4l2.7 3.6H1Z" />
                                    </svg>
                                    <a v-if="article.category" @click="goCategoryArticleListPage(article.category.id, article.category.name)" class="cursor-pointer text-gray-400 hover:underline">{{ article.category.name }}</a>
                                </p>
                            </div>

                            <!-- 是否置顶 -->
                            <div v-if="article.isTop" class="absolute inline-flex items-center justify-center w-14 h-7 text-xs font-bold text-white bg-red-500 border-2 border-white rounded-full -top-2 -end-2 dark:border-gray-900">
                                置顶
                            </div>
                        </div>
                    </div>
                </div>
                <!-- 分页 -->
                <nav aria-label="Page navigation example" class="mt-10 flex justify-center items-center animate__animated animate__fadeInUp">
                    <span class="text-sm text-gray-600 mr-5 dark:text-gray-400">
                        共 {{ total }} 篇文章
                    </span>
                    <ul class="flex items-center -space-x-px h-10 text-base">
                        <!-- 上一页 -->
                        <li>
                            <a @click="getArticles(current - 1)"
                                class="flex items-center justify-center px-4 h-10 ml-0 leading-tight text-gray-500 bg-white border border-gray-300 rounded-l-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                                :class="[current > 1 ? '' : 'cursor-not-allowed']"
                                >

                                <span class="sr-only">上一页</span>
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                    viewBox="0 0 6 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="M5 1 1 5l4 4" />
                                </svg>
                            </a>
                        </li>
                        <!-- 页码 -->
                        <li v-for="(pageNo, index) in pages" :key="index">
                            <a @click="getArticles(pageNo)"
                                class="flex items-center justify-center px-4 h-10 leading-tight border  dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                                :class="[pageNo == current ? 'text-sky-600  bg-sky-50 border-sky-500 hover:bg-blue-100 hover:text-sky-700' : 'text-gray-500 border-gray-300 bg-white hover:bg-gray-100 hover:text-gray-700']"
                                >
                                {{ index + 1 }}
                            </a>
                        </li>
                        <!-- 下一页 -->
                        <li>
                            <a @click="getArticles(current + 1)"
                                class="flex items-center justify-center px-4 h-10 leading-tight text-gray-500 bg-white border border-gray-300 rounded-r-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"
                                :class="[current < pages ? '' : 'cursor-not-allowed']"
                                >
                                <span class="sr-only">下一页</span>
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                    viewBox="0 0 6 10">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                        stroke-width="2" d="m1 9 4-4-4-4" />
                                </svg>
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>


            <!-- 右边侧边栏，占用一列 -->
            <aside class="col-span-12 md:col-span-4 lg:col-span-3 animate__animated animate__fadeInUp">
                <div class="sticky top-[4.5rem]">
                    <!-- 博主信息 -->
                    <UserInfoCard></UserInfoCard>

                    <!-- 聊天室入口 -->
                    <div @click="jump2ChatRoomPage" class="group w-full py-5 px-4 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700 cursor-pointer hover:shadow-md transition-shadow">
                        <div class="flex items-center justify-between">
                            <div class="flex items-center gap-3">
                                <!-- 聊天图标 -->
                                <div class="w-10 h-10 rounded-full bg-sky-100 dark:bg-sky-900 flex items-center justify-center">
                                    <svg t="1768296965309" class="w-4 h-4 text-sky-600 dark:text-sky-400" fill="currentColor" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9348" width="200" height="200">
                                        <path d="M512 457.031816A55.033029 55.033029 0 1 0 567.033029 512.064845 55.033029 55.033029 0 0 0 512 457.031816z m220.132117 0A55.033029 55.033029 0 1 0 786.738533 512.064845a55.033029 55.033029 0 0 0-55.033029-55.033029z m-439.837621 0A55.033029 55.033029 0 1 0 346.900913 512.064845a55.033029 55.033029 0 0 0-55.03303-55.033029z" p-id="9349"></path><path d="M917.70861 637.062345a405.70861 405.70861 0 0 1-93.854778 127.983789A442.823909 442.823909 0 0 1 682.645052 853.354948a475.246469 475.246469 0 0 1-324.225598 7.252415 35.835461 35.835461 0 0 0-29.436272 2.986288L213.37116 929.718609l-18.344343-154.860384a35.835461 35.835461 0 0 0-10.238703-20.904019 389.923943 389.923943 0 0 1-115.18541-274.738533 378.832015 378.832015 0 0 1 34.12901-157.846673 405.70861 405.70861 0 0 1 93.854778-127.983789A442.823909 442.823909 0 0 1 341.354948 104.223172a474.393244 474.393244 0 0 1 343.849779 0 441.117458 441.117458 0 0 1 138.649105 87.882201 406.135223 406.135223 0 0 1 93.854778 127.983789 380.111852 380.111852 0 0 1 0 315.266733zM981.273892 291.932729a476.099694 476.099694 0 0 0-110.492671-152.300709 511.935155 511.935155 0 0 0-160.406348-102.387031 546.91739 546.91739 0 0 0-396.749746 0 511.935155 511.935155 0 0 0-162.539411 102.387031A478.232757 478.232757 0 0 0 42.726108 291.932729a451.782774 451.782774 0 0 0-42.661263 187.282944 461.168252 461.168252 0 0 0 127.983789 316.119958l23.037082 196.668422a36.262073 36.262073 0 0 0 35.835461 31.995947 36.262073 36.262073 0 0 0 17.91773-4.692739l148.461195-85.322526a547.344003 547.344003 0 0 0 358.354608-13.651604 511.935155 511.935155 0 0 0 162.539412-102.387031A477.806145 477.806145 0 0 0 981.273892 666.925229a453.915837 453.915837 0 0 0 0-374.9925z" p-id="9350"></path>
                                    </svg>
                                </div>
                                <!-- 描述信息 -->
                                <div>
                                    <h3 class="text-sm font-medium text-gray-900 dark:text-white">公共聊天室</h3>
                                    <p class="text-xs text-gray-500 dark:text-gray-400">一起来聊天吧</p>
                                </div>
                            </div>
                            <!-- 箭头 -->
                            <svg class="w-3 h-3 text-gray-400 dark:text-white transition-transform duration-300 group-hover:translate-x-1" aria-hidden="true"
                                xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 8 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="m1 13 5.7-5.326a.909.909 0 0 0 0-1.348L1 1" />
                            </svg>
                        </div>
                    </div>

                    <!-- 分类 -->
                    <CategoryListCard></CategoryListCard>

                    <!-- 标签 -->
                    <TagListCard></TagListCard>
                </div>
            </aside>
    </div>

    </main>

    <!-- 返回顶部 -->
    <ScrollToTopButton></ScrollToTopButton>

    <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import UserInfoCard from '@/layouts/frontend/components/UserInfoCard.vue'
import CategoryListCard from '@/layouts/frontend/components/CategoryListCard.vue'
import TagListCard from '@/layouts/frontend/components/TagListCard.vue'
import ScrollToTopButton from '@/layouts/frontend/components/ScrollToTopButton.vue'
import { initTooltips } from 'flowbite'
import { onMounted, ref } from 'vue'
import { getArticlePageList } from '@/api/frontend/article'
import { useRouter } from 'vue-router'
import Notice from '@/components/Notice.vue'

const router = useRouter()

// 跳转分类文章列表页
const goCategoryArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（分类 ID、分类名称）
    router.push({path: '/category/article/list', query: {id, name}})
}

// 跳转公共聊天室页面
const jump2ChatRoomPage = () => {
    router.push('/chat')
}

// initialize components based on data attribute selectors
onMounted(() => {
    initTooltips()
})

// 文章集合
const articles = ref([])
// 当前页码
const current = ref(1)
// 每页显示的文章数
const size = ref(10)
// 总文章数
const total = ref(0)
// 总共多少页
const pages = ref(0)


function getArticles(currentNo) {
    // 上下页是否能点击判断，当要跳转上一页且页码小于 1 时，则不允许跳转；当要跳转下一页且页码大于总页数时，则不允许跳转
    if (currentNo < 1 || (pages.value > 0 && currentNo > pages.value)) return
    // 调用分页接口渲染数据
    getArticlePageList({current: currentNo, size: size.value}).then((res) => {
        if (res.success) {
            articles.value = res.data
            current.value = res.current
            size.value = res.size
            total.value = res.total
            pages.value = res.pages
        }
    })
}
getArticles(current.value)

// 跳转文章详情页
const goArticleDetailPage = (articleId) => {
    router.push('/article/' + articleId)
}

// 跳转标签文章列表页
const goTagArticleListPage = (id, name) => {
    // 跳转时通过 query 携带参数（标签 ID、标签名称）
    router.push({path: '/tag/article/list', query: {id, name}})
}
</script>
