package dev.koen.aoc.day3;

import dev.koen.aoc.common.Coordinate;

import java.util.List;
import java.util.stream.Collectors;

public class InfiniteWidthCharMap {

    private final List<String> map;

    public InfiniteWidthCharMap(List<String> rows) {
        map = rows.stream()
                .map(String::strip)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }

    private char getChar(Coordinate coordinate) {
        final var x = coordinate.x() % width();
        final var y = coordinate.y();
        return map.get(y).charAt(x);
    }

    private int height() {
        return map.size();
    }

    private int width() {
        return map.get(0).length();
    }

    public List<Character> walkDown(Slope slope) {
        return slope.stepsToHeight(height())
                .stream()
                .map(this::getChar)
                .collect(Collectors.toList());
    }
}
