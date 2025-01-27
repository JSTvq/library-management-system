package com.kir138;

import com.kir138.service.BookService;
import com.kir138.testTransaction.PendingBookProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        //ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        SpringApplication.run(Main.class, args);

        //BookService bookService = context.getBean(BookService.class);
        //PendingBookProcessor pendingBookProcessor = context.getBean(PendingBookProcessor.class);

        //bookService.addBooks(10000);

    }
}
