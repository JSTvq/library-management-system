package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.entity.Book;
import com.kir138.entity.BorrowReport;
import com.kir138.entity.Reader;
import com.kir138.entityDto.BookDto;
import com.kir138.entityDto.BorrowReportDto;
import com.kir138.entityDto.ReaderDto;
import com.kir138.mapper.BookMapper;
import com.kir138.mapper.BorrowReportMapper;
import com.kir138.mapper.ReaderMapper;
import com.kir138.repository.BookRepositoryImpl;
import com.kir138.repository.BorrowReportRepositoryImpl;
import com.kir138.repository.ReaderRepositoryImpl;
import jakarta.persistence.EntityManager;
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
    /*public void updateBorrowedBooks(Long readId, Long bookId) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                Reader reader = readerRepositoryImpl.findById(readId).orElseThrow();
                Book book = bookRepositoryImpl.findById(bookId).orElseThrow();

                book.setReader(reader);
                bookRepositoryImpl.save(book);

                BorrowReport borrowReport = borrowReportRepositoryImpl.findExistingBorrowReport(bookId).orElseThrow();

                if (borrowReport.getId() != null) {
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
    }*/

    public void updateBorrowedBooks(Long readId, Long bookId) {

                Reader reader = readerRepositoryImpl.findById(readId).orElseThrow();
                Book book = bookRepositoryImpl.findById(bookId).orElseThrow();

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
        }

    /**
     * Метод для возврата книги (обновление статуса returnStatus).
     */
    public void updateReturnStatus(Long reportId) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            EntityTransaction entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            try {
                Optional<BorrowReport> borrowReport = borrowReportRepositoryImpl.findById(reportId);

                if (borrowReport.isEmpty()) {
                    System.out.println("такого id нет в системе");
                    return;
                }

                BorrowReport borrowReport1 = borrowReport.get();
                borrowReport1.setIsReturn(true);
                borrowReport1.setReader(null);
                borrowReportRepositoryImpl.save(borrowReport1);

                Book book = borrowReport1.getBook();
                book.setReader(null);
                bookRepositoryImpl.save(book);
                entityTransaction.commit();
            } catch (Exception e) {
                entityTransaction.rollback();
                throw new RuntimeException(e);
            }
        }
    }
}
