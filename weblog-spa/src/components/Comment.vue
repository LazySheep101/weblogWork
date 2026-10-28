<template>
    <div class="flex justify-center items-center mt-14 mb-7 text-gray-500 relative">
        <h2>全部评论
            (<span>{{ total }}</span>)
        </h2>
        <!-- <div class="absolute right-0 flex items-center text-sm text-gray-400 cursor-pointer">
            热门排序
            <span><svg t="1707712486359" class="icon w-5 h-5" viewBox="0 0 1024 1024" version="1.1"
                    xmlns="http://www.w3.org/2000/svg" p-id="4208" width="200" height="200">
                    <path
                        d="M468 246c0-19.9-16.1-36-36-36s-36 16.1-36 36v436.1l-58.5-58.5c-14.1-14.1-36.9-14.1-50.9 0-14.1 14.1-14.1 36.9 0 50.9L402.1 790c6.5 9.6 17.4 16 29.9 16 10.1 0 19.2-4.1 25.7-10.8l0.2-0.2c8.5-8.5 11.9-20.2 10.1-31.3V246zM741.9 340.5L621.5 220.1c-14.1-14.1-36.9-14.1-50.9 0-6.7 6.7-10.2 15.3-10.5 24 0 0.6-0.1 1.3-0.1 1.9v524c0 19.9 16.1 36 36 36s36-16.1 36-36V332.4l59 59c7 7 16.2 10.5 25.5 10.5s18.4-3.5 25.5-10.5c14-14 14-36.8-0.1-50.9z"
                        p-id="4209" fill="#8a8a8a"></path>
                </svg></span>
        </div> -->
    </div>
    <div class="w-full px-5 py-10 mb-3 bg-white border border-gray-200 rounded-lg dark:bg-gray-800 dark:border-gray-700">

        <form class="mt-5">
            <div class="flex gap-3">
                <div>
                    <!-- 头像 -->
                    <img v-if="commentStore.userInfo.avatar && commentStore.userInfo.avatar.length > 0" :src="commentStore.userInfo.avatar"
                        class="w-10 h-10 rounded-full">
                    <svg v-else class="w-10 h-10 text-gray-400 dark:text-gray-400" aria-hidden="true"
                        xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                        <path
                            d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                    </svg>


                </div>
                <div class="grow">
                    <div class="flex items-center gap-5 flex-row">
                        <div class="flex basis-1/3">
                            <span
                                class="inline-flex border-e-0 items-center px-3 text-xs text-gray-900 bg-gray-100 border rounded-e-0 border-gray-300 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                                昵称
                            </span>
                            <input @blur="onNicknameBlur" v-model="commentStore.userInfo.nickname"
                                data-tooltip-target="nickname-tooltip-click" data-tooltip-trigger="click" type="text"
                                id="website-admin"
                                class="rounded-none rounded-e-lg  border text-gray-900 focus:ring-sky-500 
focus:border-sky-500 block flex-1 min-w-0 w-full text-xs border-gray-300 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500"
                                placeholder="必填">
                            <!-- 昵称 Tooltip -->
                            <div id="nickname-tooltip-click" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 
text-xs font-medium text-white bg-gray-900 rounded-md shadow-sm opacity-0 tooltip dark:bg-gray-700">
                                输入 QQ 号会自动获取昵称和头像
                                <div class="tooltip-arrow" data-popper-arrow></div>
                            </div>
                        </div>
                        <div class="flex basis-1/3">
                            <span
                                class="inline-flex border-e-0 items-center px-3 text-xs text-gray-900 bg-gray-100 border rounded-e-0 border-gray-300 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                                邮箱
                            </span>
                            <input v-model="commentStore.userInfo.mail" data-tooltip-target="mail-tooltip-click"
                                data-tooltip-trigger="click" type="text" id="website-admin" class="rounded-none rounded-e-lg  border text-gray-900 focus:ring-sky-500 
