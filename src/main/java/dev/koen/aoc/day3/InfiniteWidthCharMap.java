package dev.koen.aoc.day3;

import dev.koen.aoc.common.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class InfiniteWidthCharMap {

    private final char[][] charMap;

    public InfiniteWidthCharMap(List<String> rows) {
        final var height = rows.size();
        final var width = rows.get(0).length();
        charMap = new char[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                charMap[y][x] = rows.get(y).charAt(x);
            }
        }
    }

    public char getChar(Coordinate coordinate) {
        final var x = coordinate.x() % charMap[0].length;
        final var y = coordinate.y();
        return charMap[y][x];
    }

    public int height() {
        return charMap.length;
    }

    public List<Character> walkDown(Coordinate start, Slope slope) {
        final var charactersWalkedOn = new ArrayList<Character>();

        var currentCoordinate = start;
        do {
            final var currentChar = getChar(currentCoordinate);
            charactersWalkedOn.add(currentChar);
            currentCoordinate = currentCoordinate.translate(slope);

        } while (currentCoordinate.y() < height());

        return charactersWalkedOn;
    }
}
