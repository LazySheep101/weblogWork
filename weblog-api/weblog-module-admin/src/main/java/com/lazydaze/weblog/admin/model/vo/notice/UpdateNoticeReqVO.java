package com.lazydaze.weblog.admin.model.vo.notice;

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
 * @description: 公告修改
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "修改公告 VO")
public class UpdateNoticeReqVO {

    @NotNull(message = "公告 ID 不能为空")
    private Long id;

    @NotBlank(message = "公告内容不能为空")
    @Length(min = 1, max = 460, message = "公告内容字数限制 1 ~ 460 之间")
    private String content;

}
