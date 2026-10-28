package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.search.SearchArticlePageListReqVO;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description:
 **/
public interface SearchService {


    Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}
