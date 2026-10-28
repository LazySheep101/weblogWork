<template>
    <Header></Header>

    <!-- 主内容区域 -->
    <main class="container max-w-screen-xl mx-auto px-4 md:px-6 py-4">
        <div class="flex h-[calc(100vh-180px)] border border-gray-200 rounded-lg bg-white dark:bg-gray-800 dark:border-gray-700">
            <!-- 左侧：聊天区域 -->
            <div class="flex-1 flex flex-col">
                <!-- 历史聊天记录区域 -->
                <div class="flex-1 flex flex-col min-h-0">
                    <!-- 聊天室标题 -->
                    <div class="p-4 border-b dark:border-gray-700 flex gap-3 justify-between items-center">
                        <div class="flex gap-3">
                            <!-- Logo 图标 -->
                            <div class="w-12 rounded-full bg-sky-100 dark:bg-sky-900 flex items-center justify-center">
                                <svg t="1768296965309" class="w-4 h-4 text-sky-600 dark:text-sky-400" fill="currentColor" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="9348" width="200" height="200">
                                    <path d="M512 457.031816A55.033029 55.033029 0 1 0 567.033029 512.064845 55.033029 55.033029 0 0 0 512 457.031816z m220.132117 0A55.033029 55.033029 0 1 0 786.738533 512.064845a55.033029 55.033029 0 0 0-55.033029-55.033029z m-439.837621 0A55.033029 55.033029 0 1 0 346.900913 512.064845a55.033029 55.033029 0 0 0-55.03303-55.033029z" p-id="9349"></path><path d="M917.70861 637.062345a405.70861 405.70861 0 0 1-93.854778 127.983789A442.823909 442.823909 0 0 1 682.645052 853.354948a475.246469 475.246469 0 0 1-324.225598 7.252415 35.835461 35.835461 0 0 0-29.436272 2.986288L213.37116 929.718609l-18.344343-154.860384a35.835461 35.835461 0 0 0-10.238703-20.904019 389.923943 389.923943 0 0 1-115.18541-274.738533 378.832015 378.832015 0 0 1 34.12901-157.846673 405.70861 405.70861 0 0 1 93.854778-127.983789A442.823909 442.823909 0 0 1 341.354948 104.223172a474.393244 474.393244 0 0 1 343.849779 0 441.117458 441.117458 0 0 1 138.649105 87.882201 406.135223 406.135223 0 0 1 93.854778 127.983789 380.111852 380.111852 0 0 1 0 315.266733zM981.273892 291.932729a476.099694 476.099694 0 0 0-110.492671-152.300709 511.935155 511.935155 0 0 0-160.406348-102.387031 546.91739 546.91739 0 0 0-396.749746 0 511.935155 511.935155 0 0 0-162.539411 102.387031A478.232757 478.232757 0 0 0 42.726108 291.932729a451.782774 451.782774 0 0 0-42.661263 187.282944 461.168252 461.168252 0 0 0 127.983789 316.119958l23.037082 196.668422a36.262073 36.262073 0 0 0 35.835461 31.995947 36.262073 36.262073 0 0 0 17.91773-4.692739l148.461195-85.322526a547.344003 547.344003 0 0 0 358.354608-13.651604 511.935155 511.935155 0 0 0 162.539412-102.387031A477.806145 477.806145 0 0 0 981.273892 666.925229a453.915837 453.915837 0 0 0 0-374.9925z" p-id="9350"></path>
                                </svg>
                            </div>
                            <!-- 标题与描述 -->
                            <div>
                                <h2 class="font-bold text-gray-800 dark:text-gray-200 text-lg">公共聊天室</h2>
                                <p class="text-xs text-gray-500 dark:text-gray-400">欢迎来到聊天室</p>
                            </div>
                        </div>
                        
                        <!-- 右侧：用户信息 -->
                        <div v-if="chatUserStore.chatUserInfo && chatUserStore.chatUserInfo.nickname" class="flex items-center gap-3">
                            <!-- 当前用户头像 -->
                            <img v-if="chatUserStore.chatUserInfo.avatar" :src="chatUserStore.chatUserInfo.avatar" :alt="chatUserStore.chatUserInfo.nickname" class="w-10 h-10 rounded-full object-cover border-2 border-sky-200 dark:border-sky-800" />
                            <svg v-else class="w-10 h-10 text-gray-400 border-2 border-sky-200 dark:border-sky-800 rounded-full" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"></path>
                            </svg>
                            <div>
                                <!-- 当前用户昵称 -->
                                <p class="text-sm font-medium text-gray-900 dark:text-gray-200">{{ chatUserStore.chatUserInfo.nickname }}</p>
                                <button 
                                    @click="leaveChatRoom"
                                    class="text-xs text-red-500 hover:text-red-600 transition-colors flex items-center gap-1"
                                >
                                    <!-- 退出图标 -->
                                    <svg class="w-3.5 h-3.5" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"></path>
                                    </svg>
                                    退出聊天
                                </button>
                            </div>
                        </div>
                    </div>
                    
                    <!-- 聊天记录 -->
                    <div ref="chatMessagesContainer" @scroll="handleScroll" class="flex-1 overflow-y-auto p-4 space-y-4 bg-gray-50 dark:bg-gray-900">
                        <!-- 没有更多消息提示 -->
                        <div v-if="isNoMoreMessages && chatMessages.length > 0" class="flex justify-center py-2">
                            <span class="text-xs text-gray-400 bg-gray-100 dark:bg-gray-800 px-3 py-1 rounded-full">没有更多消息了</span>
                        </div>
                        
                        <!-- 加载更多提示 -->
                        <div v-if="isLoadingMore" class="flex justify-center py-2">
                            <div class="flex items-center gap-2 text-sm text-gray-500">
                                <svg class="animate-spin h-4 w-4" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                    <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                                    <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                </svg>
                                <span>加载更多消息...</span>
                            </div>
                        </div>
                        
                        <!-- 动态渲染消息 -->
                        <template v-for="message in chatMessages">
                            <!-- 系统提示消息 (type === 0) -->
                            <div v-if="message.type === 0" class="flex justify-center">
                                <span class="text-xs text-gray-500 bg-gray-200 dark:bg-gray-700 dark:text-gray-400 px-4 py-1.5 rounded-full">
                                    {{ message.nickname }} {{ message.content }}，当前在线人数: {{ message.onlineCount }}
                                </span>
                            </div>

                            <!-- 用户消息 (type === 1) -->
                            <div v-else-if="message.type === 1" :class="isMyMessage(message) ? 'flex gap-3 flex-row-reverse' : 'flex gap-3'">
                                <!-- 头像 -->
                                <img v-if="message.avatar" :src="message.avatar" :alt="message.nickname" class="w-10 h-10 rounded-full flex-shrink-0 object-cover" />
                                <svg v-else class="w-10 h-10 text-gray-400 dark:text-gray-400 flex-shrink-0" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"></path>
                                </svg>
                                
                                <!-- 消息内容 -->
                                <div :class="isMyMessage(message) ? 'flex-1 flex flex-col items-end' : 'flex-1'">
                                    <div :class="isMyMessage(message) ? 'flex items-center gap-2 mb-1 flex-row-reverse' : 'flex items-center gap-2 mb-1'">
                                        <span class="text-sm font-medium text-gray-900 dark:text-gray-200">{{ message.nickname }}</span>
                                        <span class="text-xs text-gray-400">{{ message.time }}</span>
                                    </div>
                                    <div :class="isMyMessage(message) ? 'inline-block bg-sky-500 dark:bg-sky-600 rounded-lg px-4 py-2 shadow-sm' : 'inline-block bg-white dark:bg-gray-800 rounded-lg px-4 py-2 shadow-sm'">
                                        <p :class="isMyMessage(message) ? 'text-sm text-white' : 'text-sm text-gray-700 dark:text-gray-300'" style="white-space: pre-wrap; word-break: break-word;">{{ message.content }}</p>
                                    </div>
                                </div>
                            </div>
                        </template>
                    </div>
                </div>
                <!-- 输入区域 -->
                <div class="h-32 border-t border-gray-200 dark:border-gray-700 py-3 px-4 flex flex-col gap-2 bg-white dark:bg-gray-800">
                    <!-- 输入框 -->
                    <textarea 
                        v-model="messageInput"
                        @focus="onMessageInputFocus"
                        @keydown="onMessageInputKeydown"
                        placeholder="请输入消息..."
                        class="flex-1 p-0 resize-none border-0 focus:outline-none bg-transparent dark:text-white dark:placeholder-gray-400"
                    ></textarea>

                    <!-- 发送按钮区域 -->
                    <div class="flex justify-between items-center">
                        <!-- Emoji 表情选择 -->
                        <button 
                            id="emoji-button"
                            type="button"
                            class="p-2 text-gray-500 rounded-lg hover:bg-gray-100 dark:text-gray-400 dark:hover:bg-gray-700 focus:outline-none"
                        >
                            <svg t="1768467954407" class="w-6 h-6" viewBox="0 0 1024 1024" fill="currentColor" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5062" width="200" height="200"><path d="M512 102.4C286.037333 102.4 102.4 286.378667 102.4 512c0 225.962667 183.637333 409.6 409.6 409.6 225.621333 0 409.6-183.637333 409.6-409.6 0-225.621333-183.978667-409.6-409.6-409.6z m-220.501333 315.733333c0-37.546667 30.378667-68.266667 67.925333-68.266666 37.888 0 68.608 30.72 68.608 68.266666 0 37.888-30.72 68.608-68.608 68.608a68.266667 68.266667 0 0 1-67.925333-68.608z m369.322666 268.629334c-40.96 40.96-95.573333 63.829333-153.6 63.829333s-112.64-22.869333-153.6-63.829333a33.9968 33.9968 0 1 1 48.128-48.128c28.330667 27.989333 65.536 43.690667 105.472 43.690666 39.594667 0 77.141333-15.701333 105.130667-43.690666 13.653333-13.312 35.157333-13.312 48.469333 0 13.312 13.312 13.312 34.816 0 48.128z m2.730667-200.021334a68.266667 68.266667 0 0 1-67.925333-68.608c0-37.546667 30.378667-68.266667 67.925333-68.266666 37.888 0 68.608 30.72 68.608 68.266666 0 37.888-30.72 68.608-68.608 68.608z" p-id="5063"></path></svg>
                        </button>

                        <!-- Emoji 下拉菜单 -->
                        <div id="emoji-dropdown" class="z-10 hidden bg-white rounded-lg shadow-lg dark:bg-gray-700">
                            <div class="p-3 grid grid-cols-8 gap-2">
                                <button 
                                    v-for="emoji in emojis" 
                                    :key="emoji"
                                    @click="insertEmoji(emoji)"
                                    class="text-2xl hover:bg-gray-100 dark:hover:bg-gray-600 rounded p-1 transition-colors"
                                >
                                    {{ emoji }}
                                </button>
                            </div>
                        </div>

                        <!-- 发送按钮 -->
                        <button 
                            @click="sendMessage"
                            :disabled="!ws || ws.readyState !== WS_OPEN || !messageInput.trim()"
                            class="px-6 py-2 text-sm bg-sky-600 text-white rounded-lg hover:bg-sky-700 transition-colors focus:outline-none focus:ring-2 focus:ring-sky-500 focus:ring-offset-1 dark:focus:ring-offset-gray-800 flex items-center gap-2 disabled:opacity-50 disabled:cursor-not-allowed"
                        >

                            发送
                            <!-- 发送图标 -->
                            <svg t="1768380323381" class="w-3.5 h-3.5" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="8161" width="200" height="200"><path d="M388.266667 981.333333c-4.266667 0-12.8 0-17.066667-4.266666-12.8-8.533333-25.6-21.333333-25.6-38.4v-243.2c0-12.8 4.266667-21.333333 12.8-29.866667l311.466667-302.933333c17.066667-17.066667 42.666667-17.066667 59.733333 0 17.066667 17.066667 17.066667 42.666667 0 59.733333l-298.666667 290.133333v136.533334l102.4-81.066667c12.8-12.8 34.133333-12.8 51.2-4.266667l170.666667 106.666667 132.266667-708.266667L174.933333 512l106.666667 64c21.333333 12.8 25.6 38.4 12.8 59.733333-12.8 21.333333-38.4 25.6-59.733333 12.8l-170.666667-106.666666c-12.8-4.266667-21.333333-21.333333-21.333333-34.133334 0-17.066667 8.533333-29.866667 25.6-34.133333l853.333333-422.4c12.8-8.533333 29.866667-4.266667 42.666667 4.266667 12.8 8.533333 17.066667 25.6 17.066666 42.666666l-157.866666 853.333334c-4.266667 12.8-12.8 25.6-25.6 29.866666-12.8 4.266667-29.866667 4.266667-38.4-4.266666L558.933333 853.333333l-145.066666 119.466667c-8.533333 4.266667-17.066667 8.533333-25.6 8.533333z" p-id="8162" fill="currentColor"></path></svg>
                        </button>
                    </div>
                </div>
            </div>

            <!-- 右侧：成员列表 -->
            <div class="w-64 border-l border-gray-200 dark:border-gray-700 flex flex-col">
                <!-- 成员列表标题 -->
                <div class="p-4 border-b dark:border-gray-700 flex gap-3">
                    <!-- 图标 -->
                    <div class="w-12 rounded-full bg-sky-100 dark:bg-sky-900 flex items-center justify-center">
                        <svg t="1768555042444" class="w-5 h-5 text-sky-600 dark:text-sky-400" fill="currentColor" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5627" width="200" height="200"><path d="M500.6 513.2c44.7-35 73.4-89.5 73.4-150.7 0-105.7-85.6-191.3-191.3-191.3s-191.3 85.7-191.3 191.3c0 61.7 29.3 116.6 74.7 151.6C147.8 563.6 64.3 684 64.3 818.7v0.1h0.1c0 17.7 14.5 32 32.2 32 17.8 0 32.2-14.4 32.2-32.1 0-1.1-0.1-2.2-0.2-3.3 2.3-139 115.9-260 255.8-260 140.3 0 254.3 121.7 255.9 261.3 0 0.6-0.1 1.3-0.1 1.9 0 17.7 14.4 32.1 32.2 32.1 17.8 0.1 32.2-14.2 32.3-32 0-135.5-84.7-256.7-204.1-305.5zM382.7 233.3c70.8 0 128.3 57.4 128.3 128.2 0 70.9-57.4 128.4-128.3 128.4s-128.3-57.5-128.3-128.3c0-70.9 57.5-128.3 128.3-128.3z" p-id="5628"></path><path d="M896.3 799.6a32.2 32.1 0 1 0 64.4 0 32.2 32.1 0 1 0-64.4 0Z" p-id="5629"></path>
                            <path d="M931 675.6c0-6.5-2-12.8-5.6-17.9-35.6-65-95.7-114.5-168.8-144.5 44.7-35 73.4-89.5 73.4-150.7 0-82.5-52.3-152.9-125.5-179.7-0.4-0.1-0.8-0.3-1.2-0.4-2.8-0.8-5.8-1.3-8.9-1.3-17.2 0-31.2 13.9-31.2 31 0 13.9 9.2 25.7 21.8 29.6 48 18.6 82 65.2 82 119.7 0 52.8-31.8 98.1-77.3 117.8-15.6 6.7-26.5 22.2-26.5 40.2 0 20.8 14.6 38.3 34.1 42.7 76.5 18.5 140 64.4 173.5 129.3 5.5 9.7 16 16.3 28 16.3 17.8 0 32.2-14.4 32.2-32.1z" p-id="5630"></path></svg>
                    </div>
                    <!-- 标题和在线人数 -->
                    <div>
                        <h3 class="font-bold text-gray-800 dark:text-gray-200 text-lg">群聊成员</h3>
                        <p class="text-xs text-gray-500 dark:text-gray-400">共 {{ onlineCount }} 人在线</p>
                    </div>
                </div>
                <!-- 成员列表 -->
                <div class="flex-1 overflow-y-auto p-4 space-y-1">
                    <!-- 动态渲染成员列表 -->
                    <template v-if="onlineUsers && onlineUsers.length > 0">
                        <div v-for="user in onlineUsers" :key="user.nickname" class="flex items-center gap-3 p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-700 transition-colors cursor-pointer">
                            <!-- 头像 -->
                            <div class="relative">
                                <img v-if="user.avatar" :src="user.avatar" :alt="user.nickname" class="w-10 h-10 rounded-full object-cover border-2 border-green-400" />
                                <svg v-else class="w-10 h-10 text-gray-400 dark:text-gray-400 border-2 border-green-400 rounded-full" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                    <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"></path>
                                </svg>
                                <!-- 右下角在线状态小圆点 -->
                                <span v-if="user.online" class="absolute bottom-0 right-0 w-3 h-3 bg-green-400 border-2 border-white dark:border-gray-800 rounded-full"></span>
                            </div>
                            <!-- 昵称和状态 -->
                            <div class="flex-1">
                                <p class="text-sm font-medium text-gray-900 dark:text-gray-200">{{ user.nickname }}</p>
                                <p :class="user.online ? 'text-xs text-green-500' : 'text-xs text-gray-400'">{{ user.online ? '在线' : '离线' }}</p>
                            </div>
                        </div>
                    </template>
                    
                    <!-- 无在线成员提示 -->
                    <div v-else class="flex flex-col items-center justify-center h-full text-center py-8">
                        <svg class="w-12 h-12 text-gray-300 dark:text-gray-600 mb-4" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5155">
                            <path d="M0.512 0h1024v1024H0.512z" fill="currentColor" fill-opacity=".01" p-id="5156"></path>
                            <path d="M380.974545 83.316364a246.318545 246.318545 0 0 1 156.439273 436.642909 385.303273 385.303273 0 0 1 224.069818 349.556363 45.149091 45.149091 0 1 1-90.251636 0c0-160.256-129.954909-293.515636-290.257455-293.515636-160.256 0-290.210909 133.259636-290.210909 293.562182a45.149091 45.149091 0 1 1-90.251636 0 385.536 385.536 0 0 1 224.069818-349.696 246.318545 246.318545 0 0 1 156.392727-436.549818z m263.07491-19.316364a246.318545 246.318545 0 0 1 158.440727 434.920727c131.025455 56.785455 222.021818 183.063273 222.021818 336.989091a45.149091 45.149091 0 0 1-90.251636 0c0-132.701091-100.538182-248.040727-233.890909-273.873454a381.393455 381.393455 0 0 0-79.36-97.373091 156.113455 156.113455 0 1 0 2.373818-309.061818 45.428364 45.428364 0 0 1-22.574546-87.645091c0.139636-0.186182 0.325818-0.232727 0.512-0.279273 13.870545-2.420364 28.16-3.677091 42.728728-3.677091zM380.974545 173.568a156.113455 156.113455 0 1 0 0 312.226909 156.113455 156.113455 0 0 0 0-312.226909z" fill="currentColor" p-id="5157"></path>
                        </svg>
                        <p class="text-sm text-gray-500 dark:text-gray-400">暂无在线成员</p>
                        <p class="text-xs text-gray-400 dark:text-gray-500 mt-1">快来加入聊天室吧~</p>
                    </div>
                </div>
            </div>
        </div>
    </main>


