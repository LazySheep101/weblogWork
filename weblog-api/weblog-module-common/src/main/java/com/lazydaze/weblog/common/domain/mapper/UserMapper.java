package com.lazydaze.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lazydaze.weblog.common.domain.dos.UserDO;

import java.time.LocalDateTime;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-08-22 17:06
 * @description: TODO
 **/
public interface UserMapper extends BaseMapper<UserDO> {
    default UserDO findByUsername(String username) {
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username);
        return selectOne(wrapper);
    }

    default int updatePasswordByUsername(String username, String password) {
         LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
         // 设置要更新的字段
         wrapper.set(UserDO::getPassword, password);
         wrapper.set(UserDO::getUpdateTime, LocalDateTime.now());
         // 更新条件
         wrapper.eq(UserDO::getUsername, username);

         return update(null, wrapper);
    }
}
