package com.lazydaze.weblog.web.model.vo.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 文章归档
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchArticlePageListRspVO {
    private Long id;
    private String cover;
    private String title;
    private String summary;
    /**
     * 发布日期
     */
    private String createDate;
}
