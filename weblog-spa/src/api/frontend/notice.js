import axios from "@/axios";

// 获取公告信息
export function getNoticeInfo() {
    return axios.post("/notice/info")
}


