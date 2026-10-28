import { defineStore } from 'pinia'
import { ref } from 'vue'

/** 聊天用户 Pinia */
export const useChatUserStore = defineStore('chatUser', () => {
  // 聊天用户信息
  const chatUserInfo = ref({
    qq: '',
    avatar: '',
    nickname: ''
  })

  // 更新聊天用户信息
  function updateChatUserInfo(data) {
    chatUserInfo.value = data
  }

  // 清空聊天用户信息
  function clearChatUserInfo() {
    chatUserInfo.value = {
      qq: '',
      avatar: '',
      nickname: ''
    }
  }

  return { chatUserInfo, updateChatUserInfo, clearChatUserInfo }
},
{
  // 开启持久化
  persist: true,
}
)
