package dev.koen.aoc.day3;

import dev.koen.aoc.common.Coordinate;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public class Solution {

    public static final Coordinate START = new Coordinate(0, 0);
    private static final char TREE = '#';

    public static void main(String[] args) {
        final var lines = FileReader.readLines(Path.of("src/test/resources/input-three.txt"));
        final var map = new InfiniteWidthCharMap(lines);

        solve1().andThen(solve2()).apply(map);
    }

    private static Function<InfiniteWidthCharMap, InfiniteWidthCharMap> solve1() {
        return map -> {
            final var numberOfTrees = countTreesOnSlope(map)
                    .apply(new Slope(3, 1));

            System.out.println("Result one: " + numberOfTrees);

            return map;
        };
    }

    private static Function<InfiniteWidthCharMap, InfiniteWidthCharMap> solve2() {
        return map -> {
            final var numberOfTrees = Stream.of(
                    new Slope(1, 1),
                    new Slope(3, 1),
                    new Slope(5, 1),
                    new Slope(7, 1),
                    new Slope(1, 2)
            )
                    .map(countTreesOnSlope(map))
                    .mapToLong(Long::longValue)
                    .reduce(1, Math::multiplyExact);

            System.out.println("Result two: " + numberOfTrees);
            return map;
        };
    }

    private static Function<Slope, Long> countTreesOnSlope(InfiniteWidthCharMap map) {
        return slope -> map.walkDown(START, slope)
                .stream()
                .filter(c -> c == TREE)
                .count();
    }
}