<!-- 昵称输入模态框 -->
<div id="nickname-modal" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-2xl max-h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow-sm dark:bg-gray-700">
            <!-- Modal header -->
            <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600 border-gray-200">
                <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                    加入聊天室
                </h3>
                <button @click="closeNicknameModal" type="button" class="text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                    </svg>
                    <span class="sr-only">Close modal</span>
                </button>
            </div>
            <!-- Modal body -->
            <div class="p-4 md:p-5 space-y-4">
                <!-- Tab 选项卡 -->
                <div class="text-sm font-medium text-center text-gray-500 border-b border-gray-200 dark:text-gray-400 dark:border-gray-700">
                    <ul class="flex flex-wrap justify-center -mb-px">
                        <li class="me-2">
                            <button @click="activeTab = 'qq'" :class="activeTab === 'qq' ? 'text-sky-600 border-sky-600 dark:text-sky-500 dark:border-sky-500' : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'" class="inline-block p-4 border-b-2 rounded-t-lg">QQ</button>
                        </li>
                        <li class="me-2">
                            <button @click="activeTab = 'custom'" :class="activeTab === 'custom' ? 'text-sky-600 border-sky-600 dark:text-sky-500 dark:border-sky-500' : 'border-transparent hover:text-gray-600 hover:border-gray-300 dark:hover:text-gray-300'" class="inline-block p-4 border-b-2 rounded-t-lg">自定义</button>
                        </li>
                    </ul>
                </div>

                <!-- Tab 内容 -->
                <div class="mt-4">
                    <!-- QQ Tab 内容 -->
                    <div v-show="activeTab === 'qq'" class="p-4">
                        <div class="flex gap-2">
                            <!-- QQ 号输入框 -->
                            <input v-model="qqInput" class="bg-gray-50 border outline-none border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500" placeholder="请输入你的 QQ 号">
                            <!-- 获取按钮 -->
                            <button type="button" @click="handleGetQQUserInfo" :disabled="isLoading" class="text-white bg-sky-700 hover:bg-sky-800 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-lg text-sm px-5 py-2.5 whitespace-nowrap dark:bg-sky-600 dark:hover:bg-sky-700 dark:focus:ring-sky-800 disabled:opacity-50 disabled:cursor-not-allowed">
                                <span v-if="!isLoading">获取</span>
                                <!-- 如果正在获取中， 按钮展示 Loading 动画 -->
                                <span v-else class="flex items-center gap-2">
                                    <svg class="animate-spin h-4 w-4 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                                        <circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4"></circle>
                                        <path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                                    </svg>
                                    获取中...
                                </span>
                            </button>
                        </div>
                        <p class="mt-2 text-sm text-gray-500 dark:text-gray-400">输入 QQ 号，可获取昵称、头像</p>
                        
                        <!-- QQ 信息展示区域 -->
                        <div v-if="userInfo.avatar" class="mt-4 p-4 bg-gray-50 dark:bg-gray-800 rounded-lg border border-gray-200 dark:border-gray-600">
                            <div class="flex items-center gap-4">
                                <!-- 头像 -->
                                <img :src="userInfo.avatar" alt="QQ头像" class="w-16 h-16 rounded-full object-cover border-2 border-gray-300 dark:border-gray-600" />
                                <!-- 昵称信息 -->
                                <div class="flex-1">
                                    <p class="text-sm text-gray-500 dark:text-gray-400 mb-1">昵称</p>
                                    <p class="text-lg font-medium text-gray-900 dark:text-white">{{ userInfo.nickname || '无名'}}</p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- 自定义 Tab 内容 -->
                    <div v-show="activeTab === 'custom'" class="p-4">
                        <!-- <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">昵称</label> -->
                        <div class="flex items-center gap-3">
                            <!-- 头像图标 -->
                            <svg class="w-10 h-10 text-gray-400 dark:text-gray-400 flex-shrink-0" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                                <path d="M10 0a10 10 0 1 0 10 10A10.011 10.011 0 0 0 10 0Zm0 5a3 3 0 1 1 0 6 3 3 0 0 1 0-6Zm0 13a8.949 8.949 0 0 1-4.951-1.488A3.987 3.987 0 0 1 9 13h2a3.987 3.987 0 0 1 3.951 3.512A8.949 8.949 0 0 1 10 18Z"></path>
                            </svg>
                            <!-- 输入框 -->
                            <input v-model="userInfo.nickname" class="bg-gray-50 border outline-none border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-sky-500 focus:border-sky-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500" placeholder="请输入你的聊天昵称">
                        </div>
                    </div>
                </div>
            </div>
            <!-- Modal footer -->
            <div class="flex items-center gap-3 p-4 md:p-5 border-t border-gray-200 rounded-b dark:border-gray-600">
                <button type="button" @click="closeNicknameModal" class="flex-1 py-2.5 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-sky-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-gray-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-gray-700">算了吧</button>
                <!-- 加入聊天室按钮 -->
                <button type="button" @click="joinChatRoom" class="flex-1 text-white bg-sky-700 hover:bg-sky-800 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-sky-600 dark:hover:bg-sky-700 dark:focus:ring-sky-800">加入聊天室</button>
            </div>
        </div>
    </div>
