package com.lazydaze.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lazydaze.weblog.admin.convert.CommentConvert;
import com.lazydaze.weblog.admin.model.vo.comment.DeleteCommentReqVO;
import com.lazydaze.weblog.admin.model.vo.comment.ExamineCommentReqVO;
import com.lazydaze.weblog.admin.model.vo.comment.FindCommentPageListReqVO;
import com.lazydaze.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.lazydaze.weblog.admin.service.AdminCommentService;
import com.lazydaze.weblog.common.domain.dos.CommentDO;
import com.lazydaze.weblog.common.domain.mapper.CommentMapper;
import com.lazydaze.weblog.common.enums.CommentStatusEnum;
import com.lazydaze.weblog.common.enums.ResponseCodeEnum;
import com.lazydaze.weblog.common.event.PublishCommentEvent;
import com.lazydaze.weblog.common.exception.BizException;
import com.lazydaze.weblog.common.utils.PageResponse;
import com.lazydaze.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 评论
 **/
@Service
@Slf4j
public class AdminCommentServiceImpl implements AdminCommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 查询评论分页数据
     *
     * @param findCommentPageListReqVO
     * @return
     */
    @Override
    public Response findCommentPageList(FindCommentPageListReqVO findCommentPageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findCommentPageListReqVO.getCurrent();
        Long size = findCommentPageListReqVO.getSize();
        LocalDate startDate = findCommentPageListReqVO.getStartDate();
        LocalDate endDate = findCommentPageListReqVO.getEndDate();
        // 加一天，保证结束日期包含当天
        if (Objects.nonNull(endDate)) {
            endDate = endDate.plusDays(1);
        }
        String routerUrl = findCommentPageListReqVO.getRouterUrl();
        Integer status = findCommentPageListReqVO.getStatus();

        // 执行分页查询
        Page<CommentDO> commentDOPage = commentMapper.selectPageList(current, size, routerUrl, startDate, endDate, status);

        List<CommentDO> commentDOS = commentDOPage.getRecords();

        // DO 转 VO
        List<FindCommentPageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(commentDOS)) {
            vos = commentDOS.stream()
                    .map(commentDO -> CommentConvert.INSTANCE.convertDO2VO(commentDO))
                    .collect(Collectors.toList());
        }

        return PageResponse.success(commentDOPage, vos);
    }

    /**
     * 删除评论
     *
     * @param deleteCommentReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response deleteComment(DeleteCommentReqVO deleteCommentReqVO) {
        Long commentId = deleteCommentReqVO.getId();

        // 查询该评论是一级评论，还是二级评论
        CommentDO commentDO = commentMapper.selectById(commentId);

        if (Objects.isNull(commentDO)) {
            log.warn("该评论存在, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_NOT_FOUND);
        }

        // 删除评论
        commentMapper.deleteById(commentId);

        Long replayCommentId = commentDO.getReplyCommentId();

        // 一级评论
        if (Objects.isNull(replayCommentId)) {
            // 删除子评论
            commentMapper.deleteByParentCommentId(commentId);
        } else { // 二级评论
            // 删除此评论, 以及此评论下的所有回复
            deleteAllChildComment(commentId);
        }

        return Response.success();
    }

    private void deleteAllChildComment(Long commentId) {
        List<CommentDO> childCommentDOS = commentMapper.selectByReplyCommentId(commentId);

        if (CollectionUtils.isEmpty(childCommentDOS))
            return;

        childCommentDOS.forEach(childCommentDO -> {
            Long childCommentId = childCommentDO.getId();

            commentMapper.deleteById(childCommentId);
            // 递归删除
            deleteAllChildComment(childCommentId);
        });

    }

    /**
     * 评论审核
     *
     * @param examineCommentReqVO
     * @return
     */
    @Override
    public Response examine(ExamineCommentReqVO examineCommentReqVO) {
        Long commentId = examineCommentReqVO.getId();
        Integer status = examineCommentReqVO.getStatus();
        String reason = examineCommentReqVO.getReason();

        CommentDO commentDO = commentMapper.selectById(commentId);

        if (Objects.isNull(commentDO)) {
            log.warn("该评论不存在, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_NOT_FOUND);
        }

        Integer currStatus = commentDO.getStatus();

        // 若未处于待审核状态
        if (!Objects.equals(currStatus, CommentStatusEnum.WAIT_EXAMINE.getCode())) {
            log.warn("该评论未处于待审核状态, commentId: {}", commentId);
            throw new BizException(ResponseCodeEnum.COMMENT_STATUS_NOT_WAIT_EXAMINE);
        }

        commentMapper.updateById(CommentDO.builder()
                        .id(commentId)
                        .status(status)
                        .reason(reason)
                        .build());

        // 未通过的评论，不予以邮件通知
        if (Objects.equals(status, CommentStatusEnum.NORMAL.getCode())) {
            // 发送文章发布事件
            eventPublisher.publishEvent(new PublishCommentEvent(this, commentId));
        }

        return Response.success();
    }


}
