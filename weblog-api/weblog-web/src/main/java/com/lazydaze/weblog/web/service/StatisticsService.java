package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 统计信息
 **/
public interface StatisticsService {
    /**
     * 获取文章总数、分类总数、标签总数、总访问量统计信息
     * @return
     */
    Response findBaseInfo();
}