</div>

    <Footer></Footer>
</template>

<script setup>
import Header from '@/layouts/frontend/components/Header.vue'
import Footer from '@/layouts/frontend/components/Footer.vue'
import { ref, onMounted, onUnmounted, nextTick } from 'vue'
import { Dropdown, Modal } from 'flowbite'
import { getUserInfoByQQ } from '@/api/frontend/comment'
import { showMessage } from '@/composables/util'
import { useChatUserStore } from '@/stores/chatUser'
import { getChatHistory, getOnlineUsers } from '@/api/frontend/chatroom'

// 聊天用户 Pinia
const chatUserStore = useChatUserStore()

// Dropdown 实例
let emojiDropdown = null

// 输入昵称模态框
let nicknameModal = null

// Tab 选项卡状态, 默认为 QQ 号加入聊天室
const activeTab = ref('qq')

// QQ 号输入
const qqInput = ref('')

// 聊天消息记录
const chatMessages = ref([])

// 在线用户列表
const onlineUsers = ref([])

// 在线人数
const onlineCount = ref(0)

// 当前会话的 sessionId
const mySessionId = ref('')

// 聊天记录容器引用
const chatMessagesContainer = ref(null)

// 聊天用户信息
const userInfo = ref({
    qq: '',
    avatar: '',
    nickname: ''
})

