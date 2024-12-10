package com.kir138.servlet;
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
import com.kir138.service.ReportService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kir138.service.ReaderService;

public class ConfigureServerServlet {
    public static void configureServer() throws Exception {
        ReaderRepositoryImpl readerRepositoryImpl = new ReaderRepositoryImpl(Reader.class);
        BookRepositoryImpl bookRepositoryImpl = new BookRepositoryImpl(Book.class);
        BookMapper bookMapper = new BookMapper();
        BorrowReportRepositoryImpl borrowReportRepositoryImpl = new BorrowReportRepositoryImpl(BorrowReport.class);
        ReaderMapper readerMapper = new ReaderMapper();
        BorrowReportMapper borrowReportMapper = new BorrowReportMapper();
        ReportService reportService = new ReportService(bookMapper, borrowReportMapper);
        ReaderService readerService = new ReaderService(readerRepositoryImpl, readerMapper);
        BorrowReportService borrowReportService = new BorrowReportService(borrowReportMapper, borrowReportRepositoryImpl);
        BookService bookService = new BookService(bookRepositoryImpl, bookMapper, borrowReportRepositoryImpl, readerRepositoryImpl);

        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        ObjectMapper objectMapper = new ObjectMapper();
        ReaderServlet readerServlet = new ReaderServlet(readerService, objectMapper);
        ReaderGetBookServlet readerGetBookServlet = new ReaderGetBookServlet(bookService,
                readerService, borrowReportService, objectMapper);
        ReaderReturnBookServlet readerReturnBookServlet = new ReaderReturnBookServlet(bookService);
        handler.addServlet(new ServletHolder(readerServlet), "/api/v1/reader");
        handler.addServlet(new ServletHolder(readerGetBookServlet), "/api/v1/readerId/bookId");
        handler.addServlet(new ServletHolder(readerReturnBookServlet), "/api/v1/reportId");
        server.setHandler(handler);

        server.start();
        server.join();
    }
}
