package com.kir138;

import com.kir138.service.BookService;
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

        //bookService.addBooks(10000);
    }
}
