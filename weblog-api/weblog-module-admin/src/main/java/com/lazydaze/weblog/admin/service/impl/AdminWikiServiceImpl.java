package com.lazydaze.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.lazydaze.weblog.admin.model.vo.wiki.*;
import com.lazydaze.weblog.admin.service.AdminWikiService;
import com.lazydaze.weblog.common.domain.dos.ArticleDO;
import com.lazydaze.weblog.common.domain.dos.WikiCatalogDO;
import com.lazydaze.weblog.common.domain.dos.WikiDO;
import com.lazydaze.weblog.common.domain.mapper.ArticleMapper;
import com.lazydaze.weblog.common.domain.mapper.WikiCatalogMapper;
import com.lazydaze.weblog.common.domain.mapper.WikiMapper;
import com.lazydaze.weblog.common.enums.ResponseCodeEnum;
import com.lazydaze.weblog.common.exception.BizException;
import com.lazydaze.weblog.common.utils.PageResponse;
import com.lazydaze.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 知识库
 **/
@Service
@Slf4j
public class AdminWikiServiceImpl implements AdminWikiService {

    @Autowired
    private WikiMapper wikiMapper;
    @Autowired
    private WikiCatalogMapper wikiCatalogMapper;
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 新增知识库
     *
     * @param addWikiReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response addWiki(AddWikiReqVO addWikiReqVO) {
        // VO 转 DO
        WikiDO wikiDO = WikiDO.builder()
                .cover(addWikiReqVO.getCover())
                .title(addWikiReqVO.getTitle())
                .summary(addWikiReqVO.getSummary())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();

        wikiMapper.insert(wikiDO);
        Long wikiId = wikiDO.getId();

        // 初始化默认目录
        // > 概述
        // > 基础
        wikiCatalogMapper.insert(WikiCatalogDO.builder().wikiId(wikiId).title("概述").sort(1).build());
        wikiCatalogMapper.insert(WikiCatalogDO.builder().wikiId(wikiId).title("基础").sort(2).build());
        return Response.success();
    }

    /**
     * 知识库分页查询
     *
     * @param findWikiPageListReqVO
     * @return
     */
    @Override
    public Response findWikiPageList(FindWikiPageListReqVO findWikiPageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findWikiPageListReqVO.getCurrent();
        Long size = findWikiPageListReqVO.getSize();
        String title = findWikiPageListReqVO.getTitle();
        LocalDate startDate = findWikiPageListReqVO.getStartDate();
        LocalDate endDate = findWikiPageListReqVO.getEndDate();
        // 加一天，保证结束日期包含当天
        if (Objects.nonNull(endDate)) {
            endDate = endDate.plusDays(1);
        }

        // 执行分页查询
        Page<WikiDO> wikiDOPage = wikiMapper.selectPageList(current, size, title, startDate, endDate, null);

        List<WikiDO> wikiDOS = wikiDOPage.getRecords();

        // DO 转 VO
        List<FindWikiPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(wikiDOS)) {
            vos = wikiDOS.stream()
                    .map(articleDO -> FindWikiPageListRspVO.builder()
                            .id(articleDO.getId())
                            .title(articleDO.getTitle())
                            .cover(articleDO.getCover())
                            .summary(articleDO.getSummary())
                            .createTime(articleDO.getCreateTime())
                            .isTop(articleDO.getWeight() > 0)
                            .isPublish(articleDO.getIsPublish())
                            .build())
                    .collect(Collectors.toList());

            // 第一篇文章 ID
            vos.forEach(vo -> {
                Long wikiId = vo.getId();
                WikiCatalogDO wikiCatalogDO = wikiCatalogMapper.selectFirstArticleId(wikiId);
                vo.setFirstArticleId(Objects.nonNull(wikiCatalogDO) ? wikiCatalogDO.getArticleId() : null);
            });
        }

