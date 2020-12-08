package dev.koen.aoc.day6;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        final var groups = Arrays.stream(FileReader.read(Path.of("src/test/resources/input-six.txt"))
                .split("\n\n"))
                .map(groupstring -> groupstring.split("\n"))
                .map(GroupAnswers::new)
                .collect(Collectors.toList());

        TimeUtil.print(groups, sumOfAnyones());
        TimeUtil.print(groups, sumOfEveryones());
    }

    private static Function<List<GroupAnswers>, Long> sumOfEveryones() {
        return groups -> groups.stream()
                .flatMap(GroupAnswers::intersection)
                .count();
    }

    private static Function<List<GroupAnswers>, Long> sumOfAnyones() {
        return groups -> groups.stream()
                .flatMap(GroupAnswers::distinct)
                .count();
    }
}

