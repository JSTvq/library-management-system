package com.kir138.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    READER_NOT_FOUND("READER_NOT_FOUND", "Reader not found with id: %d"),
    INVALID_REQUEST("INVALID_REQUEST", "Invalid request data");

    private final String code;
    private final String messageTemplate;
}
