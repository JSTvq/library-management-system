package com.kir138;

import com.kir138.mapper.BookMapper;
import com.kir138.mapper.BorrowReportMapper;
import com.kir138.mapper.ReaderMapper;
import com.kir138.model.entity.Book;
import com.kir138.model.entity.BorrowReport;
import com.kir138.model.entity.Reader;
import com.kir138.repository.BookRepositoryImpl;
import com.kir138.repository.BorrowReportRepositoryImpl;
import com.kir138.repository.ReaderRepositoryImpl;
import com.kir138.service.BookService;
import com.kir138.service.BorrowReportService;
import com.kir138.service.ReaderService;
import com.kir138.service.ReportService;
import com.kir138.servlet.ConfigureServerServlet;


public class Main {
    public static void main(String[] args) throws Exception {

        ReaderRepositoryImpl readerRepositoryImpl = new ReaderRepositoryImpl(Reader.class);
        BookRepositoryImpl bookRepositoryImpl = new BookRepositoryImpl(Book.class);
        BookMapper bookMapper = new BookMapper();
        BorrowReportRepositoryImpl borrowReportRepositoryImpl = new BorrowReportRepositoryImpl(BorrowReport.class);
        ReaderMapper readerMapper = new ReaderMapper();
        BorrowReportMapper borrowReportMapper = new BorrowReportMapper();
        ReportService reportService = new ReportService(bookMapper);
        ReaderService readerService = new ReaderService(readerRepositoryImpl, readerMapper);
        BorrowReportService borrowReportService = new BorrowReportService(borrowReportMapper, borrowReportRepositoryImpl);
        BookService bookService = new BookService(bookRepositoryImpl, bookMapper, borrowReportRepositoryImpl, readerRepositoryImpl);

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




        //ConfigureServerServlet.configureServer(readerService);

    }
}