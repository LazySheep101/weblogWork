package com.lazydaze.weblog.web.service.impl;

import com.google.common.collect.Lists;
import com.lazydaze.weblog.common.utils.PageResponse;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.search.LuceneHelper;
import com.lazydaze.weblog.search.config.LuceneProperties;
import com.lazydaze.weblog.search.index.ArticleIndex;
import com.lazydaze.weblog.web.model.vo.search.SearchArticlePageListReqVO;
import com.lazydaze.weblog.web.model.vo.search.SearchArticlePageListRspVO;
import com.lazydaze.weblog.web.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 文章归档
 **/
@Service
@Slf4j
public class SearchServiceImpl implements SearchService {

    @Autowired
    private LuceneProperties luceneProperties;
    @Autowired
    private LuceneHelper luceneHelper;

    @Override
    public Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO) {
        int current = Math.toIntExact(searchArticlePageListReqVO.getCurrent());
        int size = Math.toIntExact(searchArticlePageListReqVO.getSize());

        // 文章索引存放目录
        String articleIndexDir = luceneProperties.getIndexDir() + File.separator + ArticleIndex.NAME;
        String word = searchArticlePageListReqVO.getWord();

        String[] columns = {ArticleIndex.COLUMN_TITLE, ArticleIndex.COLUMN_SUMMARY};
        // 查询总记录数
        long total = luceneHelper.searchTotal(articleIndexDir, word, columns);

        // 分页查询
        List<Document> documents = luceneHelper.search(articleIndexDir, word, columns, current, size);

        if (CollectionUtils.isEmpty(documents)) {
            return PageResponse.success(total, current, size, null);
        }

        // 搜索关键词
        Analyzer analyzer = new SmartChineseAnalyzer();
        QueryParser parser = new QueryParser(ArticleIndex.COLUMN_TITLE, analyzer);
        Query query = null;
        try {
            query = parser.parse(word);
        } catch (ParseException e) {
            log.error("解析关键词错误:", e);
        }

        // 创建高亮器
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style=\"color: #f73131\">", "</span>");
        Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query));

        List<SearchArticlePageListRspVO> vos = Lists.newArrayList();
        documents.forEach(document -> {
            try {
                // result.add(Long.parseLong(doc.get("id")));
                String title = document.get(ArticleIndex.COLUMN_TITLE);

                // 获取高亮的片段
                String titleFragment = getHighlightHtmlWith(analyzer, title, highlighter);

                // 如果没有匹配到关键词，则返回原始文本
                String highlightedTitle = StringUtils.isNoneBlank(titleFragment) ? titleFragment : title;

                String cover = document.get(ArticleIndex.COLUMN_COVER);
                String id = document.get(ArticleIndex.COLUMN_ID);
                String createTime = document.get(ArticleIndex.COLUMN_CREATE_TIME);

                String summary = document.get(ArticleIndex.COLUMN_SUMMARY);

                String summaryFragment = getHighlightHtmlWith(analyzer, summary, highlighter);

                // 如果没有匹配到关键词，则返回原始文本
                String highlightedSummary = StringUtils.isNoneBlank(summaryFragment) ? summaryFragment : summary;

                SearchArticlePageListRspVO vo = SearchArticlePageListRspVO.builder()
                        .id(Long.valueOf(id))
                        .title(highlightedTitle)
                        .summary(highlightedSummary)
                        .cover(cover)
                        .createDate(createTime)
                        .build();

                vos.add(vo);
            } catch (Exception e) {
                log.error("文档转换错误: ", e);
            }
        });

        return PageResponse.success(total, current, size, vos);
    }

    private String getHighlightHtmlWith(Analyzer analyzer, String column, Highlighter highlighter) throws IOException, InvalidTokenOffsetsException {
        // 获取高亮的片段
        TokenStream tokenStream = analyzer.tokenStream(ArticleIndex.COLUMN_SUMMARY, new StringReader(column));
        String fragment = highlighter.getBestFragment(tokenStream, column);
        return fragment;
    }
}
