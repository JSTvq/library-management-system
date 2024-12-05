package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class BookRepositoryImpl extends SimpleCrudRepository<Book, Long> implements BookRepository {

    public BookRepositoryImpl(Class<Book> entityClass) {
        super(entityClass);
    }
}
