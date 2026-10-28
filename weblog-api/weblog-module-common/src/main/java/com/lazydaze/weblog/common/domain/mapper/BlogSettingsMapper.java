package com.lazydaze.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lazydaze.weblog.common.domain.dos.BlogSettingsDO;
import com.lazydaze.weblog.common.domain.dos.UserDO;

import java.time.LocalDateTime;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:06
 * @description: TODO
 **/
public interface BlogSettingsMapper extends BaseMapper<BlogSettingsDO> {
}
