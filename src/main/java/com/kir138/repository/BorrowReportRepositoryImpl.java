package com.kir138.repository;

import com.kir138.model.entity.BorrowReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowReportRepositoryImpl implements BorrowReportCustom{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<BorrowReport> findExistingBorrowReport(Long book) {
        try {
            BorrowReport borrowReport = entityManager.createQuery("FROM BorrowReport WHERE " +
                            "book.id = :book_id AND isReturn = true", BorrowReport.class)
                    .setParameter("book_id", book)
                    .getSingleResult();

            return Optional.ofNullable(borrowReport);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<BorrowReport> findByReaderIdAndBorrowDateBetween(Long readerId, LocalDate fromDate,
                                                                 LocalDate toDate) {

        try {
            return entityManager.createQuery("SELECT br FROM BorrowReport br " +
                            "WHERE br.reader.id = :reader_id AND br.borrowDate " +
                            "BETWEEN :fromDate AND :toDate", BorrowReport.class)
                    .setParameter("reader_id", readerId)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