// 是否正在加载
const isLoading = ref(false)

// 是否正在加载更多历史消息
const isLoadingMore = ref(false)

// 是否已经加载到最后一页（没有更多消息）
const isNoMoreMessages = ref(false)

// WebSocket 连接状态常量
const WS_OPEN = 1

// WebSocket 实例
let ws = null

// 常用 emoji 表情列表
const emojis = ref([
    '😀', '😃', '😄', '😁', '😆', '😅', '🤣', '😂',
    '🙂', '🙃', '😉', '😊', '😇', '🥰', '😍', '🤩',
    '😘', '😗', '😚', '😙', '🥲', '😋', '😛', '😜',
    '🤪', '😝', '🤑', '🤗', '🤭', '🤫', '🤔', '🤐',
    '🤨', '😐', '😑', '😶', '😏', '😒', '🙄', '😬',
    '🤥', '😌', '😔', '😪', '🤤', '😴', '😷', '🤒',
    '🤕', '🤢', '🤮', '🤧', '🥵', '🥶', '😎', '🤓',
    '🧐', '😕', '😟', '🙁', '☹️', '😮', '😯', '😲',
    '😳', '🥺', '😦', '😧', '😨', '😰', '😥', '😢',
    '😭', '😱', '😖', '😣', '😞', '😓', '😩', '😫',
    '🥱', '😤', '😡', '😠', '🤬', '👍', '👎', '👏',
    '🙌', '👋', '🤝', '🙏', '💪', '❤️', '💔', '💯'
])

