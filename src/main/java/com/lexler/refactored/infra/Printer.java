package com.lexler.refactored.infra;

import com.lexler.refactored.util.OutputListener;
import com.lexler.refactored.util.OutputTracker;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

public class Printer {

    private final PrintStream out;
    private final OutputListener<String> printedLines = new OutputListener<>();

    private Printer(PrintStream out) {
        this.out = out;
    }

    public static Printer create() {
        return new Printer(System.out);
    }

    public static Printer createNull() {
        return new Printer(new PrintStream(OutputStream.nullOutputStream()));
    }

    public void printReminder(String borrowerName, List<String> bookTitles) {
        String titles = String.join("', '", bookTitles);
        print("%s: please return '%s'".formatted(borrowerName, titles));
    }

    private void print(String line) {
        out.println(line);
        printedLines.emit(line);
    }

    public OutputTracker<String> trackPrintedLines() {
        return printedLines.createTracker();
    }
}
