package dev.koen.aoc.day8;

record Instruction(String type, int amount) {

    boolean isAcc() {
        return "acc".equals(type);
    }

    boolean isJmp() {
        return "jmp".equals(type);
    }

    int nextIndex(int index) {
        if (isJmp()) {
            return index + amount;
        }
        return index + 1;
    }

    Instruction transform() {
        return switch (type) {
            case "nop" -> new Instruction("jmp", amount);
            case "jmp" -> new Instruction("nop", amount);
            default -> this;
        };
    }
}