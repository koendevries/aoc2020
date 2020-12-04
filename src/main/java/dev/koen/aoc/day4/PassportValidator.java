package dev.koen.aoc.day4;

import dev.koen.aoc.common.Range;

import java.util.List;

public class PassportValidator {
    public static final String CENTIMETERS = "cm";
    public static final String INCHES = "in";
    public static final Range HEIGHT_RANGE_CM = new Range(150, 193);
    public static final Range HEIGHT_RANGE_INCHES = new Range(59, 76);
    public static final Range BIRTH_YEAR_RANGE = new Range(1920, 2002);
    public static final Range ISSUE_YEAR_RANGE = new Range(2010, 2020);
    public static final Range EXPIRATION_YEAR_RANGE = new Range(2020, 2030);
    private static final List<String> REQUIRED_PROPERTIES = List.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
    private static final List<String> EYE_COLORS = List.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    private PassportValidator() {
        throw new IllegalStateException("Unable to create instance of " + getClass().getName());
    }

    public static boolean hasRequiredProperties(Passport passport) {
        return passport.hasProperties(REQUIRED_PROPERTIES);
    }

    public static boolean hasValidProperties(Passport passport) {
        return hasRequiredProperties(passport)
                && isValidYear(passport, "byr", BIRTH_YEAR_RANGE)
                && isValidYear(passport, "iyr", ISSUE_YEAR_RANGE)
                && isValidYear(passport, "eyr", EXPIRATION_YEAR_RANGE)
                && hasValidId(passport)
                && hasValidHairColor(passport)
                && hasValidHeight(passport)
                && hasValidEyeColor(passport);
    }

    private static boolean isValidYear(Passport passport, String passportPropertyName, Range yearRange) {
        final var year = Integer.valueOf(passport.get(passportPropertyName));

        return yearRange.contains(year);
    }

    private static boolean hasValidId(Passport passport) {
        final var id = passport.get("pid");

        return id.length() == 9 && id.chars().allMatch(Character::isDigit);
    }

    private static boolean hasValidHairColor(Passport passport) {
        final var hairColor = passport.get("hcl");
        final var numberOfValidCharacters = hairColor.substring(1).chars()
                .filter(charAsInt -> Character.isDigit(charAsInt) || "abcdef".contains(String.valueOf((char) charAsInt)))
                .count();

        return hairColor.length() == 7
                && hairColor.charAt(0) == '#'
                && numberOfValidCharacters == 6;
    }

    private static boolean hasValidHeight(Passport passport) {
        final var heightString = passport.get("hgt");

        if (heightString.endsWith("cm")) {
            final var height = Integer.valueOf(heightString.replace(CENTIMETERS, ""));
            return HEIGHT_RANGE_CM.contains(height);
        } else if (heightString.endsWith("in")) {
            final var height = Integer.valueOf(heightString.replace(INCHES, ""));
            return HEIGHT_RANGE_INCHES.contains(height);
        } else {
            return false;
        }
    }

    private static boolean hasValidEyeColor(Passport passport) {
        return EYE_COLORS.contains(passport.get("ecl"));
    }
}
