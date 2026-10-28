package com.lazydaze.weblog.web.controller;

import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.wiki.FindWikiArticlePreNextReqVO;
import com.lazydaze.weblog.web.model.vo.wiki.FindWikiCatalogListReqVO;
import com.lazydaze.weblog.web.service.WikiService;
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
 * @description: 知识库
 **/
@RestController
@RequestMapping("/wiki")
@Tag(name = "知识库")
public class WikiController {

    @Autowired
    private WikiService wikiService;

    @PostMapping("/list")
    @Operation(summary = "获取知识库数据")
    @ApiOperationLog(description = "获取知识库数据")
    public Response findWikiList() {
        return wikiService.findWikiList();
    }

    @PostMapping("/catalog/list")
    @Operation(summary = "获取知识库目录数据")
    @ApiOperationLog(description = "获取知识库目录数据")
    public Response findWikiCatalogList(@RequestBody @Validated FindWikiCatalogListReqVO findWikiCatalogListReqVO) {
        return wikiService.findWikiCatalogList(findWikiCatalogListReqVO);
    }


    @PostMapping("/article/preNext")
    @Operation(summary = "获取知识库文章上下页")
    @ApiOperationLog(description = "获取知识库文章上下页")
    public Response findArticlePreNext(@RequestBody FindWikiArticlePreNextReqVO findWikiArticlePreNextReqVO) {
        return wikiService.findArticlePreNext(findWikiArticlePreNextReqVO);
    }

}
