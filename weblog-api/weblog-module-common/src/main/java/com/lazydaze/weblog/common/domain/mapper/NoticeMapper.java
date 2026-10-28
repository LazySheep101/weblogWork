package com.lazydaze.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lazydaze.weblog.common.domain.dos.NoticeDO;

import java.time.LocalDate;
import java.util.Objects;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:06
 * @description: TODO
 **/
public interface NoticeMapper extends BaseMapper<NoticeDO> {

    default NoticeDO selectShow() {
        // 业务约定同时只展示一条；加 LIMIT 1 避免脏数据触发 TooManyResultsException
        return selectOne(Wrappers.<NoticeDO>lambdaQuery()
                .eq(NoticeDO::getIsShow, 1)
                .orderByDesc(NoticeDO::getUpdateTime)
                .last("LIMIT 1"));
    }

    default int updateAllNotShow() {
        return update(null,
                Wrappers.<NoticeDO>lambdaUpdate().set(NoticeDO::getIsShow, Boolean.FALSE));
    }

    /**
     * 查询分类分页数据
     * @return
     */
    default Page<NoticeDO> selectPageList(long current, long size, String content, LocalDate startDate, LocalDate endDate, Boolean isShow) {
        // 分页对象(查询第几页、每页多少数据)
        Page<NoticeDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<NoticeDO> wrapper = new LambdaQueryWrapper<>();

        wrapper
                .like(StringUtils.isNotBlank(content), NoticeDO::getContent, content.trim()) // like 模块查询
                .ge(Objects.nonNull(startDate), NoticeDO::getCreateTime, startDate) // 大于等于 startDate
                .le(Objects.nonNull(endDate), NoticeDO::getCreateTime, endDate)  // 小于等于 endDate
                .eq(Objects.nonNull(isShow), NoticeDO::getIsShow, isShow)
                .orderByDesc(NoticeDO::getIsShow)
                .orderByDesc(NoticeDO::getCreateTime); // 按创建时间倒叙

        return selectPage(page, wrapper);
    }
}
