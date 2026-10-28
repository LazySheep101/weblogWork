package com.lazydaze.weblog.web.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 获取 QQ 号用户信息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindQQUserInfoRspVO {

    private String avatar;

    private String nickname;

    private String mail;

}
