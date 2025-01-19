package com.kir138.controller;

import com.kir138.exception.ErrorResponse;
import com.kir138.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handle(ServiceException ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                        .message(ex.getMessage())
                        .code(ex.getErrorCode().getCode())
                        .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
