package com.kir138.mapper;

import com.kir138.model.entity.BorrowReport;
import com.kir138.model.dto.BorrowReportDto;

public class BorrowReportMapper {
    public BorrowReportDto toBorrowReport(BorrowReport borrowReport) {
        return BorrowReportDto.builder()
                .book(borrowReport.getBook())
                .reader(borrowReport.getReader())
                .isReturn(borrowReport.getIsReturn())
                .borrowDate(borrowReport.getBorrowDate())
                .build();
    }
}