focus:border-sky-500 block flex-1 min-w-0 w-full text-xs border-gray-300 p-2.5  dark:bg-gray-700 dark:border-gray-600 
dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500" placeholder="必填">
                            <!-- 邮箱 Tooltip -->
                            <div id="mail-tooltip-click" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 
text-xs font-medium text-white bg-gray-900 rounded-md shadow-sm opacity-0 tooltip dark:bg-gray-700">
                                收到回复将会发送到您的邮箱
                                <div class="tooltip-arrow" data-popper-arrow></div>
                            </div>
                        </div>
                        <div class="flex basis-1/3">
                            <span
                                class="inline-flex border-e-0 items-center px-3 text-xs text-gray-900 bg-gray-100 border rounded-e-0 border-gray-300 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                                网址
                            </span>
                            <input v-model="commentStore.userInfo.website" data-tooltip-target="website-tooltip-click"
                                data-tooltip-trigger="click" type="text" id="website-admin" class="rounded-none rounded-e-lg  border text-gray-900 
focus:ring-sky-500 focus:border-sky-500 block flex-1 min-w-0 w-full text-xs border-gray-300 p-2.5  dark:bg-gray-700
dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500"
                                placeholder="选填">
                            <!-- 网址 Tooltip -->
                            <div id="website-tooltip-click" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 
text-xs font-medium text-white bg-gray-900 rounded-md shadow-sm opacity-0 tooltip dark:bg-gray-700">
                                可通过点击头像访问你的网站 (非必填)
                                <div class="tooltip-arrow" data-popper-arrow></div>
                            </div>

                        </div>
                    </div>
                    <div
                        class="w-full mb-4 mt-4 border border-gray-200 rounded-lg bg-gray-50 dark:bg-gray-700 dark:border-gray-600">

                        <div class="px-4 py-2 bg-white rounded-t-lg dark:bg-gray-800">
                            <label for="comment" class="sr-only">Your comment</label>
                            <textarea v-model="commentForm.content" id="comment" rows="4" class="w-full px-0 text-sm text-gray-900 bg-white border-0 dark:bg-gray-800 
focus:ring-0 dark:text-white dark:placeholder-gray-400" placeholder="发表一个友善的评论吧..." required></textarea>
                        </div>
                        <div class="flex items-center justify-between px-3 py-2 border-t dark:border-gray-600">
                            <div @click="onPublishCommentClick" class="inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white 
