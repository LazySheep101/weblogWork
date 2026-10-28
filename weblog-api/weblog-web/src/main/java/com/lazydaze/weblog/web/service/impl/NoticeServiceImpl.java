package com.lazydaze.weblog.web.service.impl;

import com.lazydaze.weblog.common.domain.dos.NoticeDO;
import com.lazydaze.weblog.common.domain.mapper.NoticeMapper;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.model.vo.notice.FindNoticeInfoRspVO;
import com.lazydaze.weblog.web.service.NoticeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 评论
 **/
@Service
@Slf4j
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public Response findNoticeInfo() {
        NoticeDO noticeDO = noticeMapper.selectShow();

        if (Objects.nonNull(noticeDO)) {
            return Response.success(FindNoticeInfoRspVO.builder()
                    .content(noticeDO.getContent())
                    .build());
        }

        return Response.success();
    }
}
