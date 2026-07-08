package com.lexler.legacy;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Library {

    private final Loans loans;
    private final Printer printer;

    public Library(DataSource dataSource) {
        this.loans = new Loans(dataSource);
        this.printer = new Printer();
    }

    public int overdueCount() {
        return loans.overdue(LocalDate.now()).size();
    }

    public void remindOverdue() {
        LocalDate today = LocalDate.now();
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
