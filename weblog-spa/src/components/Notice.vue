<template>
                    <div v-if="content" id="alert-5" class="  transition-all flex items-center p-4 mb-5 text-sm text-gray-800 border border-gray-300 rounded-lg bg-gray-50 
        dark:bg-gray-800 dark:text-gray-300 dark:border-gray-600 animate__animated animate__fadeInUp" role="alert">
  <svg class="flex-shrink-0 inline w-4 h-4 me-2" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M11 9H5a1 1 0 0 0-1 1v4c0 .6.4 1 1 1h6m0-6v6m0-6 5.4-3.9A1 1 0 0 1 18 6v12.2a1 1 0 0 1-1.6.8L11 15m7 0a3 3 0 0 0 0-6M6 15h3v5H6v-5Z"/>
  </svg>
  <span class="sr-only">Info</span>
  <div>
    <span class="font-medium mr-2">公告 :</span> 
    <span v-html="content"></span>
  </div>
  <button type="button" class="ms-auto -mx-1.5 -my-1.5 bg-gray-50 text-gray-500 rounded-lg focus:ring-2 focus:ring-gray-400 p-1.5 hover:bg-gray-200 inline-flex items-center justify-center h-8 w-8 dark:bg-gray-800 dark:text-gray-300 dark:hover:bg-gray-700 dark:hover:text-white" data-dismiss-target="#alert-5" aria-label="Close">
    <span class="sr-only">Dismiss</span>
    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
      <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
    </svg>
  </button>
        </div>
</template>

<script setup>
import { onMounted, ref, nextTick } from 'vue'
import { initDismisses } from 'flowbite'
import { getNoticeInfo } from '@/api/frontend/notice'

onMounted(() => {
    
})

const content = ref(null)

getNoticeInfo().then(res => {
    if (res.success) {
        content.value = res.data.content
        nextTick(() => initDismisses())
    }
})

</script>