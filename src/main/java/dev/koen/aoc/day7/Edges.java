package dev.koen.aoc.day7;

import java.util.List;
import java.util.stream.Stream;

record Edges(List<Edge> edges) {

    Stream<Edge> all() {
        return edges.stream();
    }

    Stream<String> distinct() {
        return all()
                .flatMap(e -> Stream.of(e.source, e.destination))
                .distinct();
    }

    Stream<Edge> withSource(String source) {
        return all().filter(e -> source.equals(e.source()));
    }

    Stream<Edge> allSources(String destination) {
        return withDestination(destination)
                .flatMap(e -> Stream.concat(withDestination(destination), Stream.of(e)));
    }

    Stream<String> sourceless() {
        return all()
                .filter(e -> withDestination(e.source).count() == 0)
                .map(Edge::source);
    }

    Stream<Edge> withDestination(String destination) {
        return all().filter(e -> destination.equals(e.destination()));
    }

    Stream<String> destinationless() {
        return all()
                .filter(e -> withSource(e.destination).count() == 0)
                .map(Edge::destination);
    }

    long count(Edge edge) {
        return all()
                .filter(edge::equals)
                .count();
    }

    record Edge(String source, String destination) {
    }

}
