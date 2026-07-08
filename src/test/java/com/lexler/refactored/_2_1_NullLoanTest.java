package com.lexler.refactored;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class _2_1_NullLoanTest {

    // Your toolbox:
    //   Loans.createNull()                    loans with no books out
    //   List<Book> overdue(forDate)
    //   assertThat(...).isEmpty()

    private static final LocalDate TODAY = LocalDate.of(2026, 7, 1);

    @Test
    void noLoans() {
        // create Loans with no borrowed books

        // ask for TODAY's overdue books

        // there are none
    }
}
