package com.lazydaze.weblog.admin.model.vo.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 公告分页
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoticePageListRspVO {

    private Long id;

    private String content;

    private Boolean isShow;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
