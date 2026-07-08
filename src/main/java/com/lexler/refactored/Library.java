package com.lexler.refactored;

import com.lexler.refactored.domain.Book;
import com.lexler.refactored.domain.Borrower;
import com.lexler.refactored.infra.Clock;
import com.lexler.refactored.infra.Printer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private final Loans loans;
    private final Printer printer;
    private final Clock clock;

    public Library(Loans loans, Printer printer, Clock clock) {
        this.loans = loans;
        this.printer = printer;
        this.clock = clock;
    }

    public int overdueCount() {
        return loans.overdue(clock.today()).size();
    }

    public void remindOverdue() {
        LocalDate today = clock.today();
        overdueTitlesByBorrower(today).forEach(
            (borrower, titles) -> printer.printReminder(borrower.name(), titles));
    }

    private Map<Borrower, List<String>> overdueTitlesByBorrower(LocalDate today) {
        Map<Borrower, List<String>> titlesByBorrower = new LinkedHashMap<>();
        for (Book book : loans.overdue(today)) {
            titlesByBorrower
                .computeIfAbsent(book.borrower(), borrower -> new ArrayList<>())
                .add(book.title());
        }
        return titlesByBorrower;
    }
}
