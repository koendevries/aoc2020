package dev.koen.aoc.day1;

import dev.koen.aoc.util.IntPair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class One {

    public static void main(String[] args) {
        final var numbers = readNumbers(Paths.get("src/test/resources/input-one.txt"));
        final var pair = findPairWithSumOf(numbers, 2020);
        System.out.println(pair.multiply());
    }

    public static List<Integer> readNumbers(Path p) {
        try (final var lines = Files.lines(p)) {
            return lines.map(Integer::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input file in path " + p.toString());
        }
    }

    public static IntPair findPairWithSumOf(List<Integer> numbers, int sum) {
        if (numbers.size() <= 1) {
            throw new IllegalArgumentException("Input should have at least 2 numbers");
        }

        for (int i = 0; i < numbers.size() - 1; i++) {
            final var first = numbers.get(i);
            final var second = sum - first;
            if (numbers.subList(i, numbers.size() - 1).contains(second)) {
                return new IntPair(first, second);
            }
        }

        throw new NoSuchElementException("No two numbers sum to " + sum);
    }
}
