package com.lexler.refactored.domain;

import java.time.LocalDate;

public record Book(String title, Borrower borrower, LocalDate dueDate) {
    @Override
    public String toString() {
        return "<'%s', %s, %s>".formatted(title, borrower.name(), dueDate);
    }
}
