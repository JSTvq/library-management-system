package com.kir138.repository;

import com.kir138.model.entity.BorrowReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowReportRepository extends JpaRepository<BorrowReport, Long>, BorrowReportCustom {

    Optional<BorrowReport> findExistingBorrowReport(Long book);
}
