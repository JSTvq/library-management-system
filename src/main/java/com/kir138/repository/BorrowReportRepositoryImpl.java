package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.BorrowReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BorrowReportRepositoryImpl extends SimpleCrudRepository<BorrowReport, Long> implements BorrowReportRepository {

    public BorrowReportRepositoryImpl(Class<BorrowReport> entityClass) {
        super(entityClass);
    }

    @Override
    public Optional<BorrowReport> findExistingBorrowReport(Long book) {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            BorrowReport borrowReport = entityManager.createQuery("from BorrowReport where " +
                            "book.id = :book_id AND isReturn = true", BorrowReport.class)
                    .setParameter("book_id", book)
                    .getSingleResult();
            return Optional.ofNullable(borrowReport);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<BorrowReport> findByReaderIdAndBorrowDateBetween(Long readerId, LocalDate fromDate,
                                                                 LocalDate toDate) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            return entityManager.createQuery("select from BorrowReport br " +
                            "where br.reader.id = :reader_id and and br.borrowdate " +
                            "betweeen :fromDate and :toDate", BorrowReport.class)
                    .setParameter("reader_id", readerId)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .getResultList();
        }
    }
}
