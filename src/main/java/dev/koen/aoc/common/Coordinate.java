package dev.koen.aoc.common;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Coordinate(int x, int y) {

    public static final int TURN_SIZE = 90;
    public static final int MAX_DEGREES = 360;

    public Stream<Coordinate> adjacent() {
        return IntStream.range(x - 1, x + 2)
                .mapToObj(column -> new Coordinate(column, this.y))
                .flatMap(c -> IntStream.range(y - 1, y + 2).mapToObj(y -> new Coordinate(c.x, y)))
                .filter(c -> !equals(c));
    }

    public Coordinate transpose(Coordinate coordinate) {
        return new Coordinate(x + coordinate.x, y + coordinate.y);
    }

    public Coordinate multiply(int factor) {
        return new Coordinate(x * factor, y * factor);
    }

    public Coordinate rotate(int degrees) {
        if (degrees == 0) {
            return this;
        }
        return rotate90R(timesToRotate90R(degrees));
    }

    private int timesToRotate90R(int degrees) {
        return degrees > 0
                ? degrees / TURN_SIZE
                : (MAX_DEGREES + degrees) / TURN_SIZE;
    }

    private Coordinate rotate90R(int times) {
        if (times == 0) {
            return this;
        }
        return this.rotate90R().rotate90R(times - 1);
    }

    private Coordinate rotate90R() {
        return new Coordinate(y, x * -1);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