// 初始化 Flowbite 组件
onMounted(() => {
    // 加载历史消息（第一页）
    loadChatHistory()
    
    // 加载在线用户列表
    loadOnlineUsers()

    // 下拉菜单容器
    const targetEl = document.getElementById('emoji-dropdown')
    // 触发按钮
    const triggerEl = document.getElementById('emoji-button')
    
    // 下拉菜单配置项
    const options = {
        placement: 'top', // 显示位置
        triggerType: 'click', // 触发方式
        offsetSkidding: 0, // 水平偏移
        offsetDistance: 10, // 垂直偏移
    }
    
    // 创建 Dropdown 实例
    emojiDropdown = new Dropdown(targetEl, triggerEl, options)

    // 初始化昵称模态框
    // 获取模态框 DOM 元素
    const modalEl = document.getElementById('nickname-modal')
    // 创建模态框实例，closable: false 表示只能通过代码关闭，不能点击背景关闭
    nicknameModal = new Modal(modalEl, {
        backdrop: 'dynamic',
        backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
        closable: false,
    })

    // 检查 Pinia 中是否已保存用户信息
    if (chatUserStore.chatUserInfo && chatUserStore.chatUserInfo.nickname) {
        // 从 Pinia 恢复用户信息
        userInfo.value = {
            qq: chatUserStore.chatUserInfo.qq || '',
            avatar: chatUserStore.chatUserInfo.avatar || '',
            nickname: chatUserStore.chatUserInfo.nickname
        }

        // 自动建立 WebSocket 连接
        connectWebSocket()
    }
})

