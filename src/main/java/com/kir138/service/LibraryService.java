package com.kir138.service;

import com.kir138.connect.HibernateUtil;
import com.kir138.entity.Book;
import com.kir138.entity.BorrowReport;
import com.kir138.entity.Reader;
import com.kir138.repository.BookRepository;
import com.kir138.repository.BorrowReportRepository;
import com.kir138.repository.ReaderRepository;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
public class LibraryService {
    private final ReaderRepository readerRepository;
    private final BookRepository bookRepository;
    private final BorrowReportRepository borrowReportRepository;

    /**
     * Метод для того, чтобы читатель мог брать книги (обновление таблицы borrowedBooks).
     */
    public void updateBorrowedBooks(Long readId, Long bookId) {

        Optional<Reader> reader = readerRepository.findById(readId);
        Optional<Book> book = bookRepository.findById(bookId);

        if (book.isEmpty()) {
            System.out.println("книга не найдена");
            return;
        }

        Reader reader1 = reader.get();
        Book book1 = book.get();

        if (book1.getReader() != null) {
            System.out.println("книга уже выдана");
            return;
        }

        book1.setReader(reader1);
        bookRepository.update(book1);

        Optional<BorrowReport> borrowReport = borrowReportRepository.findExistingBorrowReport(bookId);

        if (borrowReport.isPresent()) {
            BorrowReport borrowReport1 = borrowReport.get();
            borrowReport1.setReturnStatus(false);
            borrowReport1.setReaderId(reader1);
            borrowReportRepository.update(borrowReport1);
        } else {
            BorrowReport newBorrowReport = BorrowReport.builder()
                    .borrowDate(LocalDate.now())
                    .readerId(reader1)
                    .bookId(book1)
                    .returnStatus(false)
                    .build();
            borrowReportRepository.save(newBorrowReport);
        }
    }

    /**
     * Метод для возврата книги (обновление статуса returnStatus).
     */
    public void updateReturnStatus(Long reportId) {

        Optional<BorrowReport> borrowReport = borrowReportRepository.findById(reportId);

        if (borrowReport.isEmpty()) {
            System.out.println("такого id нет в системе");
            return;
        }

        BorrowReport borrowReport1 = borrowReport.get();
        borrowReport1.setReturnStatus(true);
        borrowReport1.setReaderId(null);
        borrowReportRepository.update(borrowReport1);

        Book book = borrowReport1.getBookId();
        book.setReader(null);
        bookRepository.update(book);
    }

    /**
     * Метод для получения списка всех книг, взятых читателями за последний месяц.
     */
    public List<BorrowReport> borrowedBooksLastMonth() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate localDate = LocalDate.now().minusMonths(1);
            String hql = "from BorrowReport br where br.borrowDate >= :date";
            Query<BorrowReport> query = session.createQuery(hql, BorrowReport.class);
            query.setParameter("date", localDate);
            return query.list();
        }
    }

    /**
     * Метод для получения списка книг, которые находятся на руках у читателей
     * дольше двух недель (учитывая дату выдачи).
     */
    public List<BorrowReport> borrowedBooksFourteenDays() {
        try(Session session = HibernateUtil.getSessionFactory().openSession()) {
            LocalDate localDate = LocalDate.now().minusDays(14);
            String hql = "from BorrowReport br where br.borrowDate <= :date and br.returnStatus = false";
            Query<BorrowReport> query = session.createQuery(hql, BorrowReport.class);
            query.setParameter("date", localDate);
            return query.list();
        }
    }

    /**
     * Продемонстрируйте поведение ленивой загрузки в методе для получения всех читателей и их книг
     * (необходимо показать, что книги загружаются только при непосредственном доступе к ним).
     */
    public void displayReadersAndBooks() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Reader> readers = session.createQuery("FROM Reader", Reader.class).list();
            for (Reader reader : readers) {
                System.out.println("Читатель: " + reader.getName());
                // Книги загружаются только при доступе к borrowedBooks
                for (Book book : reader.getBorrowedBooks()) {
                    System.out.println("\tКнига: " + book.getTitle());
                }
            }
        }
    }
}
