package dev.koen.aoc.day4;

import java.util.Collection;
import java.util.Map;

public class Passport {

    final Map<String, String> properties;

    public Passport(Map<String, String> properties) {
        this.properties = properties;
    }


    public boolean hasProperties(Collection<String> propertyNames) {
        return properties.keySet().containsAll(propertyNames);
    }

    public String get(String propertyName) {
        return properties.get(propertyName);
    }

}
