package com.lazydaze.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lazydaze.weblog.admin.model.vo.notice.*;
import com.lazydaze.weblog.admin.service.AdminNoticeService;
import com.lazydaze.weblog.common.domain.dos.NoticeDO;
import com.lazydaze.weblog.common.domain.mapper.NoticeMapper;
import com.lazydaze.weblog.common.utils.PageResponse;
import com.lazydaze.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 公告
 **/
@Service
@Slf4j
public class AdminNoticeServiceImpl implements AdminNoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 添加公告
     *
     * @param addNoticeReqVO
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response addNotice(AddNoticeReqVO addNoticeReqVO) {
        // 是否处于展示状态
        Boolean isShow = addNoticeReqVO.getIsShow();

        if (isShow) {
            // 将其他公告 is_show 全部置为 false
            noticeMapper.updateAllNotShow();
        }

        noticeMapper.insert(NoticeDO.builder()
                        .content(addNoticeReqVO.getContent())
                        .isShow(isShow)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build());

        return Response.success();
    }

    /**
     * 删除公告
     *
     * @param deleteNoticeReqVO
     * @return
     */
    @Override
    public Response deleteNotice(DeleteNoticeReqVO deleteNoticeReqVO) {
        Long id = deleteNoticeReqVO.getId();

        noticeMapper.deleteById(id);

        return Response.success();
    }

    @Override
    public Response findNoticePageList(FindNoticePageListReqVO findNoticePageListReqVO) {
        // 获取当前页、以及每页需要展示的数据数量
        Long current = findNoticePageListReqVO.getCurrent();
        Long size = findNoticePageListReqVO.getSize();
        String content = findNoticePageListReqVO.getContent();
        LocalDate startDate = findNoticePageListReqVO.getStartDate();
        LocalDate endDate = findNoticePageListReqVO.getEndDate();
        // 加一天，保证结束日期包含当天
        if (Objects.nonNull(endDate)) {
            endDate = endDate.plusDays(1);
        }
        Boolean isShow = findNoticePageListReqVO.getIsShow();

        // 执行分页查询
        Page<NoticeDO> noticeDOPage = noticeMapper.selectPageList(current, size, content, startDate, endDate, isShow);

        List<NoticeDO> noticeDOS = noticeDOPage.getRecords();

        // DO 转 VO
        List<FindNoticePageListRspVO> vos = null;
        if (!CollectionUtils.isEmpty(noticeDOS)) {
            vos = noticeDOS.stream()
                    .map(noticeDO -> FindNoticePageListRspVO.builder()
                            .id(noticeDO.getId())
                            .content(noticeDO.getContent())
                            .createTime(noticeDO.getCreateTime())
                            .updateTime(noticeDO.getUpdateTime())
                            .isShow(noticeDO.getIsShow())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(noticeDOPage, vos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response updateNoticeIsShow(UpdateNoticeIsShowReqVO updateNoticeIsShowReqVO) {
        Long id = updateNoticeIsShowReqVO.getId();
        Boolean isShow = updateNoticeIsShowReqVO.getIsShow();

        if (isShow) {
            // 将其他公告 is_show 全部置为 false
            noticeMapper.updateAllNotShow();
        }

        noticeMapper.updateById(NoticeDO.builder()
                        .id(id)
                        .isShow(isShow)
                        .updateTime(LocalDateTime.now())
                        .build());
        return Response.success();
    }

    @Override
    public Response updateNotice(UpdateNoticeReqVO updateNoticeReqVO) {
        Long id = updateNoticeReqVO.getId();

        noticeMapper.updateById(NoticeDO.builder()
                        .id(id)
                        .content(updateNoticeReqVO.getContent())
                        .updateTime(LocalDateTime.now())
                        .build());
        return Response.success();
    }
}
