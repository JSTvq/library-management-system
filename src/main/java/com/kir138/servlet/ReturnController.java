package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.service.BookService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class ReturnController extends HttpServlet {
    private final BookService bookService;
    private final ObjectMapper objectMapper;

    /**
     * Возврат книги читателем
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String reportIdParam = req.getParameter("reportId");

            if (reportIdParam == null) {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST,
                        "параметры reportId обязательны");
            } else {
                Long reportId = Long.parseLong(reportIdParam);
                bookService.returnBook(reportId);
                resp.setStatus(HttpServletResponse.SC_OK);
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
