package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.ReaderDto;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.model.entity.Reader;
import com.kir138.service.ReaderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//curl GET http://localhost:8080/api/v1/reader/312
@Slf4j
public class ReaderServlet extends HttpServlet {
    private final ReaderService readerService;
    private final ObjectMapper objectMapper;

    public ReaderServlet(ReaderService readerService, ObjectMapper objectMapper) {
        this.readerService = readerService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        try {
            long id = Long.parseLong(req.getParameter("id"));
            ReaderDto readerDto = readerService.getReaderById(id);
            resp.setContentType("application/json");
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), readerDto);

        } catch (Exception e) {
            resp.setContentType("text/plain");
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("пользователь не найден, ошибка 9999999");
            resp.getWriter().close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {

        log.info("POST /api/v1/reader {}", req);

        ReaderRegistrationRq reader = parseReaderFromRequest(req);
        ReaderDto readerDto = readerService.saveOrUpdateReader(reader);
        objectMapper.writeValue(resp.getWriter(), readerDto);
        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().close();

        log.info("POST /api/v1/reader {}", resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                            HttpServletResponse resp) throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Параметр id не указан");
            return;
        } else {
            long id = Long.parseLong(idParam);
            readerService.deleteReader(id);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        resp.getWriter().close();
    }

    private ReaderRegistrationRq parseReaderFromRequest(HttpServletRequest req) {
        try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
            String string = scanner.useDelimiter("\\A").next();
            return objectMapper.readValue(string, ReaderRegistrationRq.class);
        } catch (IOException e) {
            log.info("Error while parsing request {}", req, e);
            throw new RuntimeException(e);
        }
    }
}