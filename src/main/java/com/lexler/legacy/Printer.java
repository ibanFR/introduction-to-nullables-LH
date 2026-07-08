package com.lexler.legacy;

import java.util.List;

public class Printer {

    public void printReminder(String borrowerName, List<String> bookTitles) {
        String titles = String.join("', '", bookTitles);
        System.out.println("%s: please return '%s'".formatted(borrowerName, titles));
    }
}
