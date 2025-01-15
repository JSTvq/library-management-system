package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BorrowReportDto;
import com.kir138.service.ReportService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BorrowReportController extends HttpServlet {
    private final ReportService reportService;
    private final ObjectMapper objectMapper;



    /**
     * Отчет о возвращенных книгах за определенный период. Кол-во, автор, читатель
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        log.info("POST /api/v1/borrowReport - начало обработки запроса");

        String readerIdParam = req.getParameter("readerId");
        String fromDateParam = req.getParameter("fromDate");
        String toDateParam = req.getParameter("toDate");

        if (readerIdParam == null || fromDateParam == null || toDateParam == null) {
            sendErrorResponse(resp, HttpServletResponse.SC_NOT_FOUND,
                    "Параметр id обязателен и должен быть числом");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                long readerId = Long.parseLong(readerIdParam);
                LocalDate fromDate = LocalDate.parse(fromDateParam);
                LocalDate toDate = LocalDate.parse(toDateParam);

                List<BorrowReportDto> borrowReportDto = reportService
                        .findByReaderIdAndBorrowDateBetween(readerId, fromDate, toDate);
                int count = borrowReportDto.size();

                Map<String, Object> responseMap = new HashMap<>();
                responseMap.put("readerId", readerId);
                responseMap.put("fromDate", fromDate);
                responseMap.put("toDate", toDate);
                responseMap.put("booksBorrowed", count);
                responseMap.put("borrowDetails", borrowReportDto);

                resp.setContentType("application/json");
                resp.setStatus(HttpServletResponse.SC_OK);
                objectMapper.writeValue(resp.getWriter(), responseMap);

                log.info("POST /api/v1/borrowReport - отчет за дату с " + fromDate
                        + " до" + toDate + " за читателя " + readerId + " получен");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

        private void sendErrorResponse (HttpServletResponse resp,int statusCode,
        String message) throws IOException {
            resp.setContentType("application/json");
            resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
            resp.setStatus(statusCode);
            Map<String, String> error = new HashMap<>();
            error.put("error", message);
            objectMapper.writeValue(resp.getWriter(), error);
        }
    }

