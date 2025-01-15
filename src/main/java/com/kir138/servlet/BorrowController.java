package com.kir138.servlet;

import com.kir138.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/borrow")
@RestController
public class BorrowController {
    private final BookService bookService;

    /**
     * Взятие книги читателем
     */
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void borrowBook(@RequestParam Long reader, @RequestParam Long book) {
        log.info("POST /api/v1/borrow - читатель {} берет книгу {}", reader, book);
        bookService.borrowBook(reader, book);
    }

    /*@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        log.info("POST /api/v1/readerGetBook - начало обработки запроса");

        try {
            String readIdParam = req.getParameter("readId");
            String bookIdParam = req.getParameter("bookId");

            if (readIdParam == null || bookIdParam == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                        "параметры readId и bookId обязательны");
            } else {
                Long readId = Long.parseLong(readIdParam);
                Long bookId = Long.parseLong(bookIdParam);
                bookService.borrowBook(readId, bookId); //по хорошему поменять метод borrowBook
                // чтобы возвращал тип данных а не void и записать результат в object mapper

                resp.setStatus(HttpServletResponse.SC_OK);
                log.info("POST /api/v1/readerGetBook - читатель " + readId + " взял книгу " + bookId);
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
