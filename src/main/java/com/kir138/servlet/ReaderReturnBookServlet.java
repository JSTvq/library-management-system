package com.kir138.servlet;

import com.kir138.service.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class ReaderReturnBookServlet extends HttpServlet {
    private final BookService bookService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String reportIdParam = req.getParameter("reportId");

            if (reportIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("параметры readerId обязательны");
            } else {
                Long reportId = Long.parseLong(reportIdParam);
                bookService.returnBook(reportId);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write("Книга успешно возвращена читателем");
                resp.getWriter().close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
