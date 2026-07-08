package com.lexler.refactored.util;

import java.util.ArrayList;
import java.util.List;

public class OutputTracker<T> {

    private final List<T> data = new ArrayList<>();

    void add(T item) {
        data.add(item);
    }

    public List<T> data() {
        return List.copyOf(data);
    }
}
