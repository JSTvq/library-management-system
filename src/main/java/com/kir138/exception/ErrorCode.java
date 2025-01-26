package com.kir138.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    READER_NOT_FOUND_BY_ID("READER_NOT_FOUND",
            "Reader not found with id: %d", 404),
    INVALID_REQUEST("INVALID_REQUEST", "Invalid request data", 400),
    BOOK_NOT_FOUND_BY_AUTHOR("BOOK_NOT_FOUND",
            "Book not found with author: %s", 404),
    BOOK_NOT_FOUND_BY_ID("BOOK_NOT_FOUND",
            "Book not found with id: %d", 404),
    BOOK_HAS_NOT_BEEN_RETURNED("BOOK_HAS_NOT_BEEN_RETURNED",
            "The book with id %d was taken by another reader", 404),
    BOOK_RETURNED("BOOK_RETURNED", "The book has already been returned", 400),
    BORROW_REPORT_NOT_FOUND("BORROW_REPORT_NOT_FOUND",
            "Report not found with id: %d", 404);

    private final String code;
    private final String messageTemplate;
    private final int httpStatus;
}
