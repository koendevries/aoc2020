package dev.koen.aoc.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public record IntPair(int left, int right) {

    public static Predicate<IntPair> allAvailableIn(List<Integer> numbers) {
        final var remainingNumbers = new ArrayList<>(numbers);

        return pair -> Objects.nonNull(remainingNumbers.remove(Integer.valueOf(pair.left)))
                && Objects.nonNull(remainingNumbers.remove(Integer.valueOf(pair.right)));
    }

    public int multiply() {
        return left * right;
    }

    public int sum() {
        return left + right;
    }
}
