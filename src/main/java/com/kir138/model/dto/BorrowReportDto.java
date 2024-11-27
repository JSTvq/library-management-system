package com.kir138.model.dto;

import com.kir138.model.entity.Book;
import com.kir138.model.entity.Reader;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Builder
@Getter
@Setter
public class BorrowReportDto {
    private LocalDate borrowDate;
    private Reader reader;
    private Book book;
    private Boolean isReturn;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BorrowReportDto that = (BorrowReportDto) o;
        return Objects.equals(borrowDate, that.borrowDate) && Objects.equals(reader, that.reader) && Objects.equals(book, that.book) && Objects.equals(isReturn, that.isReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(borrowDate, reader, book, isReturn);
    }

    @Override
    public String toString() {
        return "BorrowReportDto{" +
                "borrowDate=" + borrowDate +
                ", isReturn=" + isReturn +
                '}';
    }
}
