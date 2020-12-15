package dev.koen.aoc.day12;

import dev.koen.aoc.common.Coordinate;
import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Consumer;

public class Solution {

    public static void main(String[] args) {
        process(new World1(new Coordinate(0, 0), Direction.EAST));
        process(new World2(new Coordinate(0, 0), new Coordinate(10, 1)));
    }

    private static void process(World world) {
        FileReader.readLines(Path.of("src/test/resources/input-twelve.txt"))
                .stream()
                .forEachOrdered(processInstructions(world));


        System.out.println(world.manhattanDistance());
    }

    private static Consumer<String> processInstructions(World world) {
        return instruction -> {
            char action = instruction.charAt(0);
            final var value = Integer.parseInt(instruction.substring(1));

            switch (action) {
                case 'N' -> world.move(Direction.NORTH, value);
                case 'E' -> world.move(Direction.EAST, value);
                case 'S' -> world.move(Direction.SOUTH, value);
                case 'W' -> world.move(Direction.WEST, value);
                case 'L' -> world.turnLeft(value);
                case 'R' -> world.turnRight(value);
                case 'F' -> world.move(value);
                default -> throw new IllegalArgumentException("Invalid instruction to process");
            }
        };
    }
}


