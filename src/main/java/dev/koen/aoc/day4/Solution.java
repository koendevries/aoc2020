package dev.koen.aoc.day4;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Solution {

    public static final String PASSPORT_DELIMITER = "\n\n";
    public static final String PASSPORT_PROPERTIES_DELIMITER = "\\s";
    public static final int MIN_HEIGHT_CM = 150;
    public static final int MAX_HEIGHT_CM = 193;
    public static final int MIN_HEIGHT_INCHES = 59;
    public static final int MAX_HEIGHT_INCHES = 76;
    public static final String CENTIMETERS = "cm";
    public static final String INCHES = "in";

    public static void main(String[] args) {
        final var validPassports = Arrays.stream(FileReader.read(Path.of("src/test/resources/input-four.txt"))
                .split(PASSPORT_DELIMITER))
                .map(p -> p.split(PASSPORT_PROPERTIES_DELIMITER))
                .map(asPassport())
                .filter(hasRequiredFields())
                .filter(hasValidProperties())
                .collect(Collectors.toList());

        System.out.println(validPassports.size());
    }

    private static Function<String[], Map<String, String>> asPassport() {
        return properties -> Arrays.stream(properties)
                .map(p -> p.split(":"))
                .collect(Collectors.toMap(p -> p[0], p -> p[1]));
    }

    private static Predicate<Map<String, String>> hasRequiredFields() {
        final var requiredProperties = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        return passport -> passport.keySet().containsAll(requiredProperties);
    }

    private static Predicate<Map<String, String>> hasValidProperties() {
        return isValidYear("byr", 1920, 2002)
                .and(isValidYear("iyr", 2010, 2020))
                .and(isValidYear("eyr", 2020, 2030))
                .and(hasValidId())
                .and(hasValidHairColor())
                .and(hasValidHeight())
                .and(hasValidEyeColor());
    }

    private static Predicate<Map<String, String>> isValidYear(String passportPropertyName, int start, int end) {
        return passport -> {
            final var yearString = passport.get(passportPropertyName);
            final var year = Integer.valueOf(yearString);

            return year >= start && year <= end;
        };
    }

    private static Predicate<Map<String, String>> hasValidEyeColor() {
        final var validColors = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
        return passport -> {
            final var eyeColor = passport.get("ecl");
            return validColors.contains(eyeColor);
        };
    }

    private static Predicate<Map<String, String>> hasValidId() {
        return passport -> {
            final var id = passport.get("pid");

            return id.length() == 9 && id.chars().allMatch(Character::isDigit);
        };
    }

    private static Predicate<Map<String, String>> hasValidHairColor() {
        return passport -> {
            final var hairColor = passport.get("hcl");
            final var numberOfValidCharacters = hairColor.substring(1).chars()
                    .filter(charAsInt -> Character.isDigit(charAsInt) || "abcdef".contains(String.valueOf((char) charAsInt)))
                    .count();

            return hairColor.length() == 7
                    && hairColor.charAt(0) == '#'
                    && numberOfValidCharacters == 6;
        };
    }

    private static Predicate<Map<String, String>> hasValidHeight() {
        return passport -> {
            final var heightString = passport.get("hgt");

            if (heightString.endsWith("cm")) {
                final var height = Integer.valueOf(heightString.replace(CENTIMETERS, ""));
                return height >= MIN_HEIGHT_CM && height <= MAX_HEIGHT_CM;
            } else if (heightString.endsWith("in")) {
                final var height = Integer.valueOf(heightString.replace(INCHES, ""));
                return height >= MIN_HEIGHT_INCHES && height <= MAX_HEIGHT_INCHES;
            } else {
                return false;
            }
        };
    }
}

