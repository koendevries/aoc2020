package dev.koen.aoc.day3;

import dev.koen.aoc.common.Coordinate;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public record Slope(int horizontal, int vertical) {

    public List<Coordinate> stepsToHeight(final int height) {
        return IntStream.range(0, height)
                .mapToObj(i -> new Coordinate(i * horizontal, i * vertical))
                .filter(coordinate -> coordinate.y() < height)
                .collect(Collectors.toList());
    }
}
