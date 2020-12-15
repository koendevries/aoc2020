package dev.koen.aoc.day12;

import dev.koen.aoc.common.Coordinate;

public class World2 implements World {

    private Coordinate position;
    private Coordinate relativeWaypoint;

    World2(Coordinate position, Coordinate relativeWaypoint) {
        this.position = position;
        this.relativeWaypoint = relativeWaypoint;
    }

    public void turnLeft(int degrees) {
        relativeWaypoint = relativeWaypoint.rotate(-degrees);
    }

    public void turnRight(int degrees) {
        relativeWaypoint = relativeWaypoint.rotate(degrees);
    }

    public void move(int distance) {
        final var step = relativeWaypoint.multiply(distance);

        position = position.transpose(step);
    }

    public void move(Direction direction, int distance) {
        relativeWaypoint = relativeWaypoint.transpose(direction.stepOfSize(distance));
    }

    public int manhattanDistance() {
        return Math.abs(position.x()) + Math.abs(position.y());
    }

    @Override
    public String toString() {
        return "World2{" +
                "position=" + position +
                ", relativeWaypoint=" + relativeWaypoint +
                '}';
    }
}
