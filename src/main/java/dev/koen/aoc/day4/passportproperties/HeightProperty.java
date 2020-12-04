package dev.koen.aoc.day4.passportproperties;

import java.util.Optional;
import java.util.function.Predicate;

public final class HeightProperty implements PassportProperty {
    private final String value;

    public HeightProperty(String value) {
        this.value = value;
    }

    @Override
    public boolean isValid() {
        return Optional.ofNullable(value)
                .filter(hasUnit())
                .map(Height::parse)
                .filter(Height::inRange)
                .isPresent();
    }

    private Predicate<String> hasUnit() {
        return s -> s.endsWith("cm") || s.endsWith("in");
    }
}

