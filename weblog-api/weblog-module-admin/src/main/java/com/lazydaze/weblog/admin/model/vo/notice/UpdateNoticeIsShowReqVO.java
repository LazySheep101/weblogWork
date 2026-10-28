package com.lazydaze.weblog.admin.model.vo.notice;

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
 * @description: 修改公告展示状态
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "修改公告展示状态 VO")
public class UpdateNoticeIsShowReqVO {

    @NotNull(message = "公告 ID 不能为空")
    private Long id;

    @NotNull(message = "公告状态不能为空")
    private Boolean isShow;

}
