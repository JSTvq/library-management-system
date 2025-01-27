/*package com.kir138.controller;

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
}*/

