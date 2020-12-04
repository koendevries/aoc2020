package dev.koen.aoc.day4.passportproperties;

import dev.koen.aoc.common.Range;

public final class ExpirationYearProperty extends YearProperty {
    public ExpirationYearProperty(String year) {
        super(year, new Range(2020, 2030));
    }
}
