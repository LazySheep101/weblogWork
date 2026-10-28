package com.lazydaze.weblog.web.service;

import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.comment.FindPageCommentsReqVO;
import com.lazydaze.weblog.web.model.vo.comment.FindQQUserInfoReqVO;
import com.lazydaze.weblog.web.model.vo.comment.PublishCommentReqVO;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 评论
 **/
public interface CommentService {

    /**
     * 根据 QQ 号获取用户信息
     * @param findQQUserInfoReqVO
     * @return
     */
    Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO);

    /**
     * 发布评论
     * @param publishCommentReqVO
     * @return
     */
    Response publishComment(PublishCommentReqVO publishCommentReqVO);

    /**
     * 查询页面所有评论
     * @param findPageCommentsReqVO
     * @return
     */
    Response findPageComments(FindPageCommentsReqVO findPageCommentsReqVO);
}
