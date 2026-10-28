package com.lazydaze.weblog.admin.controller;

import com.lazydaze.weblog.admin.model.vo.notice.*;
import com.lazydaze.weblog.admin.service.AdminNoticeService;
import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:01
 * @description: 公告模块
 **/
@RestController
@RequestMapping("/admin/notice")
@Tag(name = "Admin 公告模块")
public class AdminNoticeController {

    @Autowired
    private AdminNoticeService noticeService;

    @PostMapping("/add")
    @Operation(summary = "添加公告")
    @ApiOperationLog(description = "添加公告")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response addNotice(@RequestBody @Validated AddNoticeReqVO addNoticeReqVO) {
        return noticeService.addNotice(addNoticeReqVO);
    }

    @PostMapping("/update")
    @Operation(summary = "修改公告")
    @ApiOperationLog(description = "修改公告")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateNotice(@RequestBody @Validated UpdateNoticeReqVO updateNoticeReqVO) {
        return noticeService.updateNotice(updateNoticeReqVO);
    }

    @PostMapping("/delete")
    @Operation(summary = "公告删除")
    @ApiOperationLog(description = "公告删除")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response deleteNotice(@RequestBody @Validated DeleteNoticeReqVO deleteNoticeReqVO) {
        return noticeService.deleteNotice(deleteNoticeReqVO);
    }

    @PostMapping("/list")
    @Operation(summary = "查询公告分页数据")
    @ApiOperationLog(description = "查询公告分页数据")
    public Response findNoticePageList(@RequestBody @Validated FindNoticePageListReqVO findNoticePageListReqVO) {
        return noticeService.findNoticePageList(findNoticePageListReqVO);
    }

    @PostMapping("/isShow/update")
    @Operation(summary = "公告展示状态修改")
    @ApiOperationLog(description = "公告展示状态修改")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Response updateNoticeIsShow(@RequestBody @Validated UpdateNoticeIsShowReqVO updateNoticeIsShowReqVO) {
        return noticeService.updateNoticeIsShow(updateNoticeIsShowReqVO);
    }

}
