package dev.koen.aoc.day6;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

class GroupAnswers {
    private final List<String> resultsPerUser;

    GroupAnswers(String[] resultsPerUser) {
        this.resultsPerUser = Arrays.asList(resultsPerUser);
    }

    Stream<Character> distinct() {
        return resultsPerUser.stream()
                .flatMapToInt(String::chars)
                .distinct()
                .mapToObj(i -> (char) i);
    }

    Stream<Character> intersection() {
        return resultsPerUser.get(0).chars()
                .mapToObj(i -> (char) i)
                .filter(this::allAnswered);
    }

    private boolean allAnswered(char c) {
        return resultsPerUser.stream()
                .allMatch(s -> s.contains(String.valueOf(c)));
    }
}
