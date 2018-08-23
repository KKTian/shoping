package com.study.shoping.base.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * @description: 权限异常
 * @outhor KK
 * @time 2018-08-17 16:27
 */
public class AuthException extends AccessDeniedException {

    public AuthException(String msg, Throwable t) {
        super(msg, t);
    }
}