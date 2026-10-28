package com.lazydaze.weblog.admin.service;

import com.lazydaze.weblog.admin.model.vo.dashboard.FindDashboardCategoryStatisticsInfoRspVO;
import com.lazydaze.weblog.admin.model.vo.dashboard.FindDashboardTagStatisticsInfoRspVO;
import com.lazydaze.weblog.common.utils.Response;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 仪表盘
 **/
public interface AdminDashboardService {

    /**
     * 获取仪表盘基础统计信息
     * @return
     */
    Response findDashboardStatistics();

    /**
     * 获取文章发布热点统计信息
     * @return
     */
    Response findDashboardPublishArticleStatistics();

    /**
     * 获取文章最近一周 PV 访问量统计信息
     * @return
     */
    Response findDashboardPVStatistics();

    /**
     * 获取分类饼图信息
     * @return
     */
    Response findDashboardCategoryStatistics();

    /**
     * 获取标签饼图信息
     * @return
     */
    Response findDashboardTagStatistics();
}
