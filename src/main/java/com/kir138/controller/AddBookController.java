package com.kir138.controller;

import com.kir138.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ad") // Базовый URL для управления книгами
public class AddBookController {

    private final BookService bookService;

    @Autowired
    public AddBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add") // Обработчик для метода добавления книг
    public ResponseEntity<String> addBooks(@RequestParam int n) {
        // Вызываем метод добавления книг в сервисе
        bookService.addBooks(n);

        return ResponseEntity.ok("Successfully added " + n + " books.");
    }
}
