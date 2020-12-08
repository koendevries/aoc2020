package dev.koen.aoc.day7;

import java.util.List;
import java.util.stream.Stream;

record Edges(List<Edge> edges) {

    Stream<Edge> all() {
        return edges.stream();
    }

    Stream<String> allSourceNamesOf(String destination) {
        return allDistinctSourcesOf(destination)
                .distinct()
                .map(Edge::source)
                .distinct();
    }

    Stream<Edge> allDestinationsOf(String source) {
        return withSource(source)
                .flatMap(e -> Stream.concat(Stream.of(e), allDestinationsOf(e.destination)));
    }

    private Stream<Edge> allDistinctSourcesOf(String destination) {
        return withDestination(destination)
                .distinct()
                .flatMap(e -> Stream.concat(Stream.of(e), allDistinctSourcesOf(e.source)));
    }

    private Stream<Edge> withDestination(String destination) {
        return all().filter(e -> destination.equals(e.destination()));
    }

    private Stream<Edge> withSource(String source) {
        return all().filter(e -> source.equals(e.source));
    }

    record Edge(String source, String destination) {
    }

}
