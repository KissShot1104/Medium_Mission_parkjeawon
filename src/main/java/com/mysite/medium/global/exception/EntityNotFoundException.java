package com.mysite.medium.global.exception;

public class EntityNotFoundException extends BusinessException{
    public EntityNotFoundException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
