package dev.koen.aoc.day11;

enum State {
    EMPTY('L'), OCCUPIED('#'), FLOOR('.');

    private final char character;

    State(char c) {
        this.character = c;
    }

    char character() {
        return character;
    }
}
