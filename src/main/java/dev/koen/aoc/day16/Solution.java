package dev.koen.aoc.day16;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        final var input = FileReader.read(Path.of("src/test/resources/input-sixteen.txt"));

        final var allowed = Arrays.stream(input.split("\\s+"))
                .filter(s -> s.contains("-"))
                .flatMapToInt(readRanges())
                .boxed()
                .collect(Collectors.toSet());

        final var values = Arrays.stream(input.split("nearby tickets:")[1].split("\n"))
                .flatMap(s -> Arrays.stream(s.split(",")))
                .map(String::strip)
                .filter(s -> !s.isBlank())
                .map(Integer::parseUnsignedInt)
                .collect(Collectors.toList());

        final var sum = values.stream()
                .filter(o -> !allowed.contains(o))
                .mapToInt(Integer::valueOf)
                .sum();

        System.out.println(sum);

    }

    private static Function<? super String, ? extends IntStream> readRanges() {
        return range -> {
            final var splitted = range.split("-");

            return IntStream.range(Integer.parseUnsignedInt(splitted[0]), Integer.parseUnsignedInt(splitted[1]) + 1);
        };
    }
}