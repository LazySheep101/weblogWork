package com.lazydaze.weblog.admin.controller;

import com.lazydaze.weblog.admin.model.vo.dashboard.FindDashboardCategoryStatisticsInfoRspVO;
import com.lazydaze.weblog.admin.model.vo.dashboard.FindDashboardTagStatisticsInfoRspVO;
import com.lazydaze.weblog.admin.service.AdminDashboardService;
import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:01
 * @description: 仪表盘
 **/
@RestController
@RequestMapping("/admin/dashboard")
@Tag(name = "Admin 仪表盘")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService dashboardService;

    @PostMapping("/statistics")
    @Operation(summary = "获取后台仪表盘基础统计信息")
    @ApiOperationLog(description = "获取后台仪表盘基础统计信息")
    public Response findDashboardStatistics() {
        return dashboardService.findDashboardStatistics();
    }

    @PostMapping("/publishArticle/statistics")
    @Operation(summary = "获取后台仪表盘文章发布热点统计信息")
    @ApiOperationLog(description = "获取后台仪表盘文章发布热点统计信息")
    public Response findDashboardPublishArticleStatistics() {
        return dashboardService.findDashboardPublishArticleStatistics();
    }

    @PostMapping("/pv/statistics")
    @Operation(summary = "获取后台仪表盘最近一周 PV 访问量信息")
    @ApiOperationLog(description = "获取后台仪表盘最近一周 PV 访问量信息")
    public Response findDashboardPVStatistics() {
        return dashboardService.findDashboardPVStatistics();
    }

    @PostMapping("/category/statistics")
    @Operation(summary = "获取后台仪表盘分类饼图信息")
    @ApiOperationLog(description = "获取后台仪表盘分类饼图信息")
    public Response findDashboardCategoryStatistics() {
        return dashboardService.findDashboardCategoryStatistics();
    }

    @PostMapping("/tag/statistics")
    @Operation(summary = "获取后台仪表盘标签条形图信息")
    @ApiOperationLog(description = "获取后台仪表盘标签条形图信息")
    public Response findDashboardTagStatistics() {
        return dashboardService.findDashboardTagStatistics();
    }

}
