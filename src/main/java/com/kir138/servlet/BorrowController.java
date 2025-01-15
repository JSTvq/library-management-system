package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.entity.BorrowReport;
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
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BorrowController extends HttpServlet {
    private final BookService bookService;
    private final ObjectMapper objectMapper;

    /*@GetMapping({"/{id"})
    public BorrowReport getById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }*/

    @PostMapping("/borrow")
    @ResponseStatus(HttpStatus.OK)
    public void borrowBook(@RequestParam Long readerId, @RequestParam Long bookId) {
        log.info("POST /api/v1/books/borrow - читатель {} берет книгу {}", readerId, bookId);
        bookService.borrowBook(readerId, bookId);
    }

    /**
     * Взятие книги читателем
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        log.info("POST /api/v1/readerGetBook - начало обработки запроса");

        try {
            String readIdParam = req.getParameter("readId");
            String bookIdParam = req.getParameter("bookId");

            if (readIdParam == null || bookIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        "параметры readId и bookId обязательны");
            } else {
                Long readId = Long.parseLong(readIdParam);
                Long bookId = Long.parseLong(bookIdParam);
                bookService.borrowBook(readId, bookId); //по хорошему поменять метод borrowBook
                // чтобы возвращал тип данных а не void и записать результат в object mapper

                resp.setStatus(HttpServletResponse.SC_OK);
                log.info("POST /api/v1/readerGetBook - читатель " + readId + " взял книгу " + bookId);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
