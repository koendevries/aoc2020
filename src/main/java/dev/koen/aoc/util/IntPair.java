package dev.koen.aoc.util;

public record IntPair(int left, int right) {

    public int multiply() {
        return left * right;
    }

}
