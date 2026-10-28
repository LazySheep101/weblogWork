package com.lazydaze.weblog.admin.convert;

import com.lazydaze.weblog.admin.model.vo.comment.FindCommentPageListRspVO;
import com.lazydaze.weblog.common.domain.dos.CommentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2023/10/8 14:57
 * @description: 文章详情转换
 **/
@Mapper
public interface CommentConvert {
    /**
     * 初始化 convert 实例
     */
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    /**
     * 将 DO 转化为 VO
     * @param bean
     * @return
     */
    FindCommentPageListRspVO convertDO2VO(CommentDO bean);

}
