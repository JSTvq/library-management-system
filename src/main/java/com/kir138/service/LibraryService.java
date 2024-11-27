package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Book;
import com.kir138.model.entity.BorrowReport;
import com.kir138.model.entity.Reader;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BorrowReportDto;
import com.kir138.model.dto.ReaderDto;
import com.kir138.mapper.BookMapper;
import com.kir138.mapper.BorrowReportMapper;
import com.kir138.mapper.ReaderMapper;
import com.kir138.repository.BookRepositoryImpl;
import com.kir138.repository.BorrowReportRepositoryImpl;
import com.kir138.repository.ReaderRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.EntityTransaction;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LibraryService {
    private final ReaderRepositoryImpl readerRepositoryImpl;
    private final BookRepositoryImpl bookRepositoryImpl;
    private final BorrowReportRepositoryImpl borrowReportRepositoryImpl;
    private final BookMapper bookMapper;
    private final BorrowReportMapper borrowReportMapper;
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
                .orElseThrow(() -> new IllegalArgumentException("Читатель с таким id не найден"));
    }

    public void deleteReader(Long id) {
        readerRepositoryImpl.deleteById(id);
    }

    public BorrowReportDto saveOrUpdateBorrowReport(BorrowReport borrowReport) {
        return borrowReportMapper.toBorrowReport(borrowReportRepositoryImpl.save(borrowReport));
    }

    public List<BorrowReportDto> getAllBorrowReport() {
        return borrowReportRepositoryImpl.findAll()
                .stream()
                .map(borrowReportMapper::toBorrowReport)
                .toList();
    }

    public BorrowReportDto getBorrowReportById(Long id) {
        return borrowReportRepositoryImpl.findById(id)
                .map(borrowReportMapper::toBorrowReport)
                .orElseThrow(() -> new IllegalArgumentException("Отчет с таким id не найден"));
    }

    public void deleteBorrowReport(Long id) {
        borrowReportRepositoryImpl.deleteById(id);
    }

    public BookDto saveOrUpdateBook(Book book) {
        return bookMapper.toBook(bookRepositoryImpl.save(book));
    }

    public List<BookDto> getAllBook() {
        return bookRepositoryImpl.findAll()
                .stream()
                .map(bookMapper::toBook)
                .toList();
    }

    public BookDto getBookById(Long id) {
        return bookRepositoryImpl.findById(id)
                .map(bookMapper::toBook)
                .orElseThrow(() -> new IllegalArgumentException("Книга с таким id не найдена"));
    }

    public void deleteBook(Long id) {
        bookRepositoryImpl.deleteById(id);
    }

    /**
     * Метод для того, чтобы читатель мог брать книги (обновление таблицы borrowedBooks).
     */
    public void borrowBook(Long readId, Long bookId) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {

                Reader reader = readerRepositoryImpl.findById(readId)
                        .orElseThrow(() -> new EntityNotFoundException("Читатель не найден"));
                Book book = bookRepositoryImpl.findById(bookId)
                        .orElseThrow(() -> new EntityNotFoundException("Книга не найдена"));

                book.setReader(reader);
                bookRepositoryImpl.save(book);

                Optional<BorrowReport> borrowReportOpt = borrowReportRepositoryImpl.findExistingBorrowReport(bookId);

                if (borrowReportOpt.isPresent()) {
                    BorrowReport borrowReport = borrowReportOpt.get();
                    borrowReport.setIsReturn(false);
                    borrowReport.setReader(reader);
                    borrowReportRepositoryImpl.save(borrowReport);
                } else {
                    BorrowReport newBorrowReport = BorrowReport.builder()
                            .borrowDate(LocalDate.now())
                            .reader(reader)
                            .book(book)
                            .isReturn(false)
                            .build();
                    borrowReportRepositoryImpl.save(newBorrowReport);
                }
                entityTransaction.commit();
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Метод для возврата книги (обновление статуса returnStatus).
     */
    public void returnBook(Long reportId) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                BorrowReport borrowReport = borrowReportRepositoryImpl.findById(reportId)
                        .orElseThrow(() -> new EntityNotFoundException("Отчет не найден"));

                borrowReport.setIsReturn(true);
                borrowReport.setReader(null);
                borrowReportRepositoryImpl.save(borrowReport);

                Book book = borrowReport.getBook();
                book.setReader(null);
                bookRepositoryImpl.save(book);
                entityTransaction.commit();
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException("Ошибка при возврате книги", e);
            }
        }
    }
}
