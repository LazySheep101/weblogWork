package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.article.FindArticleDetailReqVO;
import com.lazydaze.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 文章
 **/
public interface ArticleService {
    /**
     * 获取首页文章分页数据
     * @param findIndexArticlePageListReqVO
     * @return
     */
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);

    /**
     * 获取文章详情
     * @param findArticleDetailReqVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

}
