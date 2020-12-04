package dev.koen.aoc.day4.passportproperties;

import dev.koen.aoc.common.Range;

public final class BirthYearProperty extends YearProperty {
    public BirthYearProperty(String year) {
        super(year, new Range(1920, 2002));
    }
}