bg-sky-600 rounded-lg focus:ring-4 focus:ring-sky-200 dark:focus:ring-sky-900 hover:bg-sky-700">
                                发送
                            </div>
                            <div class="flex ps-0 space-x-1 rtl:space-x-reverse sm:ps-2">
                                <!-- emoji -->
                                <div data-popover-target="emoji-popover-no-arrow"
                                    class="inline-flex justify-center items-center p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600">
                                    <svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                        viewBox="0 0 24 24">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                            stroke-width="2"
                                            d="M15 9h0M9 9h0m12 3a9 9 0 1 1-18 0 9 9 0 0 1 18 0ZM7 13c0 1 .5 2.4 1.5 3.2a5.5 5.5 0 0 0 7 0c1-.8 1.5-2.2 1.5-3.2 0 0-2 1-5 1s-5-1-5-1Z" />
                                    </svg>
                                </div>
                                <div data-popover id="emoji-popover-no-arrow" role="tooltip"
                                    class="absolute z-10 invisible inline-block w-64 text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800">
                                    <div class="p-2">
                                        <div class="grid grid-cols-6 gap-2">
                                            <div v-for="(emoji, index) in emojis" :key="index"
                                                class="text-2xl hover:cursor-pointer" @click="addEmoji(emoji)">{{ emoji }}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>

        <!-- 评论内容 -->
        <div v-if="comments && comments.length > 0" v-for="(comment, index) in comments" :key="index">

            <!-- 边界线 -->
            <div v-if="index > 0" class="border-t ml-12 mt-5  border-gray-100 dark:border-gray-700"></div>

            <div class="flex gap-3 mt-5">
                <div>
                    <!-- 头像 -->
                    <a v-if="comment.website && comment.website.length > 0" @click="jumpToWebsite(comment.website)"
                        class="cursor-pointer">
                        <img v-if="comment.avatar && comment.avatar.length > 0" :src="comment.avatar"
                            class="w-10 h-10 rounded-full">
                        <svg v-else class="w-10 h-10 text-gray-400 rounded-full dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                        </svg>
                    </a>
                    <div v-else>
                        <img v-if="comment.avatar && comment.avatar.length > 0" :src="comment.avatar"
                            class="w-10 h-10 rounded-full">
                        <svg v-else class="w-10 h-10 text-gray-400 rounded-full dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                        </svg>
                    </div>

                </div>
                <div class="flex flex-col gap-0.5 grow">
                    <div class="text-xs text-[#FB7299] font-bold">{{ comment.nickname }}
                        <!-- 博主标识 -->
                        <!-- <span v-if="comment.isAuthor"
                            class="bg-gray-100 text-sky-600 text-[10px] font-medium me-2 px-1 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300">博主</span> -->
                    </div>

                    <div class="mt-2 text-sm dark:text-gray-400">
                        {{ comment.content }}
                    </div>

                    <div class="flex items-center text-xs mt-2 text-gray-400">
                        <div>{{ comment.createTime }}</div>

                        <!-- <div class="flex items-center cursor-pointer">
                            <svg t="1707638319075" class="icon  w-[14px] h-[14px] ml-4 mr-2 " viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="20397" width="200" height="200">
                                <path
                                    d="M832 364.8h-147.2s19.2-64 32-179.2c6.4-57.6-38.4-115.2-102.4-121.6h-12.8c-51.2 0-83.2 32-102.4 76.8l-38.4 96c-32 64-57.6 102.4-76.8 115.2-25.6 12.8-121.6 12.8-128 12.8H128c-38.4 0-64 25.6-64 57.6v480c0 32 25.6 57.6 64 57.6h646.4c96 0 121.6-64 134.4-153.6l51.2-307.2c6.4-70.4-6.4-134.4-128-134.4z m-576 537.6H128V422.4h128v480z m640-409.6l-51.2 307.2c-12.8 57.6-12.8 102.4-76.8 102.4H320V422.4c44.8 0 70.4-6.4 89.6-19.2 32-12.8 64-64 108.8-147.2 25.6-64 38.4-96 44.8-102.4 6.4-19.2 19.2-32 44.8-32h6.4c32 0 44.8 32 44.8 51.2-12.8 102.4-32 166.4-32 166.4l-25.6 83.2h243.2c19.2 0 32 0 44.8 12.8 12.8 12.8 6.4 38.4 6.4 57.6z"
                                    p-id="20398" fill="#8a8a8a"></path>
                            </svg>
                            <span class="text-xs">0</span>
                        </div> -->

                        <div class="text-gray-400 cursor-pointer ml-4 rounded-sm hover:text-sky-600"
                            @click="showReplyForm(index, comment.nickname, comment.id, comment.id)">
                            回复
                        </div>
                    </div>

                </div>

            </div>

            <!-- 子评论 -->
            <div class="pl-12" v-if="comment.childComments && comment.childComments.length > 0"
                v-for="(childComment, index2) in comment.childComments" :key="index2">
                <!-- 评论内容 -->
                <div class="flex items-center gap-3 mt-5">
                    <div>
                        <div v-if="childComment.website && childComment.website.length > 0">
                            <a @click="jumpToWebsite(childComment.website)"
                        class="cursor-pointer">
                                <img v-if="childComment.avatar && childComment.avatar.length > 0" :src="childComment.avatar"
                            class="w-6 h-6 rounded-full">
                        <svg v-else class="w-6 h-6 text-gray-400 rounded-full dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                        </svg>
                            </a>
                        </div>
                        <div v-else>
                            <img v-if="childComment.avatar && childComment.avatar.length > 0" :src="childComment.avatar"
                            class="w-6 h-6 rounded-full">
                        <svg v-else class="w-6 h-6 text-gray-400 rounded-full dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                        </svg>
                        </div>
                    </div>
                    <div class="flex gap-0.5">
                        <div class="text-xs text-[#FB7299] font-bold">{{ childComment.nickname }}
                            <!-- 博主标识 -->
                            <!-- <span v-if="childComment.isAuthor"
                                class="bg-gray-100 text-sky-600 text-[10px] font-medium me-2 px-1 py-0.5 rounded dark:bg-blue-900 dark:text-blue-300">博主</span> -->
                            <span v-if="childComment.replyNickname" class="text-gray-400 font-normal ml-1 mr-1">回复
                                <span class="text-sky-600 font-normal text-xs">@{{ childComment.replyNickname }}</span>
                                <span class="text-gray-400"> :</span>
                            </span>

                        </div>

                    </div>
                    <div class="text-sm dark:text-gray-400">{{ childComment.content }}</div>
                </div>
                <div class="flex gap-3 items-center text-xs mt-2 text-gray-400">
                    <div class="ml-9 text-xs text-gray-400">{{ childComment.createTime }}</div>
                    <div class="flex items-center text-gray-400">


                        <!-- <div class="flex items-center cursor-pointer">
                            <svg t="1707638319075" class="icon  w-[14px] h-[14px] ml-1 mr-2 " viewBox="0 0 1024 1024" version="1.1"
                                xmlns="http://www.w3.org/2000/svg" p-id="20397" width="200" height="200">
                                <path
                                    d="M832 364.8h-147.2s19.2-64 32-179.2c6.4-57.6-38.4-115.2-102.4-121.6h-12.8c-51.2 0-83.2 32-102.4 76.8l-38.4 96c-32 64-57.6 102.4-76.8 115.2-25.6 12.8-121.6 12.8-128 12.8H128c-38.4 0-64 25.6-64 57.6v480c0 32 25.6 57.6 64 57.6h646.4c96 0 121.6-64 134.4-153.6l51.2-307.2c6.4-70.4-6.4-134.4-128-134.4z m-576 537.6H128V422.4h128v480z m640-409.6l-51.2 307.2c-12.8 57.6-12.8 102.4-76.8 102.4H320V422.4c44.8 0 70.4-6.4 89.6-19.2 32-12.8 64-64 108.8-147.2 25.6-64 38.4-96 44.8-102.4 6.4-19.2 19.2-32 44.8-32h6.4c32 0 44.8 32 44.8 51.2-12.8 102.4-32 166.4-32 166.4l-25.6 83.2h243.2c19.2 0 32 0 44.8 12.8 12.8 12.8 6.4 38.4 6.4 57.6z"
                                    p-id="20398" fill="#8a8a8a"></path>
                            </svg>
                            <span class="text-xs">0</span>
                        </div> -->

                        <div class="text-gray-400 cursor-pointer ml-1 rounded-sm hover:text-sky-600"
                            @click="showReplyForm(index, childComment.nickname, childComment.id, comment.id)">
                            回复
                        </div>
                    </div>

                </div>

            </div>

            <!-- 回复框 -->
            <form v-if="comment.isShowReplyForm" class="mt-5 pl-12">
                <div class="flex gap-3">
                    <div>
                        <!-- 头像 -->
                        <img v-if="commentStore.userInfo.avatar && commentStore.userInfo.avatar.length > 0" :src="commentStore.userInfo.avatar"
                            class="w-10 h-10 rounded-full">
                        <svg v-else class="w-9 h-9 text-gray-400 dark:text-gray-400" aria-hidden="true"
                            xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                            <path
                                d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z" />
                        </svg>
                    </div>
                    <div class="grow">
                        <div class="flex items-center gap-5 flex-row">
                            <div class="flex basis-1/3">
                                <span
                                    class="inline-flex border-e-0 items-center px-3 text-xs text-gray-900 bg-gray-100 border rounded-e-0 border-gray-300 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                                    昵称
                                </span>
                                <input @blur="onNicknameBlur" v-model="commentStore.userInfo.nickname"
                                    data-tooltip-target="nickname-tooltip-click2" data-tooltip-trigger="click" type="text"
                                    id="website-admin"
                                    class="rounded-none rounded-e-lg  border text-gray-900 focus:ring-sky-500 
