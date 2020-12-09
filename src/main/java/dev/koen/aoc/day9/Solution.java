package dev.koen.aoc.day9;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Solution {

    public static void main(String[] args) {
        final var numbers = FileReader.readLines(Path.of("src/test/resources/input-nine.txt")).stream()
                .map(Long::parseUnsignedLong)
                .collect(Collectors.toList());

        final var cipher = new XmasCipher(numbers, 25);

        final var firstFailure = TimeUtil.printWithMillis(cipher, c -> c.failures().findFirst().orElseThrow(IllegalStateException::new));

        TimeUtil.printWithMillis(cipher, c -> {
            final var sequence = c.sequenceSummingTo(firstFailure);
            final var min = LongStream.of(sequence).min().orElseThrow(IllegalStateException::new);
            final var max = LongStream.of(sequence).max().orElseThrow(IllegalStateException::new);

            return min + max;
        });
    }
}

