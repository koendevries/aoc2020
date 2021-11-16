package dev.koen.aoc.day20;

import dev.koen.aoc.util.FileReader;
import io.vavr.Function1;
import io.vavr.collection.List;
import io.vavr.collection.Stream;

import java.nio.file.Path;
import java.util.function.Predicate;

public class Solution {

    public static void main(String[] args) {
        final var tiles = readTileIdBorderReversedBorder();

        System.out.println(findCorners(tiles)
                .map(Tile::id)
                .map(Integer::longValue)
                .reduce(Math::multiplyExact));
    }

    private static List<Tile> findCorners(List<Tile> tiles) {
        return tiles.filter(tile -> findNeighbours(tiles).apply(tile).size() == 2);
    }


    private static Function1<Tile, List<Tile>> findNeighbours(List<Tile> all) {
        return tile -> all.filter(t -> t.id() != tile.id())
                .filter(isNeighbour(tile));
    }

    private static Predicate<Tile> isNeighbour(Tile other) {
        return tile -> tile.borders().exists(matchingBorder(other));
    }

    private static Predicate<? super String> matchingBorder(Tile other) {
        return border -> other.borders()
                .appendAll(other.reversedBorders())
                .contains(border);
    }


    private static List<Tile> readTileIdBorderReversedBorder() {
        final var tiles = FileReader.read(Path.of("src/test/resources/input-twenty.txt"))
                .split("\n\n");

        return Stream.of(tiles)
                .map(Solution::readTile)
                .toList();
    }

    private static Tile readTile(String tile) {
        final var splitted = tile.split("\n");
        final var id = Integer.parseInt(splitted[0].split(" ")[1]
                .split(":")[0]);


        final var top = new StringBuilder();
        final var bottom = new StringBuilder();
        final var left = new StringBuilder();
        final var right = new StringBuilder();
        for (int i = 1; i < splitted.length; i++) {
            top.append(splitted[1].charAt(i - 1));
            right.append(splitted[i].charAt(9));
            bottom.append(splitted[10].charAt(10 - i));
            left.append(splitted[11 - i].charAt(0));
        }

        return new Tile(
                id,
                tile.split(":\n")[1],
                List.of(
                        top.toString(),
                        right.toString(),
                        bottom.toString(),
                        left.toString()),
                List.of(
                        top.reverse().toString(),
                        right.reverse().toString(),
                        bottom.reverse().toString(),
                        left.reverse().toString()
                )
        );
    }


}

record Tile(
        int id,
        String value,
        List<String> borders,
        List<String> reversedBorders
) {
}