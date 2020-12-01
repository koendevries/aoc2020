package dev.koen.aoc.day1;

import dev.koen.aoc.util.IntTriple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class Two {

    public static void main(String[] args) {
        final var numbers = readNumbers(Paths.get("src/test/resources/input-one.txt"));
        final var triple = findTripleWithSumOf(numbers, 2020);
        System.out.println(triple.multiply());
    }

    public static List<Integer> readNumbers(Path p) {
        try (final var lines = Files.lines(p)) {
            return lines.map(Integer::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input file in path " + p.toString());
        }
    }

    public static IntTriple findTripleWithSumOf(List<Integer> numbers, int sum) {
        if (numbers.size() <= 2) {
            throw new IllegalArgumentException("Input should have at least 2 numbers");
        }

        for (int i = 0; i < numbers.size() - 2; i++) {
            for (int j = i + 1; j < numbers.size() - 1; j++) {
                final var first = numbers.get(i);
                final var second = numbers.get(j);
                final var third = sum - first - second;
                if (numbers.subList(j, numbers.size() - 1).contains(third)) {
                    return new IntTriple(first, second, third);
                }
            }
        }

        throw new NoSuchElementException("No three numbers sum to " + sum);
    }

}
