package com.kir138.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.model.entity.Book;
import com.kir138.service.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/v1/books")
public class BookController extends HttpServlet {
    private final BookService bookService;
    private final ObjectMapper objectMapper;

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

    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.OK)
    public void borrowBook(@RequestParam Long readerId, @RequestParam Long bookId) {
        log.info("POST /api/v1/books/borrow - читатель {} берет книгу {}", readerId, bookId);
        bookService.borrowBook(readerId, bookId);
    }

    private void sendErrorResponse(HttpServletResponse resp, int statusCode,
                                   String message) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setStatus(statusCode);
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        objectMapper.writeValue(resp.getWriter(), error);
    }
}
