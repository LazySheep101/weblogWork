package com.lazydaze.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
public class FindChatMessagePageListRspVO {

    /**
     * 聊天消息
     */
    private List<ChatMessageVO> messages;

    /**
     * 是否还有下一页
     */
    private Boolean hasMore;

}
