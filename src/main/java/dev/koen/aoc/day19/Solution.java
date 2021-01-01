package dev.koen.aoc.day19;

import dev.koen.aoc.util.FileReader;
import io.vavr.collection.*;
import io.vavr.control.Option;
import io.vavr.control.Try;

import java.nio.file.Path;

public class Solution {

    private static final java.util.Map<Integer, Set<String>> memoization = new java.util.HashMap<>();

    public static void main(String[] args) {
        final var file = FileReader.read(Path.of("src/test/resources/input-nineteen.txt"))
                .split("\n\n");

        final var searchNumbers = parseSearchLine(file[0]);
        final var rules = parseRules(file[0]);

        final var endpoints = parseEndpoints(file[0]);
        memoization.putAll(endpoints.toJavaMap());
        searchNumbers.forEach(n -> search(n, rules));

        Set<String> result = HashSet.empty();
        for (Integer n : searchNumbers) {
            result = result.isEmpty()
                    ? memoization.get(n)
                    : result.flatMap(r -> memoization.get(n).map(next -> r + next));
        }

        final var messages = parseMessages(file[1]);
        System.out.println(messages.count(result::contains));

    }

    private static Set<String> search(Integer index, Set<Rule> rules) {
        if (memoization.containsKey(index)) {
            return memoization.get(index);
        }

        final var searchValue = index == 8 ? 42 : index;

        final var combinations = rules.filter(r -> r.index() == searchValue)
                .flatMap(r -> search(r.first(), rules).flatMap(f -> r.second().map(s -> search(s, rules))
                        .getOrElse(() -> HashSet.of(""))
                        .map(s -> f + s)));

        memoization.put(index, combinations);

        return combinations;
    }


    private static List<Integer> parseSearchLine(String rules) {
        return Stream.of(rules.split("\n"))
                .find(s -> s.startsWith("0"))
                .map(s -> s.split(": ")[1])
                .get()
                .transform(s -> Stream.of(s.split("\s")))
                .map(Integer::parseUnsignedInt)
                .toList();
    }

    private static List<String> parseMessages(String messages) {
        return Stream.of(messages.split("\n"))
                .map(String::strip)
                .filterNot(String::isBlank)
                .toList();
    }

    private static Set<Rule> parseRules(String rules) {
        return Stream.of(rules.split("\n"))
                .filterNot(s -> s.startsWith("0"))
                .flatMap(Rule::parse)
                .toSet();
    }

    private static Map<Integer, Set<String>> parseEndpoints(String rules) {
        return Stream.of(rules.split("\n"))
                .filter(s -> s.contains("\""))
                .map(s -> s.split(": "))
                .toMap(pair -> Integer.parseUnsignedInt(pair[0]),
                        pair -> HashSet.of(String.valueOf(pair[1].charAt(1))));
    }

}

record Rule(int index, int first, Option<Integer> second) {

    static Set<Rule> parse(String rule) {
        final var splitted = rule.split(": ");
        final var index = Integer.parseUnsignedInt(splitted[0]);

        return Stream.of(splitted[1].split("\\|"))
                .map(String::strip)
                .filterNot(pair -> pair.contains("\""))
                .map(pair -> pair.split("\s"))
                .map(pair -> new Rule(index, Integer.parseUnsignedInt(pair[0]),
                        Try.of(() -> Integer.parseUnsignedInt(pair[1])).toOption()))
                .toSet();
    }

}
