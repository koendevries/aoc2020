package dev.koen.aoc.day2;

import dev.koen.aoc.common.IntPair;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Predicate;

public class Two {

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

            final IntPair indices = readIndices(parts[0]);
            final char c = parts[1].charAt(0);

            return parts[2].charAt(indices.left()) == c
                    ^ parts[2].charAt(indices.right()) == c;
        };
    }

    private static IntPair readIndices(String part) {
        final var numbers = part.split("-");
        final var left = Integer.parseUnsignedInt(numbers[0]) - 1;
        final var right = Integer.parseUnsignedInt(numbers[1]) - 1;
        return new IntPair(left, right);
    }
}
