package com.kir138.controller;

import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1") // Базовый URL для управления книгами
public class AddBooksController {

    private final BookService bookService;

    @PostMapping("/addBook") // Обработчик для метода добавления книг
    public ResponseEntity<String> addBooks(@RequestParam int n) {
        long startTime = System.currentTimeMillis();
        log.info("Adding " + n + " books");
        bookService.addBooks(n);

        long endTime = System.currentTimeMillis(); // Окончание отсчёта времени
        long duration = endTime - startTime;
        return ResponseEntity.ok("Успешно добавлено " + n + " книг за " + duration + " мс.");
    }
}
