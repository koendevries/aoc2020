package dev.koen.aoc.day5;

record Seat(int row, int column) {
    Integer getId() {
        return 8 * row + column;
    }
}
