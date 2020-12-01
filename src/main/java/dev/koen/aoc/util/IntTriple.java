package dev.koen.aoc.util;

public record IntTriple(int a, int b, int c) {
    public boolean isDistinct() {
        return a != b && a != c && b != c;
    }

    public int sum() {
        return a + b + c;
    }

    public int multiply() {
        return a * b * c;
    }
}
