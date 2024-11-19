package com.kir138;

import com.kir138.connect.PgConnect;
import com.kir138.entity.Reader;
import com.kir138.repository.BookRepository;
import com.kir138.repository.BorrowReportRepository;
import com.kir138.repository.ReaderRepository;
import com.kir138.service.LibraryService;
import org.hibernate.SessionFactory;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = PgConnect.getSessionFactory();
        ReaderRepository readerRepository = new ReaderRepository(sessionFactory);
        BookRepository bookRepository = new BookRepository(sessionFactory);
        BorrowReportRepository borrowReportRepository = new BorrowReportRepository(sessionFactory);
        LibraryService libraryService = new LibraryService(readerRepository, bookRepository, borrowReportRepository);

        /*bookRepository.save(Book.builder()
                .year(2001)
                .title("Книга1")
                .author("Автор1")
                .build());

        bookRepository.save(Book.builder()
                .year(2002)
                .title("Книга2")
                .author("Автор2")
                .build());

        bookRepository.save(Book.builder()
                .year(2003)
                .title("Книга3")
                .author("Автор3")
                .build());

        bookRepository.save(Book.builder()
                .year(2004)
                .title("Книга4")
                .author("Автор4")
                .build());

        readerRepository.save(Reader.builder()
                .name("человек1")
                .email("почта1")
                .build());

        readerRepository.save(Reader.builder()
                .name("человек2")
                .email("почта2")
                .build());

        readerRepository.save(Reader.builder()
                .name("человек3")
                .email("почта3")
                .build());

        readerRepository.save(Reader.builder()
                .name("человек4")
                .email("почта4")
                .build());

        libraryService.updateBorrowedBooks(1L, 1L);
        libraryService.updateBorrowedBooks(2L, 2L);
        libraryService.updateBorrowedBooks(3L, 3L);*/

        /*Book book5 = Book.builder()
                .year(2005)
                .title("Книга5")
                .author("Автор5")
                .build();

        Reader reader5 = Reader.builder()
                .name("человек5")
                .email("почта5")
                .build();

        BorrowReport borrowReport = BorrowReport.builder()
                .bookId(book5)
                .readerId(reader5)
                .returnStatus(false)
                .borrowDate(LocalDate.of(2023, 10, 22))
                .build();

        Book book6 = Book.builder()
                .year(2005)
                .title("Книга5")
                .author("Автор5")
                .build();

        Reader reader6 = Reader.builder()
                .name("человек5")
                .email("почта5")
                .build();

        BorrowReport borrowReport6 = BorrowReport.builder()
                .bookId(book6)
                .readerId(reader6)
                .returnStatus(false)
                .borrowDate(LocalDate.now())
                .build();*/

        //bookRepository.save(book6);
        //readerRepository.save(reader6);
        //borrowReportRepository.save(borrowReport6);

        //bookRepository.save(book5);
        //readerRepository.save(reader5);
        //borrowReportRepository.save(borrowReport);
        //libraryService.updateReturnStatus(5L);

        //System.out.println(bookRepository.findById(1L));

        //System.out.println(libraryService.borrowedBooksLastMonth());
        //System.out.println(libraryService.borrowedBooksFourteenDays());
        //libraryService.displayReadersAndBooks();

        List<Reader> readers = readerRepository.getAllReadersWithBooksOptimized();
        for (Reader reader : readers) {
            System.out.println(reader);
        }
    }

}