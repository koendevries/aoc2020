package dev.koen.aoc.day4.passportproperties;

public sealed interface PassportProperty permits YearProperty, PassportIdProperty, EyeColorProperty, HairColorProperty, HeightProperty, CountryIdProperty {
    boolean isValid();
}


