package com.kir138.model.dto;

import com.kir138.model.entity.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
public class ReaderDto {
    private String name;
    private String email;
    @Builder.Default
    private List<Book> borrowedBooks = new ArrayList<>();
}
