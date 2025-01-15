package com.kir138.repository;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl extends SimpleCrudRepository<Book, Long> implements CrudRepository<Book, Long> {


}
