package com.lazydaze.weblog.admin.service;

import com.lazydaze.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.lazydaze.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.lazydaze.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.lazydaze.weblog.admin.model.vo.tag.AddTagReqVO;
import com.lazydaze.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.lazydaze.weblog.common.utils.PageResponse;
import com.lazydaze.weblog.common.utils.Response;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: TODO
 **/
public interface AdminCategoryService {
    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);

    /**
     * 分类分页数据查询
     * @param findCategoryPageListReqVO
     * @return
     */
    PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO);

    /**
     * 删除分类
     * @param deleteCategoryReqVO
     * @return
     */
    Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO);

    /**
     * 获取文章分类的 Select 列表数据
     * @return
     */
    Response findCategorySelectList();

}
