package dev.koen.aoc.day4.passportproperties;

import java.util.List;

public final class EyeColorProperty implements PassportProperty {

    public static final List<String> EYE_COLORS = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
    private final String color;

    public EyeColorProperty(String color) {
        this.color = color;
    }

    @Override
    public boolean isValid() {
        return EYE_COLORS.contains(color);
    }
}
