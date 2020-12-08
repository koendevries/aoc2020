package dev.koen.aoc.day8;

import dev.koen.aoc.util.FileReader;
import dev.koen.aoc.util.TimeUtil;

import java.nio.file.Path;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        final var listOfInstructions = FileReader.readLines(Path.of("src/test/resources/input-eight.txt"))
                .stream()
                .map(Solution::readInstruction)
                .collect(Collectors.toList());

        final var instructions = new Instructions(listOfInstructions);

        TimeUtil.print(instructions, Instructions::accumulate);
        TimeUtil.print(instructions, i -> i.fixed().accumulate());
    }

    private static Instruction readInstruction(String instruction) {
        final var parts = instruction.split("\s");

        final var type = parts[0];
        final var amount = Integer.parseInt(parts[1]);

        return new Instruction(type, amount);
    }

}

