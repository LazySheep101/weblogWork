package com.lazydaze.weblog.web.model.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-04-19 16:06
 * @description: 标签
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagListRspVO {
    private Long id;
    private String name;
    private Integer articlesTotal;
}
