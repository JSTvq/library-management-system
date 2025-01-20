package com.kir138.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    READER_NOT_FOUND_BY_ID("READER_NOT_FOUND",
            "Reader not found with id: %d"),
    INVALID_REQUEST("INVALID_REQUEST", "Invalid request data"),
    BOOK_NOT_FOUND_BY_AUTHOR("BOOK_NOT_FOUND",
            "Book not found with author: %s"),
    BOOK_NOT_FOUND_BY_ID("BOOK_NOT_FOUND",
            "Book not found with id: %d"),
    BOOK_HAS_NOT_BEEN_RETURNED("BOOK_HAS_NOT_BEEN_RETURNED",
            "The book with id %d was taken by another reader"),
    BOOK_RETURNED("BOOK_RETURNED", "The book has already been returned"),
    BORROW_REPORT_NOT_FOUND("BORROW_REPORT_NOT_FOUND",
            "Report not found with id: %d");

    private final String code;
    private final String messageTemplate;
}
