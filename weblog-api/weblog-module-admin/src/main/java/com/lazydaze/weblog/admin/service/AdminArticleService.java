package com.lazydaze.weblog.admin.service;

import com.lazydaze.weblog.admin.model.vo.article.*;
import com.lazydaze.weblog.common.utils.Response;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 文章
 **/
public interface AdminArticleService {
    /**
     * 发布文章
     * @param publishArticleReqVO
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

    /**
     * 删除文章
     * @param deleteArticleReqVO
     * @return
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);

    /**
     * 查询文章分页数据
     * @param findArticlePageListReqVO
     * @return
     */
    Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO);

    /**
     * 查询文章详情
     * @param findArticleDetailReqVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

    /**
     * 更新文章
     * @param updateArticleReqVO
     * @return
     */
    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);

    /**
     * 更新文章置顶状态
     * @param updateArticleIsTopReqVO
     * @return
     */
    Response updateArticleIsTop(UpdateArticleIsTopReqVO updateArticleIsTopReqVO);

    /**
     * 更新文章发布状态
     * @param updateArticleIsPublishReqVO
     * @return
     */
    Response updateArticleIsPublish(UpdateArticleIsPublishReqVO updateArticleIsPublishReqVO);
}
