package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Reader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class SimpleCrudRepository<T, K> implements CrudRepository<T, K> {

    private final Class<T> entityClass;

    @Override
    public T save(T reader) {
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
    public List<T> findAll() {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            return entityManager.createQuery("from " + entityClass.getSimpleName(), entityClass).getResultList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<T> findById(K id) {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            T reader = entityManager.find(entityClass, id);
            return Optional.ofNullable(reader);
        } catch (Exception e) {
            throw new RuntimeException("не найден пользователь" + e);
        }
    }

    @Override
    public void deleteById(K id) {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                T deleteReader = entityManager.getReference(entityClass, id);
                entityManager.remove(deleteReader);
                entityTransaction.commit();
            } catch (RuntimeException e) {
                entityTransaction.rollback();
                throw e;
            }
        }
    }
}