// 组件卸载时断开 WebSocket 连接
onUnmounted(() => {
    disconnectWebSocket()
})

// 消息输入框内容
const messageInput = ref('')

// 插入 emoji 到输入框
const insertEmoji = (emoji) => {
    messageInput.value += emoji
    // 模拟点击触发按钮来关闭 dropdown
    const triggerEl = document.getElementById('emoji-button')
    if (triggerEl) {
        triggerEl.click()
    }
}

// 输入框获取焦点事件
const onMessageInputFocus = () => {
    // 从 Pinia 中判断用户是否已输入昵称
    if (!chatUserStore.chatUserInfo.nickname) {
        // 若未输入, 则弹出模态框，让用户先输入昵称
        if (nicknameModal) {
            nicknameModal.show()
        }
    }
}

// 输入框键盘事件
const onMessageInputKeydown = (event) => {
    // 检测是否按下了回车键
    if (event.key === 'Enter') {
        // 如果同时按下了 Shift，允许换行
        if (event.shiftKey) {
            return
        }
        // 阻止默认行为，不然会换行
        event.preventDefault()
        // 发送消息
        sendMessage()
    }
}

// 关闭昵称模态框
const closeNicknameModal = () => {
    if (nicknameModal) {
        nicknameModal.hide()
    }
}

