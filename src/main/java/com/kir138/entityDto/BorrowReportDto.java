package com.kir138.entityDto;

import com.kir138.entity.Book;
import com.kir138.entity.Reader;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class BorrowReportDto {
    private LocalDate borrowDate;
    private Reader readerId;
    private Book bookId;
    private Boolean returnStatus;
}
