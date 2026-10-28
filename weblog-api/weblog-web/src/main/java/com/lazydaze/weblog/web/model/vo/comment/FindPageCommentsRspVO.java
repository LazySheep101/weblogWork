package com.lazydaze.weblog.web.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 查询评论
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPageCommentsRspVO {

    private Integer total;

    private List<FindPageCommentRspVO> comments;

}
