package com.lexler.refactored.infra;

import java.time.LocalDate;

public class Clock {

    private final Time time;

    private Clock(Time time) {
        this.time = time;
    }

    public static Clock create() {
        return new Clock(new SystemTime());
    }

    public static Clock createNull() {
        return createNull(LocalDate.EPOCH);
    }

    public static Clock createNull(LocalDate fixedToday) {
        return new Clock(new FixedTime(fixedToday));
    }

    public LocalDate today() {
        return time.now();
    }

    private interface Time {
        LocalDate now();
    }

    private static class SystemTime implements Time {
        @Override
        public LocalDate now() {
            return LocalDate.now();
        }
    }

    private static class FixedTime implements Time {
        private final LocalDate today;

        FixedTime(LocalDate today) {
            this.today = today;
        }

        @Override
        public LocalDate now() {
            return today;
        }
    }
}
