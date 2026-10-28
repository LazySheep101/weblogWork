package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.category.FindCategoryArticlePageListReqVO;
import com.lazydaze.weblog.web.model.vo.category.FindCategoryListReqVO;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 分类
 **/
public interface CategoryService {
    /**
     * 获取分类列表
     * @return
     */
    Response findCategoryList(FindCategoryListReqVO findCategoryListReqVO);

    /**
     * 获取分类下文章分页数据
     * @param findCategoryArticlePageListReqVO
     * @return
     */
    Response findCategoryArticlePageList(FindCategoryArticlePageListReqVO findCategoryArticlePageListReqVO);
}
