package com.lazydaze.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: LazyDaze101
 * @url: www.lazydaze101.dev
 * @date: 2026-09-15
 * @description: 验证码校验失败异常
 **/
public class CaptchaVerificationFailedException extends AuthenticationException {
    public CaptchaVerificationFailedException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CaptchaVerificationFailedException(String msg) {
        super(msg);
    }
}