// 获取 QQ 用户信息
const handleGetQQUserInfo = () => {
    // 判断是否输入 QQ 号
    if (!qqInput.value.trim()) {
        showMessage('请输入 QQ 号', 'warning')
        return
    }

    // 设置正在加载
    isLoading.value = true

    getUserInfoByQQ(qqInput.value.trim()).then(res => {
            // 若响应失败
            if (!res.success) {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示错误消息
                showMessage(message, 'error')
            }

            // 保存用户信息
            userInfo.value = {
                qq: qqInput.value,
                avatar: res.data.avatar,
                nickname: res.data.nickname
            }

            // 提示成功
            showMessage('获取 QQ 信息成功')
        }).catch(error => {
            console.error('获取 QQ 用户信息失败:', error)
        })
        .finally(() => {
            // 设置加载完成
            isLoading.value = false
        })  
}

// 加入聊天室
const joinChatRoom = () => {
    // 判断是否输入昵称
    if (!userInfo.value.nickname.trim()) {
        showMessage('请输入聊天昵称', 'warning')
        return
    }

    // 关闭模态框
    closeNicknameModal()

    // 建立 WebSocket 连接
    connectWebSocket()
}

// 建立 WebSocket 连接
const connectWebSocket = () => {
    // 如果已存在连接，先关闭
    if (ws) {
        ws.close()
    }

    // 获取 WebSocket 地址
    const wsProtocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:'
    const wsHost = import.meta.env.VITE_APP_WS_HOST
    
    // 构建 WebSocket URL，携带 nickname、avatar、qq 号参数
    const wsUrl = `${wsProtocol}//${wsHost}/ws/chat?nickname=${encodeURIComponent(userInfo.value.nickname)}&avatar=${encodeURIComponent(userInfo.value.avatar)}&qq=${encodeURIComponent(userInfo.value.qq)}`
    // 创建 WebSocket 连接
    ws = new WebSocket(wsUrl)

    // 连接建立时触发
    ws.onopen = () => {
        console.log('WebSocket 连接已建立')
        // 将用户信息存入 Pinia
        chatUserStore.updateChatUserInfo(userInfo.value)
        // 提示加入聊天室成功
        showMessage('加入聊天室成功')
    }

    // 接收消息时触发
    ws.onmessage = (event) => {
        try {
            const message = JSON.parse(event.data)
            
            // 根据消息类型处理
            if (message.type === 2) { // 在线用户消息
                // 更新在线用户列表
                onlineUsers.value = message.users
                onlineCount.value = message.onlineCount
            } else if (message.type === 3) { // 初始化消息（包含 sessionId）
                // 保存自己的 sessionId
                mySessionId.value = String(message.sessionId)

                // 加入聊天室成功后，重新加载历史消息，带上入参 sessionId
                loadChatHistory(mySessionId.value)
            } else { // 其他类型消息（系统消息、用户消息）
                // 将消息添加到聊天记录
                chatMessages.value.push(message)

                // 滚动到最底部
                scrollToBottom()
            }
        } catch (error) {
            console.error('解析消息失败:', error)
        }
    }

    // 连接关闭时触发
    ws.onclose = (event) => {
        console.log('WebSocket 连接已关闭:', event.code, event.reason)
        ws = null
    }

    // 连接错误时触发
    ws.onerror = (error) => {
        console.error('WebSocket 连接错误:', error)
    }
}

// 关闭 WebSocket 连接
const disconnectWebSocket = () => {
    if (ws) {
        ws.close()
        ws = null
    }
}

