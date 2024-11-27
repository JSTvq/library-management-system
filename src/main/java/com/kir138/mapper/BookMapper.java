package com.kir138.mapper;

import com.kir138.model.entity.Book;
import com.kir138.model.dto.BookDto;


public class BookMapper {
    public BookDto toBook(Book book) {
        return BookDto.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
    }
}
