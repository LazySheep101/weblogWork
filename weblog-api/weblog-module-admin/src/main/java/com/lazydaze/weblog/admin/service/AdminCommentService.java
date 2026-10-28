package com.lazydaze.weblog.admin.service;

import com.lazydaze.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.lazydaze.weblog.admin.model.vo.comment.ExamineCommentReqVO;
import com.lazydaze.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.lazydaze.weblog.common.utils.Response;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 评论
 **/
public interface AdminCommentService {
    /**
     * 查询评论分页数据
     * @param findCommentPageListReqVO
     * @return
     */
    Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO);

    /**
     * 删除评论
     * @param deleteCommentReqVO
     * @return
     */
    Response deleteComment(DeleteCommentReqVO deleteCommentReqVO);

    /**
     * 评论审核通过
     * @param examineCommentReqVO
     * @return
     */
    Response examine(ExamineCommentReqVO examineCommentReqVO);
}
