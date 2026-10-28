package com.lazydaze.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lazydaze.weblog.common.domain.dos.ChatMessageDO;

import java.util.List;
import java.util.Objects;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:06
 * @description: 聊天室消息
 **/
public interface ChatMessageMapper extends BaseMapper<ChatMessageDO> {

    /**
     * 查询最近的消息记录
     * @param limit 条数
     * @return
     */
    default List<ChatMessageDO> selectRecentMessages(int limit) {
        return selectList(Wrappers.<ChatMessageDO>lambdaQuery()
                .orderByDesc(ChatMessageDO::getCreateTime) // 按创建时间倒序
                .last("LIMIT " + limit)); // 添加 limit
    }

    /**
     * 分页查询历史消息（向前翻页）
     * PS: 游标分页，防止深度分页问题
     * @param lastId
     * @param limit
     * @return
     */
    default List<ChatMessageDO> selectMessagesBefore(Long lastId, int limit) {
        return selectList(Wrappers.<ChatMessageDO>lambdaQuery()
                .lt(Objects.nonNull(lastId), ChatMessageDO::getId, lastId) // 过滤出小于 lastId 的记录
                .orderByDesc(ChatMessageDO::getCreateTime) // 按创建时间倒序
                .last("LIMIT " + limit)); // 添加 limit
    }
}
