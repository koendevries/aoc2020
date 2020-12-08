package dev.koen.aoc.day8;

record Instruction(String type, int amount) {

    Instruction transform() {
        return switch (type) {
            case "nop" -> new Instruction("jmp", amount);
            case "jmp" -> new Instruction("nop", amount);
            default -> this;
        };
    }

    int nextIndex(int index) {
        if ("jmp".equals(type)) {
            return index + amount;
        } else {
            return index + 1;
        }
    }
}
