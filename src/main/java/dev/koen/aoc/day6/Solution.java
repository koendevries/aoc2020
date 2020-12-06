package dev.koen.aoc.day6;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        final var groups = Arrays.stream(FileReader.read(Path.of("src/test/resources/input-six.txt"))
                .split("\n\n"))
                .map(groupstring -> groupstring.split("\n"))
                .collect(Collectors.toList());

        final var sumOfAnyones = groups.stream()
                .map(distinctChars())
                .mapToInt(Set::size)
                .sum();

        final var sumOfEveryones = groups.stream()
                .map(charsPresentInEach())
                .mapToInt(Set::size)
                .sum();

        System.out.println(sumOfAnyones);
        System.out.println(sumOfEveryones);
    }

    private static Function<String[], Set<Character>> charsPresentInEach() {
        return items -> {
            final var chars = items[0];
            return chars.chars().filter(charPresentInEach(items))
                    .mapToObj(i -> (char) i)
                    .collect(Collectors.toSet());
        };
    }

    private static IntPredicate charPresentInEach(String[] items) {
        return i -> {
            char c = (char) i;
            return Arrays.stream(items)
                    .allMatch(s -> s.contains(String.valueOf(c)));
        };
    }

    private static Function<String[], Set<Character>> distinctChars() {
        return items -> Arrays.stream(items)
                .flatMapToInt(String::chars)
                .distinct()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toSet());
    }
}
