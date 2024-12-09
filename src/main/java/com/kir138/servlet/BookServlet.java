package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.service.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@RequiredArgsConstructor
@Slf4j
public class BookServlet extends HttpServlet {
    private final BookService bookService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) {

        long id = Long.parseLong(req.getParameter("id"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        BookRegistrationRq bookRegistrationRq = parseBookFromRequest(req);
        BookDto bookDto = bookService.saveOrUpdateBook(bookRegistrationRq);
        try {
            objectMapper.writeValue(resp.getWriter(), bookDto);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req,
                          HttpServletResponse resp) {

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

}
