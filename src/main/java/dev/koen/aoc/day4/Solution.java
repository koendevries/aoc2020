package dev.koen.aoc.day4;

import dev.koen.aoc.day4.passportproperties.*;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static final String PASSPORT_DELIMITER = "\n\n";
    public static final String PASSPORT_PROPERTIES_DELIMITER = "\\s";

    public static void main(String[] args) {
        final var validPassports = Arrays.stream(FileReader.read(Path.of("src/test/resources/input-four.txt"))
                .split(PASSPORT_DELIMITER))
                .map(p -> p.split(PASSPORT_PROPERTIES_DELIMITER))
                .map(asPassport())
                .filter(Passport::isValid)
                .collect(Collectors.toList());

        System.out.println(validPassports.size());
    }

    private static Function<String[], Passport> asPassport() {
        return properties -> {
            var passportProperties = Arrays.stream(properties)
                    .map(p -> p.split(":"))
                    .map(asProperty())
                    .collect(Collectors.toList());

            return new Passport(passportProperties);
        };
    }

    private static Function<String[], ? extends PassportProperty> asProperty() {
        return pair -> switch (pair[0]) {
            case "byr" -> new BirthYearProperty(pair[1]);
            case "iyr" -> new IssueYearProperty(pair[1]);
            case "eyr" -> new ExpirationYearProperty(pair[1]);
            case "pid" -> new PassportIdProperty(pair[1]);
            case "ecl" -> new EyeColorProperty(pair[1]);
            case "hgt" -> new HeightProperty(pair[1]);
            case "hcl" -> new HairColorProperty(pair[1]);
            case "cid" -> new CountryIdProperty();
            default -> throw new IllegalStateException("Unexpected value: " + pair[0]);
        };
    }
}

