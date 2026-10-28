package com.lazydaze.weblog.web.service.impl;

import com.google.common.collect.Lists;
import com.lazydaze.weblog.common.domain.dos.WikiCatalogDO;
import com.lazydaze.weblog.common.domain.dos.WikiDO;
import com.lazydaze.weblog.common.domain.mapper.WikiCatalogMapper;
import com.lazydaze.weblog.common.domain.mapper.WikiMapper;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.article.FindPreNextArticleRspVO;
import com.lazydaze.weblog.web.model.vo.wiki.*;
import com.lazydaze.weblog.web.service.WikiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

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
public class WikiServiceImpl implements WikiService {

    @Autowired
    private WikiMapper wikiMapper;
    @Autowired
    private WikiCatalogMapper wikiCatalogMapper;

    /**
     * 获取知识库
     *
     * @return
     */
    @Override
    public Response findWikiList() {
        List<WikiDO> wikiDOS = wikiMapper.selectPublished();

        List<FindWikiListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(wikiDOS)) {
            vos = wikiDOS.stream()
                    .map(wikiDO -> FindWikiListRspVO.builder()
                            .id(wikiDO.getId())
                            .title(wikiDO.getTitle())
                            .cover(wikiDO.getCover())
                            .summary(wikiDO.getSummary())
                            .isTop(wikiDO.getWeight() > 0)
                            .build())
                    .collect(Collectors.toList());

            // 第一篇文章 ID
            vos.forEach(vo -> {
                Long wikiId = vo.getId();
                WikiCatalogDO wikiCatalogDO = wikiCatalogMapper.selectFirstArticleId(wikiId);
                vo.setFirstArticleId(Objects.nonNull(wikiCatalogDO) ? wikiCatalogDO.getArticleId() : null);
            });
        }

        return Response.success(vos);
    }

    /**
     * 获取知识库目录
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

                if (!CollectionUtils.isEmpty(level2CatalogDOS)) {
                    List<FindWikiCatalogListRspVO> level2Catalogs = level2CatalogDOS.stream()
                            .map(catalogDO -> FindWikiCatalogListRspVO.builder()
                                    .id(catalogDO.getId())
                                    .articleId(catalogDO.getArticleId())
                                    .title(catalogDO.getTitle())
                                    .level(catalogDO.getLevel())
                                    .build())
                            .collect(Collectors.toList());
                    level1Catalog.setChildren(level2Catalogs);
                }
            });
        }

        return Response.success(vos);
    }

    /**
     * 获取上下页
     *
     * @param findWikiArticlePreNextReqVO
     * @return
     */
    @Override
    public Response findArticlePreNext(FindWikiArticlePreNextReqVO findWikiArticlePreNextReqVO) {
        Long wikiId = findWikiArticlePreNextReqVO.getId();
        Long articleId = findWikiArticlePreNextReqVO.getArticleId();

        FindWikiArticlePreNextRspVO vo = new FindWikiArticlePreNextRspVO();
        WikiCatalogDO wikiCatalogDO = wikiCatalogMapper.selectByWikiIdAndArticleId(wikiId, articleId);

        // 上一篇
        WikiCatalogDO preArticleDO = wikiCatalogMapper.selectPreArticle(wikiId, wikiCatalogDO.getId());
        if (Objects.nonNull(preArticleDO)) {
            FindPreNextArticleRspVO preArticleVO = FindPreNextArticleRspVO.builder()
                    .articleId(preArticleDO.getArticleId())
                    .articleTitle(preArticleDO.getTitle())
                    .build();
            vo.setPreArticle(preArticleVO);
        }

        // 下一篇
        WikiCatalogDO nextArticleDO = wikiCatalogMapper.selectNextArticle(wikiId, wikiCatalogDO.getId());
        if (Objects.nonNull(nextArticleDO)) {
            FindPreNextArticleRspVO nextArticleVO = FindPreNextArticleRspVO.builder()
                    .articleId(nextArticleDO.getArticleId())
                    .articleTitle(nextArticleDO.getTitle())
                    .build();
            vo.setNextArticle(nextArticleVO);
        }

        return Response.success(vo);
    }
}
