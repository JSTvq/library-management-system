package com.kir138.repository;

import com.kir138.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    @Query("SELECT b FROM Book b WHERE b.status = 'SENDED_PENDING_RETURN'")
    List<Book> findAllPendingReturnBooks();
    Optional<Book> findByAuthor(String title);
}