// 退出聊天室
const leaveChatRoom = () => {
    if (confirm('确定要退出聊天室吗？')) {
        // 清空聊天记录
        chatMessages.value = []
        
        // 清空在线用户列表
        onlineUsers.value = []
        // 清空在线人数
        onlineCount.value = 0
        
        // 清空自己的 sessionId
        mySessionId.value = ''
        
        // 清空 Pinia 中的用户信息
        chatUserStore.clearChatUserInfo()
        
        // 断开 WebSocket 连接
        disconnectWebSocket()

        // 重新加载历史消息（第一页）
        loadChatHistory()
    
        // 重新加载在线用户列表
        loadOnlineUsers()
    }
}

// 发送消息
const sendMessage = () => {
    // 检查 WebSocket 连接状态
    if (!ws || ws.readyState !== WS_OPEN) {
        showMessage('WebSocket 未连接，请先加入聊天室', 'error')
        return
    }

    // 检查消息内容是否为空
    if (!messageInput.value.trim()) {
        showMessage('请输入消息内容', 'warning')
        return
    }

    // 发送消息
    ws.send(messageInput.value.trim())

    // 清空输入框
    messageInput.value = ''
}


// 滚动聊天记录到最底部
const scrollToBottom = () => {
    nextTick(() => {
        if (chatMessagesContainer.value) {
            chatMessagesContainer.value.scrollTop = chatMessagesContainer.value.scrollHeight
        }
    })
}

// 聊天记录区域滚动事件处理
const handleScroll = (event) => {
    const scrollTop = event.target.scrollTop
    // 当滚动到顶部时，加载更多历史消息
    // 判断条件: scrollTop === 0 表示滚动到了最顶部
    if (scrollTop === 0 && !isLoadingMore.value && !isNoMoreMessages.value) {
        // 1. 获取当前最早的记录 ID
        if (chatMessages.value.length === 0) {
            return
        }
        
        // 获取第一条消息的 ID 作为 lastId
        const lastId = chatMessages.value[0].id
        
        // 2. 调用接口加载上一页数据
        loadMoreChatHistory(lastId)
    }
}

// 加载更多历史聊天记录
const loadMoreChatHistory = (lastId) => {     
    // 防止重复加载
    if (isLoadingMore.value) {
        return
    }
    
    // 设置加载状态为 “正在加载中..”
    isLoadingMore.value = true

    // 保存当前滚动位置
    const container = chatMessagesContainer.value
    const oldScrollHeight = container.scrollHeight

    // 调用接口加载更多数据
    getChatHistory(lastId, mySessionId.value).then(res => {
        if (res.success) {
            // 检查 data 和 messages 是否存在
            if (!res.data || !res.data.messages) {
                isNoMoreMessages.value = true
                return
            }
            
            const newMessages = res.data.messages
            
            // 如果有新消息，插入到数组开头
            if (newMessages && newMessages.length > 0) {
                // 将新消息添加到数组开头
                chatMessages.value = [...newMessages, ...chatMessages.value]
                
                // 重置没有更多消息的状态
                isNoMoreMessages.value = false

                // 4. 保持滚动位置
                // 使用 nextTick 确保 DOM 更新后再设置滚动位置
                nextTick(() => {
                    const newScrollHeight = container.scrollHeight
                    // 恢复滚动位置，让用户感觉是自然加载
                    container.scrollTop = newScrollHeight - oldScrollHeight
                })
            } else {
                // 没有更多数据了
                isNoMoreMessages.value = true
            }
        }
    }).catch(error => {
        console.error('加载更多历史消息失败:', error)
        showMessage('加载更多消息失败', 'error')
    }).finally(() => {
        // 设置加载完成
        isLoadingMore.value = false
    })
}

// 加载历史聊天记录
const loadChatHistory = (sessionId = null) => {
    // 加载第一页数据
    getChatHistory(null, sessionId).then(res => {
        if (res.success) {
            chatMessages.value = res.data.messages
            // 滚动到最底部
            scrollToBottom()
        }
    }).catch(error => {
        console.error('加载历史消息失败:', error)
    })
}

// 加载在线用户列表
const loadOnlineUsers = () => {
    getOnlineUsers().then(res => {
        if (res.success && res.data && res.data.length > 0) {
            // 设置在线用户与在线用户数
            onlineUsers.value = res.data
            onlineCount.value = res.data.length
        }
    }).catch(error => {
        console.error('加载在线用户失败:', error)
    })
}

// 判断是否为自己的消息
// 历史消息使用 isSelf 字段，WebSocket 实时消息使用 sessionId 判断
const isMyMessage = (message) => {
    // 如果有 isSelf 字段（历史消息），直接通过此字段来判断
    if (message.isSelf !== undefined) {
        return message.isSelf
    }
    // 否则使用 sessionId 判断（WebSocket 实时消息）
    return message.sessionId === mySessionId.value
}
</script>
