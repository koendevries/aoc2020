package dev.koen.aoc.day12;

import dev.koen.aoc.common.Coordinate;

import java.util.Arrays;

enum Direction {
    NORTH(0, new Coordinate(0, 1)),
    EAST(90, new Coordinate(1, 0)),
    SOUTH(180, new Coordinate(0, -1)),
    WEST(270, new Coordinate(-1, 0));

    public static final int MAX = 360;

    final int degrees;
    final Coordinate step;

    Direction(int degrees, Coordinate step) {
        this.degrees = degrees;
        this.step = step;
    }

    Direction left(int degrees) {
        return Arrays.stream(values())
                .filter(v -> v.degrees == calculateLeft(degrees) % MAX)
                .findAny()
                .orElseThrow();
    }

    Direction right(int degrees) {
        return Arrays.stream(values())
                .filter(v -> v.degrees == (this.degrees + degrees) % MAX)
                .findAny()
                .orElseThrow();
    }

    private int calculateLeft(int degrees) {
        return degrees > this.degrees
                ? (MAX + this.degrees - degrees) % MAX
                : this.degrees - degrees;
    }

    Coordinate stepOfSize(int size) {
        return new Coordinate(step.x() * size, step.y() * size);
    }
}
