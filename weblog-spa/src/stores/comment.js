import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getUserInfoByQQ } from '@/api/frontend/comment'
import { showMessage } from '@/composables/util'

export const useCommentStore = defineStore('comment', () => {
  // 用户信息
  const userInfo = ref({})

  // // 根据 qq 号获取用户信息
  // function storeUserInfo(avatar, nickname, mail, website) {
  //   userInfo.value = {
  //     avatar, nickname, mail, website
  //   }
  // }


  return { userInfo }
},
  {
    // 开启持久化
    persist: true,
  }
)