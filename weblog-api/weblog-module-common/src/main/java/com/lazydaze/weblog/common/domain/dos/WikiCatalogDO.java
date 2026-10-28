package com.lazydaze.weblog.common.domain.dos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:01
 * @description: 知识库目录
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_wiki_catalog")
public class WikiCatalogDO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long wikiId;

    private Long articleId;

    private String title;

    private Integer level;

    private Long parentId;

    private Integer sort;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean isDeleted;
}
