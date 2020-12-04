package dev.koen.aoc.day4.passportproperties;

import dev.koen.aoc.common.Range;

public record Height(int value, String unit) {

    static Height parse(String value) {
        final var height = Integer.valueOf(value.substring(0, value.length() - 2));
        final var metric = value.substring(value.length() - 2);
        return new Height(height, metric);
    }

    boolean inRange() {
        return switch (unit) {
            case "cm" -> new Range(150, 193).contains(value);
            case "in" -> new Range(59, 76).contains(value);
            default -> false;
        };
    }

}
