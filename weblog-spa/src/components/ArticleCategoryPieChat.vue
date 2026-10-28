<template>
    <!-- 饼图容器 -->
    <div id="pieChat" class="overflow-x-auto w-full h-60 mt-10"></div>
</template>

<script setup>
import * as echarts from 'echarts'
import { watch } from 'vue'

// 对外暴露的属性值
const props = defineProps({
    value: { // 属性值名称
        type: Array, // 类型为对象
        default: [] // 默认为 null
    }
})

// 初始化
function initPieChat() {
    var chartDom = document.getElementById('pieChat');
    var myChart = echarts.init(chartDom);
    var option;

    // 从 props.value 中获取文章分类统计数据
    const articleCategories = props.value

    option = {
        title: {
            // text: 'Referer of a Website',
            // subtext: 'subtext',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        // legend: {
        //     orient: 'vertical',
        //     left: 'left'
        // },
        series: [
            {
                name: '文章数',
                type: 'pie',
                radius: '50%',
                data: articleCategories,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    option && myChart.setOption(option);
}

// 侦听属性, 监听 props.value 的变化，一旦 props.value 发生变化，就调用 initPieChat 初始化饼形图
watch(() => props.value, () => initPieChat())
</script>