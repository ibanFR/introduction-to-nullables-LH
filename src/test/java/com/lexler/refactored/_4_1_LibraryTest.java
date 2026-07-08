package com.lexler.refactored;

import com.lexler.refactored.domain.Book;
import com.lexler.refactored.domain.Borrower;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class _4_1_LibraryTest {

    // Your toolbox:
    //   Loans.createNull(books...)
    //   Clock.createNull(today)

    /*
       TODO:
       Uncomment the test and make it pass:
         - add a second overloaded createNull: createNull(today, books...)
         - pass books to Loans.createNull
         - pass date to clock
    */

    private static final LocalDate TODAY = LocalDate.of(2026, 7, 1);
    private static final LocalDate DUE_LAST_YEAR = TODAY.minusYears(1);

    private static final Borrower TOM = new Borrower("Tom", "tom@example.com");

    @Test
    void oneOverdueBook() {
//        Book overdueBook = new Book("Solaris", TOM, DUE_LAST_YEAR);
//        Library library = Library.createNull(TODAY, overdueBook);
//
//        int overdueCount = library.overdueCount();
//
//        assertThat(overdueCount).isEqualTo(1);
    }
}
