package com.kir138.connect;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

public class TransactionManager {
    @PersistenceContext
    private EntityManager entityManager;

    public <T> T executeInTransaction(TransactionCallback<T> action) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            T result = action.doInTransaction(entityManager);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при выполнении транзакции", e);
        }
    }

    public interface TransactionCallback<T> {
        T doInTransaction(EntityManager entityManager);
    }
}