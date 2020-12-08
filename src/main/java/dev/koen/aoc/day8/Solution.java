package dev.koen.aoc.day8;

import dev.koen.aoc.util.FileReader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) {
        final var instructions = FileReader.readLines(Path.of("src/test/resources/input-eight.txt"))
                .stream()
                .map(asInstruction())
                .collect(Collectors.toList());

        final var indicesToRunOnce = findIndicesToRunOnce(instructions);
        System.out.println(accumulated(instructions, indicesToRunOnce));

        final var fixedInstructions = findFixedInstructions(instructions);
        System.out.println(accumulated(fixedInstructions, findIndicesToRunOnce(fixedInstructions)));
    }

    private static Function<String, Instruction> asInstruction() {
        return string -> {
            final var parts = string.split("\s");
            final var type = parts[0];
            final var amount = Integer.parseInt(parts[1]);

            return new Instruction(type, amount);
        };
    }

    private static List<Instruction> findFixedInstructions(List<Instruction> instructions) {
        var index = 0;
        var currentInstructions = new ArrayList<Instruction>();
        do {
            final var currentInstruction = instructions.get(index);
            final var transformedInstruction = currentInstruction.transform();

            currentInstructions = new ArrayList<>(instructions);
            currentInstructions.remove(index);
            currentInstructions.add(index, transformedInstruction);
            index += 1;
        } while (!completes(currentInstructions));

        return currentInstructions;
    }

    private static boolean completes(List<Instruction> instructions) {
        final var indices = findIndicesToRunOnce(instructions);
        final var lastIndex = indices.get(indices.size() - 1);
        final var lastInstruction = instructions.get(lastIndex);

        return switch (lastInstruction.type()) {
            case "jmp" -> lastIndex + lastInstruction.amount() == instructions.size();
            default -> lastIndex + 1 == instructions.size();
        };
    }

    private static List<Integer> findIndicesToRunOnce(List<Instruction> instructions) {
        final var executedIndices = new ArrayList<Integer>();

        var nextIndex = 0;
        do {
            final var instruction = instructions.get(nextIndex);
            executedIndices.add(nextIndex);
            if ("jmp".equals(instruction.type())) {
                nextIndex += instruction.amount();
            } else if ("acc".equals(instruction.type())) {
                nextIndex += 1;
            } else {
                nextIndex += 1;
            }
        } while (!executedIndices.contains(nextIndex) && nextIndex < instructions.size());

        return executedIndices;
    }

    private static int accumulated(List<Instruction> instructions, List<Integer> indices) {
        return indices.stream()
                .map(index -> instructions.get(index))
                .filter(instruction -> "acc".equals(instruction.type()))
                .mapToInt(Instruction::amount)
                .sum();
    }
}

record Instruction(String type, int amount) {

    Instruction transform() {
        return switch (type) {
            case "nop" -> new Instruction("jmp", amount);
            case "jmp" -> new Instruction("nop", amount);
            default -> this;
        };
    }
}
