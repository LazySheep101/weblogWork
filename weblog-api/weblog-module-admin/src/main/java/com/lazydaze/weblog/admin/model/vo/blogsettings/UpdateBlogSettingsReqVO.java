package com.lazydaze.weblog.admin.model.vo.blogsettings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 博客基础信息修改
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = " 博客基础信息修改 VO")
public class UpdateBlogSettingsReqVO {

    @NotBlank(message = "博客 LOGO 不能为空")
    private String logo;

    @NotBlank(message = "博客名称不能为空")
    private String name;

    @NotBlank(message = "博客作者不能为空")
    private String author;

    @NotBlank(message = "博客介绍语不能为空")
    private String introduction;

    @NotBlank(message = "博客头像不能为空")
    private String avatar;

    private String githubHomepage;

    private String csdnHomepage;

    private String giteeHomepage;

    private String zhihuHomepage;

    @Email(message = "邮箱格式不正确")
    private String mail;

    @NotNull(message = "请设置评论敏感词过滤是否开启")
    private Boolean isCommentSensiWordOpen;

    @NotNull(message = "请设置评论审核是否开启")
    private Boolean isCommentExamineOpen;
}
