package dev.koen.aoc.day5;

enum BinaryChar {
    F("0"), B("1"), L("0"), R("1");

    private final String value;

    BinaryChar(String value) {
        this.value = value;
    }

    static BinaryChar from(int charCode) {
        final var c = (char) charCode;
        return switch (c) {
            case 'F' -> F;
            case 'B' -> B;
            case 'L' -> L;
            case 'R' -> R;
            default -> throw new IllegalArgumentException("Unable to convert char " + c + " to BinaryChar");
        };
    }

    String value() {
        return value;
    }
}