focus:border-sky-500 block flex-1 min-w-0 w-full text-xs border-gray-300 p-2.5  dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500"
                                    placeholder="必填">
                                <!-- 昵称 Tooltip -->
                                <div id="nickname-tooltip-click2" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 
text-xs font-medium text-white bg-gray-900 rounded-md shadow-sm opacity-0 tooltip dark:bg-gray-700">
                                    输入 QQ 号会自动获取昵称和头像
                                    <div class="tooltip-arrow" data-popper-arrow></div>
                                </div>
                            </div>
                            <div class="flex basis-1/3">
                                <span
                                    class="inline-flex border-e-0 items-center px-3 text-xs text-gray-900 bg-gray-100 border rounded-e-0 border-gray-300 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                                    邮箱
                                </span>
                                <input v-model="commentStore.userInfo.mail" data-tooltip-target="mail-tooltip-click2"
                                    data-tooltip-trigger="click" type="text" id="website-admin" class="rounded-none rounded-e-lg  border text-gray-900 focus:ring-sky-500 
focus:border-sky-500 block flex-1 min-w-0 w-full text-xs border-gray-300 p-2.5  dark:bg-gray-700 dark:border-gray-600 
dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500" placeholder="必填">
                                <!-- 邮箱 Tooltip -->
                                <div id="mail-tooltip-click2" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 
