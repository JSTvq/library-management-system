package com.kir138;

import com.kir138.mapper.BookMapper;
import com.kir138.mapper.BorrowReportMapper;
import com.kir138.mapper.ReaderMapper;
import com.kir138.repository.BookRepositoryImpl;
import com.kir138.repository.BorrowReportRepositoryImpl;
import com.kir138.repository.ReaderRepositoryImpl;
import com.kir138.service.LibraryService;
import com.kir138.service.ReportService;


public class Main {
    public static void main(String[] args) {
        ReaderRepositoryImpl readerRepositoryImpl = new ReaderRepositoryImpl();
        BookRepositoryImpl bookRepositoryImpl = new BookRepositoryImpl();
        BorrowReportRepositoryImpl borrowReportRepositoryImpl = new BorrowReportRepositoryImpl();
        ReaderMapper readerMapper = new ReaderMapper();
        BookMapper bookMapper = new BookMapper();
        BorrowReportMapper borrowReportMapper = new BorrowReportMapper();
        ReportService reportService = new ReportService(bookMapper);
        LibraryService libraryService = new LibraryService(readerRepositoryImpl, bookRepositoryImpl, borrowReportRepositoryImpl,
                bookMapper, borrowReportMapper, readerMapper);

        /*bookRepositoryImpl.save(Book.builder()
                .year(2001)
                .title("Книга1")
                .author("Автор1")
                .build());

        bookRepositoryImpl.save(Book.builder()
                .year(2002)
                .title("Книга2")
                .author("Автор2")
                .build());

        bookRepositoryImpl.save(Book.builder()
                .year(2003)
                .title("Книга3")
                .author("Автор3")
                .build());

        bookRepositoryImpl.save(Book.builder()
                .year(2004)
                .title("Книга4")
                .author("Автор4")
                .build());

        readerRepositoryImpl.save(Reader.builder()
                .name("человек1")
                .email("почта1")
                .build());

        readerRepositoryImpl.save(Reader.builder()
                .name("человек2")
                .email("почта2")
                .build());

        readerRepositoryImpl.save(Reader.builder()
                .name("человек3")
                .email("почта3")
                .build());

        readerRepositoryImpl.save(Reader.builder()
                .name("человек4")
                .email("почта4")
                .build());*/



    }
}