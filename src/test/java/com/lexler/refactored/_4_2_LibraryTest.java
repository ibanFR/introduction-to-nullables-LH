package com.lexler.refactored;

import com.lexler.refactored.domain.Book;
import com.lexler.refactored.domain.Borrower;
import com.lexler.refactored.util.OutputTracker;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class _4_2_LibraryTest {

    // Your toolbox:
    //   printer.trackPrintedLines()   starts tracking, returns OutputTracker<String>

    /*
       TODO:
       Uncomment the tests and make them pass:
         - add trackPrintedReminders() to Library
         - inside: tell the printer to track its printed lines
           and return the tracker it gives you
    */

    private static final LocalDate TODAY = LocalDate.of(2026, 7, 1);
    private static final LocalDate DUE_LAST_YEAR = TODAY.minusYears(1);
    private static final LocalDate DUE_NEXT_WEEK = TODAY.plusDays(7);

    private static final Borrower TOM = new Borrower("Tom", "tom@example.com");
    private static final Borrower ANNA = new Borrower("Anna", "anna@example.com");

    @Test
    void printsTheReminderLetter() {
//        Book overdueBook = new Book("Solaris", TOM, DUE_LAST_YEAR);
//        Library library = Library.createNull(TODAY, overdueBook);
//        OutputTracker<String> printed = library.trackPrintedReminders();
//
//        library.remindOverdue();
//
//        assertThat(printed.data()).containsExactly("Tom: please return 'Solaris'");
    }

    // Bonus:
    // write missing tests for Library
}
