package dev.koen.aoc.common;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coordinate(int x, int y) {

    public Stream<Coordinate> adjacent() {
        return IntStream.range(x - 1, x + 2)
                .mapToObj(column -> new Coordinate(column, this.y))
                .flatMap(c -> IntStream.range(y - 1, y + 2).mapToObj(y -> new Coordinate(c.x, y)))
                .filter(c -> !equals(c));
    }

    public Coordinate transpose(Coordinate coordinate) {
        return new Coordinate(x + coordinate.x, y + coordinate.y);
    }
}
