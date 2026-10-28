package com.lazydaze.weblog.web.controller;

import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.lazydaze.weblog.web.service.CommentService;
import com.lazydaze.weblog.web.service.NoticeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:01
 * @description: 公告
 **/
@RestController
@RequestMapping("/notice")
@Tag(name = "公告")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @PostMapping("/info")
    @Operation(summary = "获取公告信息")
    @ApiOperationLog(description = "获取公告信息")
    public Response findNoticeInfo() {
        return noticeService.findNoticeInfo();
    }


}
