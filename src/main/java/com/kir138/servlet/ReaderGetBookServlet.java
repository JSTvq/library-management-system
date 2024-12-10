package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.service.BookService;
import com.kir138.service.BorrowReportService;
import com.kir138.service.ReaderService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ReaderGetBookServlet extends HttpServlet {
    private final BookService bookService;
    private final ReaderService readerService;
    private final BorrowReportService borrowReportService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String readIdParam = req.getParameter("readId");
            String bookIdParam = req.getParameter("bookId");

            if (readIdParam == null || bookIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("параметры readId и bookId обязательны");
            } else {
                Long readId = Long.parseLong(readIdParam);
                Long bookId = Long.parseLong(bookIdParam);
                bookService.borrowBook(readId, bookId);

                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Книга успешно взята читателем");
                resp.getWriter().close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
