package dev.koen.aoc.day6;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        final var groups = Arrays.stream(FileReader.read(Path.of("src/test/resources/input-six.txt"))
                .split("\n\n"))
                .map(groupstring -> groupstring.split("\n"))
                .map(GroupAnswers::new)
                .collect(Collectors.toList());

        System.out.println(sumOfAnyones(groups));
        System.out.println(sumOfEveryones(groups));
    }

    private static long sumOfEveryones(List<GroupAnswers> groups) {
        return groups.stream()
                .flatMap(GroupAnswers::intersection)
                .count();
    }

    private static long sumOfAnyones(List<GroupAnswers> groups) {
        return groups.stream()
                .flatMap(GroupAnswers::distinct)
                .count();
    }
}

