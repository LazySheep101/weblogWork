package com.lazydaze.weblog.web.convert;

import com.lazydaze.weblog.common.domain.dos.CommentDO;
import com.lazydaze.weblog.web.model.vo.comment.FindPageCommentRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2023/10/8 14:57
 * @description: 评论转换
 **/
@Mapper
public interface CommentConvert {
    /**
     * 初始化 convert 实例
     */
    CommentConvert INSTANCE = Mappers.getMapper(CommentConvert.class);

    /**
     * CommentDO -> FindPageCommentRspVO
     * @param bean
     * @return
     */
    FindPageCommentRspVO convertDO2VO(CommentDO bean);

}
