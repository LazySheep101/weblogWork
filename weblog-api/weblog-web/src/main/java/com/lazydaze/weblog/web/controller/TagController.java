package com.lazydaze.weblog.web.controller;

import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.tag.FindTagArticlePageListReqVO;
import com.lazydaze.weblog.web.model.vo.tag.FindTagListReqVO;
import com.lazydaze.weblog.web.service.TagService;
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
 * @description: 标签
 **/
@RestController
@RequestMapping("/tag")
@Tag(name = "标签")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("/list")
    @Operation(summary = "前台获取标签列表")
    @ApiOperationLog(description = "前台获取标签列表")
    public Response findTagList(@RequestBody @Validated FindTagListReqVO findTagListReqVO) {
        return tagService.findTagList(findTagListReqVO);
    }

    @PostMapping("/article/list")
    @Operation(summary = "前台获取标签下文章列表")
    @ApiOperationLog(description = "前台获取标签下文章列表")
    public Response findTagPageList(@RequestBody @Validated FindTagArticlePageListReqVO findTagArticlePageListReqVO) {
        return tagService.findTagPageList(findTagArticlePageListReqVO);
    }

}
