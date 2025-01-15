package com.kir138.service;

import com.kir138.mapper.BookMapper;
import com.kir138.model.dto.BookDto;
import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.model.entity.Book;
import com.kir138.model.entity.BorrowReport;
import com.kir138.model.entity.Reader;
import com.kir138.repository.BookRepository;
import com.kir138.repository.BorrowReportRepository;
import com.kir138.repository.ReaderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BorrowReportRepository borrowReportRepository;
    private final ReaderRepository readerRepository;

    public BookDto saveOrUpdateBook(BookRegistrationRq request) {
        Book book = bookMapper.toBook(request);
        return bookMapper.toBook(bookRepository.save(book));
    }

    public List<BookDto> getAllBook() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBook)
                .toList();
    }

    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBook)
                .orElseThrow(() -> new IllegalArgumentException("Книга с таким id не найдена"));
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Метод для того, чтобы читатель мог брать книги (обновление таблицы borrowedBooks).
     */
    @Transactional
    public void borrowBook(Long readerId, Long bookId) {

        Reader reader = readerRepository.findById(readerId).orElseThrow(()
                -> new EntityNotFoundException("Читатель не найден"));
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new EntityNotFoundException("Книга не найдена"));

        if (book.getReader() != null) {
            throw new IllegalStateException("Книга уже взята другим читателем");
        }

        book.setReader(reader);
        bookRepository.save(book);

        Optional<BorrowReport> borrowReportOpt = borrowReportRepository
                .findExistingBorrowReport(bookId);

        if (borrowReportOpt.isPresent()) {
            BorrowReport borrowReport = borrowReportOpt.get();
            borrowReport.setIsReturn(false);
            borrowReport.setReader(reader);
            borrowReportRepository.save(borrowReport);
        } else {
            BorrowReport newBorrowReport = BorrowReport.builder()
                    .borrowDate(LocalDate.now())
                    .reader(reader)
                    .book(book)
                    .isReturn(false)
                    .build();
            borrowReportRepository.save(newBorrowReport);
        }
    }

    /**
     * Метод для возврата книги (обновление статуса returnStatus).
     */
    @Transactional
    public void returnBook(Long reportId) {

        BorrowReport borrowReport = borrowReportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Отчет не найден"));

        if (borrowReport.getIsReturn()) {
            throw new IllegalStateException("Книга уже была возвращена");
        }

        borrowReport.setIsReturn(true);
        borrowReport.setReader(null);
        borrowReportRepository.save(borrowReport);

        Book book = borrowReport.getBook();
        book.setReader(null);
        bookRepository.save(book);
    }
}
