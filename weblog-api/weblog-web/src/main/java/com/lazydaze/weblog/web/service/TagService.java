package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;
import com.lazydaze.weblog.web.model.vo.tag.FindTagListReqVO;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 分类
 **/
public interface TagService {
    /**
     * 获取标签列表
     * @return
     */
    Response findTagList(FindTagListReqVO findTagListReqVO);

    /**
     * 获取标签下文章分页列表
     * @param findTagArticlePageListReqVO
     * @return
     */
    Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO);
}
