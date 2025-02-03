package com.kir138.testTransaction;

import com.kir138.model.entity.Book;
import com.kir138.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PendingBookProcessor {
    private final BookRepository bookRepository;
    private final PendingBookProcessor self;

    @Autowired
    public PendingBookProcessor(@Lazy PendingBookProcessor self,
                                BookRepository bookRepository) {
        this.self = self;
        this.bookRepository = bookRepository;
    }

    //@Scheduled(cron = "0 0 0 * * *") // at 00:00 new *
    // на базах есть прибивалки транзакций
    public void processPendingBooks() {
        Pageable pageable = Pageable.ofSize(100);
        List<Book> bookList;

        do {
            bookList = bookRepository.findAllByStatus(Book.BookStatus.SENDED_PENDING_RETURN, pageable);
            for (Book book : bookList) {
                try {
                    self.updateAndHttpCall(book);
                } catch (Exception e) {
                    System.out.println("ошибка обработки книги " + book.getId() + " " + e.getMessage());
                }
            }
            pageable = pageable.next();
        } while (!bookList.isEmpty());
    }

    @Transactional
    public void updateAndHttpCall(Book book) throws InterruptedException {
        httpCall(book);
        book.setStatus(Book.BookStatus.RETURNED); //пройдет ли сохранение без селекта?
    }

    // псевдокод
    // http up to 60 sec
    public void httpCall(Book book) throws InterruptedException {
        Thread.sleep(10);
    }
}
