package com.kir138.servlet;

import com.kir138.model.dto.ReaderDto;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.service.ReaderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


//curl GET http://localhost:8080/api/v1/readers/1
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/readers")
@RestController
public class ReaderController {
    private final ReaderService readerService;

    @GetMapping("/{id}")
    public ReaderDto getReaderById(@PathVariable Long id) {
        log.info("GET /api/v1/readers/{} - получение книги по id", id);
       return readerService.getReaderById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReaderDto saveOrUpdateReader(@RequestBody ReaderRegistrationRq readerRegistrationRq) {
        log.info("POST /api/v1/readers - начало обработки запроса");
        ReaderDto readerDto = readerService.saveOrUpdateReader(readerRegistrationRq);
        log.info("POST /api/v1/readers - успешно добавлен/обновлен " +
                "читатель с id {}", readerDto.getId());
        return readerDto;
    }

    @GetMapping
    public List<ReaderDto> getAllReaders() {
        log.info("GET /api/v1/readers - получение всех читателей");
        return readerService.getAllReaders();
    }

    @DeleteMapping("/{id}")
    public void deleteReader(@PathVariable Long id) {
        log.info("DELETE /api/v1/readers/{} - удаление читателя по id", id);
        readerService.deleteReader(id);
    }

    /*@Override
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

    *//**
     * Добавление читателя(Reader)
     *//*
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

    *//**
     * Валидация данных читателя
     *//*
    private void validateReaderRegistrationRq(ReaderRegistrationRq reader) {
        if (reader.getName() == null || reader.getName().trim().isEmpty()) {
            throw new RuntimeException("Имя читателя не может быть пустым");
        }
    }

    *//**
     * Валидация параметра id
     *//*
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
    }*/
}


