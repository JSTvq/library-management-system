package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.mapper.BorrowReportMapper;
import com.kir138.model.dto.BorrowReportDto;
import com.kir138.model.dto.BorrowReportRegistrationRq;
import com.kir138.model.entity.Book;
import com.kir138.model.dto.BookDto;
import com.kir138.mapper.BookMapper;
import com.kir138.model.entity.BorrowReport;
import com.kir138.model.entity.Reader;
import com.kir138.repository.BorrowReportRepositoryImpl;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final BookMapper bookMapper;
    private final BorrowReportMapper borrowReportMapper;

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

    /**
     * Отчет, сколько книг взял читатель за определенные период (fromDate, toDate, readerId)
     */
    public List<BorrowReportDto> findByReaderIdAndBorrowDateBetween(Long readerId, LocalDate fromDate,
                                                                 LocalDate toDate) {

        try (EntityManager entityManager = HibernateUtil.getEntityManager()) {
            return entityManager.createQuery("select br from BorrowReport br " +
                            "where br.reader.id = :reader_id and br.borrowDate " +
                            "between :fromDate and :toDate", BorrowReport.class)
                    .setParameter("reader_id", readerId)
                    .setParameter("fromDate", fromDate)
                    .setParameter("toDate", toDate)
                    .getResultList()
                    .stream()
                    .map(borrowReportMapper::toBorrowReport)
                    .toList();
        }




    }
}
