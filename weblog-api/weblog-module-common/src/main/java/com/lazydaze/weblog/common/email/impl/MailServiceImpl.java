package com.lazydaze.weblog.common.email.impl;

import com.lazydaze.weblog.common.email.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 邮件
 **/
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * 发送 html 的邮件
     *
     * @param to
     * @param title
     * @param html
     * @return
     */
    @Override
    public boolean sendWithHtml(String to, String title, String html) {
        log.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            // 邮件发送来源
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            // 邮件发送目标
            mimeMessageHelper.setTo(to);
            // 设置标题
            mimeMessageHelper.setSubject(title);
            // 设置内容，并设置内容 html 格式为 true
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMessage);
            log.info("## Send the mail with html success ...");
        } catch (Exception e) {
            log.error("Send html mail error: ", e);
            return false;
        }

        return true;
    }
}
