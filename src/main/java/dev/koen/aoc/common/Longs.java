package dev.koen.aoc.common;

import java.util.function.Function;

public class Longs {

    private Longs() {
        throw new IllegalStateException("Integers is a utility class");
    }

    public static Function<Long, Long> multiply(long by) {
        return number -> number * by;
    }

    public static Function<Long, Long> remaining(long divideBy) {
        return number -> number % divideBy;
    }

}
