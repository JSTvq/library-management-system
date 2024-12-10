package com.kir138.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kir138.model.dto.BorrowReportDto;
import com.kir138.service.ReportService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class BorrowReportServlet extends HttpServlet {
    private final ReportService reportService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {
            String readerIdParam = req.getParameter("readerId");
            String fromDateParam = req.getParameter("fromDate");
            String toDateParam = req.getParameter("toDate");

            if (readerIdParam == null || fromDateParam == null || toDateParam == null) {
                resp.getWriter().write("данные некорректны");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } else {
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
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
