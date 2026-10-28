package com.lazydaze.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lazydaze.weblog.common.domain.dos.CommentDO;
import com.lazydaze.weblog.common.enums.CommentStatusEnum;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:06
 * @description: 评论
 **/
public interface CommentMapper extends BaseMapper<CommentDO> {

    /**
     * 根据路由地址查询对应的评论
     * @param routerUrl
     * @return
     */
    default List<CommentDO> selectByRouterUrl(String routerUrl) {
        return selectList(Wrappers.<CommentDO>lambdaQuery()
                .eq(CommentDO::getRouterUrl, routerUrl)
                .eq(CommentDO::getStatus, CommentStatusEnum.NORMAL.getCode()) // 仅查询状态正常的评论
                .orderByDesc(CommentDO::getCreateTime)
        );
    }

    /**
     * 根据 reply_comment_id 查询评论
     * @param replyCommentId
     * @return
     */
    default List<CommentDO> selectByReplyCommentId(Long replyCommentId) {
        return selectList(Wrappers.<CommentDO>lambdaQuery()
                .eq(CommentDO::getReplyCommentId, replyCommentId)
                .orderByDesc(CommentDO::getCreateTime)
        );
    }

    /**
     * 分页查询
     * @param current
     * @param size
     * @param startDate
     * @param endDate
     * @return
     */
    default Page<CommentDO> selectPageList(Long current, Long size, String routerUrl,
                                           LocalDate startDate, LocalDate endDate, Integer status) {
        // 分页对象(查询第几页、每页多少数据)
        Page<CommentDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<CommentDO> wrapper = Wrappers.<CommentDO>lambdaQuery()
                .like(StringUtils.isNotBlank(routerUrl), CommentDO::getRouterUrl, routerUrl) // like 模块查询
                .eq(Objects.nonNull(status), CommentDO::getStatus, status) // 状态
                .ge(Objects.nonNull(startDate), CommentDO::getCreateTime, startDate) // 大于等于 startDate
                .le(Objects.nonNull(endDate), CommentDO::getCreateTime, endDate)  // 小于等于 endDate
                .orderByDesc(CommentDO::getCreateTime); // 按创建时间倒叙

        return selectPage(page, wrapper);
    }

    /**
     * 根据 parent_comment_id 删除
     * @param id
     * @return
     */
    default int deleteByParentCommentId(Long id) {
        return delete(Wrappers.<CommentDO>lambdaQuery()
                .eq(CommentDO::getParentCommentId, id));
    }

    /**
     * 根据 reply_comment_id 删除
     * @param id
     * @return
     */
    default int deleteByReplyCommentId(Long id) {
        return delete(Wrappers.<CommentDO>lambdaQuery()
                .eq(CommentDO::getReplyCommentId, id));
    }

}
