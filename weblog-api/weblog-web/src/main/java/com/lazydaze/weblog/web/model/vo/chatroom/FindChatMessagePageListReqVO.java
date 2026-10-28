package com.lazydaze.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 获取历史消息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindChatMessagePageListReqVO {

    /**
     * 游标 ID
     */
    private Long lastId;

    /**
     * 用于后端判断消息是否是自己发送的
     *
     * 如果是 QQ 号方式加入，则提交的是 qq 号
     * 如果是自定义昵称方式加入，则提交的是系统分配的 sessionId
     */
    private String sessionId;

}
