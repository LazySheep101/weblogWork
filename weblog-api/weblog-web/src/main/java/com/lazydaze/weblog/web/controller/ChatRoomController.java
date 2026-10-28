package com.lazydaze.weblog.web.controller;

import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.chatroom.FindChatMessagePageListReqVO;
import com.lazydaze.weblog.web.model.vo.chatroom.FindChatMessagePageListRspVO;
import com.lazydaze.weblog.web.model.vo.chatroom.OnlineUserVO;
import com.lazydaze.weblog.web.service.ChatRoomService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:01
 * @description: 聊天室
 **/
@RestController
@RequestMapping("/chat")
@Tag(name = "聊天室")
public class ChatRoomController {

    @Autowired
    private ChatRoomService chatRoomService;

    @PostMapping("/message/history")
    @Operation(summary = "获取历史消息")
    @ApiOperationLog(description = "获取历史消息")
    public Response<FindChatMessagePageListRspVO> findHistoryMessages(@RequestBody @Validated FindChatMessagePageListReqVO findChatMessagePageListReqVO) {
        return chatRoomService.findHistoryMessages(findChatMessagePageListReqVO);
    }

    @PostMapping("/online/users")
    @Operation(summary = "获取所有在线用户")
    @ApiOperationLog(description = "获取所有在线用户")
    public Response<List<OnlineUserVO>> findOnlineUsers() {
        return chatRoomService.findOnlineUsers();
    }

}
