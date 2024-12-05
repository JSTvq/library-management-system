package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.model.entity.Book;
import com.kir138.model.dto.BookDto;
import com.kir138.mapper.BookMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ReportService {
    private final BookMapper bookMapper;

    /**
     * Метод для получения списка всех книг, взятых читателями за последний месяц.
     */
    public List<BookDto> getBooksBorrowedInLastMonth() {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            LocalDate localDate = LocalDate.now().minusMonths(1);
            String hql = "select br.book from BorrowReport br where br.borrowDate >= :date";

            return entityManager.createQuery(hql, Book.class)
                    .setParameter("date", localDate)
                    .getResultStream()
                    .map(bookMapper::toBook)
                    .toList();
        }
    }

    /**
     * Метод для получения списка книг, которые находятся на руках у читателей
     * дольше двух недель (учитывая дату выдачи).
     */
    public List<BookDto> getOverdueBooksMoreThanTwoWeeks() {
        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            LocalDate localDate = LocalDate.now().minusDays(14);
            String hql = "select br.book from BorrowReport br where br.borrowDate <= :date and br.isReturn = false";

            return entityManager.createQuery(hql, Book.class)
                    .setParameter("date", localDate)
                    .getResultStream()
                    .map(bookMapper::toBook)
                    .toList();
        }
    }
}
