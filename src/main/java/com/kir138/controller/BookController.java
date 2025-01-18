package com.kir138.controller;

import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        log.info("GET /api/v1/books/{} - получение книги по id", id);
        return bookService.getBookById(id);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        log.info("GET /api/v1/books - получение всех книг");
        return bookService.getAllBook();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto saveOrUpdateBook(@RequestBody BookRegistrationRq bookRegistrationRq) {
        log.info("POST /api/v1/books - обработка запроса");
        BookDto bookDto = bookService.saveOrUpdateBook(bookRegistrationRq);
        log.info("Книга успешно обновлена/добавлена с id = {}", bookDto.getId());
        return bookDto;
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        log.info("DELETE /api/v1/books/{} - удаление книги по id", id);
        bookService.deleteBook(id);
    }
}
