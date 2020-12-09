package dev.koen.aoc.day9;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Solution {

    public static void main(String[] args) {
        final var numbers = FileReader.readLines(Path.of("src/test/resources/input-nine.txt")).stream()
                .map(Long::parseUnsignedLong)
                .collect(Collectors.toList());

        final var cipher = new XmasCipher(numbers, 25);
        final var cipher2 = new XmasCipher2(numbers, 25);

        TimeUtil.printWithMillis(cipher, c -> {
            final var failure = c.failure();
            final var sequence = c.sequenceSummingTo(failure);
            final var min = LongStream.of(sequence).min().orElseThrow(IllegalStateException::new);
            final var max = LongStream.of(sequence).max().orElseThrow(IllegalStateException::new);

            return min + max;
        });

        TimeUtil.printWithMillis(cipher2, c -> {
            final var failure = c.failure();
            final var sequence = c.contiguousSumToEqual(failure);
            final var min = Arrays.stream(sequence).min().orElseThrow();
            final var max = Arrays.stream(sequence).max().orElseThrow();

            return min + max;
        });
    }
}

