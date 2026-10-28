<template>
    <div>
        <!-- 表头分页查询条件， shadow="never" 指定 card 卡片组件没有阴影 -->
        <el-card shadow="never" class="mb-5">
            <!-- flex 布局，内容垂直居中 -->
            <div class="flex items-center">
                <el-text>公告内容</el-text>
                <div class="ml-3 w-52 mr-5"><el-input v-model="searchContent" placeholder="请输入（模糊查询）" clearable /></div>

                <el-text>创建日期</el-text>
                <div class="ml-3 w-30 mr-5">
                    <!-- 日期选择组件（区间选择） -->
                    <el-date-picker v-model="pickDate" type="daterange" range-separator="至" start-placeholder="开始时间"
                        end-placeholder="结束时间" size="default" :shortcuts="shortcuts" @change="datepickerChange" />
                </div>

                <el-text>是否展示</el-text>
                <div class="ml-3 w-30 mr-5">
                    <el-select v-model="isShow" placeholder="---请选择---">
                        <el-option v-for="item in isShowOptions" :key="item.value" :label="item.label"
                            :value="item.value" />
                    </el-select>
                </div>

                <el-button type="primary" class="ml-3" :icon="Search" @click="getTableData">查询</el-button>
                <el-button class="ml-3" :icon="RefreshRight" @click="reset">重置</el-button>
            </div>
        </el-card>

        <el-card shadow="never">
                        <!-- 新增按钮 -->
                        <div class="mb-5">
                <el-button type="primary" @click="addNoticeBtnClick">
                    <el-icon class="mr-1">
                        <Plus />
                    </el-icon>
                    新增</el-button>
            </div>

            <!-- 分页列表 -->
            <el-table :data="tableData" border stripe v-loading="tableLoading" table-layout="auto">
                <el-table-column type="index" label="序号" width="60" />
                <el-table-column prop="content" label="公告内容" >
                    <template #default="scope">
                        <p v-html="scope.row.content"></p>
                    </template>
                </el-table-column>
                <!-- <el-table-column prop="mail" label="邮件"  />
                <el-table-column prop="website" label="网站"  /> -->
                
                <el-table-column prop="isShow" label="是否展示">
                    <template #default="scope">
                        <el-switch
                            @change="handleIsShowChange(scope.row)"
                            v-model="scope.row.isShow"
                            inline-prompt
                            :active-icon="Check"
                            :inactive-icon="Close"
                        />
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="发布时间" width="200" />
                <el-table-column fixed="right" label="操作" width="150">
                    <template #default="scope">


                        <el-tooltip class="box-item" effect="dark" content="编辑" placement="bottom">
                            <el-button size="small" @click="showEditNoticeDialog(scope.row)" :icon="Edit" circle >
                            </el-button>
                        </el-tooltip>

                        <el-tooltip class="box-item" effect="dark" content="删除" placement="bottom">
                            <el-button type="danger" size="small" @click="deleteNoticeSubmit(scope.row)" :icon="Delete"
                                circle>
                            </el-button>
                        </el-tooltip>

                    </template>
                </el-table-column>
            </el-table>

            <!-- 分页 -->
            <div class="mt-10 flex justify-center">
                <el-pagination v-model:current-page="current" v-model:page-size="size" :page-sizes="[10, 20, 50]"
                    :small="false" :background="true" layout="total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="handleSizeChange" @current-change="getTableData" />
            </div>

        </el-card>

     <!-- 添加公告 -->
    <FormDialog ref="formDialogRef" title="添加公告" destroyOnClose @submit="onSubmit">
        <el-form ref="formRef" :rules="rules" :model="form">
                    <el-form-item label="公告内容" prop="content" label-width="80px" size="large">
                        <el-input type="textarea" v-model="form.content" placeholder="请输入公告内容" :rows="6" maxlength="460" show-word-limit clearable/>
                    </el-form-item>
                </el-form>
    </FormDialog>

         <!-- 编辑公告 -->
         <FormDialog ref="editNoticeFormDialogRef" title="编辑公告" destroyOnClose @submit="onEditSubmit">
        <el-form ref="formRef" :rules="rules" :model="editForm">
                    <el-form-item label="公告内容" prop="content" label-width="80px" size="large">
                        <el-input type="textarea" v-model="editForm.content" placeholder="请输入公告内容" :rows="6" maxlength="460" show-word-limit clearable/>
                    </el-form-item>
                </el-form>
    </FormDialog>


    </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { Search, RefreshRight, Check, Close, Delete, Edit, Tickets, View } from '@element-plus/icons-vue'
import { getNoticePageList, updateNoticeIsShow, deleteNotice, addNotice, updateNotice } from '@/api/admin/notice'
import moment from 'moment'
import { showMessage, showModel } from '@/composables/util'
import { useRouter } from 'vue-router'
import FormDialog from '@/components/FormDialog.vue'
import { ElTable } from 'element-plus'

const router = useRouter()

const formDialogRef = ref(null)
const addNoticeBtnClick = () => {
    formDialogRef.value.open()
}

// 评论审核表单对象
const form = reactive({
    content: '',
    isShow: false
})

