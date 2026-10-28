package com.lazydaze.weblog.web.controller;

import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.comment.FindPageCommentsReqVO;
import com.lazydaze.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.lazydaze.weblog.web.model.vo.comment.PublishCommentReqVO;
import com.lazydaze.weblog.web.service.CommentService;
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
 * @description: 评论
 **/
@RestController
@RequestMapping("/comment")
@Tag(name = "评论")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/qq/userInfo")
    @Operation(summary = "获取 QQ 用户信息")
    @ApiOperationLog(description = "获取 QQ 用户信息")
    public Response findQQUserInfo(@RequestBody @Validated FindQQUserInfoReqVO findQQUserInfoReqVO) {
        return commentService.findQQUserInfo(findQQUserInfoReqVO);
    }

    @PostMapping("/publish")
    @Operation(summary = "发布评论")
    @ApiOperationLog(description = "发布评论")
    public Response publishComment(@RequestBody @Validated PublishCommentReqVO publishCommentReqVO) {
        return commentService.publishComment(publishCommentReqVO);
    }


    @PostMapping("/list")
    @Operation(summary = "获取页面所有评论")
    @ApiOperationLog(description = "获取页面所有评论")
    public Response findPageComments(@RequestBody @Validated FindPageCommentsReqVO findPageCommentsReqVO) {
        return commentService.findPageComments(findPageCommentsReqVO);
    }

}
