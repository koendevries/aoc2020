package dev.koen.aoc.day5;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        final var usedSeatIds = FileReader.readLines(Path.of("src/test/resources/input-five.txt"))
                .stream()
                .map(asSeat())
                .map(Seat::getId)
                .collect(Collectors.toList());

        final var max = usedSeatIds.stream().mapToInt(Integer::valueOf).max().orElseThrow(IllegalStateException::new);

        IntStream.range(0, max)
                .filter(hasNeighbours(usedSeatIds))
                .filter(seatId -> !usedSeatIds.contains(seatId))
                .findAny()
                .ifPresent(System.out::println);
    }

    private static IntPredicate hasNeighbours(List<Integer> usedSeatIds) {
        return seatId -> usedSeatIds.contains(seatId - 1) && usedSeatIds.contains(seatId + 1);
    }

    private static Function<String, Seat> asSeat() {
        return s -> {
            final var binarySeat = s.chars()
                    .mapToObj(toBinaryChar())
                    .reduce("", (a, b) -> a + b);

            final var row = Integer.valueOf(binarySeat.substring(0, 7), 2);
            final var column = Integer.valueOf(binarySeat.substring(7), 2);
            return new Seat(row, column);
        };
    }

    private static IntFunction<String> toBinaryChar() {
        return number -> {
            final var c = (char) number;
            if (c == 'F' || c == 'L') {
                return "0";
            } else if (c == 'B' || c == 'R') {
                return "1";
            } else {
                throw new IllegalStateException("Unable to parse char " + c + " to binary int");
            }
        };
    }
}

