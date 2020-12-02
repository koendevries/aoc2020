package dev.koen.aoc.day2;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.IntPair;

import java.nio.file.Path;

public class Two {

    public static void main(String[] args) {
        final var count = FileReader.readLines(Path.of("src/test/resources/input-two.txt"))
                .stream()
                .filter(Two::hasValidPassword)
                .count();

        System.out.println(count);
    }

    private static boolean hasValidPassword(String line) {
        final var parts = line.split(" ");
        final IntPair pair = readPair(parts[0]);
        final char c = parts[1].charAt(0);

        return parts[2].charAt(pair.left() - 1) == c
                ^ parts[2].charAt(pair.right() - 1) == c;
    }

    private static IntPair readPair(String part) {
        final var numbers = part.split("-");
        final var left = Integer.parseUnsignedInt(numbers[0]);
        final var right = Integer.parseUnsignedInt(numbers[1]);
        return new IntPair(left, right);
    }
}
