package dev.koen.aoc.util;

public record Range(long min, long max) {
    public boolean contains(long number) {
        return number >= min && number <= max;
    }
}
