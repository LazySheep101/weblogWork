import axios from "@/axios";

// 获取聊天历史消息（lastId 为 null 时，表示请求第一页数据）
export function getChatHistory(lastId = null, sessionId = null) {
    return axios.post("/chat/message/history", {lastId, sessionId})
}

// 获取所有在线用户
export function getOnlineUsers() {
    return axios.post("/chat/online/users")
}