text-xs font-medium text-white bg-gray-900 rounded-md shadow-sm opacity-0 tooltip dark:bg-gray-700">
                                    收到回复将会发送到您的邮箱
                                    <div class="tooltip-arrow" data-popper-arrow></div>
                                </div>
                            </div>
                            <div class="flex basis-1/3">
                                <span
                                    class="inline-flex border-e-0 items-center px-3 text-xs text-gray-900 bg-gray-100 border rounded-e-0 border-gray-300 rounded-s-md dark:bg-gray-600 dark:text-gray-400 dark:border-gray-600">
                                    网址
                                </span>
                                <input v-model="commentStore.userInfo.website" data-tooltip-target="website-tooltip-click2"
                                    data-tooltip-trigger="click" type="text" id="website-admin" class="rounded-none rounded-e-lg  border text-gray-900 
focus:ring-sky-500 focus:border-sky-500 block flex-1 min-w-0 w-full text-xs border-gray-300 p-2.5  dark:bg-gray-700
dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500"
                                    placeholder="选填">
                                <!-- 网址 Tooltip -->
                                <div id="website-tooltip-click2" role="tooltip" class="absolute z-10 invisible inline-block px-3 py-2 
text-xs font-medium text-white bg-gray-900 rounded-md shadow-sm opacity-0 tooltip dark:bg-gray-700">
                                    可通过点击头像访问你的网站 (非必填)
                                    <div class="tooltip-arrow" data-popper-arrow></div>
                                </div>

                            </div>
                        </div>
                        <div
                            class="w-full mb-4 mt-4 border border-gray-200 rounded-lg bg-gray-50 dark:bg-gray-700 dark:border-gray-600">

                            <div class="px-4 py-2 bg-white rounded-t-lg dark:bg-gray-800">
                                <label for="comment" class="sr-only">Your comment</label>
                                <textarea v-model="replyContent" id="comment" rows="4" class="w-full px-0 text-sm text-gray-900 bg-white border-0 dark:bg-gray-800 
