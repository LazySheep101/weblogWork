package com.lazydaze.weblog.web.service.impl;

import com.lazydaze.weblog.common.domain.dos.BlogSettingsDO;
import com.lazydaze.weblog.common.domain.mapper.BlogSettingsMapper;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.convert.BlogSettingsConvert;
import com.lazydaze.weblog.web.model.vo.blogsettings.FindBlogSettingsDetailRspVO;
import com.lazydaze.weblog.web.service.BlogSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 博客设置
 **/
@Service
@Slf4j
public class BlogSettingsServiceImpl implements BlogSettingsService {

    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    /**
     * 获取博客设置信息
     *
     * @return
     */
    @Override
    public Response findDetail() {
        // 查询博客设置信息（约定的 ID 为 1）
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        // DO 转 VO
        FindBlogSettingsDetailRspVO vo = BlogSettingsConvert.INSTANCE.convertDO2VO(blogSettingsDO);

        return Response.success(vo);
    }
}
