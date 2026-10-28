package com.lazydaze.weblog.web.controller;


import cloud.tianai.captcha.application.ImageCaptchaApplication;
import cloud.tianai.captcha.application.vo.ImageCaptchaVO;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.generator.common.model.dto.GenerateParam;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.lazydaze.weblog.common.aspect.ApiOperationLog;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:01
 * @description: 行为验证码
 **/
@RestController
@RequestMapping("/captcha")
@Tag(name = "行为验证码")
public class CaptchaController {

    @Autowired
    private ImageCaptchaApplication imageCaptchaApplication;

    /**
     * 生成行为验证码接口
     *
     * @param request
     * @param type 验证码类型（SLIDER-滑块、ROTATE-旋转、CONCAT-滑动还原、WORD_IMAGE_CLICK-文字点选、RANDOM-随机），可选参数
     * @return 验证码数据（包含背景图、模板图、验证码 ID 等）
     */
    @PostMapping("/gen")
    @Operation(summary = "生成行为验证码")
    @ApiOperationLog(description = "生成行为验证码")
    public ApiResponse<ImageCaptchaVO> genCaptcha(HttpServletRequest request, @RequestParam(value = "type", required = false)String type) {
        // 如果前端没有传递 type 参数，则默认使用滑块验证码类型
        if (StringUtils.isBlank(type)) {
            type = CaptchaTypeConstant.SLIDER;
        }

        // 如果前端传递的是 RANDOM，则随机生成一种验证码类型
        if ("RANDOM".equals(type)) {
            // 生成 0-3 的随机整数（包含0，不包含4）
            int i = ThreadLocalRandom.current().nextInt(0, 4);

            // 根据随机数选择对应的验证码类型
            if (i == 0) {
                // 0 -> 滑块验证码
                type = CaptchaTypeConstant.SLIDER;
            } else if (i == 1) {
                // 1 -> 滑动还原验证码
                type = CaptchaTypeConstant.CONCAT;
            } else if (i == 2) {
                // 2 -> 旋转验证码
                type = CaptchaTypeConstant.ROTATE;
            } else {
                // 文字点选验证码
                type = CaptchaTypeConstant.WORD_IMAGE_CLICK;
            }
        }

        // 构建验证码生成参数对象
        GenerateParam generateParam = new GenerateParam();
        // 设置要生成的验证码类型
        generateParam.setType(type);

        // 生成验证码，返回验证码数据（包含背景图、模板图、验证码 ID 等）
        ApiResponse<ImageCaptchaVO> response = imageCaptchaApplication.generateCaptcha(generateParam);
        return response;
    }

    /**
     * 校验行为验证码接口
     *
     * @param data 包含验证码 ID 和用户滑动轨迹数据
     * @return 校验结果
     */
    @PostMapping("/check")
    @Operation(summary = "校验行为验证码")
    @ApiOperationLog(description = "校验行为验证码")
    public ApiResponse<?> checkCaptcha(@RequestBody CaptchaData data) {
        // 验证码校验，内部会校验滑块是否对齐了，以及滑动轨迹是否是真人
        ApiResponse<?> response = imageCaptchaApplication.matching(data.getId(), data.getData());

        // 校验成功，返回验证码ID
        if (response.isSuccess()) {
            return ApiResponse.ofSuccess(Collections.singletonMap("id", data.getId()));
        }

        // 校验失败，返回错误响应
        return response;
    }

    /**
     * 验证码校验请求数据
     */
    @Data
    public static class CaptchaData {
        private String id;
        private ImageCaptchaTrack data;
    }

}
