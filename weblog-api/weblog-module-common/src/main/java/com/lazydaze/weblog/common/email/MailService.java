package com.lazydaze.weblog.common.email;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15 14:03
 * @description: 邮件
 **/
public interface MailService {

    /**
     * 发送 html 的邮件
     * @param to
     * @param title
     * @param html
     * @return
     */
    boolean sendWithHtml(String to, String title, String html);
}
