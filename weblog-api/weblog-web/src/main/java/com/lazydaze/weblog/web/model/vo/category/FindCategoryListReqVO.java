package com.lazydaze.weblog.web.model.vo.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 分类文章
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindCategoryListReqVO {

    private Long size;

}
