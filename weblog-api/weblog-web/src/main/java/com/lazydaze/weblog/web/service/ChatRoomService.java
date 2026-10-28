package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.chatroom.FindChatMessagePageListReqVO;
import com.lazydaze.weblog.web.model.vo.chatroom.FindChatMessagePageListRspVO;
import com.lazydaze.weblog.web.model.vo.chatroom.OnlineUserVO;

import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 聊天室
 **/
public interface ChatRoomService {

    /**
     * 获取历史消息
     * @param findChatMessagePageListReqVO
     * @return
     */
    Response<FindChatMessagePageListRspVO> findHistoryMessages(FindChatMessagePageListReqVO findChatMessagePageListReqVO);

    /**
     * 获取所有在线用户
     * @return
     */
    Response<List<OnlineUserVO>> findOnlineUsers();
}
