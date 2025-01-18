package com.kir138.controller;

import com.kir138.exception.ServiceException;
import com.kir138.model.dto.Error;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Error> handle(ServiceException ex) {
        return ResponseEntity.status(400).body(Error.builder()
                        .message(ex.getMessage())
                        .code(ex.getMessage())
                        .build());
    }
}
