package com.kir138.service;

import com.kir138.mapper.BorrowReportMapper;
import com.kir138.model.dto.BorrowReportDto;
import com.kir138.model.entity.BorrowReport;
import com.kir138.repository.BorrowReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BorrowReportService {
    private final BorrowReportMapper borrowReportMapper;
    private final BorrowReportRepository borrowReportRepository;

    @Transactional
    public BorrowReportDto saveOrUpdateBorrowReport(BorrowReport borrowReport) {
        //TODO написать логику обновления
        return borrowReportMapper.toBorrowReport(borrowReportRepository.save(borrowReport));
    }

    @Transactional
    public List<BorrowReportDto> getAllBorrowReport() {
        return borrowReportRepository.findAll()
                .stream()
                .map(borrowReportMapper::toBorrowReport)
                .toList();
    }

    @Transactional(readOnly = true)
    public BorrowReportDto getBorrowReportById(Long id) {
        return borrowReportRepository.findById(id)
                .map(borrowReportMapper::toBorrowReport)
                .orElseThrow(() -> new IllegalArgumentException("Отчет с таким id не найден"));
    }

    @Transactional
    public void deleteBorrowReport(Long id) {
        borrowReportRepository.deleteById(id);
    }
}
