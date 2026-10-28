import axios from "@/axios";

// 获取 QQ 用户信息
export function getUserInfoByQQ(qq) {
    return axios.post("/comment/qq/userInfo", {qq})
}
// 发布评论
export function publishComment(data) {
    return axios.post("/comment/publish", data)
}

// 获取所有评论
export function findComments(routerUrl) {
    return axios.post("/comment/list", {routerUrl})
}

// // 获取知识库文章上一页
// export function getWikiArticlePreNext(data) {
//     return axios.post("/wiki/article/preNext", data)
// }


