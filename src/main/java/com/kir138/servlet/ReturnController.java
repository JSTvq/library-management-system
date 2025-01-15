package com.kir138.servlet;

import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/return")
@Slf4j
public class ReturnController {
    private final BookService bookService;

    /**
     * Возврат книги читателем
     */
    @PostMapping
    public void returnBook(@RequestParam Long reportId) {
        log.info("POST api/v1/return - книга {} возвращена", reportId);
        bookService.returnBook(reportId);
    }

    /*@Override
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
    }*/
}
