package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.entity.BorrowReport;
import com.kir138.entityDto.BorrowReportDto;
import com.kir138.mapper.BorrowReportMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class ReportService {
    private final BorrowReportMapper borrowReportMapper;

    /**
     * Метод для получения списка всех книг, взятых читателями за последний месяц.
     */
    //тут наверное вообще сделать неправильно. Нужно возвращать Book а не BorrowReport
    public List<BorrowReportDto> borrowedBooksLastMonth() {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            LocalDate localDate = LocalDate.now().minusMonths(1);
            String hql = "from BorrowReport br where br.borrowDate >= :date";
            TypedQuery<BorrowReport> typedQuery = entityManager.createQuery(hql, BorrowReport.class);
            typedQuery.setParameter("date", localDate);
            return typedQuery.getResultStream().map(borrowReportMapper::toBorrowReport).toList();
        }
    }

    /**
     * Метод для получения списка книг, которые находятся на руках у читателей
     * дольше двух недель (учитывая дату выдачи).
     */
    //тут тоже
    public List<BorrowReport> borrowedBooksFourteenDays() {
        try(EntityManager entityManager = HibernateUtil.getEntityManager()) {
            LocalDate localDate = LocalDate.now().minusDays(14);
            String hql = "from BorrowReport br where br.borrowDate <= :date and br.returnStatus = false";
            TypedQuery<BorrowReport> typedQuery = entityManager.createQuery(hql, BorrowReport.class);
            typedQuery.setParameter("date", localDate);
            return typedQuery.getResultList();
        }
    }
}
