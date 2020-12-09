package dev.koen.aoc.util;

import java.util.function.Function;

public class TimeUtil {

    public static <T, R> R printWithMillis(T input, Function<T, R> function) {
        final var start = System.currentTimeMillis();
        final var output = function.apply(input);
        final var end = System.currentTimeMillis();

        System.out.println("Result: " + output + "\t\tExecution time: " + (end - start) + "ms");
        return output;
    }

    public static <T, R> R printWithNano(T input, Function<T, R> function) {
        final var start = System.nanoTime();
        final var output = function.apply(input);
        final var end = System.nanoTime();

        System.out.println("Result: " + output + "\t\tExecution time: " + (end - start) + "ns");
        return output;
    }
}
