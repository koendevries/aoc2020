package dev.koen.aoc.day4.passportproperties;

public final class PassportIdProperty implements PassportProperty {
    private final String id;

    public PassportIdProperty(String id) {
        this.id = id;
    }

    @Override
    public boolean isValid() {
        return id.length() == 9 && id.chars().allMatch(Character::isDigit);
    }
}
