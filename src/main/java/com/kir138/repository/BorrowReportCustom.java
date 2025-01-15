package com.kir138.repository;

import com.kir138.model.entity.BorrowReport;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BorrowReportCustom {
    Optional<BorrowReport> findExistingBorrowReport(Long book);
    List<BorrowReport> findByReaderIdAndBorrowDateBetween(Long readerId, LocalDate fromDate,
                                                                 LocalDate toDate);
}
