package com.kir138.controller;

import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/return")
@Slf4j
public class ReturnController {
    private final BookService bookService;

    /**
     * Возврат книги читателем
     */
    @PostMapping
    public void returnBook(@RequestParam Long reportId) {
        log.info("POST api/v1/return - книга {} возвращена", reportId);
        bookService.returnBook(reportId);
    }
}
