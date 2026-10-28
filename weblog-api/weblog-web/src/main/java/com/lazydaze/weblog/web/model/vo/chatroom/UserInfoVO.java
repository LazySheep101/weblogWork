package com.lazydaze.weblog.web.model.vo.chatroom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: LazyDaze101
 * @Date: 2026/1/6 20:48
 * @Version: v1.0.0
 * @Description: 用户信息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoVO {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * QQ 号（可为空，仅用于 QQ 登录方式）
     */
    private String qq;
}
