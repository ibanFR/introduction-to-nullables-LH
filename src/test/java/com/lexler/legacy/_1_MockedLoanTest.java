package com.lexler.legacy;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class _1_MockedLoanTest {

    private static final LocalDate TODAY = LocalDate.of(2026, 7, 1);

    @Test
    void noLoans() throws Exception {
        // arrange:
        // DataSource → Connection → PreparedStatement → empty ResultSet
        DataSource dataSource = mock(DataSource.class);
        // create a mocked Connection
        // create a mocked PreparedStatement
        // create a mocked ResultSet

        // make dataSource.getConnection() return the connection: when(..).thenReturn(..)
        // make connection.prepareStatement() return the statement when called with anyString()
        // make statement.executeQuery() return the resultSet
        // the database is empty: make resultSet.next() return false

        Loans loans = new Loans(dataSource);

        // act:
        List<Book> overdueBooks = loans.overdue(TODAY);

        // assert:
        assertThat(overdueBooks).isEmpty();
    }
}
