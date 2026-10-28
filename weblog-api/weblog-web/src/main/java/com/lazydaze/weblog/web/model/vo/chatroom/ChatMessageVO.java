package com.lazydaze.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LazyDaze101
 * @Date: 2026/1/6 20:48
 * @Version: v1.0.0
 * @Description: 聊天室消息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageVO {

    /**
     * 消息 ID
     */
    private Long id;

    /**
     * 消息类型
     */
    private Integer type;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送时间
     */
    private String time;

    /**
     * 是否是当前用户发送的消息
     */
    private Boolean isSelf;
}
