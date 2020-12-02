package dev.koen.aoc.day2;

import dev.koen.aoc.common.Range;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Predicate;

public class One {

    public static void main(String[] args) {
        final var numberOfValidPasswords = FileReader.readLines(Path.of("src/test/resources/input-two.txt"))
                .stream()
                .filter(hasValidPassword())
                .count();

        System.out.println(numberOfValidPasswords);
    }

    private static Predicate<String> hasValidPassword() {
        return line -> {
            final var parts = line.split(" ");
            final var range = readRange(parts[0]);
            final var c = parts[1].charAt(0);

            final var count = parts[2].chars()
                    .filter(current -> current == c)
                    .count();

            return range.contains(count);
        };
    }

    private static Range readRange(String part) {
        final var numbers = part.split("-");
        final var min = Integer.parseUnsignedInt(numbers[0]);
        final var max = Integer.parseUnsignedInt(numbers[1]);
        return new Range(min, max);
    }
}
