package com.lazydaze.weblog.admin.event.subscriber;

import com.lazydaze.weblog.admin.event.PublishArticleEvent;
import com.lazydaze.weblog.admin.service.AdminStatisticsService;
import com.lazydaze.weblog.common.constant.Constants;
import com.lazydaze.weblog.common.domain.dos.ArticleContentDO;
import com.lazydaze.weblog.common.domain.dos.ArticleDO;
import com.lazydaze.weblog.common.domain.mapper.ArticleContentMapper;
import com.lazydaze.weblog.common.domain.mapper.ArticleMapper;
import com.lazydaze.weblog.search.LuceneHelper;
import com.lazydaze.weblog.search.config.LuceneProperties;
import com.lazydaze.weblog.search.index.ArticleIndex;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2023/11/9 10:08
 * @description: TODO
 **/
@Component
@Slf4j
public class PublishArticleSubscriber implements ApplicationListener<PublishArticleEvent> {

    @Autowired
    private LuceneHelper luceneHelper;
    @Autowired
    private LuceneProperties luceneProperties;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleContentMapper articleContentMapper;
    @Autowired
    private AdminStatisticsService statisticsService;

    @Override
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(PublishArticleEvent event) {
        // 在这里处理收到的事件，可以是任何逻辑操作
        Long articleId = event.getArticleId();

        // 获取当前线程名称
        String threadName = Thread.currentThread().getName();

        log.info("==> threadName: {}", threadName);
        log.info("==> 文章发布事件消费成功，articleId: {}", articleId);

        ArticleDO articleDO = articleMapper.selectById(articleId);
        ArticleContentDO articleContentDO = articleContentMapper.selectByArticleId(articleId);

        // 文章索引存放目录
        String articleIndexDir = luceneProperties.getIndexDir() + File.separator + ArticleIndex.NAME;

        // 添加 Lucene 索引
        Document document = new Document();
        document.add(new TextField(ArticleIndex.COLUMN_ID, String.valueOf(articleId), Field.Store.YES));
        document.add(new TextField(ArticleIndex.COLUMN_TITLE, articleDO.getTitle(), Field.Store.YES));
        document.add(new TextField(ArticleIndex.COLUMN_COVER, articleDO.getCover(), Field.Store.YES));
        document.add(new TextField(ArticleIndex.COLUMN_SUMMARY, articleDO.getSummary(), Field.Store.YES));
        document.add(new TextField(ArticleIndex.COLUMN_CONTENT, articleContentDO.getContent(), Field.Store.YES));
        document.add(new TextField(ArticleIndex.COLUMN_CREATE_TIME, Constants.DATE_TIME_FORMATTER.format(articleDO.getCreateTime()), Field.Store.YES));
        long count = luceneHelper.addDocument(articleIndexDir, document);

        log.info("==> 添加文章对应 Lucene 文档结束，articleId: {}，受影响行数: {}", articleId, count);

        // 重新统计各分类下文章总数
        statisticsService.statisticsCategoryArticleTotal();
        log.info("==> 重新统计各分类下文章总数");

        // 重新统计各标签下文章总数
        statisticsService.statisticsTagArticleTotal();
        log.info("==> 重新统计各标签下文章总数");
    }
}