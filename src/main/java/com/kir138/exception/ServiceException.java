package com.kir138.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final ErrorCode errorCode;

    public ServiceException(ErrorCode errorCode, Object... args) {
        super(String.format(errorCode.getMessageTemplate(), args));
        this.errorCode = errorCode;
    }
}
