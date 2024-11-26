package com.kir138.mapper;

import com.kir138.entity.BorrowReport;
import com.kir138.entityDto.BorrowReportDto;

public class BorrowReportMapper {
    public BorrowReportDto toBorrowReport(BorrowReport borrowReport) {
        return BorrowReportDto.builder()
                .bookId(borrowReport.getBook())
                .readerId(borrowReport.getReader())
                .returnStatus(borrowReport.getIsReturn())
                .borrowDate(borrowReport.getBorrowDate())
                .build();
    }
}
