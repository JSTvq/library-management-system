package com.kir138.controller;

import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/borrow")
@RestController
public class BorrowController {
    private final BookService bookService;

    /**
     * Взятие книги читателем
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void borrowBook(@RequestParam Long reader, @RequestParam Long book) {
        log.info("POST /api/v1/borrow - читатель {} берет книгу {}", reader, book);
        bookService.borrowBook(reader, book);
    }
}
