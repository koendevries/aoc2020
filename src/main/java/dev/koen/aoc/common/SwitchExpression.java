package dev.koen.aoc.common;

import java.util.Map;
import java.util.function.IntBinaryOperator;

public class SwitchExpression {

    public static void main(String[] args) {
        final var left = 3;
        final var right = 5;
        final var operator = "*";

        System.out.println(java11(operator, left, right));
        System.out.println(java17(operator, left, right));
    }

    private static int java17(String operator, int left, int right) {
        return switch (operator) {
            case "+" -> plus().applyAsInt(left, right);
            case "-" -> minus().applyAsInt(left, right);
            case "*" -> multiply().applyAsInt(left, right);
            case "/" -> divide().applyAsInt(left, right);
            default -> left;
        };
    }


    private static int java11(String operator, int left, int right) {
        return Map.of(
                        "+", plus(),
                        "-", minus(),
                        "*", multiply(),
                        "/", divide()
                )
                .getOrDefault(operator, (a, b) -> a)
                .applyAsInt(left, right);
    }

    private static IntBinaryOperator plus() {
        return (a, b) -> a + b;
    }

    private static IntBinaryOperator minus() {
        return (a, b) -> a - b;
    }

    private static IntBinaryOperator multiply() {
        return (a, b) -> a * b;
    }

    private static IntBinaryOperator divide() {
        return (a, b) -> a / b;
    }

}
