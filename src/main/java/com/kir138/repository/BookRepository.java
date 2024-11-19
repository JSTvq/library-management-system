package com.kir138.repository;

import com.kir138.entity.Book;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BookRepository implements CrudRepository<Book, Long> {

    private final SessionFactory sessionFactory;

    /**
     * Реализуйте методы для добавления новых читателей и книг.
     * @param book
     * @return
     */
    @Override
    public Book save(Book book) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(book);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("сохранение не произошло");
            }
        }
        return book;
    }

    @Override
    public List<Book> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Book", Book.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Book book = session.find(Book.class, id);
            return Optional.ofNullable(book);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book update(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Book book = session.createQuery("from Book where id = :id", Book.class)
                        .setParameter("id", id)
                        .uniqueResult();
                session.merge(book);
                transaction.commit();
                return book;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("обновление не произошло");
            }
        }
    }

    public Book update(Book book) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(book);
                transaction.commit();
                return book;
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
                Book deleteBook = session.get(Book.class, id);
                session.remove(deleteBook);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("удаление не произошло");
            }
        }
    }
}
