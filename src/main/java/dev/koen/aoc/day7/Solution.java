package dev.koen.aoc.day7;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Solution {

    public static final Color SHINY_GOLD = new Color("shiny gold");

    public static void main(String[] args) {
        final var ruleMap = FileReader.readLines(Path.of("src/test/resources/input-seven.txt"))
                .stream()
                .map(readRule())
                .collect(Collectors.toMap(Rule::color, Rule::bags));

        final var rules = new Rules(ruleMap);

        TimeUtil.printWithMillis(rules, r -> r.findAllContaining(SHINY_GOLD).size());
        TimeUtil.printWithMillis(rules, r -> r.numberOfBagsInside(SHINY_GOLD));
    }

    private static Function<String, Rule> readRule() {
        return rule -> {
            final var splitted = rule
                    .replaceAll("bags", "")
                    .replaceAll("bag", "")
                    .split("contain");

            final var color = new Color(splitted[0].strip());

            final var bags = Arrays.stream(splitted[1].split(","))
                    .filter(s -> !s.contains("no other"))
                    .map(readBags())
                    .collect(Collectors.toSet());

            return new Rule(color, bags);
        };
    }

    private static Function<String, Bags> readBags() {
        return s -> {
            final var words = s.strip().split(" ");
            final var amount = Integer.parseInt(words[0]);

            final var color = Arrays.asList(words)
                    .subList(1, words.length)
                    .stream()
                    .map(String::strip)
                    .filter(v -> !".".equals(v))
                    .reduce("", (a, b) -> a + " " + b)
                    .strip();

            return new Bags(new Color(color), amount);
        };
    }
}

record Color(String color) {
}

record Bags(Color color, int amount) {
}

record Rule(Color color, Set<Bags> bags) {
}

record Rules(Map<Color, Set<Bags>> rules) {

    int numberOfBagsInside(Color c) {
        final var bagAmounts = rules.get(c);
        if (bagAmounts.isEmpty()) {
            return 0;
        }

        return bagAmounts.stream()
                .mapToInt(b -> b.amount() + b.amount() * numberOfBagsInside(b.color()))
                .sum();
    }

    Set<Color> findAllContaining(Color color) {
        return rules.keySet().stream()
                .filter(c -> !color.equals(c))
                .filter(contains(color))
                .collect(Collectors.toSet());
    }

    Predicate<Color> contains(Color search) {
        return bag -> {
            if (bag.equals(search)) {
                return true;
            }
            return rules.get(bag)
                    .stream()
                    .map(Bags::color)
                    .anyMatch(contains(search));
        };
    }
}