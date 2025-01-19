package com.kir138.testTransaction;

import com.kir138.model.entity.Book;
import com.kir138.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*@Service
@RequiredArgsConstructor
public class PendingBookProcessor {
    private final BookRepository bookRepository;

    @Scheduled(cron = "0 0 0 * * *") // at 00:00 new *
    @Transactional // на базах есть прибивалки транзакций
    public void processPendingBooks() {
        List<Book> bookList = bookRepository.findAllByStatus_PendingReturn();

        for (Book book : bookList) {
            httpCall(book); // положить в кафку / раббит
            update(book);
        }
    }

    void update(Book book) {
        book.setStatus(Book.BookStatus.SENDED_PENDING_RETURN);
    }

    // псевдокод
    // http up to 60 sec
    public void httpCall(Book book) {
    }
}*/
