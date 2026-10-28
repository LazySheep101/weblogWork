package com.lazydaze.weblog.admin.service;

import com.lazydaze.weblog.admin.model.vo.notice.*;
import com.lazydaze.weblog.common.utils.Response;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 公告
 **/
public interface AdminNoticeService {
    /**
     * 添加公告
     * @param addNoticeReqVO
     * @return
     */
    Response addNotice(AddNoticeReqVO addNoticeReqVO);

    /**
     * 删除公告
     * @param deleteNoticeReqVO
     * @return
     */
    Response deleteNotice(DeleteNoticeReqVO deleteNoticeReqVO);

    Response findNoticePageList(FindNoticePageListReqVO findNoticePageListReqVO);

    Response updateNoticeIsShow(UpdateNoticeIsShowReqVO updateNoticeIsShowReqVO);

    Response updateNotice(UpdateNoticeReqVO updateNoticeReqVO);
}
