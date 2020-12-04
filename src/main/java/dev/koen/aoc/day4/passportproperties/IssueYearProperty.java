package dev.koen.aoc.day4.passportproperties;

import dev.koen.aoc.common.Range;

public final class IssueYearProperty extends YearProperty {
    public IssueYearProperty(String year) {
        super(year, new Range(2010, 2020));
    }
}
