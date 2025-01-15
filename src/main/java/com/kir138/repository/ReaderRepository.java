package com.kir138.repository;

import com.kir138.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
    List<Reader> getAllReadersWithBorrowedBooks();
    void displayReadersAndBooks();
}
