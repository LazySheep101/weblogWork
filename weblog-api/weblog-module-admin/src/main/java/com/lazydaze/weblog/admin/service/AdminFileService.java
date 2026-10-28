package com.lazydaze.weblog.admin.service;

import com.lazydaze.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;
import com.lazydaze.weblog.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: TODO
 **/
public interface AdminFileService {
    /**
     * 上传文件
     * @param file
     * @return
     */
    Response uploadFile(MultipartFile file);
}
