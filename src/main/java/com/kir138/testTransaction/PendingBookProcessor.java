package com.kir138.testTransaction;

import com.kir138.model.entity.Book;
import com.kir138.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PendingBookProcessor {
    private final PendingBookProcessor self;
    private final BookRepository bookRepository;

    @Scheduled(cron = "0 0 0 * * *") // at 00:00 new *
    // на базах есть прибивалки транзакций
    public void processPendingBooks() {
        List<Book> bookList = bookRepository.findAllPendingReturnBooks();

        for (Book book : bookList) {
            try {
                httpCall(book);
                self.update(book);
            } catch (Exception e) {
                System.out.println("ошибка обработки книги " + book.getId() + " " + e.getMessage());
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void update(Book book) {
        book.setStatus(Book.BookStatus.SENDED_PENDING_RETURN);
        bookRepository.save(book);
    }

    // псевдокод
    // http up to 60 sec
    public void httpCall(Book book) throws InterruptedException {
        Thread.sleep(60000);
    }
}

    /*@Async
    public CompletableFuture<Void> processBook(Book book) {
        try {
            httpCall(book); // положить в кафку / раббит
            self.update(book);
        } catch (Exception e) {
            System.out.println("ошибка обработки книги " + book.getId() + " " + e.getMessage());
        }
        return CompletableFuture.completedFuture(null);
    }*/
