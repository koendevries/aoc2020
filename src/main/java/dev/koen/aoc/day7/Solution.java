package dev.koen.aoc.day7;

import dev.koen.aoc.day7.DirectedGraph.Edge;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {

    public static final String SHINY_GOLD = "shiny gold";

    public static void main(String[] args) {
        final var edges = FileReader.readLines(Path.of("src/test/resources/input-seven.txt"))
                .stream()
                .map(s -> s.replace('.', ' '))
                .filter(s -> !s.strip().isEmpty())
                .flatMap(readEdge())
                .collect(Collectors.toList());

        final var graph = new DirectedGraph(edges);

        System.out.println(graph.allSourceNamesOf(SHINY_GOLD).count());
        System.out.println(graph.numberOfBagsInside(SHINY_GOLD));
    }

    private static Function<String, Stream<Edge>> readEdge() {
        return rule -> {
            final var fromAndTo = rule.split("contain");
            final var from = readNode(0).apply(fromAndTo[0].split("\s"));

            return Arrays.stream(fromAndTo[1].split(","))
                    .map(String::strip)
                    .filter(s -> !s.startsWith("no other"))
                    .map(readEdge(from));
        };
    }

    private static Function<String[], String> readNode(int startIndex) {
        return fullNode -> {
            final var range = Arrays.copyOfRange(fullNode, startIndex, startIndex + 2);

            return Arrays.stream(range)
                    .map(String::strip)
                    .collect(Collectors.joining(" "));
        };
    }

    private static Function<String, Edge> readEdge(String from) {
        return fullDestination -> {
            final var splitted = fullDestination.split("\s");

            final var distance = Integer.parseUnsignedInt(splitted[0]);
            final var destination = readNode(1).apply(splitted);

            return new Edge(from, destination, distance);
        };
    }
}


