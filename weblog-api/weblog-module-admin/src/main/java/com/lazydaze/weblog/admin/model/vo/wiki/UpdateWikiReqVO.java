package com.lazydaze.weblog.admin.model.vo.wiki;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 更新知识库
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "更新知识库 VO")
public class UpdateWikiReqVO {

    @NotNull(message = "ID 不能为空")
    private Long id;

    @NotBlank(message = "知识库标题不能为空")
    @Length(min = 1, max = 20, message = "知识库标题字数需大于 1 小于 20")
    private String title;

    @NotBlank(message = "知识库摘要不能为空")
    private String summary;

    @NotBlank(message = "知识库封面不能为空")
    private String cover;

}
