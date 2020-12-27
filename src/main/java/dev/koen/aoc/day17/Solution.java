package dev.koen.aoc.day17;

import dev.koen.aoc.util.FileReader;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Set;

import java.nio.file.Path;
import java.util.function.Function;


public class Solution {

    public static void main(String[] args) {
        final var initial3DCoordinates = List.ofAll(FileReader.readLines(Path.of("src/test/resources/input-seventeen.txt")))
                .zipWithIndex()
                .flatMap(readLine3D())
                .toMap(c -> c._1, c -> c._2);

        final var transformed3D = new Cube3D(initial3DCoordinates).next(6);
        System.out.println(transformed3D.active());

        final var initial4DCoordinates = List.ofAll(FileReader.readLines(Path.of("src/test/resources/input-seventeen.txt")))
                .zipWithIndex()
                .flatMap(readLine4D())
                .toMap(c -> c._1, c -> c._2);

        final var transformed4D = new Cube4D(initial4DCoordinates).next(6);
        System.out.println(transformed4D.active());
    }


    private static Function<Tuple2<String, Integer>, Set<Tuple2<Coordinate3D, Boolean>>> readLine3D() {
        return lineAndY -> {
            final var y = lineAndY._2;
            return List.ofAll(lineAndY._1.toCharArray())
                    .zipWithIndex()
                    .map(ci -> new Tuple2<>(new Coordinate3D(ci._2, y, 0), readActive(ci._1)))
                    .toSet();
        };
    }

    private static Function<Tuple2<String, Integer>, Set<Tuple2<Coordinate4D, Boolean>>> readLine4D() {
        return lineAndY -> {
            final var y = lineAndY._2;
            return List.ofAll(lineAndY._1.toCharArray())
                    .zipWithIndex()
                    .map(ci -> new Tuple2<>(new Coordinate4D(ci._2, y, 0, 0), readActive(ci._1)))
                    .toSet();
        };
    }

    private static Boolean readActive(char character) {
        return character == '#';
    }
}