focus:ring-0 dark:text-white dark:placeholder-gray-400" :placeholder="replyPlaceholderText" required></textarea>
                            </div>
                            <div class="flex items-center justify-between px-3 py-2 border-t dark:border-gray-600">
                                <div @click="onReplyContentSubmit" class="inline-flex items-center py-2.5 px-4 text-xs font-medium text-center text-white 
 rounded-lg  bg-sky-600 focus:ring-4 focus:ring-sky-200 dark:focus:ring-sky-900 hover:bg-sky-700">
                                    发送
                                </div>
                                <div class="flex ps-0 space-x-1 rtl:space-x-reverse sm:ps-2">
                                    <!-- emoji -->
                                    <div data-popover-target="emoji-popover-no-arrow2"
                                        class="inline-flex justify-center items-center p-2 text-gray-500 rounded cursor-pointer hover:text-gray-900 hover:bg-gray-100 dark:text-gray-400 dark:hover:text-white dark:hover:bg-gray-600">
                                        <svg class="w-4 h-4" aria-hidden="true" xmlns="http://www.w3.org/2000/svg"
                                            fill="none" viewBox="0 0 24 24">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                                stroke-width="2"
                                                d="M15 9h0M9 9h0m12 3a9 9 0 1 1-18 0 9 9 0 0 1 18 0ZM7 13c0 1 .5 2.4 1.5 3.2a5.5 5.5 0 0 0 7 0c1-.8 1.5-2.2 1.5-3.2 0 0-2 1-5 1s-5-1-5-1Z" />
                                        </svg>
                                        <span class="sr-only">Upload image</span>
                                    </div>
                                    <div data-popover id="emoji-popover-no-arrow2" role="tooltip"
                                        class="absolute z-10 invisible inline-block w-64 text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 dark:text-gray-400 dark:border-gray-600 dark:bg-gray-800">
                                        <div class="p-2">
                                            <div class="grid grid-cols-6 gap-2">
                                                <div v-for="(emoji, index) in emojis" :key="index"
                                                    class="text-2xl hover:cursor-pointer" @click="addReplyEmoji(emoji)">{{
                                                        emoji }}
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div v-else class="flex items-center mt-10 mb-5 justify-center text-gray-400">还没有任何评论哟~</div>

    </div>
</template>

<script setup>
import { ref, reactive, onMounted, nextTick, watch } from 'vue'
import { showMessage } from '@/composables/util'
import {
    initTooltips, initPopovers
} from 'flowbite'
import { publishComment, findComments, getUserInfoByQQ } from '@/api/frontend/comment'
import { useRoute } from 'vue-router'
import { useCommentStore } from '@/stores/comment'

const commentStore = useCommentStore()

const route = useRoute()

// 监听路由
watch(route, (newRoute, oldRoute) => {
    commentForm.routerUrl = route.path
    // 重新渲染评论数据
    initComments()
})

onMounted(() => {
    initTooltips(),
    initPopovers()
})

const emojis = ref(['😃', '😁', '😅', '😂', '😍', '😜', '😝', '🤑', '🥵', '🥰', '😙', '😎'
    , '😵', '😭', '😱', '😖', '🥳', '👽', '🙈', '🤡', '😤', '💣', '💯', '💢', '❤️', '👋', '👌', '🤏', '🙏'])

const commentForm = reactive({
    avatar: '',
    content: '',
    mail: '',
    nickname: '',
    routerUrl: route.path,
    website: '',
    replyCommentId: null,
    parentCommentId: null
})

function initCommentUserInfo() {
    commentForm.avatar = commentStore.userInfo.avatar
    commentForm.mail = commentStore.userInfo.mail
    commentForm.nickname = commentStore.userInfo.nickname
    commentForm.website = commentStore.userInfo.website
}
initCommentUserInfo()

