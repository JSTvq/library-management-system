package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.model.dto.ReaderRegistrationRq;
import com.kir138.service.BookService;
import com.kir138.service.BorrowReportService;
import com.kir138.service.ReaderService;
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
public class ReaderGetBookServlet extends HttpServlet {
    private final BookService bookService;
    private final ReaderService readerService;
    private final BorrowReportService borrowReportService;
    private final ObjectMapper objectMapper;

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String readIdParam = req.getParameter("readId");
            String bookIdParam = req.getParameter("bookId");

            if (readIdParam == null || bookIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("параметры readId и bookId обязательны");
            }

            Long readId = Long.parseLong(readIdParam);
            Long bookId = Long.parseLong(bookIdParam);
        } catch () {

        }
        bookService.borrowBook();
    }*/

    private BookRegistrationRq parseBookFromRequest(HttpServletRequest req) {
        try (Scanner scanner = new Scanner(req.getInputStream(), StandardCharsets.UTF_8)) {
            String string = scanner.useDelimiter("\\A").next();
            return objectMapper.readValue(string, BookRegistrationRq.class);
        } catch (IOException e) {
            log.info("Error while parsing request {}", req, e);
            throw new RuntimeException(e);
        }
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
