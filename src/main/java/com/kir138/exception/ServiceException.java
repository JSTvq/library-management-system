package com.kir138.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private final String message;
    private final String code;

    public ServiceException(String message, String code) {
        super(message);
        this.message = message;
        this.code = code;
    }
}
