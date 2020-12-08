package dev.koen.aoc.day8;

import dev.koen.aoc.common.IntPair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

record Instructions(List<Instruction> instructions) {
    int accumulated() {
        return indicesRun().stream()
                .map(instructions::get)
                .filter(instruction -> "acc".equals(instruction.type()))
                .mapToInt(Instruction::amount)
                .sum();
    }

    Instructions fixed() {
        return transformations()
                .filter(Instructions::completes)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private Stream<Instructions> transformations() {
        return IntStream.range(0, instructions.size())
                .mapToObj(this::transform);
    }

    private Instructions transform(int index) {
        final var result = new ArrayList<>(instructions);

        final var transformedInstruction = result.get(index).transform();
        result.remove(index);
        result.add(index, transformedInstruction);

        return new Instructions(result);
    }

    private Stream<IntPair> indices() {
        return IntStream.range(0, instructions.size())
                .mapToObj(index -> new IntPair(index, instructions.get(index).nextIndex(index)));
    }

    private List<Integer> indicesRun() {
        final var executedIndices = new ArrayList<Integer>();
        var nextIndex = 0;

        do {
            final var instruction = instructions.get(nextIndex);
            executedIndices.add(nextIndex);
            nextIndex = instruction.nextIndex(nextIndex);
        } while (!executedIndices.contains(nextIndex) && nextIndex < instructions.size());

        return executedIndices;
    }

    private boolean completes() {
        final var indices = indicesRun();

        final var lastIndex = indices.get(indices.size() - 1);
        final var lastInstruction = instructions.get(lastIndex);

        return lastInstruction.nextIndex(lastIndex) == instructions.size();
    }

}
