package com.kir138.repository;

import com.kir138.model.entity.Reader;

import java.util.List;

public interface ReaderRepositoryCustom {
    List<Reader> getAllReadersWithBorrowedBooks();
    void displayReadersAndBooks();
}
