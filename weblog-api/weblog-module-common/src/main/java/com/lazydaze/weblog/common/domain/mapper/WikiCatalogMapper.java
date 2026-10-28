package com.lazydaze.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lazydaze.weblog.common.config.InsertBatchMapper;
import com.lazydaze.weblog.common.domain.dos.WikiCatalogDO;

import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:06
 * @description: 知识库目录
 **/
public interface WikiCatalogMapper extends InsertBatchMapper<WikiCatalogDO> {

    default List<WikiCatalogDO> selectByWikiId(Long wikiId) {
        return selectList(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
        );
    }

    default int deleteByWikiId(Long wikiId) {
        return delete(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId));
    }

    default WikiCatalogDO selectFirstArticleId(Long wikiId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
                .eq(WikiCatalogDO::getLevel, 2)
                .orderByAsc(WikiCatalogDO::getId)
                .last("LIMIT 1")
        );
    }

    default WikiCatalogDO selectByWikiIdAndArticleId(Long wikiId, Long articleId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
                .eq(WikiCatalogDO::getArticleId, articleId)
        );
    }

    default WikiCatalogDO selectNextArticle(Long wikiId, Long catalogId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                    .eq(WikiCatalogDO::getWikiId, wikiId)
                    .isNotNull(WikiCatalogDO::getArticleId)
                    .orderByAsc(WikiCatalogDO::getId) // 按目录 ID 倒序排列
                    .gt(WikiCatalogDO::getId, catalogId) // 查询比当前文章 ID 大的
                    .last("limit 1")
                ); // 第一条记录即为上一篇文章
    }

    default WikiCatalogDO selectPreArticle(Long wikiId, Long catalogId) {
        return selectOne(Wrappers.<WikiCatalogDO>lambdaQuery()
                .eq(WikiCatalogDO::getWikiId, wikiId)
                .isNotNull(WikiCatalogDO::getArticleId)
                .orderByDesc(WikiCatalogDO::getId) // 按文章 ID 倒序排列
                .lt(WikiCatalogDO::getId, catalogId) // 查询比当前文章 ID 小的
                .last("limit 1")
        ); // 第一条记录即为上一篇文章
    }
}
