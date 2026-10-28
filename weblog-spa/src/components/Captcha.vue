<template>
    <div class="captcha-container">
        <!-- 验证码渲染到这个容器中 -->
        <div id="captcha-box"></div>
    </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount, watch } from 'vue'
import { showMessage } from '@/composables/util'

// ==================== Props 定义 ====================
const props = defineProps({
    // 验证码类型:
    // SLIDER - 滑块验证
    // ROTATE - 旋转验证
    // CONCAT - 滑动还原
    // WORD_IMAGE_CLICK - 点选验证
    // RANDOM - 随机类型
    type: {
        type: String,
        default: 'SLIDER'
    },
    // 是否显示验证码
    visible: {
        type: Boolean,
        default: false
    }
})

// ==================== Emits 定义 ====================
const emit = defineEmits(['success', 'fail', 'close'])

// ==================== 验证码实例 ====================
let tacInstance = null

// ==================== 初始化验证码 ====================
const initCaptcha = () => {
    // 检查 SDK 是否已加载
    if (!window.TAC) {
        console.error('TAC SDK 未加载')
        return
    }

    // 销毁旧实例,避免内存泄漏
    if (tacInstance) {
        tacInstance.destroyWindow()
        tacInstance = null
    }

    // SDK 配置
    const config = {
        // 生成验证码接口
        requestCaptchaDataUrl: `/api/captcha/gen?type=${props.type}`,
        // 校验验证码接口
        validCaptchaUrl: '/api/captcha/check',
        // 绑定的 DOM 元素
        bindEl: '#captcha-box',
        // 验证成功回调
        validSuccess: (res, c, tac) => {
            // 将验证码 ID 传递给父组件
            emit('success', res.data.id)
        },
        // 验证失败回调
        validFail: (res, c, tac) => {
            emit('fail', res)
        },
        // 关闭回调
        onClose: () => {
            emit('close')
        }
    }

    // 样式配置(可在此配置自定义样式)
    const style = {
        // 例如: btnTextColor: '#ffffff'
    }

    // 创建验证码实例并初始化
    tacInstance = new window.TAC(config, style).init()
}

// ==================== 销毁验证码 ====================
const destroyCaptcha = () => {
    if (tacInstance) {
        tacInstance.destroyWindow()
        tacInstance = null
    }
}

// ==================== 刷新验证码 ====================
const refresh = () => {
    destroyCaptcha()
    initCaptcha()
}

// ==================== 监听器 ====================
// 监听 visible 变化,控制验证码的显示与销毁
watch(() => props.visible, (newVal) => {
    if (newVal) {
        initCaptcha()
    } else {
        destroyCaptcha()
    }
})

// 监听 type 变化,刷新验证码
watch(() => props.type, () => {
    if (props.visible) {
        refresh()
    }
})

// ==================== 生命周期 ====================
onMounted(() => {
    if (props.visible) {
        initCaptcha()
    }
})

onBeforeUnmount(() => {
    destroyCaptcha()
})

// ==================== 暴露方法给父组件 ====================
defineExpose({
    refresh,
    destroy: destroyCaptcha
})
</script>

<style scoped>
.captcha-container {
    display: flex;
    justify-content: center;
    margin: 10px 0;
}
</style>