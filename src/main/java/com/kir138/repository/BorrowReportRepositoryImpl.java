package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.BorrowReport;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;

import java.util.List;
import java.util.Optional;

public class BorrowReportRepositoryImpl implements BorrowReportRepository {

    @Override
    public BorrowReport save(BorrowReport borrowReport) {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                entityManager.merge(borrowReport);
                entityTransaction.commit();
                return borrowReport;
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException("сохранение не произошло");
            }
        }
    }

    @Override
    public List<BorrowReport> findAll() {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            return entityManager.createQuery("from BorrowReport", BorrowReport.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BorrowReport> findById(Long id) {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            BorrowReport borrowReport = entityManager.find(BorrowReport.class, id);
            return Optional.ofNullable(borrowReport);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<BorrowReport> findExistingBorrowReport(Long book) {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            BorrowReport borrowReport = entityManager.createQuery("from BorrowReport where " +
                            "book.id = :book_id AND isReturn = true", BorrowReport.class)
                    .setParameter("book_id", book)
                    .getSingleResult();
            return Optional.ofNullable(borrowReport);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteById(Long id) {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                BorrowReport deleteBorrowReport = entityManager.getReference(BorrowReport.class, id);
                entityManager.remove(deleteBorrowReport);
                entityTransaction.commit();
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException("удаление не произошло");
            }
        }
    }
}
