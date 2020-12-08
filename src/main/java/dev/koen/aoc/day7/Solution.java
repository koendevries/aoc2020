package dev.koen.aoc.day7;

import dev.koen.aoc.day7.Edges.Edge;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Solution {

    public static final String SHINY_GOLD = "shiny gold";

    public static void main(String[] args) {
        final var listOfEdges = FileReader.readLines(Path.of("src/test/resources/input-seven.txt"))
                .stream()
                .map(s -> s.replace('.', ' '))
                .filter(s -> !s.strip().isEmpty())
                .flatMap(readEdges())
                .collect(Collectors.toList());
        final var edges = new Edges(listOfEdges);

        System.out.println(edges.allSourceNamesOf(SHINY_GOLD).count());
        System.out.println(edges.allDestinationsOf(SHINY_GOLD).count());
    }

    private static Function<String, Stream<Edge>> readEdges() {
        return rule -> {
            final var fromAndTo = rule.split("contain");
            final var from = readNode(0).apply(fromAndTo[0].split("\s"));

            return Arrays.stream(fromAndTo[1].split(","))
                    .map(String::strip)
                    .filter(s -> !s.startsWith("no other"))
                    .flatMap(readEdges(from));
        };
    }

    private static Function<String[], String> readNode(int startIndex) {
        return strings -> {
            final var range = Arrays.copyOfRange(strings, startIndex, startIndex + 2);

            return Arrays.stream(range)
                    .map(String::strip)
                    .collect(Collectors.joining(" "));
        };
    }

    private static Function<String, Stream<Edge>> readEdges(String from) {
        return to -> {
            final var splitted = to.split("\s");

            final var amount = Integer.parseUnsignedInt(splitted[0]);
            final var color = readNode(1).apply(splitted);

            return IntStream.range(0, amount)
                    .mapToObj(i -> new Edge(from, color));
        };
    }
}


