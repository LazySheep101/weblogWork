import axios from "@/axios";

// 获取文章分页数据
export function getNoticePageList(data) {
    return axios.post("/admin/notice/list", data)
}

// 更新文章发布状态
export function updateNoticeIsShow(data) {
    return axios.post("/admin/notice/isShow/update", data)
}

// 发布文章
export function addNotice(data) {
    return axios.post("/admin/notice/add", data)
}

// 获取文章详情
export function deleteNotice(id) {
    return axios.post("/admin/notice/delete", {id})
}

// 更新文章
export function updateNotice(data) {
    return axios.post("/admin/notice/update", data)
}

// // 更新文章置顶状态
// export function updateArticleIsTop(data) {
//     return axios.post("/admin/article/isTop/update", data)
// }

// // 更新文章发布状态
// export function updateArticleIsPublish(data) {
//     return axios.post("/admin/article/isPublish/update", data)
// }
