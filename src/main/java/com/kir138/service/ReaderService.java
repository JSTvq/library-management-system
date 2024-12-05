package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Book;
import com.kir138.model.entity.BorrowReport;
import com.kir138.model.entity.Reader;
import com.kir138.model.dto.ReaderDto;
import com.kir138.mapper.ReaderMapper;
import com.kir138.repository.ReaderRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepositoryImpl readerRepositoryImpl;
    private final ReaderMapper readerMapper;

    public ReaderDto saveOrUpdateReader(Reader reader) {
        return readerMapper.toReader(readerRepositoryImpl.save(reader));
    }

    public List<ReaderDto> getAllReaders() {
        return readerRepositoryImpl.findAll()
                .stream()
                .map(readerMapper::toReader)
                .toList();
    }

    public ReaderDto getReaderById(Long id) {
        return readerRepositoryImpl.findById(id)
                .map(readerMapper::toReader)
                .orElseThrow(() -> new IllegalArgumentException("Читатель с id = " + id + " не найден"));
    }

    public void deleteReader(Long id) {
        readerRepositoryImpl.deleteById(id);
    }
}
