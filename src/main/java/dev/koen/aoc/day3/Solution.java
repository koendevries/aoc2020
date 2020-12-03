package dev.koen.aoc.day3;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Stream;

public class Solution {

    private static final char TREE = '#';

    public static void main(String[] args) {
        final var lines = FileReader.readLines(Path.of("src/test/resources/input-three.txt"));
        final var map = new InfiniteWidthCharMap(lines);

        solve1(map);
        solve2(map);
    }

    private static void solve1(InfiniteWidthCharMap map) {
        final var numberOfTrees = countTreesOnSlope(map)
                .apply(new Slope(3, 1));

        System.out.println("Result one: " + numberOfTrees);
    }

    private static void solve2(InfiniteWidthCharMap map) {
        final var numberOfTrees = Stream.of(
                new Slope(1, 1),
                new Slope(3, 1),
                new Slope(5, 1),
                new Slope(7, 1),
                new Slope(1, 2)
        )
                .map(countTreesOnSlope(map))
                .mapToLong(Long::longValue)
                .reduce(1L, Math::multiplyExact);

        System.out.println("Result two: " + numberOfTrees);
    }

    private static Function<Slope, Long> countTreesOnSlope(InfiniteWidthCharMap map) {
        return slope -> map.walkDown(slope)
                .stream()
                .filter(c -> c == TREE)
                .count();
    }
}
