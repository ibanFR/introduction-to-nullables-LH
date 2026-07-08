package com.lexler.refactored.util;

import java.util.ArrayList;
import java.util.List;

public class OutputListener<T> {

    private final List<OutputTracker<T>> trackers = new ArrayList<>();

    public void emit(T data) {
        for (OutputTracker<T> tracker : trackers) {
            tracker.add(data);
        }
    }

    public OutputTracker<T> createTracker() {
        OutputTracker<T> tracker = new OutputTracker<>();
        trackers.add(tracker);
        return tracker;
    }
}
