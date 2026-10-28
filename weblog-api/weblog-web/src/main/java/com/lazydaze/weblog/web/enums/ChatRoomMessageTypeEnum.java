package com.lazydaze.weblog.web.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-15 10:33
 * @description: 聊天室消息类型
 **/
@Getter
@AllArgsConstructor
public enum ChatRoomMessageTypeEnum {

    SYSTEM(0, "系统消息"),
    CHAT(1, "聊天消息"),
    ONLINE_USERS(2, "在线用户列表消息"),
    INIT(3, "会话初始化消息") // 用于返回 sessionId
    ;

    private Integer code;
    private String description;

}
