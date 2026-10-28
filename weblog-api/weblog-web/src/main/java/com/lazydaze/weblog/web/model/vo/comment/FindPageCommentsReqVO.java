package com.lazydaze.weblog.web.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 获取页面评论
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPageCommentsReqVO {

    @NotBlank(message = "路由地址不能为空")
    private String routerUrl;


}
