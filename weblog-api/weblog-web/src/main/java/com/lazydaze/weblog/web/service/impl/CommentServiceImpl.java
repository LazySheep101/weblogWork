package com.lazydaze.weblog.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.houbb.sensitive.word.core.SensitiveWordHelper;
import com.lazydaze.weblog.common.domain.dos.BlogSettingsDO;
import com.lazydaze.weblog.common.domain.dos.CommentDO;
import com.lazydaze.weblog.common.domain.mapper.BlogSettingsMapper;
import com.lazydaze.weblog.common.domain.mapper.CommentMapper;
import com.lazydaze.weblog.common.enums.CommentStatusEnum;
import com.lazydaze.weblog.common.enums.ResponseCodeEnum;
import com.lazydaze.weblog.common.event.PublishCommentEvent;
import com.lazydaze.weblog.common.exception.BizException;
import com.lazydaze.weblog.common.utils.Response;
import com.lazydaze.weblog.web.convert.CommentConvert;
import com.lazydaze.weblog.web.model.vo.comment.*;
import com.lazydaze.weblog.web.service.CommentService;
import com.lazydaze.weblog.web.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 知识库
 **/
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    // @Autowired
    // private IllegalWordsSearch wordsSearch;
    @Autowired
    private BlogSettingsMapper blogSettingsMapper;

    @Value("${api-key}")
    private String apiKey;

    @Override
    public Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO) {
        String qq = findQQUserInfoReqVO.getQq();

        // 校验 QQ 号
        if (!StringUtil.isPureNumber(qq)) {
            log.warn("昵称输入的格式不是 QQ 号: {}", qq);
            throw new BizException(ResponseCodeEnum.NOT_QQ_NUMBER);
        }

        // 请求第三方接口
        String url = String.format("http://api.guiguiya.com/api/qq_info?qq=%s&apiKey=%s", qq, apiKey);
        String result = restTemplate.getForObject(url, String.class);

        log.info("已切换到鬼鬼 API，通过 QQ 号获取用户信息: {}", result);

        // 解析响参
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(result, Map.class);
            if (Objects.equals(map.get("code"), HttpStatus.OK.value())) {

                // 获取响应参数中 data 节点下的数据
                Map<String, Object> data = (Map<String, Object>) map.get("data");

                if (!CollectionUtils.isEmpty(data)) {
                    // 获取用户头像、昵称、邮箱
                    return Response.success(FindQQUserInfoRspVO.builder()
                            .avatar(String.valueOf(data.get("avatar_apiurl_1")))
                            .nickname(String.valueOf(data.get("name")))
                            .mail(qq.trim() + "@qq.com") // 组合邮箱地址
                            .build());
                }
            }

            return Response.fail();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Response publishComment(PublishCommentReqVO publishCommentReqVO) {
        Long replyCommentId = publishCommentReqVO.getReplyCommentId();
        String content = publishCommentReqVO.getContent();
        String nickname = publishCommentReqVO.getNickname();

        // 查询博客设置信息（约定的 ID 为 1）
        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        boolean isCommentSensiWordOpen = blogSettingsDO.getIsCommentSensiWordOpen();
        boolean isCommentExamineOpen = blogSettingsDO.getIsCommentExamineOpen();

        // 默认未待审核
        Integer status = CommentStatusEnum.NORMAL.getCode();
        String reason = "";

        // 如果开启了审核, 设置未待审核状态
        if (isCommentExamineOpen) {
            status = CommentStatusEnum.WAIT_EXAMINE.getCode();
        }

        boolean isContainSensitiveWord = false;
        if (isCommentSensiWordOpen) {
            // 校验评论中是否包含敏感词
            // isContainSensitiveWord = wordsSearch.ContainsAny(content);
            isContainSensitiveWord = SensitiveWordHelper.contains(content);

            if (isContainSensitiveWord) {
                // 审核失败
                status = CommentStatusEnum.EXAMINE_FAILED.getCode();
                // List<IllegalWordsSearchResult> results = wordsSearch.FindAll(content);
                // List<String> keywords = results.stream().map(result -> result.Keyword).collect(Collectors.toList());

                List<String> keywords = SensitiveWordHelper.findAll(content);
                reason = String.format("系统拦截，包含敏感词：%s", keywords);
                log.warn("此评论内容中包含敏感词: {}, content: {}", keywords, content);
            }
        }

        CommentDO commentDO = CommentDO.builder()
                .avatar(publishCommentReqVO.getAvatar())
                .content(content)
                .mail(publishCommentReqVO.getMail())
                .createTime(LocalDateTime.now())
                .nickname(nickname)
                .routerUrl(publishCommentReqVO.getRouterUrl())
                .website(publishCommentReqVO.getWebsite())
                .replyCommentId(replyCommentId)
                .parentCommentId(publishCommentReqVO.getParentCommentId())
                .status(status)
                .reason(reason)
                .build();

        commentMapper.insert(commentDO);

        Long commentId = commentDO.getId();

        // 发送文章发布事件
        eventPublisher.publishEvent(new PublishCommentEvent(this, commentId));

        if (isContainSensitiveWord)
            throw new BizException(ResponseCodeEnum.COMMENT_CONTAIN_SENSITIVE_WORD);

        if (Objects.equals(status, CommentStatusEnum.WAIT_EXAMINE.getCode()))
            throw new BizException(ResponseCodeEnum.COMMENT_WAIT_EXAMINE);

        return Response.success();
    }

    @Override
    public Response findPageComments(FindPageCommentsReqVO findPageCommentsReqVO) {
        String routerUrl = findPageCommentsReqVO.getRouterUrl();

        List<CommentDO> commentDOS = commentMapper.selectByRouterUrl(routerUrl);
        Integer total = commentDOS.size();

        List<FindPageCommentRspVO> vos = null;
        // DO 转 VO
        if (!CollectionUtils.isEmpty(commentDOS)) {
            // 一级评论
            vos = commentDOS.stream()
                    .filter(commentDO -> Objects.isNull(commentDO.getParentCommentId()))
                    .map(commentDO -> CommentConvert.INSTANCE.convertDO2VO(commentDO))
                    .collect(Collectors.toList());

            // 循环设置评论回复数据
            vos.forEach(vo -> {
                Long commentId = vo.getId();
                List<FindPageCommentRspVO> childComments = commentDOS.stream()
                        .filter(commentDO -> Objects.equals(commentDO.getParentCommentId(), commentId))
                        .sorted(Comparator.comparing(CommentDO::getCreateTime))
                        .map(commentDO -> {
                            FindPageCommentRspVO findPageCommentRspVO = CommentConvert.INSTANCE.convertDO2VO(commentDO);
                            Long replyCommentId = commentDO.getReplyCommentId();
                            // 若二级评论的 replayCommentId 不等于一级评论 ID, 则需要展示【回复 @ xxx】，需要设置回复昵称
                            if (!Objects.equals(replyCommentId, commentId)) {
                                // 设置回复用户的昵称
                                Optional<CommentDO> optionalCommentDO = commentDOS.stream()
                                        .filter(commentDO1 -> Objects.equals(commentDO1.getId(), replyCommentId)).findFirst();
                                if (optionalCommentDO.isPresent()) {
                                    findPageCommentRspVO.setReplyNickname(optionalCommentDO.get().getNickname());
                                }
                            }
                            return findPageCommentRspVO;
                        }).collect(Collectors.toList());

                vo.setChildComments(childComments);
            });
        }

        return Response.success(FindPageCommentsRspVO.builder()
                .total(total)
                .comments(vos)
                .build());
    }
}
