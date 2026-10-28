<template>
    <!-- 条形图容器 -->
    <div id="barChat" class="overflow-x-auto w-full h-60"></div>
</template>

<script setup>
import * as echarts from 'echarts'
import { watch } from 'vue'

// 对外暴露的属性值
const props = defineProps({
    value: { // 属性值名称
        type: Object, // 类型为对象
        default: null // 默认为 null
    }
})

// 初始化
function initBarChat() {
    var chartDom = document.getElementById('barChat');
    var myChart = echarts.init(chartDom);
    var option;

    // 从 props.value 中获取文章标签统计数据
    const tagNames = props.value.tagNames
    const articleCounts = props.value.articleCounts

    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: tagNames,
                axisTick: {
                    alignWithLabel: true
                },
                axisLabel: {
                    interval: 0,
                },
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '文章数',
                type: 'bar',
                barWidth: '60%',
                data: articleCounts
            }
        ]
    };

    option && myChart.setOption(option);
}

// 侦听属性, 监听 props.value 的变化，一旦 props.value 发生变化，就调用 initBarChat 初始化折条形图
watch(() => props.value, () => initBarChat())
</script>