package com.kir138.repository;

import com.kir138.entity.BorrowReport;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BorrowReportRepository implements CrudRepository<BorrowReport, Long> {

    private final SessionFactory sessionFactory;

    @Override
    public BorrowReport save(BorrowReport borrowReport) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(borrowReport);
                transaction.commit();
                return borrowReport;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("сохранение не произошло");
            }
        }
    }

    @Override
    public List<BorrowReport> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from BorrowReport", BorrowReport.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BorrowReport> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            BorrowReport borrowReport = session.find(BorrowReport.class, id);
            return Optional.ofNullable(borrowReport);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<BorrowReport> findExistingBorrowReport(Long book) {
        try(Session session = sessionFactory.openSession()) {
            BorrowReport borrowReport = session.createQuery("from BorrowReport where " +
                            "bookId.id = :book_id AND returnStatus = true", BorrowReport.class)
                    .setParameter("book_id", book)
                    .uniqueResult();
            return Optional.ofNullable(borrowReport);
        }
    }

    @Override
    public BorrowReport update(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                BorrowReport borrowReport = session.createQuery("from BorrowReport where id = :id", BorrowReport.class)
                        .setParameter("id", id)
                        .uniqueResult();
                session.merge(borrowReport);
                transaction.commit();
                return borrowReport;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("обновление не произошло");
            }
        }
    }

    public BorrowReport update(BorrowReport borrowReport) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(borrowReport);
                transaction.commit();
                return borrowReport;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("обновление не произошло");
            }
        }
    }

    @Override
    public void deleteId(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                BorrowReport deleteBorrowReport = session.get(BorrowReport.class, id);
                session.remove(deleteBorrowReport);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("удаление не произошло");
            }
        }
    }
}
