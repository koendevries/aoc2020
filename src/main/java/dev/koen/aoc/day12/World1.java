package dev.koen.aoc.day12;

import dev.koen.aoc.common.Coordinate;

class World1 implements World {

    private Coordinate position;
    private Direction direction;

    World1(Coordinate position, Direction direction) {
        this.position = position;
        this.direction = direction;
    }

    public void turnLeft(int degrees) {
        direction = direction.left(degrees);
    }

    public void turnRight(int degrees) {
        direction = direction.right(degrees);
    }

    public void move(int distance) {
        move(direction, distance);
    }

    public void move(Direction direction, int distance) {
        position = position.transpose(direction.stepOfSize(distance));
    }

    public int manhattanDistance() {
        return Math.abs(position.x()) + Math.abs(position.y());
    }
}
