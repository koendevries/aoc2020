package dev.koen.aoc.util;

import java.util.function.Function;

public class TimeUtil {

    public static <T, R> void print(T input, Function<T, R> function) {
        final var start = System.currentTimeMillis();
        final var output = function.apply(input);
        final var end = System.currentTimeMillis();

        System.out.println("Result: " + output + "\t\tExecution time: " + (end - start) + "ms");
    }
}
