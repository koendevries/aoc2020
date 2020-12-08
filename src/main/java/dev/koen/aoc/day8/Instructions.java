package dev.koen.aoc.day8;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class Instructions {

    private final Map<Integer, Instruction> instructions;

    Instructions(List<Instruction> instructions) {
        this.instructions = IntStream.range(0, instructions.size())
                .boxed()
                .collect(Collectors.toMap(Function.identity(), instructions::get));
    }

    Instructions(Map<Integer, Instruction> instructions) {
        this.instructions = instructions;
    }

    Instructions fixed() {
        return IntStream.range(0, instructions.size())
                .mapToObj(this::transform)
                .filter(Instructions::completes)
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    int accumulate() {
        return path().map(IndexedInstruction::instruction)
                .filter(Instruction::isAcc)
                .mapToInt(Instruction::amount)
                .sum();
    }

    boolean completes() {
        return path().anyMatch(i -> i.nextIndex() == instructions.size());
    }

    private Instructions transform(int index) {
        final var result = new HashMap<>(instructions);
        result.put(index, instructions.get(index).transform());
        return new Instructions(result);
    }

    private Stream<IndexedInstruction> path() {
        return path(0, new HashMap<>(instructions));
    }

    private Stream<IndexedInstruction> path(int startIndex, Map<Integer, Instruction> instructionsToSearch) {
        if (!instructionsToSearch.containsKey(startIndex)) {
            return Stream.empty();
        }
        final var instruction = instructionsToSearch.remove(startIndex);
        final var nextInstructions = path(instruction.nextIndex(startIndex), instructionsToSearch);

        return Stream.concat(Stream.of(new IndexedInstruction(startIndex, instruction)), nextInstructions);
    }
}
