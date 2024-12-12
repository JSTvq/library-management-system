package com.kir138.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.ReaderDto;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.service.ReaderService;
import jakarta.servlet.ServletException;
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

//curl GET http://localhost:8080/api/v1/reader/312
@RequiredArgsConstructor
@Slf4j
public class ReaderController extends HttpServlet {
    private final ReaderService readerService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (!isValidIdParam(idParam)) {
            log.warn("GET /api/v1/reader - некорректный или отсутствующий параметр id: {}", idParam);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Параметр id обязателен и должен быть числом");
        }

        long id = Long.parseLong(idParam);

        try {
            ReaderDto readerDto = readerService.getReaderById(id);
            resp.setContentType("application/json");
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), readerDto);

            log.info("GET /api/v1/reader - успешно получен читатель с id {}", id);
        } catch (RuntimeException e) {
            log.info("GET /api/v1/reader - читатель с id {} не найден", id);
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    "Читатель с id " + id + " не найден");
        }
    }

    /**
     * Добавление читателя(Reader)
     */
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws IOException {

        log.info("POST /api/v1/reader - начало обработки запроса");

        try {
            ReaderRegistrationRq reader = parseReaderFromRequest(req);
            ReaderDto readerDto = readerService.saveOrUpdateReader(reader);

            objectMapper.writeValue(resp.getWriter(), readerDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);

            log.info("POST /api/v1/reader - успешно добавлен/обновлен " +
                    "читатель с id {}", readerDto.getId());
        } catch (JsonProcessingException e) {
            log.info("POST /api/v1/reader - ошибка парсинга JSON", e);
            sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                    "Некорректный формат JSON");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp) throws IOException {


        String idParam = req.getParameter("id");
        if (!isValidIdParam(idParam)) {
            log.warn("DELETE /api/v1/reader - некорректный или отсутствующий " +
                    "параметр id: {}", idParam);
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    "Параметр id обязателен и должен быть числом");
        }

        Long id = Long.parseLong(idParam);

        try {
            readerService.deleteReader(id);
            resp.setStatus(HttpServletResponse.SC_OK);

            log.info("DELETE /api/v1/reader - успешно удален читатель с id {}", id);
        } catch (RuntimeException e) {
            log.warn("DELETE /api/v1/reader - читатель с id {} не найден", id);
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    "Читатель с id " + id + " не найден");
        }
    }

    private ReaderRegistrationRq parseReaderFromRequest(HttpServletRequest req) {
        try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
            String body = scanner.useDelimiter("\\A").next();
            ReaderRegistrationRq reader = objectMapper.readValue(body, ReaderRegistrationRq.class);
            validateReaderRegistrationRq(reader);
            return reader;
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

    /**
     * Валидация данных читателя
     */
    private void validateReaderRegistrationRq(ReaderRegistrationRq reader) {
        if (reader.getName() == null || reader.getName().trim().isEmpty()) {
            throw new RuntimeException("Имя читателя не может быть пустым");
        }
    }

    /**
     * Валидация параметра id
     */
    private boolean isValidIdParam(String idParam) {
        if (idParam == null) {
            return false;
        }
        try {
            Long.parseLong(idParam);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}


