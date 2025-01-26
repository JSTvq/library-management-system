package com.kir138.repository;

import com.kir138.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    List<Book> findAllByStatus(Book.BookStatus status);

    Optional<Book> findByAuthor(String title);
}
