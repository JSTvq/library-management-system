package com.kir138.repository;

import com.kir138.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    //List<Book> findAllByStatus();
    Optional<Book> findByAuthor(String title);
}
