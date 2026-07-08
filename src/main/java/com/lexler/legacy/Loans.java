package com.lexler.legacy;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loans {

    private static final int GRACE_PERIOD_DAYS = 90;

    private final DataSource dataSource;

    public Loans(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> overdue(LocalDate today) {
        List<Book> overdue = new ArrayList<>();
        for (Book book : borrowedBooks()) {
            if (isPastGracePeriod(book, today)) {
                overdue.add(book);
            }
        }
        return overdue;
    }

    private static boolean isPastGracePeriod(Book book, LocalDate today) {
        return book.dueDate().plusDays(GRACE_PERIOD_DAYS).isBefore(today);
    }

    private List<Book> borrowedBooks() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                 "SELECT title, borrower_name, borrower_email, due_date FROM books")) {
            ResultSet resultSet = statement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(readBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Could not read borrowed books", e);
        }
    }

    private static Book readBook(ResultSet resultSet) throws SQLException {
        Borrower borrower = new Borrower(
            resultSet.getString("borrower_name"),
            resultSet.getString("borrower_email"));
        return new Book(
            resultSet.getString("title"),
            borrower,
            resultSet.getDate("due_date").toLocalDate());
    }
}
