package com.kir138.mapper;

import com.kir138.model.dto.BookRegistrationRq;
import com.kir138.model.entity.Book;
import com.kir138.model.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDto toBook(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
    }

    public Book toBook(BookRegistrationRq book) {
        return Book.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
    }
}
