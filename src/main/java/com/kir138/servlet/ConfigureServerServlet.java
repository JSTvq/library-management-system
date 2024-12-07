package com.kir138.servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.kir138.service.ReaderService;

public class ConfigureServerServlet {
    public static void configureServer(ReaderService readerService) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        ObjectMapper objectMapper = new ObjectMapper();
        ReaderServlet readerServlet = new ReaderServlet(readerService, objectMapper);
        handler.addServlet(new ServletHolder(readerServlet), "/api/v1/reader");
        server.setHandler(handler);

        server.start();
        server.join();
    }
}
