package dev.koen.aoc.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public record IntTriple(int a, int b, int c) {

    public int sum() {
        return a + b + c;
    }

    public int multiply() {
        return a * b * c;
    }

    public static Predicate<IntTriple> allAvailableIn(List<Integer> numbers) {
        final var remainingNumbers = new ArrayList<>(numbers);

        return triple -> Objects.nonNull(remainingNumbers.remove(Integer.valueOf(triple.a())))
                && Objects.nonNull(remainingNumbers.remove(Integer.valueOf(triple.b())))
                && Objects.nonNull(remainingNumbers.remove(Integer.valueOf(triple.c())));
    }
}
