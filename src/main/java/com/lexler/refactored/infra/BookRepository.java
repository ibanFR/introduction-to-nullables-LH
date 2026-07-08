package com.lexler.refactored.infra;

import com.lexler.refactored.domain.Book;
import com.lexler.refactored.domain.Borrower;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// high level infrastructure wrapper - operates in Books
public class BookRepository {

    private final Jdbc.DataSourceWrapper dataSource;

    private BookRepository(Jdbc.DataSourceWrapper dataSource) {
        this.dataSource = dataSource;
    }

    public static BookRepository create(DataSource dataSource) {
        return new BookRepository(Jdbc.create(dataSource));
    }

    public static BookRepository createNull(Book... borrowedBooks) {
        return new BookRepository(Jdbc.createNull(rowsFor(borrowedBooks)));
    }

    public static BookRepository createNullDatabaseDown() {
        return new BookRepository(Jdbc.createNullDown());
    }

    public List<Book> borrowedBooks() {
        try (Jdbc.ConnectionWrapper connection = dataSource.getConnection();
             Jdbc.StatementWrapper statement = connection.prepareStatement(
                 "SELECT title, borrower_name, borrower_email, due_date FROM books")) {
            Jdbc.ResultSetWrapper resultSet = statement.executeQuery();

            List<Book> books = new ArrayList<>();
            while (resultSet.next()) {
                books.add(readBook(resultSet));
            }
            return books;
        } catch (SQLException e) {
            throw new RuntimeException("Could not read borrowed books", e);
        }
    }

    private static List<Map<String, Object>> rowsFor(Book... books) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Book book : books) {
            rows.add(Map.of(
                "title", book.title(),
                "borrower_name", book.borrower().name(),
                "borrower_email", book.borrower().email(),
                "due_date", Date.valueOf(book.dueDate())));
        }
        return rows;
    }

    private static Book readBook(Jdbc.ResultSetWrapper resultSet) throws SQLException {
        Borrower borrower = new Borrower(
            resultSet.getString("borrower_name"),
            resultSet.getString("borrower_email"));
        return new Book(
            resultSet.getString("title"),
            borrower,
            resultSet.getDate("due_date").toLocalDate());
    }
}
