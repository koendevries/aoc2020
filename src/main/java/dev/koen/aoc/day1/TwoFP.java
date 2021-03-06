package dev.koen.aoc.day1;

import dev.koen.aoc.common.IntPair;
import dev.koen.aoc.common.IntTriple;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TwoFP {

    public static void main(String[] args) {
        final var numbers = readNumbers(Paths.get("src/test/resources/input-one.txt"));
        findTripleWithSumOf(numbers, 2020)
                .ifPresentOrElse(
                        triple -> System.out.println(triple.multiply()),
                        () -> System.out.println("Unable to sum values to 2020"));
    }

    public static List<Integer> readNumbers(Path p) {
        try (final var lines = Files.lines(p)) {
            return lines.map(Integer::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input file in path " + p.toString());
        }
    }

    public static Optional<IntTriple> findTripleWithSumOf(List<Integer> numbers, int sum) {
        return numbers.parallelStream()
                .flatMap(first -> numbers.stream().map(second -> new IntPair(first, second)))
                .flatMap(pair -> numbers.stream().map(third -> new IntTriple(third, pair.left(), pair.right())))
                .filter(IntTriple.allAvailableIn(numbers))
                .filter(triple -> triple.sum() == sum)
                .findAny();
    }
}
