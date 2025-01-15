package com.kir138.model.dto;

import com.kir138.model.entity.Reader;
import lombok.*;
import java.util.Objects;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRegistrationRq {
    private String title;
    private String author;
    private Integer year;
    private Reader reader;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRegistrationRq that = (BookRegistrationRq) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(year, that.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, year);
    }

    @Override
    public String toString() {
        return "BookRegistrationRq{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
