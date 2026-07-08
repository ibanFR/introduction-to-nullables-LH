package com.lexler.refactored;

import com.lexler.refactored.infra.BookRepository;
import com.lexler.refactored.domain.Book;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loans {

    private static final int GRACE_PERIOD_DAYS = 90;

    private final BookRepository bookRepository;

    private Loans(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public static Loans create(DataSource dataSource) {
        return new Loans(BookRepository.create(dataSource));
    }

    public static Loans createNull(Book... borrowedBooks) {
        return new Loans(BookRepository.createNull(borrowedBooks));
    }

    public static Loans createNullDatabaseDown() {
        return new Loans(BookRepository.createNullDatabaseDown());
    }

    public List<Book> overdue(LocalDate today) {
        List<Book> overdue = new ArrayList<>();
        for (Book book : bookRepository.borrowedBooks()) {
            if (isPastGracePeriod(book, today)) {
                overdue.add(book);
            }
        }
        return overdue;
    }

    private static boolean isPastGracePeriod(Book book, LocalDate today) {
        return book.dueDate().plusDays(GRACE_PERIOD_DAYS).isBefore(today);
    }
}
