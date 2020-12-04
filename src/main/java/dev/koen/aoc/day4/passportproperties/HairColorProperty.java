package dev.koen.aoc.day4.passportproperties;

public final class HairColorProperty implements PassportProperty {

    private final String color;

    public HairColorProperty(String color) {
        this.color = color;
    }

    @Override
    public boolean isValid() {
        final var numberOfValidCharacters = color.substring(1).chars()
                .filter(charAsInt -> Character.isDigit(charAsInt) || "abcdef".contains(String.valueOf((char) charAsInt)))
                .count();

        return color.length() == 7
                && color.charAt(0) == '#'
                && numberOfValidCharacters == 6;
    }
}
