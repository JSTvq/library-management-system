package com.kir138.repository;

import com.kir138.model.entity.Book;
import com.kir138.model.entity.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ReaderRepositoryImpl implements ReaderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Оптимизация с использованием JOIN FETCH
     */
    @Override
    public List<Reader> getAllReadersWithBorrowedBooks() {
        List<Reader> readers = null;
        EntityTransaction entityTransaction = entityManager.getTransaction();
            try {
                entityTransaction.begin();
                readers = entityManager.createQuery("from Reader r join fetch r.borrowedBooks", Reader.class)
                        .getResultList();
                entityTransaction.commit();
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при получении читателей с книгами", e);
            }
        return readers;
    }

    /**
     * Продемонстрируйте поведение ленивой загрузки в методе для получения всех читателей и их книг
     * (необходимо показать, что книги загружаются только при непосредственном доступе к ним).
     */
    @Override
    public void displayReadersAndBooks() {
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
