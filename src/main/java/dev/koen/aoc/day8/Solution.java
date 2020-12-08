package dev.koen.aoc.day8;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        final var instructionList = FileReader.readLines(Path.of("src/test/resources/input-eight.txt"))
                .stream()
                .map(asInstruction())
                .collect(Collectors.toList());

        final var instructions = new Instructions(instructionList);
        System.out.println(instructions.accumulated());
        System.out.println(instructions.fixed().accumulated());
    }

    private static Function<String, Instruction> asInstruction() {
        return string -> {
            final var parts = string.split("\s");

            final var type = parts[0];
            final var amount = Integer.parseInt(parts[1]);

            return new Instruction(type, amount);
        };
    }

}

