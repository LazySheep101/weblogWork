package com.lazydaze.weblog.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-15 10:33
 * @description: 响应异常码
 **/
@Getter
@AllArgsConstructor
public enum ArticlePublishCodeEnum {

    // ----------- 通用异常状态码 -----------
    YES(true, "已发布"),
    NO(false, "未发布");

    private Boolean value;
    private String description;

}
