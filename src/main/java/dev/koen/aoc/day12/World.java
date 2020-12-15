package dev.koen.aoc.day12;

public interface World {

    void turnLeft(int degrees);

    void turnRight(int degrees);

    void move(int distance);

    void move(Direction direction, int distance);

    int manhattanDistance();
}
