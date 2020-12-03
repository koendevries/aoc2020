package dev.koen.aoc.common;

import dev.koen.aoc.day3.Slope;

public record Coordinate(int x, int y) {
    public Coordinate translate(int x, int y) {
        return new Coordinate(this.x + x, this.y + y);
    }

    public Coordinate translate(Slope slope) {
        return translate(slope.horizontal(), slope.vertical());
    }
}
