package com.lazydaze.weblog.web.model.vo.notice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:07
 * @description: 获取公告信息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindNoticeInfoRspVO {

    private String content;

}
