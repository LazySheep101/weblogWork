package com.lazydaze.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-15 10:33
 * @description: 评论状态
 **/
@Getter
@AllArgsConstructor
public enum CommentStatusEnum {

    // ----------- 通用异常状态码 -----------
    WAIT_EXAMINE(1, "等待审核"),
    NORMAL(2, "正常"),
    EXAMINE_FAILED(3, "审核不通过"),
    ;

    private Integer code;
    private String description;

}
