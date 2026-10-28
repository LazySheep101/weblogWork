package com.lazydaze.weblog.common.event.subscriber;

import com.lazydaze.weblog.common.domain.dos.BlogSettingsDO;
import com.lazydaze.weblog.common.domain.dos.CommentDO;
import com.lazydaze.weblog.common.domain.mapper.BlogSettingsMapper;
import com.lazydaze.weblog.common.domain.mapper.CommentMapper;
import com.lazydaze.weblog.common.email.MailService;
import com.lazydaze.weblog.common.enums.CommentStatusEnum;
import com.lazydaze.weblog.common.event.PublishCommentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2023/11/9 10:08
 * @description: TODO
 **/
@Component
@Slf4j
public class PublishCommentSubscriber implements ApplicationListener<PublishCommentEvent> {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private BlogSettingsMapper blogSettingsMapper;
    @Autowired
    private MailService mailService;

    @Override
    @Async("threadPoolTaskExecutor")
    public void onApplicationEvent(PublishCommentEvent event) {
        // 在这里处理收到的事件，可以是任何逻辑操作
        Long commentId = event.getCommentId();

        // 获取当前线程名称
        String threadName = Thread.currentThread().getName();

        log.info("==> threadName: {}", threadName);
        log.info("==> 评论发布事件消费成功，commentId: {}", commentId);

        CommentDO commentDO = commentMapper.selectById(commentId);
        Long replyCommentId = commentDO.getReplyCommentId();
        String nickname = commentDO.getNickname();
        String content = commentDO.getContent();
        Integer status = commentDO.getStatus();

        BlogSettingsDO blogSettingsDO = blogSettingsMapper.selectById(1L);
        String blogName = blogSettingsDO.getName();
        String domain = "http://116.62.199.48/#";

        // 二级评论，回复类型
        if (Objects.nonNull(replyCommentId)
                && Objects.equals(status, CommentStatusEnum.NORMAL.getCode())) {
            CommentDO replyCommentDO = commentMapper.selectById(replyCommentId);

            String to = replyCommentDO.getMail();

            String routerUrl = replyCommentDO.getRouterUrl();
            String title = String.format("你在%s的评论收到了回复", blogName);

            String html = String.format("<html><body>" +
                            "<h2>你的评论:</h2><p>%s</p>" +
                            "<h2>%s 回复了你:</h2><p>%s</p>" +
                            "<p><a href='%s%s' target='_blank'>查看详情</a></p>" +
                            "</body></html>",
                    replyCommentDO.getContent(), nickname, content, domain, routerUrl);
            mailService.sendWithHtml(to, title, html);
        } else if (Objects.isNull(replyCommentId)) { // 需要直接回复的类型：一级评论, 需要通知到博主
            String to = blogSettingsDO.getMail();
            String routerUrl = commentDO.getRouterUrl();
            String title = String.format("%s收到了评论", blogName);

            String html = String.format("<html><body>" +
                            "<h2>路由:</h2><p>%s</p>" +
                            "<h2>%s 评论了你:</h2><p>%s</p>" +
                            "<p><a href='%s%s' target='_blank'>查看详情</a></p>" +
                            "</body></html>",
                    routerUrl, nickname, commentDO.getContent(), domain, routerUrl);
            mailService.sendWithHtml(to, title, html);
        }

    }
}