package com.kir138.entityDto;

import com.kir138.entity.Reader;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BookDto {
    private String title;
    private String author;
    private Integer year;
    private Reader reader;
}
