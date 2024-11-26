package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.entity.Book;
import com.kir138.entity.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class ReaderRepositoryImpl implements ReaderRepository {

    /**
     * Реализуйте методы для добавления новых читателей и книг.
     */
    @Override
    public Reader save(Reader reader) {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                entityManager.merge(reader);
                entityTransaction.commit();
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException("сохранение не произошло");
            }
        }
        return reader;
    }

    @Override
    public List<Reader> findAll() {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            return entityManager.createQuery("from Reader", Reader.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reader> findById(Long id) {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            Reader reader = entityManager.find(Reader.class, id);
            return Optional.ofNullable(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                Reader deleteReader = entityManager.getReference(Reader.class, id);
                entityManager.remove(deleteReader);
                entityTransaction.commit();
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException("удаление не произошло");
            }
        }
    }

    /**
     * Оптимизация с использованием JOIN FETCH
     */
    @Override
    public List<Reader> getAllReadersWithBooksOptimized() {
        List<Reader> readers = null;
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                TypedQuery<Reader> query = entityManager.createQuery(
                        "from Reader r join fetch r.borrowedBooks", Reader.class);
                readers = query.getResultList();

                entityTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
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
            List<Reader> readers = entityManager.createQuery("FROM Reader", Reader.class).getResultList();
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