const onPublishCommentClick = () => {
    initCommentUserInfo()
    // 校验
    if (commentForm.nickname.length === 0) {
        showMessage('请填写 QQ 号或昵称', 'warning')
        return
    }
    if (commentForm.mail.length === 0 || !emailRegex.test(commentForm.mail)) {
        showMessage('邮箱格式不正确', 'warning')
        return
    }
    if (commentForm.content.length === 0) {
        showMessage('请填写评论内容', 'warning')
        return
    }

    publishComment(commentForm).then(res => {
        if (!res.success) {
            // 获取服务端返回的错误消息
            let message = res.message
            // 提示错误消息
            showMessage(message, 'warning')
            return
        }

        showMessage('评论发布成功')
        commentForm.content = ''
        initComments()
    })
}


const onNicknameBlur = () => {

    let nickname = commentStore.userInfo.nickname
    // 校验昵称是否是纯数字
    console.log(checkIfPureNumber(nickname))
    if (!checkIfPureNumber(nickname)) {
        return
    }

    console.log('onNicknameBlur')
    // 若是
    getUserInfoByQQ(nickname).then(res => {
        if (!res.success) {
            // 提示错误消息
            showMessage('获取 QQ 信息失败', 'error')
            return
        }

        commentStore.userInfo.avatar = res.data.avatar
        commentStore.userInfo.nickname = res.data.nickname
        commentStore.userInfo.mail = res.data.mail

    })
}

// 校验是否是纯数字 QQ 号
function checkIfPureNumber(text) {
    const trimmedValue = text.trim();
    return /^\d+$/.test(trimmedValue);
}

const total = ref(0)
const comments = ref([])

function initComments() {
    findComments(route.path).then(res => {
        if (res.success) {
            total.value = res.data.total
            comments.value = res.data.comments
        }
    })
}
initComments()

const currReplyCommentId = ref(null)
const currParentCommentId = ref(null)
const showReplyForm = (index, nickname, replyCommentId, parentCommentId) => {
    currReplyCommentId.value = replyCommentId
    currParentCommentId.value = parentCommentId
    comments.value.forEach(comment => comment.isShowReplyForm = false)
    let comment = comments.value[index]
    comment.isShowReplyForm = true
    replyPlaceholderText.value = '回复 @' + nickname + ':'
    nextTick(() => {
        initPopovers(),
        initTooltips()
    })
}

const replyPlaceholderText = ref('发表一个友善的评论吧...')


const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
// 回复的评论
const replyContent = ref('')
const onReplyContentSubmit = () => {
    initCommentUserInfo()
    // 校验
    if (commentForm.nickname.length === 0) {
        showMessage('请填写 QQ 号或昵称', 'warning')
        return
    }
    if (commentForm.mail.length === 0 || !emailRegex.test(commentForm.mail)) {
        showMessage('邮箱格式不正确', 'warning')
        return
    }
    if (replyContent.value.length === 0) {
        showMessage('请填写回复内容', 'warning')
        return
    }
    commentForm.content = replyContent.value
    commentForm.replyCommentId = currReplyCommentId.value
    commentForm.parentCommentId = currParentCommentId.value
    console.log(commentForm)


    // 请求接口
    publishComment(commentForm).then(res => {
        if (!res.success) {
            // 获取服务端返回的错误消息
            let message = res.message
            // 提示错误消息
            showMessage(message, 'error')
            return
        }

        showMessage('回复评论成功')
        replyContent.value = ''
        commentForm.content = ''
        initComments()
    })
}

const addEmoji = (emoji) => {
    commentForm.content = commentForm.content + emoji
}

const addReplyEmoji = (emoji) => {
    replyContent.value = replyContent.value + emoji
}

const jumpToWebsite = (url) => {
    // 在新窗口访问新的链接地址
    window.open(url.startsWith('http') ? url : 'http://' + url, '_blank');
}

</script>