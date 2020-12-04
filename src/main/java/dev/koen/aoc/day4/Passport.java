package dev.koen.aoc.day4;

import dev.koen.aoc.day4.passportproperties.CountryIdProperty;
import dev.koen.aoc.day4.passportproperties.PassportProperty;

import java.util.List;

public class Passport {

    private final List<? extends PassportProperty> properties;

    Passport(List<? extends PassportProperty> properties) {
        this.properties = properties;
    }

    boolean hasRequiredProperties() {
        return properties.stream()
                .map(PassportProperty::getClass)
                .distinct()
                .filter(c -> !c.getName().equals(CountryIdProperty.class.getName())) //FIXME: NOHAXPLOX
                .count() == 7;
    }

    boolean isValid() {
        return hasRequiredProperties()
                && properties.stream().allMatch(PassportProperty::isValid);
    }
}
