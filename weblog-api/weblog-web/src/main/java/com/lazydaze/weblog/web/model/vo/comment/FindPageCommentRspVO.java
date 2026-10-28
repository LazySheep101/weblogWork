package com.lazydaze.weblog.web.model.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 发布评论
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPageCommentRspVO {

    private Long id;

    private String avatar;

    private String nickname;

    private String website;

    private String content;

    private LocalDateTime createTime;

    private String replyNickname;

    private List<FindPageCommentRspVO> childComments;

    private Boolean isShowReplyForm;

}