        return PageResponse.success(wikiDOPage, vos);
    }

    /**
     * 更新知识库
     *
     * @param updateWikiReqVO
     * @return
     */
    @Override
    public Response updateWiki(UpdateWikiReqVO updateWikiReqVO) {
        WikiDO wikiDO = WikiDO.builder()
                .id(updateWikiReqVO.getId())
                .title(updateWikiReqVO.getTitle())
                .cover(updateWikiReqVO.getCover())
                .summary(updateWikiReqVO.getSummary())
                .build();
        wikiMapper.updateById(wikiDO);
        return Response.success();
    }

    /**
     * 更新知识库置顶状态
     *
     * @param updateWikiIsTopReqVO
     * @return
     */
    @Override
    public Response updateWikiIsTop(UpdateWikiIsTopReqVO updateWikiIsTopReqVO) {
        Long wikiId = updateWikiIsTopReqVO.getId();
        Boolean isTop = updateWikiIsTopReqVO.getIsTop();

        Integer weight = 0;
        // 设置为置顶
        if (isTop) {
            WikiDO wikiDO = wikiMapper.selectMaxWeight();
            Integer maxWeight = wikiDO.getWeight();
            weight = maxWeight + 1;
        }

        wikiMapper.updateById(WikiDO.builder().id(wikiId).weight(weight).build());
        return Response.success();
    }

    /**
     * 更新知识库发布状态
     *
     * @param updateWikiIsPublishReqVO
     * @return
     */
    @Override
    public Response updateWikiIsPublish(UpdateWikiIsPublishReqVO updateWikiIsPublishReqVO) {
        Long wikiId = updateWikiIsPublishReqVO.getId();
        Boolean isPublish = updateWikiIsPublishReqVO.getIsPublish();

        wikiMapper.updateById(WikiDO.builder().id(wikiId).isPublish(isPublish).build());
        return Response.success();
    }

    /**
     * 查询知识库目录
     *
     * @param findWikiCatalogListReqVO
     * @return
     */
    @Override
    public Response findWikiCatalogList(FindWikiCatalogListReqVO findWikiCatalogListReqVO) {
        Long wikiId = findWikiCatalogListReqVO.getId();

        List<WikiCatalogDO> catalogDOS = wikiCatalogMapper.selectByWikiId(wikiId);

        // DO 转 VO
        List<FindWikiCatalogListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(catalogDOS)) {
            vos = Lists.newArrayList();

            // 一级目录
            List<WikiCatalogDO> level1Catalogs = catalogDOS.stream()
                    .filter(catalogDO -> catalogDO.getLevel() == 1)
                    .sorted(Comparator.comparing(WikiCatalogDO::getSort))
                    .collect(Collectors.toList());

            for (WikiCatalogDO level1Catalog : level1Catalogs) {
                vos.add(FindWikiCatalogListRspVO.builder()
                        .id(level1Catalog.getId())
                        .articleId(level1Catalog.getArticleId())
                        .title(level1Catalog.getTitle())
                                .level(level1Catalog.getLevel())
                                .sort(level1Catalog.getSort())
                                .editing(Boolean.FALSE)
                        .build());
            }

            // 二级目录
            vos.forEach(level1Catalog -> {
                Long parentId = level1Catalog.getId();
                List<WikiCatalogDO> level2CatalogDOS = catalogDOS.stream()
                        .filter(catalogDO -> Objects.equals(catalogDO.getParentId(), parentId)
                                && catalogDO.getLevel() == 2)
                        .sorted(Comparator.comparing(WikiCatalogDO::getSort))
                        .collect(Collectors.toList());

                List<FindWikiCatalogListRspVO> level2Catalogs = level2CatalogDOS.stream()
                        .map(catalogDO -> FindWikiCatalogListRspVO.builder()
                                .id(catalogDO.getId())
                                .articleId(catalogDO.getArticleId())
                                .title(catalogDO.getTitle())
                                .level(catalogDO.getLevel())
                                .sort(catalogDO.getSort())
                                .editing(Boolean.FALSE)
                                .build())
                        .collect(Collectors.toList());
                level1Catalog.setChildren(level2Catalogs);
            });
        }

        return Response.success(vos);
    }

    /**
     * 更新知识库目录
     *
     * @param updateWikiCatalogsReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateWikiCatalogs(UpdateWikiCatalogsReqVO updateWikiCatalogsReqVO) {
        Long wikiId = updateWikiCatalogsReqVO.getId();
        List<UpdateWikiCatalogItemReqVO> catalogs = updateWikiCatalogsReqVO.getCatalogs();

        // 1. 先将此知识库中的所有文章类型更新为普通
        // 查出此 wiki 下所有的文章 ID
        List<WikiCatalogDO> wikiCatalogDOS = wikiCatalogMapper.selectByWikiId(wikiId);
        List<Long> articleIds = wikiCatalogDOS.stream()
                .filter(wikiCatalogDO -> Objects.nonNull(wikiCatalogDO.getArticleId()))
                .map(WikiCatalogDO::getArticleId).collect(Collectors.toList());

        // 更新为普通文章类型
        if (!CollectionUtils.isEmpty(articleIds)) {
            articleMapper.updateByIds(ArticleDO.builder().type(1).build(), articleIds);
        }

        // 2. 先删除所有此知识库下所有目录
        wikiCatalogMapper.deleteByWikiId(wikiId);

        // 3. 再重新插入新的目录数据
        // 若目录不为空
        if (!CollectionUtils.isEmpty(catalogs)) {
            // 重新设置排序
            for (int i = 0; i < catalogs.size(); i++) {
                UpdateWikiCatalogItemReqVO vo = catalogs.get(i);
                List<UpdateWikiCatalogItemReqVO> children = vo.getChildren();
                vo.setSort(i + 1);
                if (!CollectionUtils.isEmpty(children)) {
                    for (int j = 0; j < children.size(); j++) {
                        children.get(j).setSort(j + 1);
                    }
                }
            }

            // VO 转 DO
            catalogs.forEach(catalog -> {
                // 一级目录
                WikiCatalogDO wikiCatalogDO = WikiCatalogDO.builder()
                        .wikiId(wikiId)
                        .title(catalog.getTitle())
                        .level(1)
                        .sort(catalog.getSort())
                        .build();
                // 添加一级目录
                wikiCatalogMapper.insert(wikiCatalogDO);

                Long catalogId = wikiCatalogDO.getId();

                // 二级目录
                List<UpdateWikiCatalogItemReqVO> children = catalog.getChildren();
                List<Long> updateArticleIds = Lists.newArrayList();
                if (!CollectionUtils.isEmpty(children)) {
                    List<WikiCatalogDO> level2Catalogs = Lists.newArrayList();
                    children.forEach(child -> {
                        level2Catalogs.add(WikiCatalogDO.builder()
                                .wikiId(wikiId)
                                .title(child.getTitle())
                                .level(2)
                                .sort(child.getSort())
                                .articleId(child.getArticleId())
                                .parentId(catalogId)
                                .createTime(LocalDateTime.now())
                                .updateTime(LocalDateTime.now())
                                .isDeleted(Boolean.FALSE)
                                .build());

                        updateArticleIds.add(child.getArticleId());
                    });

                    // 批量插入
                    wikiCatalogMapper.insertBatchSomeColumn(level2Catalogs);
                    // 更新为普通知识库类型
                    articleMapper.updateByIds(ArticleDO.builder().type(2).build(), updateArticleIds);
                }
            });
        }

        return Response.success();
    }

    /**
     * 删除知识库
     *
     * @param deleteWikiReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteWiki(DeleteWikiReqVO deleteWikiReqVO) {
        Long wikiId = deleteWikiReqVO.getId();

        // 删除知识库
        int count = wikiMapper.deleteById(wikiId);

        // 知识库不存在
        if (count == 0) {
            log.warn("该知识库不存在, wikiId: {}", wikiId);
            throw new BizException(ResponseCodeEnum.WIKI_NOT_FOUND);
        }

        // 修改目录中所有的文章类型
        List<WikiCatalogDO> wikiCatalogDOS = wikiCatalogMapper.selectByWikiId(wikiId);
        List<Long> articleIds = wikiCatalogDOS.stream()
                .filter(wikiCatalogDO -> Objects.nonNull(wikiCatalogDO.getArticleId())
                        && wikiCatalogDO.getLevel() == 2)
                .map(WikiCatalogDO::getArticleId)
                .collect(Collectors.toList());

        // 更新为普通知识库类型
        if (!CollectionUtils.isEmpty(articleIds)) {
            articleMapper.updateByIds(ArticleDO.builder().type(1).build(), articleIds);
        }

        // 删除知识库目录
        wikiCatalogMapper.deleteByWikiId(wikiId);
        return Response.success();
    }
}
