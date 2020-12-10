package dev.koen.aoc.day7;

import java.util.List;
import java.util.stream.Stream;

record DirectedGraph(List<Edge> edges) {

    Stream<String> allSourceNamesOf(String destination) {
        return allSourcesOf(destination)
                .map(Edge::source)
                .distinct();
    }

    int numberOfBagsInside(String source) {
        return withSource(source)
                .mapToInt(e -> e.distance + e.distance * numberOfBagsInside(e.destination))
                .sum();
    }

    private Stream<Edge> all() {
        return edges.stream();
    }

    private Stream<Edge> allSourcesOf(String destination) {
        return withDestination(destination)
                .flatMap(e -> Stream.concat(Stream.of(e), allSourcesOf(e.source)))
                .distinct();
    }

    private Stream<Edge> withDestination(String destination) {
        return all().filter(e -> destination.equals(e.destination()));
    }

    private Stream<Edge> withSource(String source) {
        return all().filter(e -> source.equals(e.source));
    }

    record Edge(String source, String destination, int distance) {
    }

}
