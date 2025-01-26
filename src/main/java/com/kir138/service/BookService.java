package com.kir138.service;

import com.kir138.exception.ErrorCode;
import com.kir138.exception.ServiceException;
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

    @Transactional
    public BookDto saveOrUpdateBook(BookRegistrationRq request) {
        return bookRepository.findByAuthor(request.getAuthor())
                .map(book -> {
                    book.setTitle(request.getTitle());
                    book.setYear(request.getYear());
                    return bookMapper.toBook(bookRepository.save(book));
                })
                .orElseGet(() -> {
                    Book newBook = bookMapper.toBook(request);
                    return bookMapper.toBook(bookRepository.save(newBook));
                });
    }

    @Transactional(readOnly = true)
    public List<BookDto> getAllBook() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toBook)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toBook)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND_BY_ID, id));
    }

    @Transactional(readOnly = true)
    public BookDto getBookByAuthor(String author) {
        return bookRepository.findByAuthor(author)
                .map(bookMapper::toBook)
                .orElseThrow(() -> new ServiceException(ErrorCode.BOOK_NOT_FOUND_BY_AUTHOR, author));
    }

    @Transactional
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Метод для того, чтобы читатель мог брать книги (обновление таблицы borrowedBooks).
     */
    @Transactional
    public void borrowBook(Long readerId, Long bookId) {

        Reader reader = readerRepository.findById(readerId).orElseThrow(()
                -> new ServiceException(ErrorCode.READER_NOT_FOUND_BY_ID, readerId));
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new ServiceException(ErrorCode.BOOK_NOT_FOUND_BY_ID, bookId));

        if (book.getReader() != null) {
            throw new ServiceException(ErrorCode.BOOK_HAS_NOT_BEEN_RETURNED, bookId);
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
                .orElseThrow(() -> new ServiceException(ErrorCode.BORROW_REPORT_NOT_FOUND, reportId));

        if (borrowReport.getIsReturn()) {
            throw new ServiceException(ErrorCode.BOOK_RETURNED, reportId);
        }

        borrowReport.setIsReturn(true);
        borrowReport.setReader(null);
        borrowReportRepository.save(borrowReport);

        Book book = borrowReport.getBook();
        book.setReader(null);
        bookRepository.save(book);
    }

    public void addBooks(int n) {
        for (int i = 1; i <= n; i++) {
            Book book = Book.builder()
                    .author("Author_new" + i)
                    .title("Title_new" + i)
                    .year(2000 + i)
                    .status(Book.BookStatus.SENDED_PENDING_RETURN)
                    .build();

            bookRepository.save(book);
        }
    }
}
