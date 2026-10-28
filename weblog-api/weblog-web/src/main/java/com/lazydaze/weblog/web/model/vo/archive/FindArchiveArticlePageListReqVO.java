package com.lazydaze.weblog.web.model.vo.archive;

import com.lazydaze.weblog.common.model.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 文章归档
 **/
@Data
@Builder
@Schema(description = "文章归档分页 VO")
public class FindArchiveArticlePageListReqVO extends BasePageQuery {
}
