package com.kir138.mapper;

import com.kir138.entity.Book;
import com.kir138.entity.BorrowReport;
import com.kir138.entityDto.BookDto;
import com.kir138.entityDto.BorrowReportDto;

public class BookMapper {
    public BookDto toBook(Book book) {
        return BookDto.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .year(book.getYear())
                .build();
    }
}
