package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Book;
import com.kir138.model.entity.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class ReaderRepositoryImpl extends SimpleCrudRepository<Reader, Long> implements ReaderRepository {

    public ReaderRepositoryImpl(Class<Reader> entityClass) {
        super(entityClass);
    }

    /**
     * Оптимизация с использованием JOIN FETCH
     */
    @Override
    public List<Reader> getAllReadersWithBorrowedBooks() {
        List<Reader> readers = null;
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                readers = entityManager.createQuery("from Reader r join fetch r.borrowedBooks", Reader.class)
                        .getResultList();
                entityTransaction.commit();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при получении читателей с книгами", e);
            }
        }
        return readers;
    }

    /**
     * Продемонстрируйте поведение ленивой загрузки в методе для получения всех читателей и их книг
     * (необходимо показать, что книги загружаются только при непосредственном доступе к ним).
     */
    @Override
    public void displayReadersAndBooks() {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            List<Reader> readers = entityManager.createQuery("FROM Reader", Reader.class)
                    .getResultList();

            for (Reader reader : readers) {
                System.out.println("Читатель: " + reader.getName());
                // Книги загружаются только при доступе к borrowedBooks
                for (Book book : reader.getBorrowedBooks()) {
                    System.out.println("\tКнига: " + book.getTitle());
                }
            }
        }
    }
}
