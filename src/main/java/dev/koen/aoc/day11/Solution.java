package dev.koen.aoc.day11;

import dev.koen.aoc.common.Coordinate;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Solution {

    public static void main(String[] args) {
        final var lines = new ArrayList<>(FileReader.readLines(Path.of("src/test/resources/input-eleven.txt")));

        final var collectionOfSeats = IntStream.range(0, lines.size())
                .mapToObj(row -> readSeats(row).apply(lines.get(row)))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        var currentLayout = new SeatLayout(collectionOfSeats);
        Optional<SeatLayout> nextLayout = Optional.of(currentLayout);
        do {
            currentLayout = nextLayout.orElseThrow();
            nextLayout = currentLayout.next();
        } while (nextLayout.isPresent());

        System.out.println(currentLayout.occupied());

    }

    private static Function<String, List<Seat>> readSeats(int row) {
        return seats -> IntStream.range(0, seats.length())
                .mapToObj(column -> readSeat(new Coordinate(column, row)).apply(seats.charAt(column)))
                .collect(Collectors.toList());
    }

    private static IntFunction<Seat> readSeat(Coordinate coordinate) {
        return seat -> {
            char c = (char) seat;
            return switch (c) {
                case 'L' -> new Seat(coordinate, State.EMPTY);
                case '#' -> new Seat(coordinate, State.OCCUPIED);
                case '.' -> new Seat(coordinate, State.FLOOR);
                default -> throw new IllegalArgumentException("Unable to read seat");
            };
        };
    }
}