const editNoticeFormDialogRef = ref(null)
const editForm = reactive({
    id: null,
    content: '',
})

const showEditNoticeDialog = (row) => {
    editNoticeFormDialogRef.value.open()
    editForm.id = row.id
    editForm.content = row.content
}

// 规则校验
const rules = {
    content: [
        {
            required: true,
            message: '公告内容不能为空',
            trigger: 'blur',
        },
        { min: 1, max: 460, message: '公告内容字数要求大于 1 个字符，小于 460 个字符', trigger: 'blur' },
    ]
}
// 表单引用
const formRef = ref(null)
const onSubmit = () => {
    // 先验证 form 表单字段
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }
        
        // 显示提交按钮 loading
        formDialogRef.value.showBtnLoading()
        addNotice(form).then((res) => {
            if (!res.success) {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示错误消息
                showMessage(message, 'error')
                return
            }

            showMessage('审核完成')
            // 将表单置空
            form.content = ''
            // 隐藏对话框
            formDialogRef.value.close()
            // 重新请求分页接口，渲染数据
            getTableData()
        }).finally(() => formDialogRef.value.closeBtnLoading()) // 隐藏提交按钮 loading

    })
}

const onEditSubmit = () => {
    // 先验证 form 表单字段
    formRef.value.validate((valid) => {
        if (!valid) {
            console.log('表单验证不通过')
            return false
        }
        
        // 显示提交按钮 loading
        editNoticeFormDialogRef.value.showBtnLoading()
        updateNotice(editForm).then((res) => {
            if (!res.success) {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示错误消息
                showMessage(message, 'error')
                return
            }

            showMessage('修改成功')
            // 隐藏对话框
            editNoticeFormDialogRef.value.close()
            // 重新请求分页接口，渲染数据
            getTableData()
        }).finally(() => editNoticeFormDialogRef.value.closeBtnLoading()) // 隐藏提交按钮 loading

    })
}



const isShow = ref(null)
const isShowOptions = [
    {
        value: false,
        label: '未展示',
    },
    {
        value: true,
        label: '展示',
    },
]

// 模糊搜索的公告内容
const searchContent = ref('')
// 日期
const pickDate = ref('')

// 查询条件：开始结束时间
const startDate = reactive({})
const endDate = reactive({})

// 监听日期组件改变事件，并将开始结束时间设置到变量中
const datepickerChange = (e) => {
    // 如果监听到日期为空，将开始时间、结束时间置空
    if (e === null) {
        startDate.value = null
        endDate.value = null
        return
    }
    startDate.value = moment(e[0]).format('YYYY-MM-DD')
    endDate.value = moment(e[1]).format('YYYY-MM-DD')

    console.log('开始时间：' + startDate.value + ', 结束时间：' + endDate.value)
}

const shortcuts = [
    {
        text: '最近一周',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            return [start, end]
        },
    },
    {
        text: '最近一个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            return [start, end]
        },
    },
    {
        text: '最近三个月',
        value: () => {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            return [start, end]
        },
    },
]

// 重置
const reset = () => {
    pickDate.value = ''
    startDate.value = null
    endDate.value = null
    searchContent.value = ''
    isShow.value = null
}

// 表格加载 Loading
const tableLoading = ref(false)
// 表格数据
const tableData = ref([])
// 当前页码，给了一个默认值 1
const current = ref(1)
// 总数据量，给了个默认值 0
const total = ref(0)
// 每页显示的数据量，给了个默认值 10
const size = ref(10)


// 获取分页数据
function getTableData() {
    // 显示表格 loading
    tableLoading.value = true
    // 调用后台分页接口，并传入所需参数
    getNoticePageList({
        current: current.value, size: size.value, startDate: startDate.value,
        endDate: endDate.value, content: searchContent.value, isShow: isShow.value
    })
        .then((res) => {
            if (res.success == true) {
                tableData.value = res.data
                current.value = res.current
                size.value = res.size
                total.value = res.total
            }
        })
        .finally(() => tableLoading.value = false) // 隐藏表格 loading
}
getTableData()

// 每页展示数量变更事件
const handleSizeChange = (chooseSize) => {
    console.log('选择的页码' + chooseSize)
    size.value = chooseSize
    getTableData()
}

// 删除公告
const deleteNoticeSubmit = (row) => {
    console.log(row)
    showModel('是否确定要删除该公告？').then(() => {
        deleteNotice(row.id).then((res) => {
            if (res.success == false) {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示错误消息
                showMessage(message, 'error')
                return
            }

            showMessage('删除成功')
            // 重新请求分页接口，渲染数据
            getTableData()
        })
    }).catch((e) => {
        console.log('取消了')
    })
}

const handleIsShowChange = (row) => {
    updateNoticeIsShow({id: row.id, isShow: row.isShow}).then((res) => {
        // 重新请求分页接口，渲染列表数据
        getTableData()

        if (res.success == false) {
                // 获取服务端返回的错误消息
                let message = res.message
                // 提示错误消息
                showMessage(message, 'error')
                return
        }

        showMessage(row.isPublish ? '展示成功' : "已取消展示")
    })
}

</script>
