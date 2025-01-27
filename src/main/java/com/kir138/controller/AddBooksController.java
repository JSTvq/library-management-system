package com.kir138.controller;

import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/books") // Базовый URL для управления книгами
public class AddBooksController {

    private final BookService bookService;

    @PostMapping("/add") // Обработчик для метода добавления книг
    public ResponseEntity<String> addBooks(@RequestParam int n) {
        log.info("Adding " + n + " books");
        bookService.addBooks(n);

        return ResponseEntity.ok("Successfully added " + n + " books.");
    }
}
