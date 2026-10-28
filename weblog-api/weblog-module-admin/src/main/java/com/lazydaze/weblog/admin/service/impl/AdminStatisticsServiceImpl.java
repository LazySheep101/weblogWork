package com.lazydaze.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lazydaze.weblog.admin.service.AdminStatisticsService;
import com.lazydaze.weblog.common.domain.dos.ArticleCategoryRelDO;
import com.lazydaze.weblog.common.domain.dos.ArticleTagRelDO;
import com.lazydaze.weblog.common.domain.dos.CategoryDO;
import com.lazydaze.weblog.common.domain.dos.TagDO;
import com.lazydaze.weblog.common.domain.mapper.ArticleCategoryRelMapper;
import com.lazydaze.weblog.common.domain.mapper.ArticleTagRelMapper;
import com.lazydaze.weblog.common.domain.mapper.CategoryMapper;
import com.lazydaze.weblog.common.domain.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 仪表盘
 **/
@Service
@Slf4j
public class AdminStatisticsServiceImpl implements AdminStatisticsService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCategoryRelMapper articleCategoryRelMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagRelMapper articleTagRelMapper;

    @Override
    public void statisticsCategoryArticleTotal() {
        List<ArticleCategoryRelDO> articleCategoryRelDOS = articleCategoryRelMapper.selectList(Wrappers.emptyWrapper());

        if (!CollectionUtils.isEmpty(articleCategoryRelDOS)) {
            Map<Long, List<ArticleCategoryRelDO>> categoryIdAndArticleCategoryRelDOMap = articleCategoryRelDOS.stream()
                    .collect(Collectors.groupingBy(ArticleCategoryRelDO::getCategoryId));

            List<CategoryDO> categoryDOS = categoryMapper.selectList(Wrappers.emptyWrapper());

            categoryDOS.forEach(categoryDO -> {
                Long categoryId = categoryDO.getId();
                List<ArticleCategoryRelDO> articleCategoryRelDOList = categoryIdAndArticleCategoryRelDOMap.get(categoryId);
                if (!CollectionUtils.isEmpty(articleCategoryRelDOList)) {
                    int articlesTotal = articleCategoryRelDOList.size();

                    CategoryDO categoryDO1 = CategoryDO.builder()
                            .id(categoryId)
                            .articlesTotal(articlesTotal)
                            .build();
                    categoryMapper.updateById(categoryDO1);
                }

            });
        }
    }

    /**
     * 统计各标签下文章总数
     */
    @Override
    public void statisticsTagArticleTotal() {
        List<ArticleTagRelDO> articleTagRelDOS = articleTagRelMapper.selectList(Wrappers.emptyWrapper());

        if (!CollectionUtils.isEmpty(articleTagRelDOS)) {
            Map<Long, List<ArticleTagRelDO>> tagIdAndArticleTagRelDOMap = articleTagRelDOS.stream()
                    .collect(Collectors.groupingBy(ArticleTagRelDO::getTagId));

            List<TagDO> tagDOS = tagMapper.selectList(Wrappers.emptyWrapper());

            tagDOS.forEach(tagDO -> {
                Long tagId = tagDO.getId();
                List<ArticleTagRelDO> articleTagRelDOList = tagIdAndArticleTagRelDOMap.get(tagId);
                if (!CollectionUtils.isEmpty(articleTagRelDOList)) {
                    int articlesTotal = articleTagRelDOList.size();

                    TagDO tagDO1 = TagDO.builder()
                            .id(tagId)
                            .articlesTotal(articlesTotal)
                            .build();
                    tagMapper.updateById(tagDO1);
                }

            });
        }
    }
}
