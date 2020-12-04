package dev.koen.aoc.day4.passportproperties;

import dev.koen.aoc.common.Range;

public abstract sealed class YearProperty implements PassportProperty permits BirthYearProperty, IssueYearProperty, ExpirationYearProperty {

    private final int year;
    private final Range range;

    public YearProperty(String value, Range range) {
        year = Integer.parseInt(value);
        this.range = range;
    }

    @Override
    public boolean isValid() {
        return range.contains(year);
    }
}
