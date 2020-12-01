package dev.koen.aoc.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OneFP {

    public static void main(String[] args) {
        final var numbers = readNumbers(Paths.get("src/test/resources/input-one.txt"));
        findPairSummingTo(numbers, 2020)
                .ifPresentOrElse(
                        p -> System.out.println(p.stream().reduce(1, Math::multiplyExact)),
                        () -> System.out.println("Unable to sum values to 2020"));
    }

    public static List<Integer> readNumbers(Path p) {
        try (final var lines = Files.lines(p)) {
            return lines.map(Integer::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid input file in path " + p.toString());
        }
    }

    public static Optional<List<Integer>> findPairSummingTo(List<Integer> numbers, int sum) {
        return numbers.stream()
                .filter(first -> numbers.stream()
                        .filter(second -> !second.equals(first))
                        .anyMatch(second -> first + second == sum)
                ).findAny()
                .map(first -> List.of(first, sum - first));
    }
}
