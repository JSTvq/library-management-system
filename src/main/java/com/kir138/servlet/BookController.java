package com.kir138.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.service.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
public class BookController extends HttpServlet {
    private final BookService bookService;
    private final ObjectMapper objectMapper;

    /**
     * Добавление книги в библиотеку(Book)
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.info("POST /api/v1/books - начало обработки запроса");

        try {
            BookRegistrationRq bookRegistrationRq = parseBookFromRequest(req);
            BookDto bookDto = bookService.saveOrUpdateBook(bookRegistrationRq);

            objectMapper.writeValue(resp.getWriter(), bookDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);

            log.info("POST /api/v1/books - книга успешно добавлена/обновлена " +
                    "с id {}", bookDto.getId());
        } catch (JsonProcessingException e) {
            log.info("POST /api/v1/books - ошибка парсинга JSON", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Некорректный формат JSON");
        }
    }

    private BookRegistrationRq parseBookFromRequest(HttpServletRequest req) {
        try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
            String string = scanner.useDelimiter("\\A").next();
            return objectMapper.readValue(string, BookRegistrationRq.class);
        } catch (IOException e) {
            log.info("Error while parsing request {}", req, e);
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
