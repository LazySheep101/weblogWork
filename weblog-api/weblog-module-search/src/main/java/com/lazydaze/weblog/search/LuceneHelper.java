package com.lazydaze.weblog.search;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2023/11/27 13:11
 * @description: TODO
 **/
@Component
@Slf4j
public class LuceneHelper {

    /**
     * 创建索引
     */
    public void createIndex(String indexDir, List<Document> documents) {
        try {
            File dir = new File(indexDir);

            // 判断该目录是否存在
            if (dir.exists()) {
                FileUtils.cleanDirectory(dir);
            } else {
                FileUtils.forceMkdir(dir);
            }

            Directory directory = FSDirectory.open(Paths.get(indexDir));

            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(directory, config);

            documents.forEach(document -> {
                try {
                    writer.addDocument(document);
                } catch (IOException e) {
                    log.error("添加 Lucene 文档错误: ", e);
                }
            });

            writer.commit();
            writer.close();
        } catch (Exception e) {
            log.error("创建 Lucene 索引失败: ", e);
        }
    }

    /**
     * 添加文档
     * @param indexDir
     * @param document
     * @return
     */
    public long addDocument(String indexDir, Document document) {
        try {
            Directory dir = FSDirectory.open(Paths.get(indexDir));

            Analyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(dir, config);

            long count = writer.addDocument(document);

            writer.commit();
            writer.close();

            return count;
        } catch (Exception e) {
            log.error("添加 Lucene 文档失败: ", e);
            return 0;
        }
    }

    /**
     * 更新文档
     * @param indexDir
     * @param document
     * @param condition
     * @return
     */
    public long updateDocument(String indexDir, Document document, Term condition) {
        try {
            // 打开索引目录
            Directory directory = FSDirectory.open(Paths.get(indexDir));

            // 配置 IndexWriter
            SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(directory, config);

            // 更新文档
            long count = writer.updateDocument(condition, document);

            // 提交更改
            writer.commit();
            writer.close();

            return count;
        } catch (Exception e) {
            log.error("更新 Lucene 索引错误: ", e);
            return 0;
        }
    }

    /**
     * 删除文档
     * @param indexDir
     * @param condition
     */
    public long deleteDocument(String indexDir, Term condition) {
        try {
            Directory directory = FSDirectory.open(Paths.get(indexDir));

            IndexWriterConfig config = new IndexWriterConfig(new SmartChineseAnalyzer());
            IndexWriter writer = new IndexWriter(directory, config);

            long count = writer.deleteDocuments(condition);

            writer.commit();
            writer.close();

            return count;
        } catch (Exception e) {
            log.error("删除 Lucene 文档错误: ", e);
            return 0;
        }
    }

    /**
     * 关键词高亮搜索
     * @param indexDir
     * @param word
     */
    public long searchTotal(String indexDir, String word, String[] columns) {
        try {
            Directory directory = FSDirectory.open(Paths.get(indexDir));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = new SmartChineseAnalyzer();
            QueryParser parser = new MultiFieldQueryParser(columns, analyzer);
            // 搜索关键词
            Query query = parser.parse(word);

            // 获取总数据量
            TopDocs totalDocs  = searcher.search(query, Integer.MAX_VALUE);
            return totalDocs.totalHits.value;
        } catch (Exception e) {
            log.error("查询 Lucene 错误: ", e);
            return 0;
        }
    }

    /**
     * 关键词高亮搜索
     * @param indexDir
     * @param word
     * @param columns
     * @param current
     * @param size
     * @return
     */
    public List<Document> search(String indexDir, String word, String[] columns, int current, int size) {
        try {
            Directory directory = FSDirectory.open(Paths.get(indexDir));
            IndexReader reader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(reader);

            Analyzer analyzer = new SmartChineseAnalyzer();
            QueryParser parser = new MultiFieldQueryParser(columns, analyzer);
            // 搜索关键词
            Query query = parser.parse(word);

            // 执行查询
            int limit = current * size;
            TopDocs topDocs = searcher.search(query, limit); // 搜索前 limit 条结果

            // 获取分页查询结果
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            int start = (current - 1) * size;
            int end = Math.min(start + size, scoreDocs.length);

            List<Document> documents = Lists.newArrayList();
            // 输出查询结果
            for (int i = start; i < end; i++) {
                Document doc = searcher.doc(scoreDocs[i].doc);
                documents.add(doc);
            }

            reader.close();
            return documents;
        } catch (Exception e) {
            log.error("查询 Lucene 错误: ", e);
            return null;
        }
    }

}
