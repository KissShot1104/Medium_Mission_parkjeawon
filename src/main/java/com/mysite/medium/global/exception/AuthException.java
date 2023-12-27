package com.mysite.medium.global.exception;

public class AuthException extends BusinessException{
    public AuthException(final ErrorCode errorCode) {
        super(errorCode);
    }
}