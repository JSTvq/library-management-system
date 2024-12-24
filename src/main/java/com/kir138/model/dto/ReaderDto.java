package com.kir138.model.dto;

import com.kir138.model.entity.Book;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {
    private Long id;
    private String name;
    private String email;
    @Builder.Default
    private List<Book> borrowedBooks = new ArrayList<>();
}
