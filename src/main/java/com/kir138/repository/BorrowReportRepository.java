package com.kir138.repository;

import com.kir138.model.entity.BorrowReport;

import java.util.Optional;

public interface BorrowReportRepository extends CrudRepository<BorrowReport, Long> {

    Optional<BorrowReport> findExistingBorrowReport(Long book);
}
