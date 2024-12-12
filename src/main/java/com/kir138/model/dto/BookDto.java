package com.kir138.model.dto;

import com.kir138.model.entity.Reader;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Builder
@Getter
@Setter
public class BookDto {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private Reader reader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(year, bookDto.year) && Objects.equals(reader, bookDto.reader);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year, reader);
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
