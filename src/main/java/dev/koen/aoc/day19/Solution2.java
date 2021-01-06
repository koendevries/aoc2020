package dev.koen.aoc.day19;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution2 {

    public static final String INPUT_PATH = "src/test/resources/input-nineteen-part2.txt";

    public static void main(String[] args) {
        final var lines = readLines();
        final var rules = readRules(lines);
        final var messages = readMessages(lines);

        System.out.println(validMessages(rules, messages).size());
    }

    private static List<String> readLines() {
        return FileReader.readLines(Path.of(INPUT_PATH))
                .stream()
                .map(String::strip)
                .collect(Collectors.toList());
    }

    private static List<String> validMessages(Map<String, String> rules, List<String> messages) {
        return messages.stream()
                .filter(m -> m.matches(rules.get("0").replace(" ", "")))
                .collect(Collectors.toList());
    }

    private static String unpack(String s, Map<String, String> rules, int depth) {
        if (depth > 12) {
            return "42 31";
        }

        return Arrays.stream(s.split(" "))
                .map(replaceNumbers(rules, depth))
                .collect(Collectors.joining())
                .replace("\"", "");
    }

    private static Function<String, String> replaceNumbers(Map<String, String> rules, int depth) {
        return str -> str.matches("\\d+")
                ? "(" + unpack(rules.get(str), rules, depth + 1) + ")"
                : str;
    }

    private static Map<String, String> readRules(List<String> lines) {
        final var rules = lines.stream()
                .map(s -> s.split(": "))
                .filter(s -> s.length == 2)
                .collect(HashMap::new,
                        (Map<String, String> map, String[] array) -> map.put(array[0], array[1]),
                        Map::putAll
                );

        for (Map.Entry<String, String> rule : rules.entrySet()) {
            rule.setValue(unpack(rule.getValue(), rules, 0));
        }

        return rules;
    }

    private static List<String> readMessages(List<String> lines) {
        return lines.stream()
                .filter(s -> !s.contains(": "))
                .filter(s -> !s.isBlank())
                .collect(Collectors.toList());
    }
}
