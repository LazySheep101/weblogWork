package com.lazydaze.weblog.web.runner;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.lazydaze.weblog.common.constant.Constants;
import com.lazydaze.weblog.common.domain.dos.ArticleContentDO;
import com.lazydaze.weblog.common.domain.dos.ArticleDO;
import com.lazydaze.weblog.common.domain.mapper.ArticleContentMapper;
import com.lazydaze.weblog.common.domain.mapper.ArticleMapper;
import com.lazydaze.weblog.search.LuceneHelper;
import com.lazydaze.weblog.search.config.LuceneProperties;
import com.lazydaze.weblog.search.index.ArticleIndex;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2023/11/27 12:54
 * @description: 初始化文章 Lucene 索引
 **/
@Component
@Slf4j
public class InitLuceneIndexRunner implements CommandLineRunner {

    @Autowired
    private LuceneProperties luceneProperties;
    @Autowired
    private LuceneHelper luceneHelper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;

    @Override
    public void run(String... args) throws Exception {
        log.info("==> 开始初始化 Lucene 索引...");

        List<ArticleDO> articleDOS = articleMapper.selectList(Wrappers.emptyWrapper());

        if (articleDOS.isEmpty()) {
            log.info("==> 未发布任何文章，暂不创建 Lucene 索引...");
            return;
        }

        if (StringUtils.isBlank(luceneProperties.getIndexDir())) {
            log.error("==> 未指定 Lucene 索引存放位置，需在 application.yml 文件中添加路径配置...");
            return;
        }

        // 文章索引存放目录
        String articleIndexDir = luceneProperties.getIndexDir() + File.separator + ArticleIndex.NAME;

        List<Document> documents = Lists.newArrayList();
        articleDOS.forEach(articleDO -> {
            Long articleId = articleDO.getId();

            ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);
            // 文档
            Document document = new Document();
            document.add(new TextField(ArticleIndex.COLUMN_ID, String.valueOf(articleId), Field.Store.YES));
            document.add(new TextField(ArticleIndex.COLUMN_TITLE, articleDO.getTitle(), Field.Store.YES));
            document.add(new TextField(ArticleIndex.COLUMN_COVER, articleDO.getCover(), Field.Store.YES));
            document.add(new TextField(ArticleIndex.COLUMN_SUMMARY, articleDO.getSummary(), Field.Store.YES));
            document.add(new TextField(ArticleIndex.COLUMN_CONTENT, articleContentDO.getContent(), Field.Store.YES));
            document.add(new TextField(ArticleIndex.COLUMN_CREATE_TIME, Constants.DATE_TIME_FORMATTER.format(articleDO.getCreateTime()), Field.Store.YES));
            documents.add(document);
        });

        luceneHelper.createIndex(articleIndexDir, documents);

        log.info("==> 结束初始化 Lucene 索引...");
    }
}
