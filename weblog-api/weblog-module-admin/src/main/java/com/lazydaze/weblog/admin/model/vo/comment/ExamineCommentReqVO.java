package com.lazydaze.weblog.admin.model.vo.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 评论审核
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "评论审核 VO")
public class ExamineCommentReqVO {

    @NotNull(message = "评论 ID 不能为空")
    private Long id;

    @NotNull(message = "评论状态不能为空")
    private Integer status;

    private String reason;
}
