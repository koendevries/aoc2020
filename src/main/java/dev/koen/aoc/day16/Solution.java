package dev.koen.aoc.day16;

import dev.koen.aoc.common.Range;
import dev.koen.aoc.util.FileReader;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;

import java.nio.file.Path;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Solution {

    public static void main(String[] args) {
        final var input = FileReader.read(Path.of("src/test/resources/input-sixteen.txt"));


        final var ranges = readRanges().apply(input.split("\nyour ticket:")[0]);
        final var nearbyTickets = readNearbyTickets().apply(input.split("nearby tickets:\n")[1]);
        final var validTickets = validTickets(ranges).apply(nearbyTickets);
        final var yourTicket = Stream.of(input.split("your ticket:\n")[1].split("nearby tickets:\n")[0])
                .map(String::strip)
                .filterNot(String::isBlank)
                .flatMap(s -> Stream.of(s.split(",")))
                .map(Integer::parseUnsignedInt);

        final var possibleIndicesPerField = ranges.map(indexesInRange(validTickets));
        final var indexByFieldName = findFieldIndices(HashMap.empty()).apply(possibleIndicesPerField);

        final var result = indexByFieldName.filter((name, index) -> name.startsWith("departure"))
                .map(Tuple2::_2)
                .map(yourTicket::get)
                .map(Integer::longValue)
                .reduce((a, b) -> a * b);

        System.out.println(result);


    }

    private static Function<Map<String, Set<Integer>>, Map<String, Integer>> findFieldIndices(Map<String, Integer> result) {
        return possibleIndicesPerField -> {
            if (possibleIndicesPerField.isEmpty()) {
                return result;
            }

            final var indexByName = possibleIndicesPerField
                    .filterNot((name, indices) -> result.keySet().contains(name))
                    .map((name, indices) -> Tuple.of(name, indices.filterNot(i -> result.values().contains(i))))
                    .find(t -> t._2.length() == +1)
                    .get()
                    .map((name, indices) -> Tuple.of(name, indices.filterNot(i -> result.get(name).contains(i)).get()));

            return findFieldIndices(result.put(indexByName)).apply(possibleIndicesPerField.remove(indexByName._1));
        };
    }

    private static BiFunction<String, Tuple2<Range, Range>, Tuple2<String, Set<Integer>>> indexesInRange(List<List<Integer>> validTickets) {
        return (name, ranges) -> {
            final var validColumnIndices = Stream.range(0, validTickets.get(0).length())
                    .map(index -> Tuple.of(index, validTickets.map(row -> row.get(index))))
                    .filter(columnContains(ranges))
                    .map(Tuple2::_1)
                    .toSet();

            return Tuple.of(name, validColumnIndices);
        };
    }

    private static Predicate<Tuple2<Integer, List<Integer>>> columnContains(Tuple2<Range, Range> ranges) {
        return columnByIndex -> columnByIndex._2
                .forAll(number -> ranges._1.contains(number) || ranges._2.contains(number));
    }

    private static Function<List<List<Integer>>, List<List<Integer>>> validTickets(Map<String, Tuple2<Range, Range>> ranges) {
        return tickets -> {
            final var validNumbers = validNumbers().apply(ranges);

            return tickets.filter(validNumbers::containsAll);
        };
    }

    private static Function<Map<String, Tuple2<Range, Range>>, Set<Integer>> validNumbers() {
        return ranges -> ranges.values()
                .flatMap(r -> Stream.of(r._1, r._2))
                .flatMap(r -> Stream.rangeClosed(r.min(), r.max()))
                .map(Long::intValue)
                .toSet();
    }

    private static Function<String, List<List<Integer>>> readNearbyTickets() {
        return tickets -> List.of(tickets.split("\n"))
                .filter(row -> !row.isBlank())
                .map(readTicket());
    }

    private static Function<String, List<Integer>> readTicket() {
        return row -> List.of(row.split(","))
                .map(Integer::parseUnsignedInt);
    }

    private static Function<String, Map<String, Tuple2<Range, Range>>> readRanges() {
        return ranges -> List.of(ranges.split("\n"))
                .filter(s -> !s.isBlank())
                .toMap(readRangeName(), readRangeValues());
    }

    private static Function<String, String> readRangeName() {
        return row -> row.split(":")[0];
    }

    private static Function<String, Tuple2<Range, Range>> readRangeValues() {
        return row -> {
            final var ranges = List.of(row.split("\s+"))
                    .filter(s -> s.contains("-"))
                    .map(readRange());

            return Tuple.of(ranges.get(0), ranges.get(1));
        };
    }

    private static Function<? super String, Range> readRange() {
        return range -> {
            final var splitted = range.split("-");

            return new Range(Integer.parseUnsignedInt(splitted[0]), Integer.parseUnsignedInt(splitted[1]));
        };
    }
}