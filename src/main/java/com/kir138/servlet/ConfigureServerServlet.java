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
import org.eclipse.jetty.server.ServerConnector;
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
        BookService bookService = new BookService(bookRepositoryImpl, bookMapper, borrowReportRepositoryImpl, readerRepositoryImpl);

        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setHost("0.0.0.0");
        connector.setPort(8080);
        server.addConnector(connector);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        ObjectMapper objectMapper = new ObjectMapper();
        ReaderController readerController = new ReaderController(readerService, objectMapper);
        BorrowController borrowController = new BorrowController(bookService, objectMapper);
        ReturnController returnController = new ReturnController(bookService, objectMapper);
        BorrowReportController borrowReportController = new BorrowReportController(reportService, objectMapper);
        BookController bookController = new BookController(bookService, objectMapper);

        handler.addServlet(new ServletHolder(readerController), "/api/v1/readers");
        handler.addServlet(new ServletHolder(bookController), "/api/v1/books");
        handler.addServlet(new ServletHolder(borrowController), "/api/v1/borrows");
        handler.addServlet(new ServletHolder(returnController), "/api/v1/returns");
        handler.addServlet(new ServletHolder(borrowReportController), "/api/v1/borrow-reports");

        server.setHandler(handler);

        server.start();
        server.join();
    }
}
