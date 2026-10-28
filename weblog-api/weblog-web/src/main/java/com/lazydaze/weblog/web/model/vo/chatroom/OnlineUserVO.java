package com.lazydaze.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LazyDaze101
 * @Date: 2026/1/6 20:48
 * @Version: v1.0.0
 * @Description: 在线用户
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OnlineUserVO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否在线
     */
    private Boolean online;
}
