package com.lazydaze.weblog.web.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-02-09
 * @description: 时间格式化工具类
 **/
public class DateTimeFormatUtil {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private static final DateTimeFormatter YESTERDAY_TIME_FORMATTER = DateTimeFormatter.ofPattern("昨天 HH:mm:ss");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm:ss");

    /**
     * 格式化聊天消息时间
     *
     * 今天的消息：显示时分秒（如 14:30:25）
     * 昨天的消息：显示"昨天 HH:mm:ss"（如 昨天 14:30:25）
     * 更早的消息：显示"yy/MM/dd HH:mm:ss"（如 24/02/09 14:30:25）
     *
     */
    public static String formatChatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }

        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(dateTime.toLocalDate(), now.toLocalDate());

        if (daysBetween == 0) {
            return dateTime.format(TIME_FORMATTER);
        } else if (daysBetween == 1) {
            return dateTime.format(YESTERDAY_TIME_FORMATTER);
        } else {
            return dateTime.format(DATE_TIME_FORMATTER);
        }
    }

    /**
     * 格式化时间为 yy/MM/dd HH:mm:ss
     */
    public static String formatFullDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * 格式化时间（仅时分秒）
     */
    public static String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return "";
        }
        return dateTime.format(TIME_FORMATTER);
    }

    /**
     * 判断是否是今天
     */
    public static boolean isToday(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return dateTime.toLocalDate().equals(now.toLocalDate());
    }

    /**
     * 判断是否是昨天
     */
    public static boolean isYesterday(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(dateTime.toLocalDate(), now.toLocalDate());
        return daysBetween == 1;
    }
}
