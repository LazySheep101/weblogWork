package com.lazydaze.weblog.web;

import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

@SpringBootTest
@Slf4j
class LuceneTests {

    // 索引存放目录
    private static final String INDEX_DIR_PATH = "E:\\java_workspace\\lucene-test";

    @Test
    void createIndex() throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(INDEX_DIR_PATH));

        Analyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // 创建索引
        IndexWriter writer = new IndexWriter(indexDir, config);

        // 文档
        Document document = new Document();
        document.add(new TextField("title", "从零手撸前后端分离博客（已更新24w+字）", Field.Store.YES));
        document.add(new TextField("content", "本实战项目是星球内部第一个项目，目前已输出 24w+ 字，更新了 158 小节内容，演示图片：1050 张，还在持续爆肝中，目标是将 Java 程序员生涯中，比较典型的项目都教会大家，如前后端分离博客全栈级开发、秒杀系统、在线商城、IM 即时通讯（Netty）、权限管理（Spring Security）、Spring Cloud Alibaba 微服务等等... 目前有 500+ 小伙伴已加入，一起学习打卡，一起进步！同频的人才能走的更快、更远 ！ 欢迎各位小伙伴加入哟~", Field.Store.YES));
        writer.addDocument(document);

        writer.commit();
        writer.close();
    }


    @Test
    public void search() throws IOException, ParseException, InvalidTokenOffsetsException {
        Directory indexDirectory = FSDirectory.open(Paths.get(INDEX_DIR_PATH));
        IndexReader reader = DirectoryReader.open(indexDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);

        Analyzer analyzer = new SmartChineseAnalyzer();
        String[] fields = {"title", "content"};
        QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        // 搜索关键词
        Query query = parser.parse("星球");

        // 创建高亮器
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span style=\"background-color: yellow\">", "</span>");
        Highlighter highlighter = new Highlighter(formatter, new QueryScorer(query));

        TopDocs topDocs = searcher.search(query, 10); // 搜索前10条结果

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            // result.add(Long.parseLong(doc.get("id")));
            String title = doc.get("title");

            // 获取高亮的片段
            TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
            String fragment = highlighter.getBestFragment(tokenStream, title);

            // 如果没有匹配到关键词，则返回原始文本
            String highlightedTitle = (fragment != null) ? fragment : title;

            String content = doc.get("content");

            // 获取高亮的片段
            TokenStream tokenStream2= analyzer.tokenStream("content", new StringReader(content));
            String fragment2 = highlighter.getBestFragment(tokenStream2, content);

            // 如果没有匹配到关键词，则返回原始文本
            String highlightedContent = (fragment2 != null) ? fragment2 : content;

            // // 获取第一段包含关键词的一部分文字
            // String snippet = getSnippet(highlightedContent, query.toString(), 10);

            log.info("结果 ： title: {}, content: {}", highlightedTitle, highlightedContent);
        }

        reader.close();
    }

    private static String getSnippet(String content, String query, int snippetLength) {
        // 查找关键词在正文中的位置
        int index = content.toLowerCase().indexOf(query.toLowerCase());

        // 如果找到了关键词
        if (index != -1) {
            int start = Math.max(0, index - snippetLength / 2);
            int end = Math.min(content.length(), index + query.length() + snippetLength / 2);

            return content.substring(start, end);
        }

        // 如果没找到关键词，返回正文的前 snippetLength 个字符
        return content.substring(0, Math.min(content.length(), snippetLength));
    }


    // private static String getHighlightedField(Query query, String fieldName, Document doc, IndexSearcher searcher) throws IOException, InvalidTokenOffsetsException {
    //     TokenStream tokenStream = TokenSources.getAnyTokenStream(searcher.getIndexReader(), doc, fieldName, doc, new StandardAnalyzer());
    //     TextFragment[] frag = new QueryScorer(query).getBestTextFragments(tokenStream, doc.get(fieldName), false, 10);
    //     StringBuilder highlightedField = new StringBuilder();
    //     for (TextFragment textFragment : frag) {
    //         if (textFragment != null && textFragment.getScore() > 0) {
    //             highlightedField.append(textFragment.toString());
    //         }
    //     }
    //     return highlightedField.toString();
    // }

}
