package dev.koen.aoc.day8;

record IndexedInstruction(int index, Instruction instruction) {

    int nextIndex() {
        return instruction.nextIndex(index);
    }

}
