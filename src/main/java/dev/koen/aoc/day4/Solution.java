package dev.koen.aoc.day4;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static final String PASSPORT_DELIMITER = "\n\n";
    public static final String PASSPORT_PROPERTIES_DELIMITER = "\\s";
    public static final String KEY_VALUE_DELIMITER = ":";

    public static void main(String[] args) {
        final var validPassports = Arrays.stream(FileReader.read(Path.of("src/test/resources/input-four.txt"))
                .split(PASSPORT_DELIMITER))
                .map(p -> p.split(PASSPORT_PROPERTIES_DELIMITER))
                .map(asPassport())
                .filter(PassportValidator::hasValidProperties)
                .collect(Collectors.toList());

        System.out.println(validPassports.size());
    }

    private static Function<String[], Passport> asPassport() {
        return properties -> {
            final var map = Arrays.stream(properties)
                    .map(p -> p.split(KEY_VALUE_DELIMITER))
                    .collect(Collectors.toMap(p -> p[0], p -> p[1]));

            return new Passport(map);
        };

    }
}

