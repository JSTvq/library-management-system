package com.kir138.repository;

import com.kir138.entity.Reader;

import java.util.List;

public interface ReaderRepository extends CrudRepository<Reader, Long> {
    List<Reader> getAllReadersWithBooksOptimized();
    void displayReadersAndBooks();
}
