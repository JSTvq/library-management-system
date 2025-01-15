package com.kir138.servlet;

import com.kir138.model.dto.BorrowReportDto;
import com.kir138.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/borrowReport")
@RestController
public class BorrowReportController {
    private final ReportService reportService;

    @GetMapping
    public List<BorrowReportDto> findByReaderIdAndBorrowDateBetween(@RequestParam Long readerId, @RequestParam LocalDate fromDate,
                                                                    @RequestParam LocalDate toDate) {
        log.info("GET /api/v1/borrowReport - начало обработки запроса");
        List<BorrowReportDto> borrowReportDtoList = reportService.findByReaderIdAndBorrowDateBetween(readerId, fromDate, toDate);
        log.info("GET /api/v1/borrowReport - отчет за дату с " + fromDate
                + " до" + toDate + " за читателя " + readerId + " получен");
        return borrowReportDtoList;
    }

    /**
     * Отчет о возвращенных книгах за определенный период. Кол-во, автор, читатель
     */
    /*@Override
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
    }*/
    }

