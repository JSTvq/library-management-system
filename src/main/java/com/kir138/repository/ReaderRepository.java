package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.entity.Reader;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReaderRepository implements CrudRepository<Reader, Long> {

    private final SessionFactory sessionFactory;
    /**
     * Реализуйте методы для добавления новых читателей и книг.
     * @param reader
     * @return
     */
    @Override
    public Reader save(Reader reader) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(reader);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("сохранение не произошло");
            }
        }
        return reader;
    }

    @Override
    public List<Reader> findAll() {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("from Reader", Reader.class).list();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Reader> findById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Reader reader = session.find(Reader.class, id);
            return Optional.ofNullable(reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reader update(Long id) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Reader reader = session.createQuery("from Reader where id = :id", Reader.class)
                        .setParameter("id", id)
                        .uniqueResult();
                session.merge(reader);
                transaction.commit();
                return reader;
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("обновление не произошло");
            }
        }
    }

    public Reader update(Reader reader) {
        try(Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(reader);
                transaction.commit();
                return reader;
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
                Reader deleteReader = session.get(Reader.class, id);
                session.remove(deleteReader);
                transaction.commit();
            } catch (Exception e) {
                transaction.rollback();
                throw new RuntimeException("удаление не произошло");
            }
        }
    }

    /**
     * Оптимизация с использованием JOIN FETCH
     */
    public List<Reader> getAllReadersWithBooksOptimized() {
        List<Reader> readers = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Query<Reader> query = session.createQuery(
                        "from Reader r join fetch r.borrowedBooks", Reader.class);
                readers = query.list();

                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return readers;
    }
}
